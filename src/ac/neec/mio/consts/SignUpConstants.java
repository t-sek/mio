package ac.neec.mio.consts;

import ac.neec.mio.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * リソースから新規登録メッセージを取得するクラス
 */
public class SignUpConstants extends AppConstants {

	/**
	 * 名前
	 * 
	 * @return 名前
	 */
	public static String name() {
		return resources.getString(R.string.pref_title_user_name);
	}

	/**
	 * 生年月日
	 * 
	 * @return 生年月日
	 */
	public static String birth() {
		return resources.getString(R.string.pref_title_user_date);
	}

	/**
	 * 性別
	 * 
	 * @return 性別
	 */
	public static String gender() {
		return resources.getString(R.string.pref_title_user_gender);
	}

	/**
	 * メールアドレス
	 * 
	 * @return メールアドレス
	 */
	public static String mail() {
		return resources.getString(R.string.pref_title_user_mail);
	}

	/**
	 * ID
	 * 
	 * @return ID
	 */
	public static String userId() {
		return resources.getString(R.string.pref_title_user_id);
	}

	/**
	 * パスワード
	 * 
	 * @return パスワード
	 */
	public static String password() {
		return resources.getString(R.string.pref_title_user_password);
	}

	/**
	 * 身長
	 * 
	 * @return 身長
	 */
	public static String height() {
		return resources.getString(R.string.pref_title_user_height);
	}

	/**
	 * 体重
	 * 
	 * @return 体重
	 */
	public static String weight() {
		return resources.getString(R.string.pref_title_user_weight);
	}

	/**
	 * 安静時心拍数
	 * 
	 * @return 安静時心拍数
	 */
	public static String quietHeartRate() {
		return resources.getString(R.string.pref_title_user_quiet_heart_rate);
	}

	/**
	 * ログアウト
	 * 
	 * @return ログアウト
	 */
	public static String logout() {
		return resources.getString(R.string.pref_title_logout);
	}

}
