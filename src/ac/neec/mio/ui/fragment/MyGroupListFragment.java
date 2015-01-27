package ac.neec.mio.ui.fragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.PermissionConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.DaoFactory;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.dao.item.api.ApiDaoFactory;
import ac.neec.mio.exception.SQLiteInsertException;
import ac.neec.mio.exception.SQLiteTableConstraintException;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.http.listener.HttpMyGroupResponseListener;
import ac.neec.mio.ui.activity.GroupDetailsActivity;
import ac.neec.mio.ui.adapter.GroupListAdapter;
import ac.neec.mio.ui.listener.SearchNotifyListener;
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

public class MyGroupListFragment extends Fragment implements Sourceable,
		SearchNotifyListener {

	private static final String TITLE = "参加しているグループ";

	private static final int MESSAGE_UPDATE = 1;
	private static final int FLAG_GROUP = 3;
	private static final int FLAG_USER = 4;

	private ListView listView;
	private List<Group> groups = new ArrayList<Group>();
	private User user = User.getInstance();
	private ApiDao dao;
	private SQLiteDao daoSql;
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
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
		affiliations = daoSql.selectAffiliation();
		groups = daoSql.selectGroup();
		listView = (ListView) view.findViewById(R.id.list_my_group);
		listView.setEmptyView(view.findViewById(R.id.empty));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String groupId = groups.get(position).getId();
				intentGroupDetails(groupId);
			}
		});
		update();
		return view;
	}

	private void intentGroupDetails(String groupId) {
		Intent intent = new Intent(getActivity().getApplicationContext(),
				GroupDetailsActivity.class);
		intent.putExtra("Group_Id", groupId);
		int permissionId = 0;
		Affiliation affiliation = daoSql.selectAffiliation(groupId);
		Log.d("activity", "a  " + affiliation);
		List<Affiliation> a = daoSql.selectAffiliation();
		Log.d("activity", "a size " + a.size());
		permissionId = affiliation.getPermition().getId();
		intent.putExtra(GroupDetailsActivity.PERMISSION_ID, permissionId);
		startActivity(intent);
	}

	private void update() {
		groups = daoSql.selectGroup();
		GroupListAdapter adapter = new GroupListAdapter(getActivity()
				.getApplicationContext(), R.layout.item_group, groups);
		listView.setAdapter(adapter);
	}

	private void setMyGroupList(UserInfo info) {
		daoSql.deleteAffiliation();
		List<Affiliation> affiliations = info.getAffiliations();
		List<Group> groups = info.getGroups();
		for (int i = 0; i < affiliations.size(); i++) {
			if (affiliations.get(i).getPermition().getId() == PermissionConstants
					.notice()) {
				break;
			}
			try {
				daoSql.insertAffiliation(affiliations.get(i).getGroupId(),
						affiliations.get(i).getPermition().getId());
				daoSql.insertGroup(groups.get(i).getId(), groups.get(i)
						.getGroupName(), groups.get(i).getComment(), groups
						.get(i).getUserId(), groups.get(i).getCreated());
			} catch (SQLiteInsertException e) {
				e.printStackTrace();
			} catch (SQLiteTableConstraintException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public String toString() {
		return TITLE;
	}

	@Override
	public void onSearchText(String text) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onClear() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSearchEnd() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpdate() {
		dao.selectUser(getActivity().getApplicationContext(), user.getId(),
				user.getPassword());
	}

	@Override
	public void complete() {
		UserInfo info = null;
		try {
			info = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
		} catch (XmlReadException e) {
			e.printStackTrace();
		}
		setMyGroupList(info);
		Message message = new Message();
		message.what = MESSAGE_UPDATE;
		handler.sendMessage(message);
	}

	@Override
	public void complete(InputStream response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void complete(Bitmap image) {
		// TODO Auto-generated method stub

	}

	@Override
	public void incomplete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void progressUpdate(int value) {
		// TODO Auto-generated method stub
		
	}
}