package ac.neec.mio.ui.activity;
//package ac.neec.mio.ui.activity;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import ac.neec.mio.R;
//import ac.neec.mio.ble.BleManager;
//import ac.neec.mio.db.DBManager;
//import ac.neec.mio.taining.TrainingMenu;
//import ac.neec.mio.ui.adapter.SettingListAdapter;
//import ac.neec.mio.ui.listener.TrainingDetailSettingCallbackListener;
//import ac.neec.mio.util.Constants;
//import ac.neec.mio.util.TextViewUtil;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.os.Bundle;
//import android.preference.PreferenceManager;
//import android.util.Log;
//import android.view.KeyEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.widget.AdapterView;
//import android.widget.AdapterView.OnItemClickListener;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class TrainingMenuDetailSettingActivity extends BlePermitBaseActivity
//		implements TrainingDetailSettingCallbackListener {
//
//	private Button buttonStartMeasurement;
//	private int selectedId;
//	private TrainingMenu trainingData;
//	private TextView textTrainingName;
//	private ListView listSetting;
//	private ArrayAdapter<String> listAdapter;
//
//	private SharedPreferences sharedPreference;
//	private Editor editor;
//
//	private List<String> settingList = new ArrayList<String>();
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_training_menu_detail_setting);
//		initSelectedId();
//		initFindViews();
//		setListeners();
//		setSettingList();
//		setPreference();
//		Log.e("TrainingMenuDetailSettting", "onCreate");
//		setTrainingData();
//
//	}
//
//	@Override
//	public boolean dispatchKeyEvent(KeyEvent event) {
//		Log.e("TrainingDetailSettingActivity", "onClick " + event.getAction());
//		if (event.getAction() == KeyEvent.ACTION_DOWN) {
//			if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
//				intentTrainingMenuSetting();
//				return false;
//			}
//		}
//		return super.dispatchKeyEvent(event);
//	}
//
//	private void intentTrainingMenuSetting() {
//		Intent intent = new Intent(TrainingMenuDetailSettingActivity.this,
//				TrainingMenuSettingActivity.class);
//		Log.e("trainingMenuDetailSetting", "intent " + intent.getAction());
//		startActivity(intent);
//		finish();
//	}
//
//	private void setPreference() {
//		sharedPreference = PreferenceManager.getDefaultSharedPreferences(this);
//		editor = sharedPreference.edit();
//		editor.putInt(Constants.trainingId(), selectedId);
//		editor.commit();
//	}
//
//	private void initSelectedId() {
//		Intent intent = getIntent();
//		if (intent != null) {
//			selectedId = intent.getIntExtra("id", 0);
//		}
//	}
//
//	private void initFindViews() {
//		buttonStartMeasurement = (Button) findViewById(R.id.btn_measurement_start);
//		textTrainingName = (TextView) findViewById(R.id.txt_training_name);
//		listSetting = (ListView) findViewById(R.id.list_setting);
//	}
//
//	private void setListeners() {
//		buttonStartMeasurement.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (BleManager.permitBle(getApplicationContext()) == BleManager.BLE_SUPPORTED) {
//					intentMeasurement();
//				} else {
//					permitBle();
//				}
//			}
//		});
//	}
//
//	private void setSettingList() {
//		settingList.add("運動強度");
//		settingList.add("運動強度2");
//		settingList.add("運動強度3");
//		settingList.add("運動強度4");
//		listAdapter = new SettingListAdapter(getApplicationContext(),
//				R.layout.list_item_setting, settingList);
//		listSetting.setAdapter(listAdapter);
//		listSetting.setOnItemClickListener(new OnItemClickListener() {
//			@Override
//			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
//					long arg3) {
//				setDialog();
//				Toast.makeText(getApplicationContext(), settingList.get(arg2),
//						Toast.LENGTH_SHORT).show();
//			}
//		});
//	}
//
//	private void setDialog() {
////		new TrainingMenuDetailSettingDialog(this);
//	}
//
//	private void setTrainingData() {
//		trainingData = DBManager.selectTrainingMenu(selectedId);
//		textTrainingName.setText(trainingData.getTrainingName());
//		// textTrainingName.setTextSize(textSizeForString(textTrainingName,
//		// trainingData.getTrainingName()));
//		textTrainingName.setTextSize(TextViewUtil.resizeTextView(
//				textTrainingName, 30));
//	}
//
//	private void intentMeasurement() {
//		Intent intent = new Intent(TrainingMenuDetailSettingActivity.this,
//				MeasurementActivity.class);
//		startActivity(intent);
//	}
//
//	@Override
//	protected void requestEnable() {
//		intentMeasurement();
//	}
//
//	@Override
//	protected void requestNotEnable() {
//		Toast.makeText(getApplicationContext(), "bluetooth許可して",
//				Toast.LENGTH_SHORT).show();
//	}
//
//}
