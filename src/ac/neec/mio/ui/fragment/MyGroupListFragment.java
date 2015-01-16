package ac.neec.mio.ui.fragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.DaoFactory;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.dao.item.api.ApiDaoFactory;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.http.listener.HttpMyGroupResponseListener;
import ac.neec.mio.ui.activity.GroupDetailsActivity;
import ac.neec.mio.ui.adapter.GroupListAdapter;
import ac.neec.mio.user.User;
import ac.neec.mio.user.UserInfo;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.VolleyError;

public class MyGroupListFragment extends Fragment implements Sourceable {

	private static final String TITLE = "参加しているグループ";

	private static final int MESSAGE_UPDATE = 1;
	private static final int FLAG_GROUP = 3;
	private static final int FLAG_USER = 4;

	private ListView listView;
	private List<Group> list = new ArrayList<Group>();
	private User user = User.getInstance();
	private ApiDao dao;
	private int daoFlag;
	private List<Affiliation> affiliations = new ArrayList<Affiliation>();

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_UPDATE:
				update();
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onResume() {
		super.onResume();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_group_list,
				container, false);
		dao = DaoFacade.getApiDao(getActivity().getApplicationContext(), this);
//		daoFlag = FLAG_GROUP;
		daoFlag = FLAG_USER;
		dao.selectUser(getActivity(), user.getId(), user.getPassword());
		// dao.selectMyGroupAll(user.getId(), user.getPassword());
		listView = (ListView) view.findViewById(R.id.list_my_group);
		listView.setEmptyView(view.findViewById(R.id.empty));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String groupId = list.get(position).getId();
				intentGroupDetails(groupId);
			}
		});
		return view;
	}

	private void intentGroupDetails(String groupId) {
		Intent intent = new Intent(getActivity().getApplicationContext(),
				GroupDetailsActivity.class);
		intent.putExtra("Group_Id", groupId);
		int permissionId = 0;
		for (Affiliation affiliation : affiliations) {
			if (affiliation.getGroupId().equals(groupId)) {
				permissionId = affiliation.getPermition().getId();
			}
		}
		intent.putExtra(GroupDetailsActivity.PERMISSION_ID, permissionId);
		intent.putExtra(GroupDetailsActivity.SHOW_ACTION_SETTING,
				GroupDetailsActivity.SETTING_SHOW);
		startActivity(intent);
	}

	private void update() {
		GroupListAdapter adapter = new GroupListAdapter(getActivity()
				.getApplicationContext(), R.layout.item_group, list);
		listView.setAdapter(adapter);
	}

	private void setGroupList() {
		List<Group> response = new ArrayList<Group>();
		try {
			response = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
		} catch (XmlReadException e) {
			e.printStackTrace();
		}
	}

	private void setMyGroupList() {

	}
	
	private void setGroups(List<Group> groups){
		for (Group group : groups) {
			Log.d("fragment", "groupName " + group.getGroupName());
			Log.d("fragment", "size " + affiliations.size());
			for (Affiliation affiliation : affiliations) {
				Log.d("fragment", "joinStatus "
						+ affiliation.getPermition().getJoinStatus());
				if (affiliation.getGroupId().equals(group.getId())) {
					// permissionId = affiliation.getPermition().getId();
					if (affiliation.getPermition().getJoinStatus()) {
						list.add(group);
					}
				}
			}
		}
	}

	private void setUser() {
		UserInfo user = null;
		try {
			user = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
		} catch (XmlReadException e) {
			e.printStackTrace();
		}
		affiliations = user.getAffiliations();
		setGroups(user.getGroups());
		Message message = new Message();
		message.what = MESSAGE_UPDATE;
		handler.sendMessage(message);
	}

	private void selectUser() {
		daoFlag = FLAG_USER;
		dao.selectUser(getActivity().getApplicationContext(), user.getId(),
				user.getPassword());
	}

	@Override
	public String toString() {
		return TITLE;
	}

	@Override
	public void complete() {
		switch (daoFlag) {
		case FLAG_GROUP:
			selectUser();
			setGroupList();
			break;
		case FLAG_USER:
			setUser();
			break;
		default:
			break;
		}
	}

	@Override
	public void incomplete() {
		// TODO Auto-generated method stub
	}

	@Override
	public void complete(InputStream response) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void complete(Bitmap image) {
		// TODO Auto-generated method stub
		
	}
}