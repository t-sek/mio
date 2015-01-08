package ac.neec.mio.pref;

import static ac.neec.mio.consts.PreferenceConstants.*;

public class DevicePreferenceManager extends AppPreference{


	public static void putDeviceAddress(String address) {
		editor.putString(deviceAddress(), address);
		editor.commit();
	}

	public static void putDeviceName(String name) {
		editor.putString(deviceName(), name);
		editor.commit();
	}

	public static String getDeviceAddress() {
		return sharedPref.getString(deviceAddress(), null);
	}

	public static String getDeviceName() {
		return sharedPref.getString(deviceName(), null);
	}

}
