package ac.neec.mio.ui.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.ble.DeviceInfo;
import ac.neec.mio.pref.DevicePreferenceManager;
import ac.neec.mio.sort.DeviceScanComparator;
import ac.neec.mio.ui.adapter.DeviceScanListAdapter;
import ac.neec.mio.ui.dialog.SelectionAlertDialog;
import ac.neec.mio.ui.listener.AlertCallbackListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

/**
 * デバイス検索を行う画面クラス
 *
 */
public class DeviceSettingActivity extends BleDeviceScanBaseActivity implements
		AlertCallbackListener {

	public static String FLAG = "intent_flag";
	public static int INTENT_MEASUREMENT = 1;

	/**
	 * デバイスを表示するリストビュー
	 */
	private ListView listView;
	/**
	 * listViewに値を設定するアダプター
	 */
	private DeviceScanListAdapter adapter;
	/**
	 * デバイスリストインデックス
	 */
	private int position;
	/**
	 * 画面遷移フラグ
	 */
	private int flag;
	/**
	 * 見つかったデバイスリスト
	 */
	private List<DeviceInfo> devices = new ArrayList<DeviceInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_setting);
		Intent intent = getIntent();
		flag = intent.getIntExtra(FLAG, 0);
		init();
		scanLeDevice(true);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	/**
	 * 画面の初期化処理をする
	 */
	private void init() {
		listView = (ListView) findViewById(R.id.list_scan_device);
		adapter = new DeviceScanListAdapter(getApplicationContext(),
				R.layout.item_device_scan, devices);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showAlertDialog(position);
			}
		});
	}

	/**
	 * 確認ダイアログを表示する
	 * 
	 * @param position
	 *            デバイスリストインデックス
	 */
	private void showAlertDialog(int position) {
		this.position = position;
		new SelectionAlertDialog(this, "このデバイスに設定しますか？", "はい", "いいえ", false)
				.show(getFragmentManager(), "dialog");
	}

	/**
	 * デバイス名とデバイスアドレスを保存する
	 * 
	 * @param position
	 *            デバイスリストインデックス
	 */
	private void setDeviceInfo(int position) {
		DeviceInfo device = devices.get(position);
		DevicePreferenceManager.putDeviceAddress(device.getAddress());
		DevicePreferenceManager.putDeviceName(device.getName());
		Toast.makeText(getApplicationContext(),
				DevicePreferenceManager.getDeviceName() + "に設定しました",
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * 計測画面に遷移する
	 */
	private void intentMeasurement() {
		Intent intent = new Intent(DeviceSettingActivity.this,
				MeasurementActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onDeviceScan(DeviceInfo device) {
		devices.add(device);
		Collections.sort(devices, new DeviceScanComparator());
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onNegativeSelected(String message) {

	}

	@Override
	public void onPositiveSelected(String message) {
		setDeviceInfo(position);
		if (flag == INTENT_MEASUREMENT) {
			intentMeasurement();
		} else {
			finish();
		}

	}
}
