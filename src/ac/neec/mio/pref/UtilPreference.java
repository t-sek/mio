package ac.neec.mio.pref;

import static ac.neec.mio.consts.PreferenceConstants.*;
import android.util.Log;

/**
 * 設定情報を保存する
 *
 */
public class UtilPreference extends AppPreference {

	/**
	 * カテゴリー設定を保存する
	 * 
	 * @param index
	 *            インデックス
	 */
	public static void putCategoryPicker(int index) {
		editor.putInt(categoryPicker(), index);
		editor.commit();
	}

	/**
	 * メニュー設定を保存する
	 * 
	 * @param index
	 *            インデックス
	 */
	public static void putMenuPicker(int index) {
		editor.putInt(menuPicker(), index);
		editor.commit();
	}

	/**
	 * カテゴリー設定を取得する
	 * 
	 * @return インデックス
	 */
	public static int getCategoryPicker() {
		return sharedPref.getInt(categoryPicker(), 0);
	}

	/**
	 * メニュー設定を取得する
	 * 
	 * @return インデックス
	 */
	public static int getMenuPicker() {
		return sharedPref.getInt(menuPicker(), 0);
	}

}
