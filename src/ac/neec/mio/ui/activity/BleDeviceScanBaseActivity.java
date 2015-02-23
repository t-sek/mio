package ac.neec.mio.ui.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ac.neec.mio.R;
import ac.neec.mio.R.layout;
import ac.neec.mio.R.string;
import ac.neec.mio.ble.DeviceInfo;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * BluetoothLE対応デバイス検索処理を定義した抽象クラス<br>
 * デバイス検索を行う場合は、このクラスを継承する。
 *
 */
public abstract class BleDeviceScanBaseActivity extends FragmentActivity {
	/**
	 * 通信リクエストコード
	 */
	private static final int REQUEST_ENABLE_BT = 1;
	/**
	 * 検索タイムアウト時間
	 */
	private static final long SCAN_PERIOD = 10000;
	/**
	 * BluetoothAdapterクラスのインスタンス
	 */
	private BluetoothAdapter mBluetoothAdapter;
	/**
	 * 検索中フラグ
	 */
	private boolean mScanning;
	/**
	 * 通信ハンドラー
	 */
	private Handler mHandler;
	/**
	 * 見つかったデバイスリスト
	 */
	private List<DeviceInfo> devices = new ArrayList<DeviceInfo>();
	/**
	 * 検索結果を受信する
	 */
	private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
		@Override
		public void onLeScan(final BluetoothDevice device, final int rssi,
				byte[] scanRecord) {
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					onDeviceScan(new DeviceInfo(device, rssi));
				}
			});
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_device_scan_base);
		mHandler = new Handler();
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT)
					.show();
			finish();
		}
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
		mBluetoothAdapter = bluetoothManager.getAdapter();
		if (mBluetoothAdapter == null) {
			Toast.makeText(this, R.string.error_bluetooth_not_supported,
					Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (!mBluetoothAdapter.isEnabled()) {
			if (!mBluetoothAdapter.isEnabled()) {
				Intent enableBtIntent = new Intent(
						BluetoothAdapter.ACTION_REQUEST_ENABLE);
				startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			}
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		scanLeDevice(false);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// User chose not to enable Bluetooth.
		if (requestCode == REQUEST_ENABLE_BT
				&& resultCode == Activity.RESULT_CANCELED) {
			finish();
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	/**
	 * 検索を開始する
	 * 
	 * @param enable
	 *            検索フラグ<br>
	 *            true 停止する<br>
	 *            false 検索を開始する
	 */
	protected void scanLeDevice(final boolean enable) {
		if (enable) {
			// Stops scanning after a pre-defined scan period.
			mHandler.postDelayed(new Runnable() {
				@Override
				public void run() {
					mScanning = false;
					mBluetoothAdapter.stopLeScan(mLeScanCallback);
					invalidateOptionsMenu();
				}
			}, SCAN_PERIOD);

			mScanning = true;
			mBluetoothAdapter.startLeScan(mLeScanCallback);
		} else {
			mScanning = false;
			mBluetoothAdapter.stopLeScan(mLeScanCallback);
		}
		invalidateOptionsMenu();
	}

	/**
	 * 見つかったデバイスを通知する
	 * 
	 * @param device
	 *            見つかったデバイス
	 */
	protected abstract void onDeviceScan(DeviceInfo device);

}
