package ac.neec.mio.http.listener;

import java.util.List;

import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.taining.category.TrainingCategory;
import ac.neec.mio.taining.menu.TrainingMenu;
import ac.neec.mio.user.UserInfo;

import com.android.volley.VolleyError;

public interface HttpTrainingMenuResponseListener {

	void networkError(VolleyError error);
	void parseError(int code);
	void responseMenuInfo(List<TrainingMenu> menu);
}
