package ac.neec.mio.pref;

import ac.neec.mio.consts.SignUpConstants;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * プリファレンスを使用する
 *
 */
public abstract class AppPreference {

	protected static SharedPreferences sharedPref;
	protected static Editor editor;

	/**
	 * 初期化する
	 * 
	 * @param context
	 *            コンテキスト
	 */
	public static void init(Context context) {
		sharedPref = context.getSharedPreferences("mio", Context.MODE_PRIVATE);
		editor = sharedPref.edit();
	}

}
