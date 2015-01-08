package ac.neec.mio.ui.dialog;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.taining.menu.TrainingMenu;
import ac.neec.mio.ui.adapter.ChangeTrainingSettingListAdapter;
import ac.neec.mio.ui.listener.MeasurementCallbackListener;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;

public class ChangeTrainingSelectDialog extends DialogFragment {

	private Dialog dialog;

	private ListView listView;

	private MeasurementCallbackListener listener;
	private List<TrainingMenu> list = new ArrayList<TrainingMenu>();
	private ChangeTrainingSettingListAdapter adapter;
	private SQLiteDao dao;

	public ChangeTrainingSelectDialog(Context context,
			MeasurementCallbackListener listener, int categoryId) {
		this.listener = listener;
		dao = DaoFacade.getSQLiteDao(context);
		list = dao.selectTrainingCategoryMenu(categoryId);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new Dialog(getActivity());
		dao = DaoFacade.getSQLiteDao(getActivity().getApplicationContext());
		setDialog();
		setAdapter();
		return dialog;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		listener.onDialogCancel();
	}

	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_new_training_select);
		dialog.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
	}

	private void setAdapter() {
		listView = (ListView) dialog.findViewById(R.id.list_training_menu);
		adapter = new ChangeTrainingSettingListAdapter(getActivity(),
				R.layout.dialog_new_training_select, list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				listener.onSelected(arg2);
				Log.d("dialog", "selected " + arg2);
				dialog.dismiss();
			}
		});

	}

}
