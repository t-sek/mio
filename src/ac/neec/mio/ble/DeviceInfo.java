package ac.neec.mio.ble;

import android.bluetooth.BluetoothDevice;

/**
 * BluetoothLE対応のデバイス情報
 *
 */
public class DeviceInfo {

	/**
	 * デバイス情報
	 */
	private BluetoothDevice device;
	/**
	 * デバイスとの距離
	 */
	private int rssi;

	/**
	 * 
	 * @param device
	 *            デバイス情報
	 * @param rssi
	 *            デバイスとの距離
	 */
	public DeviceInfo(BluetoothDevice device, int rssi) {
		this.device = device;
		this.rssi = rssi;
	}

	/**
	 * デバイス情報を取得する
	 * 
	 * @return デバイス情報
	 */
	public BluetoothDevice getDevice() {
		return device;
	}

	/**
	 * デバイスとの距離を取得する
	 * 
	 * @return 距離
	 */
	public int getRssi() {
		return rssi;
	}

	/**
	 * デバイス名を取得する
	 * 
	 * @return デバイス名
	 */
	public String getName() {
		return device.getName();
	}

	/**
	 * デバイスのアドレスを取得する
	 * 
	 * @return アドレス
	 */
	public String getAddress() {
		return device.getAddress();
	}

}
