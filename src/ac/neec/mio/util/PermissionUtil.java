package ac.neec.mio.util;

import android.util.Log;

/**
 * 権限ユーティリティークラス
 */
public class PermissionUtil {
	/**
	 * 0,1をboolean型に変換する
	 * 
	 * @param flag
	 *            数値
	 * @return boolean型
	 */
	public static boolean getBool(int flag) {
		if (flag == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * boolean型を0,1に変換する
	 * 
	 * @param flag
	 *            boolean型
	 * @return 数値
	 */
	public static int getInt(boolean flag) {
		if (flag) {
			return 1;
		} else {
			return 0;
		}
	}

}
