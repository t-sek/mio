package ac.neec.mio.util;

import android.util.Log;

public class PermissionUtil {
	public static boolean getBool(int flag) {
		if (flag == 0) {
			return false;
		} else {
			return true;
		}
	}

	public static int getInt(boolean flag) {
		if (flag) {
			return 1;
		} else {
			return 0;
		}
	}

}
