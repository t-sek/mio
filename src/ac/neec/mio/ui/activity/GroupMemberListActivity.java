package ac.neec.mio.ui.activity;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.group.Member;
import ac.neec.mio.http.HttpManager;
import ac.neec.mio.http.listener.HttpUserResponseListener;
import ac.neec.mio.ui.adapter.GroupMemberListAdapter;
import ac.neec.mio.user.UserInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.VolleyError;

public class GroupMemberListActivity extends Activity implements
		HttpUserResponseListener {

	private List<Member> members;
	private ListView listView;
	private GroupMemberListAdapter adapter;
	private boolean showMemberFlag;
	private String groupName;

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_member_list);

		Intent intent = getIntent();
		members = (List<Member>) intent.getSerializableExtra("member");
		showMemberFlag = intent.getBooleanExtra(
				GroupDetailsActivity.SHOW_ACTION_SETTING, false);
		intent.getBooleanExtra(GroupDetailsActivity.SHOW_ACTION_SETTING, false);
		groupName = intent.getStringExtra("group_name");
		getActionBar().setTitle(groupName);
		setMemberList();
	}

	private void setMemberList() {
		listView = (ListView) findViewById(R.id.list_group_member);
		adapter = new GroupMemberListAdapter(getApplicationContext(),
				R.layout.activity_group_member_list, members);
		listView.setAdapter(adapter);
		if (showMemberFlag) {
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					intentMemberInfo(members.get(position));
				}
			});
		}
	}

	private void intentMemberInfo(Member info) {
		Intent intent = new Intent(GroupMemberListActivity.this,
				GroupMemberInfoActivity.class);
		List<Member> list = new ArrayList<Member>();
		list.add(info);
		intent.putExtra(Member.ID, info.getUserId());
		intent.putExtra(Member.NAME, info.getUserName());
//		intent.putParcelableArrayListExtra("member",
//				(ArrayList<? extends Parcelable>) list);
		startActivity(intent);
	}

	@Override
	public void networkError(VolleyError error) {

	}

	@Override
	public void responseUserInfo(UserInfo user) {
		// memberInfo.add(user);
		// adapter.notifyDataSetChanged();
	}

	@Override
	public void responseUserLogin(boolean login) {
		// TODO Auto-generated method stub

	}

	@Override
	public void responseWeight(float weight) {
		// TODO Auto-generated method stub

	}

	@Override
	public void responseQuietHeartRate(int heartRate) {
		// TODO Auto-generated method stub

	}

}
