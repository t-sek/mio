package ac.neec.mio.ui.activity;

import ac.neec.mio.R;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public abstract class BlePermitBaseActivity extends Activity {

	protected static final int REQUEST_ENABLE_BT = 1;

	public static final int BLE_NOT_SUPPORTED = 1;
	public static final int BLE_SUPPORTED = 2;
	public static final int BLE_UNUSED = 3;

	protected static int bleState;

	protected BluetoothAdapter bluetoothAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQUEST_ENABLE_BT:
			if (resultCode == Activity.RESULT_OK) {
				requestEnable();
			} else {
				requestNotEnable();
			}
		}
	}

	public int isBleStated(Context context) {
		if (!context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			// BLE is not supported
			return BLE_NOT_SUPPORTED;
		}
		final BluetoothManager bluetoothManager = (BluetoothManager) context
				.getSystemService(Context.BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();
		// Checks if Bluetooth is supported on the device.
		if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
			// BLE un used
			return BLE_UNUSED;
		}
		return BLE_SUPPORTED;
	}

	public void permitBle() {
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			// BLE is not supported
//			finish();
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
			// bleConnectOnResume();
			return;
		}
	}

	protected abstract void requestEnable();

	protected abstract void requestNotEnable();

}
