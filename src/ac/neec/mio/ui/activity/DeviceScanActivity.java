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

public class DeviceScanActivity extends BleDeviceScanBaseActivity {

	private ListView listView;
	private DeviceScanListAdapter adapter;
	private List<DeviceInfo> devices = new ArrayList<DeviceInfo>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_scan);
		init();
		scanLeDevice(true);
	}

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

	private void intentQuietHeartRateMeasurement() {
		Intent intent = new Intent(DeviceScanActivity.this,
				QuietHeartRateMeasurementActivity.class);
		startActivity(intent);
		finish();
	}

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
