package ac.neec.mio.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.sax.StartElementListener;
import android.util.Log;

public class BleManager {

	public static final int BLE_NOT_SUPPORTED = 1;
	public static final int BLE_SUPPORTED = 2;
	public static final int BLE_UNUSED = 3;

	private static BluetoothAdapter bluetoothAdapter;

	public static int permitBle(Context context) {
		if (!context.getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			// BLE is not supported
			Log.e("ble", "not supported");
			return BLE_NOT_SUPPORTED;
		}
		final BluetoothManager bluetoothManager = (BluetoothManager) context
				.getSystemService(Context.BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();
		// Checks if Bluetooth is supported on the device.
		if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
			// BLE un used
			Log.e("ble", "un used");
			return BLE_UNUSED;
		}
		Log.e("ble", "supported");
		return BLE_SUPPORTED;
	}

	public static BluetoothAdapter getBluetoothAdapter() {
		return bluetoothAdapter;
	}
}
