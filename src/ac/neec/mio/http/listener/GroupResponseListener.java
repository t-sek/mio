package ac.neec.mio.http.listener;

import java.util.List;

import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupInfo;

import com.android.volley.VolleyError;

public interface GroupResponseListener {
	void networkError(VolleyError error);
	void responseGroupAll(List<Group> list);
	void responseGroupInfo(GroupInfo group);
}
