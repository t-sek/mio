package ac.neec.mio.pref;

import ac.neec.mio.consts.SQLConstants;
import ac.neec.mio.consts.PreferenceConstants;
import ac.neec.mio.util.SignUpConstants;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public abstract class AppPreference {

	protected static SharedPreferences sharedPref;
	protected static Editor editor;

	public static void init(Context context) {
		sharedPref = context.getSharedPreferences("mio", Context.MODE_PRIVATE);
		editor = sharedPref.edit();
		SQLConstants.init(context.getResources());
		SignUpConstants.init(context.getResources());
		PreferenceConstants.init(context.getResources());
	}

}
