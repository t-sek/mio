package ac.neec.mio.consts;

import ac.neec.mio.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * リソースから新規登録メッセージを取得するクラス
 *
 */
public class SignUpConstants extends AppConstants {

	public static String name() {
		return resources.getString(R.string.pref_title_user_name);
	}

	public static String birth() {
		return resources.getString(R.string.pref_title_user_date);
	}

	public static String gender() {
		return resources.getString(R.string.pref_title_user_gender);
	}

	public static String mail() {
		return resources.getString(R.string.pref_title_user_mail);
	}

	public static String userId() {
		return resources.getString(R.string.pref_title_user_id);
	}

	public static String password() {
		return resources.getString(R.string.pref_title_user_password);
	}

	public static String height() {
		return resources.getString(R.string.pref_title_user_height);
	}

	public static String weight() {
		return resources.getString(R.string.pref_title_user_weight);
	}

	public static String quietHeartRate() {
		return resources.getString(R.string.pref_title_user_quiet_heart_rate);
	}

	public static String logout() {
		return resources.getString(R.string.pref_title_logout);
	}

}
