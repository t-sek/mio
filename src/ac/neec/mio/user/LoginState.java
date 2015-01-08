package ac.neec.mio.user;

import ac.neec.mio.pref.AppPreference;
import android.content.Context;
import static ac.neec.mio.consts.PreferenceConstants.*;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class LoginState extends AppPreference {

	public static final int LOGIN = 1;
	public static final int NON_LOGIN = 2;

	protected static void putState(int state) {
		editor.putInt(loginState(), state);
		editor.commit();
	}

	public static int getState() {
		return sharedPref.getInt(loginState(), 0);
	}

}
