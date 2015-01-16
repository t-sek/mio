package ac.neec.mio.consts;

import ac.neec.mio.R;
import ac.neec.mio.pref.DevicePreferenceManager;
import android.content.res.Resources;

public class PreferenceConstants {

	private static Resources resources;

	public static void init(Resources resources) {
		PreferenceConstants.resources = resources;
	}

	public static String loginState() {
		return resources.getString(R.string.pref_login_state);
	}

	public static String deviceAddress() {
		return resources.getString(R.string.pref_device_address);
	}

	public static String deviceName() {
		return resources.getString(R.string.pref_device_name);
	}

	public static String unknownDevice() {
		return resources.getString(R.string.unknown_device);
	}

	public static String height() {
		return resources.getString(R.string.pref_profile_height);
	}

	public static String weight() {
		return resources.getString(R.string.pref_profile_weight);
	}

	public static String quietHeartRate() {
		return resources.getString(R.string.pref_profile_quiet_heart_rate);
	}

	public static String image() {
		return resources.getString(R.string.pref_profile_image);
	}

	public static String name() {
		return resources.getString(R.string.pref_profile_name);
	}

	public static String age() {
		return resources.getString(R.string.pref_profile_age);
	}

	public static String birth() {
		return resources.getString(R.string.pref_profile_birth);
	}

	public static String gender() {
		return resources.getString(R.string.pref_profile_gender);
	}

	public static String mail() {
		return resources.getString(R.string.pref_profile_mail);
	}

	public static String userId() {
		return resources.getString(R.string.pref_profile_id);
	}

	public static String password() {
		return resources.getString(R.string.pref_profile_password);
	}

	public static String created() {
		return resources.getString(R.string.pref_profile_created);
	}

	public static String facebookAccessToken() {
		return resources.getString(R.string.pref_facebook_access_token);
	}

	public static String categoryPicker() {
		return resources.getString(R.string.pref_category_picker);
	}

	public static String menuPicker() {
		return resources.getString(R.string.pref_menu_picker);
	}

}
