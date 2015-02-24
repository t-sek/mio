package ac.neec.mio.user;

import ac.neec.mio.pref.AppPreference;
import android.content.Context;
import static ac.neec.mio.consts.PreferenceConstants.*;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

/**
 * ログイン状態をプリファレンスで管理するクラス
 */
public class LoginState extends AppPreference {

	/**
	 * ログインフラグ
	 */
	public static final int LOGIN = 1;
	/**
	 * ログアウトフラグ
	 */
	public static final int NON_LOGIN = 2;

	/**
	 * ログイン状態をプリファレンスに保存する
	 * 
	 * @param state
	 *            ログイン状態<br>
	 *            LoginStateクラスのLOGIN、NON_LOGIN
	 */
	protected static void putState(int state) {
		editor.putInt(loginState(), state);
		editor.commit();
	}

	/**
	 * ログイン状態を取得する
	 * 
	 * @return ログイン状態<br>
	 *         LoginStateクラスのLOGIN、NON_LOGIN
	 * 
	 */
	public static int getState() {
		return sharedPref.getInt(loginState(), 0);
	}

}
