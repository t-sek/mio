package ac.neec.mio.pref;

import static ac.neec.mio.consts.PreferenceConstants.*;

/**
 * デバイス設定をする
 *
 */
public class DevicePreferenceManager extends AppPreference {

	/**
	 * デバイスアドレスを保存する
	 * 
	 * @param address
	 *            デバイスアドレス
	 */
	public static void putDeviceAddress(String address) {
		editor.putString(deviceAddress(), address);
		editor.commit();
	}

	/**
	 * デバイス名を保存する
	 * 
	 * @param name
	 *            デバイス名
	 */
	public static void putDeviceName(String name) {
		editor.putString(deviceName(), name);
		editor.commit();
	}

	/**
	 * デバイスアドレスを取得する
	 * 
	 * @return デバイスアドレス
	 */
	public static String getDeviceAddress() {
		return sharedPref.getString(deviceAddress(), null);
	}

	/**
	 * デバイス名を取得する
	 * 
	 * @return デバイス名
	 */
	public static String getDeviceName() {
		return sharedPref.getString(deviceName(), null);
	}

}
