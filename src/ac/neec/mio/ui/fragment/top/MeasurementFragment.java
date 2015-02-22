package ac.neec.mio.ui.fragment.top;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sek.drumpicker.DrumPicker;

import ac.neec.mio.R;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.pref.DevicePreferenceManager;
import ac.neec.mio.pref.UtilPreference;
import ac.neec.mio.training.category.TrainingCategory;
import ac.neec.mio.training.menu.TrainingMenu;
import ac.neec.mio.ui.activity.DeviceSettingActivity;
import ac.neec.mio.ui.activity.MeasurementActivity;
import ac.neec.mio.ui.dialog.TrainingSelectDialog;
import ac.neec.mio.ui.dialog.TrainingSelectedDialog;
import ac.neec.mio.ui.listener.TrainingSelectCallbackListener;
import ac.neec.mio.util.DateUtil;
import ac.neec.mio.consts.SQLConstants;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class MeasurementFragment extends TopBaseFragment implements
		TrainingSelectCallbackListener {
	public static final String TITLE = "トレーニング";
	private static final int DIALOG_NO = 0;
	private static final int DIALOG_CATEGORY = 1;
	private static final int DIALOG_MENU = 2;

	private View view;
	private int spinnerPosition = 0;
	private Button buttonMeasurement;

	private TextView textCategory;
	private TextView textMenu;

	// ダイアログ文字列
	private String[] strings;
	private int dialogState;
	private int categoryId = 1;
	private int menuId = 1;
	private SQLiteDao dao;
	private List<TrainingCategory> categorys = new ArrayList<TrainingCategory>();
	private List<TrainingMenu> menus = new ArrayList<TrainingMenu>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_measurement, null);
		dao = DaoFacade.getSQLiteDao();
		init();
		setSpinnerAdapter();
		return view;
	}

	private void init() {
		buttonMeasurement = (Button) view.findViewById(R.id.btn_measurement);
		buttonMeasurement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UtilPreference.putCategoryPicker(0);
				UtilPreference.putMenuPicker(0);
				checkBleSettingDevice();
			}
		});
		textCategory = (TextView) view.findViewById(R.id.txt_training_category);
		textMenu = (TextView) view.findViewById(R.id.txt_training_menu);
		categorys = dao.selectTrainingCategory();
		TrainingCategory category = categorys.get(UtilPreference
				.getCategoryPicker() + 1);
		categoryId = category.getTrainingCategoryId();
		Log.d("fragment", "category " + category.getTrainingCategoryName());
		dao.selectTrainingCategoryMenu(categoryId);
		Log.d("fragment", "categoryId " + categoryId);
		menus = dao.selectTrainingCategoryMenu(categoryId);
		TrainingMenu menu;
		if (menus.size() != 0) {
			menu = menus.get(UtilPreference.getMenuPicker());
			textMenu.setText(menu.getTrainingName());
		}

		// TrainingMenu menu = dao.selectTrainingMenu().get(
		// UtilPreference.getMenuPicker());
		textCategory.setText(category.getTrainingCategoryName());
		textCategory.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialogState = DIALOG_CATEGORY;
				showCategorySelectDialog();
			}
		});
		textMenu.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// if (!textMenu.getText().toString().equals("")) {
				if (!TextUtils.isEmpty(textMenu.getText())) {
					dialogState = DIALOG_MENU;
					showCategorySelectDialog();
				}
			}
		});
	}

	private void checkBleSettingDevice() {
		if (DevicePreferenceManager.getDeviceAddress() == null) {
			intentDeviceSetting();
		} else {
			intentMeasurement();
		}
	}

	private void intentDeviceSetting() {
		Intent intent = new Intent(getActivity(), DeviceSettingActivity.class);
		intent.putExtra(DeviceSettingActivity.FLAG,
				DeviceSettingActivity.INTENT_MEASUREMENT);
		startActivity(intent);
	}

	private void setDialogStrings() {
		if (dialogState == DIALOG_CATEGORY) {
			categorys = dao.selectTrainingCategory();
			strings = new String[categorys.size()];
			int i = 0;
			for (TrainingCategory category : categorys) {
				strings[i] = category.getTrainingCategoryName();
				i++;
			}
		} else if (dialogState == DIALOG_MENU) {
			menus = dao.selectTrainingCategoryMenu(categoryId);
			strings = new String[menus.size()];
			int i = 0;
			for (TrainingMenu menu : menus) {
				strings[i] = menu.getTrainingName();
				i++;
			}

		}
	}

	private void showCategorySelectDialog() {
		setDialogStrings();
		if (dialogState == DIALOG_CATEGORY) {
			new TrainingSelectedDialog(getActivity().getApplicationContext(),
					this, strings, UtilPreference.getCategoryPicker()).show(
					getActivity().getFragmentManager(), "dialog");
		} else if (dialogState == DIALOG_MENU) {
			new TrainingSelectedDialog(getActivity().getApplicationContext(),
					this, strings, UtilPreference.getMenuPicker()).show(
					getActivity().getFragmentManager(), "dialog");
		}
	}

	private void showCategoryPicker() {
		// List<TrainingCategory> list = DBManager.selectTrainingCategory();
		List<TrainingCategory> list = dao.selectTrainingCategory();
		// String[] row = (String[]) list.toArray(new String[list.size()]);
		strings = new String[list.size()];
		int i = 0;
		for (TrainingCategory category : list) {
			strings[i] = category.getTrainingCategoryName();
			i++;
		}
	}

	private void setSpinnerAdapter() {
		ArrayList<String> list = new ArrayList<String>();
		for (TrainingCategory category : dao.selectTrainingCategory()) {
			list.add(category.getTrainingCategoryName());
		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity()
				.getApplicationContext(),
				R.layout.spinner_training_category_item, list);
		adapter.setDropDownViewResource(R.layout.spinner_training_category_drop_down_item);
	}

	private void intentMeasurement() {
		Intent intent = new Intent(getActivity(), MeasurementActivity.class);
		if (textMenu.getText().toString().equals("")) {
			return;
		}
		TrainingMenu menu = dao.selectTrainingMenu((String) textMenu.getText());
		intent.putExtra(SQLConstants.trainingCategoryId(), categoryId);
		intent.putExtra(SQLConstants.trainingMenuId(), menu.getTrainingMenuId());
		startActivity(intent);
		getActivity().finish();
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public void onSelected(int index) {
		// Log.d("fragment", "index " + index);
		// Log.d("fragment", "category "
		// + dao.selectTrainingCategory(index).getTrainingCategoryName());
		if (dialogState == DIALOG_CATEGORY) {
			UtilPreference.putCategoryPicker(index + 1);
			UtilPreference.putMenuPicker(0);
			Log.d("activity", "category index " + index);
		} else if (dialogState == DIALOG_MENU) {
			UtilPreference.putMenuPicker(index + 1);
			Log.d("activity", "menu index " + index);
		}
	}

	@Override
	public void onSelected(String element) {
		if (dialogState == DIALOG_CATEGORY) {
			// List<String> elements = Arrays.asList(strings);
			// int index = elements.indexOf(element);
			int index = DateUtil.dateIndex(strings, element);
			TrainingCategory category = dao.selectTrainingCategory().get(index);
			// TrainingCategory category = dao.selectTrainingCategory(element);
			categoryId = category.getTrainingCategoryId();
			menuId = 1;
			List<TrainingMenu> list = new ArrayList<TrainingMenu>();
			// list = DBManager.selectTrainingCategoryMenu(categoryId);
			list = dao.selectTrainingCategoryMenu(categoryId);
			textCategory.setText(strings[index]);
			if (list.size() != 0) {
				textMenu.setText(list.get(0).getTrainingName());
			} else {
				textMenu.setText("");
			}
		} else if (dialogState == DIALOG_MENU) {
			// List<String> elements = Arrays.asList(strings);
			// int index = elements.indexOf(element);
			int index = DateUtil.dateIndex(strings, element);
			textMenu.setText(strings[index]);
			menuId = index + 1;
		}
	}
}
