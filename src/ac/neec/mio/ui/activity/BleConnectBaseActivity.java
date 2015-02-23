package ac.neec.mio.ui.activity;

import java.util.List;
import java.util.UUID;

import ac.neec.mio.ble.BleUuid;
import ac.neec.mio.ble.BluetoothLeService;
import ac.neec.mio.pref.DevicePreferenceManager;
import ac.neec.mio.ui.dialog.BleConnectDialog;
import ac.neec.mio.ui.listener.BleConnectCallbackListener;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

/**
 * BluetoothLE通信処理を定義した抽象クラス<br>
 * 通信を行う場合は、このクラスを継承する。
 *
 */
public abstract class BleConnectBaseActivity extends FragmentActivity implements
		BleConnectCallbackListener {

	/**
	 * 通信するデバイスのアドレス
	 */
	private static String DEVICE_ADDRESS;
	/**
	 * 通信リクエストコード
	 */
	private static final int REQUEST_ENABLE_BT = 1;
	/**
	 * 通信タイムアウト時間
	 */
	public static final int CONNECT_WAIT_TIME = 10000;

	/**
	 * BluetoothLeServiceクラスのインスタンス
	 */
	private BluetoothLeService bluetoothLeService;
	/**
	 * BluetoothAdapterクラスのインスタンス
	 */
	private BluetoothAdapter bluetoothAdapter;
	/**
	 * デバイスとの接続を行う
	 */
	private ServiceConnection serviceConnection;
	/**
	 * デバイスからのデータを受信する
	 */
	private BroadcastReceiver gattUpdateReceiver;
	/**
	 * 通信中の待機ダイアログ
	 */
	private BleConnectDialog dialog;

	/**
	 * デバイスからの心拍数取得のための、UUIDをサービスに設定する
	 */
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

	/**
	 * 受信タイプを設定する
	 * 
	 * @return 受信タイプ
	 */
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

	/**
	 * デバイスとの通信を切断する
	 */
	protected void disconnectAction() {
		bleDisconnect();
		connectBleDevice();
	}

	/**
	 * 通信前処理を行う<br>
	 * アダプターの設定、受信タイプの設定
	 */
	protected void bleConnectOnResume() {
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();
		registerReceiver(gattUpdateReceiver, makeGattUpdateIntentFilter());
		if (bluetoothLeService != null) {
			final boolean result = bluetoothLeService.connect(DEVICE_ADDRESS);
		}
	}

	/**
	 * 接続中ダイアログ表示する
	 */
	private void showConnectDialog() {
		dialog = new BleConnectDialog(BleConnectBaseActivity.this);
		dialog.setOnCancelListener(new OnCancelListener() {
			@Override
			public void onCancel(DialogInterface dialog) {
				bleConnectTimeout();
			}
		});
		dialog.show();
	}

	/**
	 * デバイスと接続する
	 */
	private void connectDevice() {
		if (DevicePreferenceManager.getDeviceAddress() != null) {
			DEVICE_ADDRESS = DevicePreferenceManager.getDeviceAddress();
		} else {
			return;
		}
		showConnectDialog();
		serviceConnection = new ServiceConnection() {
			@Override
			public void onServiceConnected(ComponentName componentName,
					IBinder service) {
				bluetoothLeService = ((BluetoothLeService.LocalBinder) service)
						.getService();
				if (!bluetoothLeService.initialize()) {
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
					bleConnected();
					dialog.dismiss();
				} else if (BluetoothLeService.ACTION_DATA_AVAILABLE
						.equals(action)) {
					dialog.dismiss();
					receiveHeartRate(intent
							.getStringExtra(BluetoothLeService.EXTRA_DATA));
				}
			}
		};
		bleConnectOnResume();
		Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
		bindService(gattServiceIntent, serviceConnection, BIND_AUTO_CREATE);
	}

	/**
	 * デバイスと切断する
	 */
	protected void bleDisconnect() {
		try {
			unbindService(serviceConnection);
			unregisterReceiver(gattUpdateReceiver);
			stopService(getIntent());
			bluetoothLeService.close();
			bluetoothLeService = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * デバイスを接続成功した
	 */
	public void onConnected() {
		dialog.dismiss();
	}

	/**
	 * デバイスを切断する
	 */
	public void onCancel() {
	}

	@Override
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

	/**
	 * 端末BluetoothLE対応チェックする
	 */
	protected void checkBleSupport() {
		// BluetoothLE サポート外
		if (!getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_BLUETOOTH_LE)) {
			return;
		}
		final BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
		bluetoothAdapter = bluetoothManager.getAdapter();
		// Bluetooth OFF
		if (bluetoothAdapter == null || !bluetoothAdapter.isEnabled()) {
			Intent enableBtIntent = new Intent(
					BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
			return;
		} else {
		}
	}

	/**
	 * デバイス接続開始する
	 */
	protected void connectBleDevice() {
		connectDevice();
	}

	/**
	 * Bluetooth立ち上げ許可する
	 */
	protected abstract void requestEnable();

	/**
	 * Bluetooth立ち上げ拒否する
	 */
	protected abstract void requestNotEnable();

	/**
	 * デバイスから心拍数受信する
	 * 
	 * @param heartRate
	 *            心拍数
	 */
	protected abstract void receiveHeartRate(String heartRate);

	/**
	 * デバイス接続成功
	 */
	protected abstract void bleConnected();

	/**
	 * デバイス切断
	 */
	protected abstract void bleDisconnected();

	/**
	 * デバイス接続タイムアウト
	 */
	protected abstract void bleConnectTimeout();

}
