package ac.neec.mio.http.listener;

import java.util.List;

import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.user.UserInfo;

import com.android.volley.VolleyError;

public interface HttpUserResponseListener {

	void networkError(VolleyError error);
	void responseUserInfo(UserInfo user);
	void responseWeight(float weight);
	void responseQuietHeartRate(int heartRate);
	void responseUserLogin(boolean login);
}
