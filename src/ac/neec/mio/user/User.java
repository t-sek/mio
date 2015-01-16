package ac.neec.mio.user;

import static ac.neec.mio.consts.PreferenceConstants.*;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.group.Affiliation;
import ac.neec.mio.pref.AppPreference;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.util.DateUtil;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

public class User extends AppPreference {

	private static User instance = new User();
	private static List<Affiliation> affiliations = new ArrayList<Affiliation>();

	private User() {
	}

	public static User getInstance() {
		return instance;
	}

	public void setListener(OnSharedPreferenceChangeListener listener) {
		sharedPref.registerOnSharedPreferenceChangeListener(listener);
	}

	public Bitmap getImage() {
		String s = sharedPref.getString(image(), null);
		if (s == null) {
			return null;
		}
		byte[] b = Base64.decode(s, Base64.DEFAULT);
		return BitmapFactory.decodeByteArray(b, 0, b.length);
	}

	public String getName() {
		return sharedPref.getString(name(), "");
	}

	public String getId() {
		return sharedPref.getString(userId(), "");
	}

	public String getPassword() {
		return sharedPref.getString(password(), "");
	}

	public int getAge() {
		return sharedPref.getInt(age(), 0);
	}

	public String getBirth() {
		return sharedPref.getString(birth(), "");
	}

	public String getGender() {
		return sharedPref.getString(gender(), Gender.MALE);
	}

	public float getHeight() {
		return sharedPref.getFloat(height(), 170);
	}

	public float getWeight() {
		return sharedPref.getFloat(weight(), 60);
	}

	public String getMail() {
		return sharedPref.getString(mail(), "");
	}

	public int getQuietHeartRate() {
		return sharedPref.getInt(quietHeartRate(), 60);
	}

	public String getCreated() {
		return sharedPref.getString(created(), null);
	}

	public String getFacebookAccessToken() {
		return sharedPref.getString(facebookAccessToken(), "");
	}

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

	public void setName(String name) {
		editor.putString(name(), name);
		editor.commit();
	}

	public void setId(String id) {
		editor.putString(userId(), id);
		editor.commit();
	}

	public void setPassword(String password) {
		editor.putString(password(), password);
		editor.commit();
	}

	public void setAge(int age) {
		editor.putInt(age(), age);
		editor.commit();
	}

	public void setBirth(String birth) {
		editor.putString(birth(), birth);
		editor.commit();
	}

	public void setGender(String gender) {
		editor.putString(gender(), gender);
		editor.commit();
	}

	public void setHeight(float height) {
		editor.putFloat(height(), Math.round(height));
		editor.commit();
	}

	public void setWeight(float weight) {
		editor.putFloat(weight(), Math.round(weight));
		editor.commit();
	}

	public void setMail(String mail) {
		editor.putString(mail(), mail);
		editor.commit();
	}

	public void setQuietHeartRate(int quietHeartRate) {
		editor.putInt(quietHeartRate(), quietHeartRate);
		editor.commit();
	}

	public void setCreated(String date) {
		editor.putString(created(), date);
		editor.commit();
	}

	public void setFacebookAccessToken(String accessToken) {
		editor.putString(facebookAccessToken(), accessToken);
		editor.commit();
	}

	public void logout() {
		LoginState.putState(LoginState.NON_LOGIN);
		clear();
	}

	public void login() {
		LoginState.putState(LoginState.LOGIN);
	}

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
