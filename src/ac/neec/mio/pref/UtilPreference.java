package ac.neec.mio.pref;

import static ac.neec.mio.consts.PreferenceConstants.*;
import android.util.Log;

public class UtilPreference extends AppPreference {

	public static void putCategoryPicker(int index) {
		Log.d("util", "putCategory "+index);
		editor.putInt(categoryPicker(), index);
		editor.commit();
	}

	public static void putMenuPicker(int index) {
		Log.d("util", "putMenu "+index);
		editor.putInt(menuPicker(), index);
		editor.commit();
	}

	public static int getCategoryPicker() {
		return sharedPref.getInt(categoryPicker(), 0);
	}

	public static int getMenuPicker() {
		return sharedPref.getInt(menuPicker(), 0);
	}

}
