//package ac.neec.mio.ui.activity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ac.neec.mio.R;
//import ac.neec.mio.db.DBManager;
//import ac.neec.mio.http.HttpManager;
//import ac.neec.mio.http.HttpResponseListener;
//import ac.neec.mio.http.item.DateNumItem;
//import ac.neec.mio.http.item.TrainingItem;
//import ac.neec.mio.http.item.TrainingLogItem;
//import ac.neec.mio.http.item.TrainingPlayItem;
//import ac.neec.mio.taining.Training;
//import ac.neec.mio.taining.category.TrainingCategory;
//import ac.neec.mio.taining.menu.TrainingMenu;
//import ac.neec.mio.ui.adapter.SettingListItem;
//import ac.neec.mio.ui.adapter.TrainingListAdapter;
//import ac.neec.mio.ui.dialog.BleConnectLoadDialog;
//import ac.neec.mio.ui.dialog.TrainingMenuDetailSettingDialog;
//import ac.neec.mio.ui.dialog.TrainingMenuDetailTimeSettingDialog;
//import ac.neec.mio.ui.listener.TrainingSettingCallbackListener;
//import ac.neec.mio.user.User;
//import ac.neec.mio.util.Constants;
//import ac.neec.mio.util.DateUtil;
//import android.app.AlertDialog;
//import android.app.DialogFragment;
//import android.app.Fragment;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.widget.ExpandableListView;
//import android.widget.ExpandableListView.OnChildClickListener;
//import android.widget.Toast;
//
//import com.android.volley.VolleyError;
//
//public class TrainingMenuSettingActivity extends BlePermitBaseActivity
//		implements TrainingSettingCallbackListener, HttpResponseListener {
//
//	private static final String LIST_STRONG = "運動強度";
//	private static final String LIST_STRONG_UNIT = "%";
//	private static final String LIST_TIME = "時間";
//	private static final String LIST_TIME_UNIT = " ";
//	private static final String LIST_CALORIE = "目標消費カロリー";
//	private static final String LIST_CALORIE_UNIT = "kcal";
//	private static final String LIST_DATA_INIT = "---";
//
//	private static final String FRAGMENT_SETTING = "FRAGMENT_SETTING";
//
////	private User user = User.getInstance();
//	private String[] hour = new String[11];
//	private String[] min = new String[60];
//	private String[] strong = { "100", "90", "80", "70", "60", "50", "40" };
//	private String[] calorie = new String[100];
//
//	private int trainingId;
//
//	ExpandableListView listView;
//	TrainingListAdapter adapter;
//	Fragment fragment;
//	private String strongData = LIST_DATA_INIT;
//	private String timeData = LIST_DATA_INIT;
//	private String calorieData = LIST_DATA_INIT;
//	private List<SettingListItem> settingList = new ArrayList<SettingListItem>();
//
//	List<String> groupList = new ArrayList<String>();
//	List<List<TrainingMenu>> childList = new ArrayList<List<TrainingMenu>>();
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_training_menu_setting);
//		initFindViews();
//		setSettingListItem();
//		setListData();
//		permitBle();
////		HttpManager
////				.uploadCreateTraining(this, getApplicationContext(), 0, 1, 1);
//		
//
//	}
//
//	@Override
//	public boolean dispatchKeyEvent(KeyEvent event) {
//		if (event.getAction() == KeyEvent.ACTION_DOWN) {
//			if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//				if (getFragmentManager().getBackStackEntryCount() == 0) {
//					intentDashboard();
//				}
//			}
//		}
//		return super.dispatchKeyEvent(event);
//	}
//
//	private void intentDashboard() {
//		Intent intent = new Intent(TrainingMenuSettingActivity.this,
//				SplashActivity.class);
//		startActivity(intent);
//		finish();
//	}
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
//	private void setListData() {
//		List<TrainingCategory> categoryBack = new ArrayList<TrainingCategory>();
//		categoryBack = DBManager.selectTrainingCategory();
//		for (TrainingCategory category : categoryBack) {
//			groupList.add(category.getTrainingCategoryName());
//		}
//		for (int i = 1; i <= groupList.size(); i++) {
//			childList.add(DBManager.selectTrainingCategoryMenu(i));
//		}
//		adapter = new TrainingListAdapter(this, groupList, childList, this,
//				trainingId);
//		listView.setAdapter(adapter);
//		listView.setOnChildClickListener(new OnChildClickListener() {
//			@Override
//			public boolean onChildClick(ExpandableListView parent, View v,
//					int groupPosition, int childPosition, long id) {
//				adapter.setDetailSetting(groupPosition, childPosition);
//				return true;
//			}
//		});
//	}
//
//	private void initFindViews() {
//		listView = (ExpandableListView) findViewById(R.id.list_training);
//	}
//
//	private void showSettingMenuDetailDialog(String name) {
//		DialogFragment dialog = null;
//		if (name == LIST_STRONG) {
//			dialog = new TrainingMenuDetailSettingDialog(this, strong);
//		} else if (name == LIST_TIME) {
//			dialog = new TrainingMenuDetailTimeSettingDialog(this, hour, min);
//		} else if (name == LIST_CALORIE) {
//			dialog = new TrainingMenuDetailSettingDialog(this, calorie);
//		} else {
//			return;
//		}
//		Bundle bundle = new Bundle();
//		bundle.putString("name", name);
//		dialog.setArguments(bundle);
//		dialog.show(getFragmentManager(), FRAGMENT_SETTING);
//	}
//
//	@Override
//	protected void requestEnable() {
//		Toast.makeText(getApplicationContext(), "Bluetooth ON",
//				Toast.LENGTH_SHORT).show();
//	}
//
//	@Override
//	protected void requestNotEnable() {
//		Toast.makeText(getApplicationContext(), "Bluetooth OFF",
//				Toast.LENGTH_SHORT).show();
//	}
//
//	@Override
//	public void onDecided(String name, String data) {
//		adapter.updateSettingList(name, data);
//	}
//
//	@Override
//	public void onDecided(String name, int hour, int min) {
//		adapter.updateSettingList(name, hour, min);
//	}
//
//	@Override
//	public void onMeasurement(int trainingId) {
//		Intent intent = new Intent(TrainingMenuSettingActivity.this,
//				MeasurementActivity.class);
//		Training training = DBManager.selectTraining(trainingId);
//		// trainingId 生成
//		// trainingId = Integer.valueOf(HttpManager.uploadTraining(
//		// getApplicationContext(), training.getTrainingMenuId(),
//		// training.getTargetHrartRate(), training.getConsumptionCal(),
//		// training.getPlayTime(), training.getTargetCal(),
//		// training.getHeartRateAvg()));
////		DBManager.insertTraining(trainingId, training.getTrainingMenuId(),
////				user.getId(), null, null, training.getPlayTime(),
////				training.getTargetHrartRate(), training.getTargetCal(),
////				training.getConsumptionCal(), 0, training.getStrange(), 0);
//		// HttpManager.
//		intent.putExtra(Constants.trainingId(), trainingId);
//		startActivity(intent);
//		finish();
//	}
//
//	@Override
//	public void showDialog(String name) {
//		showSettingMenuDetailDialog(name);
//	}
//
//	@Override
//	public void collapseGroupItem(int position) {
//		listView.collapseGroup(position);
//	}
//
//	@Override
//	public void showAlertDialog() {
//		new AlertDialog.Builder(TrainingMenuSettingActivity.this).setTitle(
//				"未入力の項目があります").show();
//	}
//
//	@Override
//	public void responseTrainingId(int trainingId) {
//		this.trainingId = trainingId;
//		Log.e("activity", "trainingId " + trainingId);
//	}
//
//	@Override
//	public void networkError(VolleyError error) {
//	}
//
//	@Override
//	public void onMeasurement(int trainingMenuId, int targetHeartRate,
//			int calorie, int strong) {
//		// DBManager.insertTraining(trainingId, trainingMenuId, user.getId(),
//		// DateUtil.nowDate(), null, null, targetHeartRate, calorie, 0, 0,
//		// strong, 0);
//		Intent intent = new Intent(TrainingMenuSettingActivity.this,
//				MeasurementActivity.class);
//		intent.putExtra(Constants.trainingId(), trainingId);
//		startActivity(intent);
//		finish();
//	}
//
//	@Override
//	public void onResponse() {
//		
//	}
//
//	@Override
//	public void responseTraining(List<TrainingItem> list) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void responseNum(DateNumItem item) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void responseTrainingLog(TrainingLogItem item) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void responseTrainingPlay(List<TrainingPlayItem> list) {
//		// TODO Auto-generated method stub
//		
//	}
//}
