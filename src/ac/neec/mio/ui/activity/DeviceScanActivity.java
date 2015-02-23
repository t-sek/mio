package ac.neec.mio.ui.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.ble.DeviceInfo;
import ac.neec.mio.pref.DevicePreferenceManager;
import ac.neec.mio.sort.DeviceScanComparator;
import ac.neec.mio.ui.adapter.DeviceScanListAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

/**
 * デバイス検索を行う画面クラス
 *
 */
public class DeviceScanActivity extends BleDeviceScanBaseActivity {

	/**
	 * 見つかったデバイス一覧を表示するリストビュー
	 */
	private ListView listView;
	/**
	 * デバイス一覧リストビューのアダプター
	 */
	private DeviceScanListAdapter adapter;
	/**
	 * 見つかったデバイスリスト
	 */
	private List<DeviceInfo> devices = new ArrayList<DeviceInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_scan);
		init();
		scanLeDevice(true);
	}

	/**
	 * 画面の初期化処理をする
	 */
	private void init() {
		listView = (ListView) findViewById(R.id.list_device_scan);
		adapter = new DeviceScanListAdapter(getApplicationContext(),
				R.layout.item_device_scan, devices);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				setDeviceInfo(position);
			}
		});
	}

	/**
	 * 安静時心拍数計測画面に遷移する
	 */
	private void intentQuietHeartRateMeasurement() {
		Intent intent = new Intent(DeviceScanActivity.this,
				QuietHeartRateMeasurementActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * デバイスを保存する
	 * 
	 * @param position
	 *            リストインデックス
	 */
	private void setDeviceInfo(int position) {
		DeviceInfo device = devices.get(position);
		DevicePreferenceManager.putDeviceAddress(device.getAddress());
		DevicePreferenceManager.putDeviceName(device.getName());
		scanLeDevice(false);
		intentQuietHeartRateMeasurement();
	}

	@Override
	protected void onDeviceScan(DeviceInfo device) {
		devices.add(device);
		Collections.sort(devices, new DeviceScanComparator());
		adapter.notifyDataSetChanged();
	}

}
