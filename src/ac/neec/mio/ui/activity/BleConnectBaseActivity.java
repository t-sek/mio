package ac.neec.mio.ui.activity;

import java.util.List;
import java.util.UUID;
import ac.neec.mio.ble.BleUuid;
import ac.neec.mio.ble.BluetoothLeService;
import ac.neec.mio.ble.DisconnectDialogFragment;
import ac.neec.mio.pref.DevicePreferenceManager;
import ac.neec.mio.ui.dialog.LoadingDialog;
import ac.neec.mio.ui.listener.BleConnectCallbackListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

public abstract class BleConnectBaseActivity extends FragmentActivity implements
		BleConnectCallbackListener {

	// WHITE
	// protected static final String DEVICE_ADDRESS = "C0:51:67:4A:63:59";
	// BLACK = "E5:85:4C:7F:12:EE"
	protected static String DEVICE_ADDRESS;
	protected static final int REQUEST_ENABLE_BT = 1;

	protected BluetoothLeService bluetoothLeService;
	protected BluetoothAdapter bluetoothAdapter;
	protected ServiceConnection serviceConnection;
	protected BroadcastReceiver gattUpdateReceiver;

	private LoadingDialog dialogRoading;

	protected void setupReadCharacteristic() {
		List<BluetoothGattService> gatts = bluetoothLeService
				.getSupportedGattServices();
		BluetoothGattCharacteristic characteristic = null;
		for (BluetoothGattService item : gatts) {
			if (item.getUuid().toString().equals(BleUuid.SERVICE_HEART_RATE)) {
				characteristic = item.getCharacteristic(UUID
						.fromString(BleUuid.CHAR_HEART_RATE_MEASUREMENT));
				bluetoothLeService.readCharacteristic(characteristic);
			}
		}
		bluetoothLeService.setCharacteristicNotification(characteristic, true);
	}

	private static IntentFilter makeGattUpdateIntentFilter() {
		final IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
		intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
		intentFilter
				.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
		intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
		return intentFilter;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		bleDisconnect();
	}

	protected void disconnectAction() {
		// dialogRoading = new BleConnectLoadDialog(getApplicationContext());
		// dialogRoading.show(getFragmentManager(), "");
		bleDisconnect();
		connectBleDevice();
	}

	protected void bleConnectOnResume() {
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();
		registerReceiver(gattUpdateReceiver, makeGattUpdateIntentFilter());
		if (bluetoothLeService != null) {
			final boolean result = bluetoothLeService.connect(DEVICE_ADDRESS);
		}
	}

	protected void connectDevice() {
		if (DevicePreferenceManager.getDeviceAddress() != null) {
			DEVICE_ADDRESS = DevicePreferenceManager.getDeviceAddress();
			// Toast.makeText(getApplicationContext(), DEVICE_ADDRESS,
			// Toast.LENGTH_SHORT).show();

		} else {
			// Toast.makeText(getApplicationContext(), "return",
			// Toast.LENGTH_SHORT).show();
			return;
		}

		dialogRoading = new LoadingDialog();
		dialogRoading.show(getFragmentManager(), "");
		serviceConnection = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName componentName,
					IBinder service) {
				bluetoothLeService = ((BluetoothLeService.LocalBinder) service)
						.getService();
				if (!bluetoothLeService.initialize()) {
					// finish();
					return;
				}
				bluetoothLeService.connect(DEVICE_ADDRESS);
			}

			@Override
			public void onServiceDisconnected(ComponentName componentName) {
				bluetoothLeService = null;
				disconnectAction();
			}
		};
		gattUpdateReceiver = new BroadcastReceiver() {
			@Override
			public void onReceive(Context context, Intent intent) {
				final String action = intent.getAction();
				if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
					invalidateOptionsMenu();
				} else if (BluetoothLeService.ACTION_GATT_DISCONNECTED
						.equals(action)) {
					disconnectAction();
					bleDisconnected();
				} else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED
						.equals(action)) {
					setupReadCharacteristic();
					dialogRoading.dismiss();
					bleConnected();
				} else if (BluetoothLeService.ACTION_DATA_AVAILABLE
						.equals(action)) {
					dialogRoading.dismiss();
					receiveHeartRate(intent
							.getStringExtra(BluetoothLeService.EXTRA_DATA));
				}
			}
		};
		bleConnectOnResume();
		Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
		bindService(gattServiceIntent, serviceConnection, BIND_AUTO_CREATE);
	}

	protected void bleDisconnect() {
		try {
			unbindService(serviceConnection);
			unregisterReceiver(gattUpdateReceiver);
			stopService(getIntent());
			bluetoothLeService.close();
			bluetoothLeService = null;
		} catch (Exception e) {
		}
	}

	public void onConnected() {
		dialogRoading.dismiss();
	}

	public void onCancel() {

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			if (resultCode == Activity.RESULT_OK) {
				requestEnable();
				connectDevice();
			} else {
				requestNotEnable();
			}
		}
	}

	public void connectBleDevice() {
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			// BLE is not supported
			// finish();
			return;
		}
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();
		// Checks if Bluetooth is supported on the device.
		if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
			// BLE un used
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			return;
		} else {
			connectDevice();
		}
	}

	protected abstract void requestEnable();

	protected abstract void requestNotEnable();

	protected abstract void receiveHeartRate(String heartRate);

	protected abstract void bleConnected();

	protected abstract void bleDisconnected();

}
