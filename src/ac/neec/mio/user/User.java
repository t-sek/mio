package ac.neec.mio.user;

import static ac.neec.mio.consts.PreferenceConstants.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.internal.ed;

import ac.neec.mio.group.Affiliation;
import ac.neec.mio.pref.AppPreference;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.util.DateUtil;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;

/**
 * ユーザ情報をプリファレンスで管理するクラス
 */

public class User extends AppPreference {

	/**
	 * Userクラスインスタンス
	 */
	private static User instance = new User();

	private User() {
	}

	/**
	 * Userクラスインスタンスを取得する
	 * 
	 * @return Userクラスインスタンス
	 */
	public static User getInstance() {
		return instance;
	}

	/**
	 * 変更を通知するリスナーを設定する
	 * 
	 * @param listener
	 *            コールバックリスナー
	 */
	public void setListener(OnSharedPreferenceChangeListener listener) {
		sharedPref.registerOnSharedPreferenceChangeListener(listener);
	}

	/**
	 * アイコンを取得する
	 * 
	 * @return アイコン
	 */
	public Bitmap getImage() {
		String s = sharedPref.getString(image(), null);
		if (s == null) {
			return null;
		}
		byte[] b = Base64.decode(s, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(b, 0, b.length);
	}

	/**
	 * ユーザ名を取得する
	 * 
	 * @return ユーザ名
	 */
	public String getName() {
		return sharedPref.getString(name(), "");
	}

	/**
	 * ユーザIDを取得する
	 * 
	 * @return ユーザID
	 */
	public String getId() {
		return sharedPref.getString(userId(), "");
	}

	/**
	 * パスワードを取得する
	 * 
	 * @return パスワード
	 */
	public String getPassword() {
		return sharedPref.getString(password(), "");
	}

	/**
	 * 年齢を取得する
	 * 
	 * @return 年齢
	 */
	public int getAge() {
		return sharedPref.getInt(age(), 0);
	}

	/**
	 * 生年月日を取得する
	 * 
	 * @return 生年月日
	 */
	public String getBirth() {
		return sharedPref.getString(birth(), "");
	}

	/**
	 * 性別を取得する
	 * 
	 * @return 性別
	 */
	public String getGender() {
		return sharedPref.getString(gender(), Gender.MALE);
	}

	/**
	 * 身長を取得する
	 * 
	 * @return 身長
	 */
	public float getHeight() {
		return sharedPref.getFloat(height(), 170);
	}

	/**
	 * 体重を取得する
	 * 
	 * @return 体重
	 */
	public float getWeight() {
		return sharedPref.getFloat(weight(), 60);
	}

	/**
	 * メールアドレスを取得する
	 * 
	 * @return メールアドレス
	 */
	public String getMail() {
		return sharedPref.getString(mail(), "");
	}

	/**
	 * 安静時心拍数を取得する
	 * 
	 * @return 安静時心拍数
	 */
	public int getQuietHeartRate() {
		return sharedPref.getInt(quietHeartRate(), 60);
	}

	/**
	 * アカウント作成日を取得する
	 * 
	 * @return アカウント作成日
	 */
	public String getCreated() {
		return sharedPref.getString(created(), null);
	}

	/**
	 * facebookアクセストークンを取得する
	 * 
	 * @return facebookアクセストークン
	 */
	public String getFacebookAccessToken() {
		return sharedPref.getString(facebookAccessToken(), "");
	}

	/**
	 * アイコンを設定する
	 * 
	 * @param image
	 *            アイコン
	 */
	public void setImage(Bitmap image) {
		String bitmapStr;
		if (image != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
			bitmapStr = Base64.encodeToString(baos.toByteArray(),
					Base64.DEFAULT);
		} else {
			bitmapStr = null;
		}
		editor.putString(image(), bitmapStr);
		editor.commit();
	}

	/**
	 * ユーザ名を設定する
	 * 
	 * @param name
	 *            ユーザ名
	 */
	public void setName(String name) {
		editor.putString(name(), name);
		editor.commit();
	}

	/**
	 * ユーザIDを設定する
	 * 
	 * @param id
	 *            ユーザID
	 */
	public void setId(String id) {
		editor.putString(userId(), id);
		editor.commit();
	}

	/**
	 * パスワードを設定する
	 * 
	 * @param password
	 *            パスワード
	 */
	public void setPassword(String password) {
		editor.putString(password(), password);
		editor.commit();
	}

	/**
	 * 年齢を設定する
	 * 
	 * @param age
	 *            年齢
	 */
	public void setAge(int age) {
		editor.putInt(age(), age);
		editor.commit();
	}

	/**
	 * 生年月日を設定する
	 * 
	 * @param birth
	 *            生年月日
	 */
	public void setBirth(String birth) {
		editor.putString(birth(), birth);
		editor.commit();
	}

	/**
	 * 性別を設定する
	 * 
	 * @param gender
	 *            性別
	 */
	public void setGender(String gender) {
		editor.putString(gender(), gender);
		editor.commit();
	}

	/**
	 * 身長を設定する
	 * 
	 * @param height
	 *            身長
	 */
	public void setHeight(float height) {
		editor.putFloat(height(), Math.round(height));
		editor.commit();
	}

	/**
	 * 体重を設定する
	 * 
	 * @param weight
	 *            体重
	 */
	public void setWeight(float weight) {
		editor.putFloat(weight(), Math.round(weight));
		editor.commit();
	}

	/**
	 * メールアドレスを設定する
	 * 
	 * @param mail
	 *            メールアドレス
	 */
	public void setMail(String mail) {
		editor.putString(mail(), mail);
		editor.commit();
	}

	/**
	 * 安静時心拍数を設定する
	 * 
	 * @param quietHeartRate
	 *            安静時心拍数
	 */
	public void setQuietHeartRate(int quietHeartRate) {
		editor.putInt(quietHeartRate(), quietHeartRate);
		editor.commit();
	}

	/**
	 * アカウント作成日を設定する
	 * 
	 * @param date
	 *            アカウント作成日
	 */
	public void setCreated(String date) {
		editor.putString(created(), date);
		editor.commit();
	}

	/**
	 * facebookアクセストークンを設定する
	 * 
	 * @param accessToken
	 *            facebookアクセストークン
	 */
	public void setFacebookAccessToken(String accessToken) {
		editor.putString(facebookAccessToken(), accessToken);
		editor.commit();
	}

	/**
	 * ログアウトする
	 */
	public void logout() {
		LoginState.putState(LoginState.NON_LOGIN);
		clear();
	}

	/**
	 * ログインする
	 */
	public void login() {
		LoginState.putState(LoginState.LOGIN);
	}

	/**
	 * ユーザ情報を初期化する
	 */
	public void clear() {
		setId(null);
		setName(null);
		setPassword(null);
		setImage(null);
		setAge(20);
		setBirth(DateUtil.japaneseNowDay());
		setGender(null);
		setHeight(170);
		setWeight(60);
		setMail(null);
		setQuietHeartRate(60);
		setFacebookAccessToken(null);
	}

}
