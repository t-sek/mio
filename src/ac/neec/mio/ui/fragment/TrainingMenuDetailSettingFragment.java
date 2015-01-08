//package ac.neec.mio.ui.fragment;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ac.neec.mio.R;
//import ac.neec.mio.db.DBManager;
//import ac.neec.mio.taining.menu.TrainingMenu;
//import ac.neec.mio.ui.adapter.SettingListAdapter;
//import ac.neec.mio.ui.adapter.SettingListItem;
//import ac.neec.mio.ui.dialog.TrainingMenuDetailSettingDialog;
//import ac.neec.mio.ui.dialog.TrainingMenuDetailTimeSettingDialog;
//import ac.neec.mio.ui.listener.TrainingSettingCallbackListener;
//import ac.neec.mio.user.User;
//import ac.neec.mio.util.CalorieUtil;
//import ac.neec.mio.util.HeartRateUtil;
//import ac.neec.mio.util.TextViewUtil;
//import ac.neec.mio.util.TimeUtil;
//import android.app.DialogFragment;
//import android.app.Fragment;
//import android.content.Context;
//import android.os.Bundle;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//
//public class TrainingMenuDetailSettingFragment extends Fragment implements
//		TrainingSettingCallbackListener {
//
//	private static final String FRAGMENT_SETTING_DIALOG = "FRAGMENT_SETTING_DIALOG";
//	private static final String LIST_STRONG = "運動強度";
//	private static final String LIST_STRONG_UNIT = "%";
//	private static final String LIST_TIME = "時間";
//	private static final String LIST_TIME_UNIT = " ";
//	private static final String LIST_CALORIE = "目標消費カロリー";
//	private static final String LIST_CALORIE_UNIT = "kcal";
//	private static final String LIST_DATA_INIT = "---";
//
//	private View view;
//	private Context context;
//
//	Fragment fragment;
//
//	private Button buttonStartMeasurement;
//	private int selectedId;
//	private TrainingMenu trainingData;
//	private TextView textTrainingName;
//	private TextView textMets;
//	private TextView textTargetHeartRate;
//	private TextView textCalorie;
//	private TextView textSettingData;
//	private ListView listSetting;
//	private ArrayAdapter<SettingListItem> listAdapter;
//
//	private TrainingSettingCallbackListener listener;
//	private TrainingMenu trainingMenu;
//	private String strongData = LIST_DATA_INIT;
//	private String timeData = LIST_DATA_INIT;
//	private String calorieData = LIST_DATA_INIT;
//	private SettingListItem[] settingList = new SettingListItem[3];
//
//	private String[] hour = new String[11];
//	private String[] min = new String[60];
//	private String[] strong = { "100", "90", "80", "70", "60", "50", "40" };
//	private String[] calorie = new String[100];
//
//	private float hourSelected;
//	private float minSelected;
//	private int targetHeartRate;
//
//	public TrainingMenuDetailSettingFragment(
//			TrainingSettingCallbackListener listener) {
//		this.listener = listener;
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container,
//			Bundle savedInstanceState) {
//		view = inflater.inflate(R.layout.fragment_training_menu_detail_setting,
//				container, false);
//		context = inflater.getContext();
//		selectedId = getArguments().getInt("id");
//		initFindViews();
//		setListeners();
//		// setSettingList();
//		setTrainingData();
//
//		return view;
//	}
//
//	private void initFindViews() {
//		buttonStartMeasurement = (Button) view
//				.findViewById(R.id.btn_measurement_start);
//		textTrainingName = (TextView) view.findViewById(R.id.txt_training_name);
//		textMets = (TextView) view.findViewById(R.id.txt_detail_mets);
//		textTargetHeartRate = (TextView) view
//				.findViewById(R.id.txt_detail_heart_rate);
//		textCalorie = (TextView) view.findViewById(R.id.txt_detail_calorie);
//		listSetting = (ListView) view.findViewById(R.id.list_setting);
//	}
//
//	private void setListeners() {
//		buttonStartMeasurement.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (strongData == LIST_DATA_INIT) {
//					strongData = "0";
//				}
//				// listener.onMeasurement(DBManager.insertTraining(selectedId,
//				// 0,
//				// selectedId, null, null, "0", targetHeartRate, 0, 0, 0,
//				// Integer
//				// .valueOf(strongData).intValue(), 0));
//			}
//		});
//	}
//
//	// private void setSettingList() {
//	// settingList.add(new SettingListItem(LIST_STRONG, strongData,
//	// LIST_STRONG_UNIT);
//	// settingList
//	// .add(new SettingListItem(LIST_TIME, timeData, LIST_TIME_UNIT));
//	// settingList.add(new SettingListItem(LIST_CALORIE, calorieData,
//	// LIST_CALORIE_UNIT));
//	// listAdapter = new SettingListAdapter(context,
//	// R.layout.list_item_setting, settingList);
//	// listSetting.setAdapter(listAdapter);
//	// listSetting.setOnItemClickListener(new OnItemClickListener() {
//	// @Override
//	// public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//	// long arg3) {
//	// setDialog(settingList.get(arg2).getName());
//	// }
//	// });
//	// }
//
//	private void setSettingListItem() {
//		for (int i = 0; i < min.length; i++) {
//			min[i] = String.valueOf(i);
//		}
//
//		for (int i = 0; i <= hour.length - 1; i++) {
//			hour[i] = String.valueOf(i);
//		}
//
//		for (int i = 0; i < calorie.length; i++) {
//			calorie[i] = String.valueOf((i + 1) * 10);
//		}
//	}
//
//	private void setDialog(String settingName) {
//		Bundle bundle = new Bundle();
//		DialogFragment dialog = null;
//		bundle.putString("name", settingName);
//		// fragment.setArguments(bundle);
//		setSettingListItem();
//		if (settingName == LIST_TIME) {
//			dialog = new TrainingMenuDetailTimeSettingDialog(this, hour, min);
//		} else if (settingName == LIST_STRONG) {
//			dialog = new TrainingMenuDetailSettingDialog(this, strong);
//		} else if (settingName == LIST_CALORIE) {
//			dialog = new TrainingMenuDetailSettingDialog(this, calorie);
//		}
//		dialog.setArguments(bundle);
//		dialog.show(getFragmentManager(), FRAGMENT_SETTING_DIALOG);
//	}
//
//	private void setTrainingData() {
//		trainingData = DBManager.selectTrainingMenu(selectedId);
//		textTrainingName.setText(trainingData.getTrainingName());
//		textTrainingName.setTextSize(TextViewUtil.resizeTextView(
//				textTrainingName, 30));
//		textMets.setText(String.valueOf(trainingData.getMets()));
//	}
//
//	private void updateTargetHeartRate() {
//		// User user = User.getInstance();
//		targetHeartRate = HeartRateUtil.calcTargetHeartRate(
//				// user.getAge(), user.getBodily().getQuietHeartRate(),
//				User.getAge(), User.getQuietHeartRate(),
//				Integer.valueOf(strongData));
//		textTargetHeartRate.setText(String.valueOf(targetHeartRate));
//	}
//
//	private void updateEstimatedCalorie() {
//		// User user = User.getInstance();
//		int estimatedCalorie = CalorieUtil.calcCalorie(trainingData.getMets(),
//				(hourSelected * 60) + minSelected,
//				// user.getBodily().getWeight()
//				Integer.valueOf(User.getWeight()));
//		textCalorie.setText(String.valueOf(estimatedCalorie));
//	}
//
//	// private float stringTimeTomsecTime() {
//	// float hour = Float.valueOf(timeData.split("時間")[0]);
//	// float min = Float.valueOf(timeData.split("時間")[1].split("分")[0]);
//	// return (hour * 60) + min;
//	// }
//
//	@Override
//	public void onMeasurement(int trainingId) {
//
//	}
//
//	@Override
//	public void onDecided(String name, String data) {
//		if (name == LIST_STRONG) {
//			if (data != null) {
//				strongData = data;
//				updateTargetHeartRate();
//			} else {
//				strongData = LIST_DATA_INIT;
//				textTargetHeartRate.setText(LIST_DATA_INIT);
//			}
//		} else if (name == LIST_CALORIE) {
//			if (data != null) {
//				calorieData = data;
//			} else {
//				strongData = LIST_DATA_INIT;
//			}
//		}
//		listAdapter.clear();
//		// setSettingList();
//	}
//
//	@Override
//	public void onDecided(String name, int hour, int min) {
//		hourSelected = Float.valueOf(hour);
//		minSelected = Float.valueOf(min);
//		timeData = TimeUtil.integerToString(hour, min);
//		if (timeData != null) {
//			updateEstimatedCalorie();
//		} else {
//			timeData = LIST_DATA_INIT;
//			textCalorie.setText(LIST_DATA_INIT);
//		}
//		listAdapter.clear();
//		// setSettingList();
//
//	}
//
//	@Override
//	public void showDialog(String name) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void collapseGroupItem(int position) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void showAlertDialog() {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onMeasurement(int trainingMenuId, int targetHrartRate,
//			int calorie, int strong) {
//		// TODO Auto-generated method stub
//
//	}
//}
