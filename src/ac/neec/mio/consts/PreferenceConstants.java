package ac.neec.mio.consts;

import ac.neec.mio.R;
import ac.neec.mio.pref.DevicePreferenceManager;
import android.content.res.Resources;

/**
 * プリファレンス保存するための項目名を取得するクラス
 *
 */
public class PreferenceConstants extends AppConstants {

	/**
	 * ログイン状態
	 * 
	 * @return 項目名
	 */
	public static String loginState() {
		return resources.getString(R.string.pref_login_state);
	}

	/**
	 * デバイスアドレス
	 * 
	 * @return 項目名
	 */
	public static String deviceAddress() {
		return resources.getString(R.string.pref_device_address);
	}

	/**
	 * デバイス名
	 * 
	 * @return 項目名
	 */
	public static String deviceName() {
		return resources.getString(R.string.pref_device_name);
	}

	/**
	 * 不明なデバイス
	 * 
	 * @return 項目名
	 */
	public static String unknownDevice() {
		return resources.getString(R.string.unknown_device);
	}

	/**
	 * 身長
	 * 
	 * @return 項目名
	 */
	public static String height() {
		return resources.getString(R.string.pref_profile_height);
	}

	/**
	 * 体重
	 * 
	 * @return 項目名
	 */
	public static String weight() {
		return resources.getString(R.string.pref_profile_weight);
	}

	/**
	 * 安静時心拍数
	 * 
	 * @return 項目名
	 */
	public static String quietHeartRate() {
		return resources.getString(R.string.pref_profile_quiet_heart_rate);
	}

	/**
	 * プロフィール画像
	 * 
	 * @return 項目名
	 */
	public static String image() {
		return resources.getString(R.string.pref_profile_image);
	}

	/**
	 * プロフィール画像URI
	 * 
	 * @return 項目名
	 */
	public static String imageUri() {
		return resources.getString(R.string.pref_profile_image_uri);
	}

	/**
	 * ユーザ名
	 * 
	 * @return 項目名
	 */
	public static String name() {
		return resources.getString(R.string.pref_profile_name);
	}

	/**
	 * 年齢
	 * 
	 * @return 項目名
	 */
	public static String age() {
		return resources.getString(R.string.pref_profile_age);
	}

	/**
	 * 誕生日
	 * 
	 * @return 項目名
	 */
	public static String birth() {
		return resources.getString(R.string.pref_profile_birth);
	}

	/**
	 * 性別
	 * 
	 * @return 項目名
	 */
	public static String gender() {
		return resources.getString(R.string.pref_profile_gender);
	}

	/**
	 * メールアドレス
	 * 
	 * @return 項目名
	 */
	public static String mail() {
		return resources.getString(R.string.pref_profile_mail);
	}

	/**
	 * ユーザID
	 * 
	 * @return 項目名
	 */
	public static String userId() {
		return resources.getString(R.string.pref_profile_id);
	}

	/**
	 * パスワード
	 * 
	 * @return 項目名
	 */
	public static String password() {
		return resources.getString(R.string.pref_profile_password);
	}

	/**
	 * アカウント作成日
	 * 
	 * @return 項目名
	 */
	public static String created() {
		return resources.getString(R.string.pref_profile_created);
	}

	/**
	 * facebookアクセストークン
	 * 
	 * @return 項目名
	 */
	public static String facebookAccessToken() {
		return resources.getString(R.string.pref_facebook_access_token);
	}

	/**
	 * 最近のトレーニングカテゴリー
	 * 
	 * @return 項目名
	 */
	public static String categoryPicker() {
		return resources.getString(R.string.pref_category_picker);
	}

	/**
	 * 最近のトレーニングメニュー
	 * 
	 * @return 項目名
	 */
	public static String menuPicker() {
		return resources.getString(R.string.pref_menu_picker);
	}

}
