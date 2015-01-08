package ac.neec.mio.http.listener;

import java.util.List;

import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupInfo;

import com.android.volley.VolleyError;

public interface HttpMyGroupResponseListener {

	void networkError(VolleyError error);
	void responseMyGroup(List<Group> group);
	void responseMyGroupInfo(GroupInfo group);
}
