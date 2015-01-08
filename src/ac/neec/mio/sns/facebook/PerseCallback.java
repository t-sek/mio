package ac.neec.mio.sns.facebook;

import org.json.JSONException;
import org.json.JSONObject;

import ac.neec.mio.user.User;

public class PerseCallback {

	private static final String NAME = "name";
	private static final String GENDER = "gender";
	private static final String EMAIL = "email";
	private static final String FIELDS = "fields";
	
	private static User user = User.getInstance();

	public static void parseUserInfo(JSONObject response) {
		try {
//			requestUserImage(response.getString("id"));
			// Log.d("JSONSampleActivity", response.getString("id"));
			user.setName(response.getString(NAME));
			user.setGender(response.getString(GENDER));
			user.setMail(response.getString(EMAIL));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

}
