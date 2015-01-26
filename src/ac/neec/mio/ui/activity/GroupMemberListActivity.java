package ac.neec.mio.ui.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.group.Member;
import ac.neec.mio.group.Permission;
import ac.neec.mio.ui.adapter.GroupMemberListAdapter;
import ac.neec.mio.ui.dialog.LoadingDialog;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class GroupMemberListActivity extends Activity implements Sourceable {

	private static final int MESSAGE_UPDATE = 2;

	private List<Member> members;
	private ListView listView;
	private GroupMemberListAdapter adapter;
	private boolean showMemberFlag;
	private GroupInfo group;
	private Permission permission;
	private ApiDao dao;
	private SQLiteDao daoSql;
	private LoadingDialog dialog;

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

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_member_list);
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
		Intent intent = getIntent();
		members = (List<Member>) intent.getSerializableExtra("member");
		String groupId = intent.getStringExtra("group_id");
		dao.selectGroup(groupId);
		dialog = new LoadingDialog();
		dialog.show(getFragmentManager(), "dialog");
		int permissionId = intent.getIntExtra("permissionId", 0);
		permission = daoSql.selectPermission(permissionId);
		init();
	}

	private void init() {
		listView = (ListView) findViewById(R.id.list_group_member);
		if (permission.getMemberListView()) {
			listView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					intentMemberInfo(members.get(position));
				}
			});
		}
	}

	private void update() {
		getActionBar().setTitle(group.getName());
		members = group.getMembers();
		adapter = new GroupMemberListAdapter(getApplicationContext(),
				R.layout.activity_group_member_list, members);
		listView.setAdapter(adapter);
		setMemberList();
		Log.e("activity", "member size " + members.size());
	}

	private void setMemberList() {
		int id = members.get(0).getAffiliation().getPermition().getId();
		Permission permission = daoSql.selectPermission(id);
		if (!permission.getJoinStatus()) {
			members.remove(0);
		}
		Iterator<Member> i = members.iterator();
		while (i.hasNext()) {
			id = i.next().getAffiliation().getPermition().getId();
			permission = daoSql.selectPermission(id);
			if (!permission.getJoinStatus()) {
				i.remove();
			}
		}
		// for (Member member : members) {
		// if (member.getPermitionId() == pending()) {
		// members.remove(member);
		// }
		// }

	}

	private void intentMemberInfo(Member info) {
		Intent intent = new Intent(GroupMemberListActivity.this,
				GroupMemberInfoActivity.class);
		List<Member> list = new ArrayList<Member>();
		list.add(info);
		intent.putExtra("user_id", info.getAffiliation().getUserId());
		intent.putExtra("user_name", info.getUserName());
		intent.putExtra("permission_id", permission.getId());
		intent.putExtra("group_id", group.getId());
		startActivity(intent);
	}

	private void setMessage(int msg) {
		Message message = new Message();
		message.what = msg;
		handler.sendMessage(message);
	}

	@Override
	public void complete() {
		dialog.dismiss();
		try {
			group = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
		} catch (XmlReadException e) {
			e.printStackTrace();
		}
		setMessage(MESSAGE_UPDATE);
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

}
