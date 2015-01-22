package ac.neec.mio.ui.fragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.DaoFactory;
import ac.neec.mio.dao.SQLiteDao;
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

public class MyGroupListFragment extends Fragment {

	private static final String TITLE = "参加しているグループ";

	private static final int MESSAGE_UPDATE = 1;
	private static final int FLAG_GROUP = 3;
	private static final int FLAG_USER = 4;

	private ListView listView;
	private List<Group> groups = new ArrayList<Group>();
	private User user = User.getInstance();
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
		Log.d("activity", "a  "+affiliation);
		List<Affiliation> a = daoSql.selectAffiliation();
		Log.d("activity", "a size "+a.size());
		permissionId = affiliation.getPermition().getId();
		intent.putExtra(GroupDetailsActivity.PERMISSION_ID, permissionId);
		startActivity(intent);
	}

	private void update() {
		GroupListAdapter adapter = new GroupListAdapter(getActivity()
				.getApplicationContext(), R.layout.item_group, groups);
		listView.setAdapter(adapter);
	}

	private void setMyGroupList() {

	}

	@Override
	public String toString() {
		return TITLE;
	}

}