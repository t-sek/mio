package ac.neec.mio.ui.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.android.gms.drive.internal.ad;

import ac.neec.mio.R;
import ac.neec.mio.consts.ErrorConstants;
import ac.neec.mio.consts.MessageConstants;
import ac.neec.mio.consts.PermissionConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.group.Member;
import ac.neec.mio.group.MemberInfo;
import ac.neec.mio.group.Permission;
import ac.neec.mio.ui.adapter.GroupMemberListAdapter;
import ac.neec.mio.ui.dialog.LoadingDialog;
import ac.neec.mio.util.BitmapUtil;
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
import android.widget.Toast;

public class GroupMemberListActivity extends Activity implements Sourceable {

	private static final int MESSAGE_UPDATE = 2;
	private static final int MESSAGE_IMAGE_UPDATE = 3;
	private static final int MESSAGE_ERROR = 4;

	private List<MemberInfo> members;
	private ListView listView;
	private GroupMemberListAdapter adapter;
	private GroupInfo group;
	private Permission permission;
	private List<MemberInfo> admin = new ArrayList<MemberInfo>();
	private List<MemberInfo> trainer = new ArrayList<MemberInfo>();
	private List<MemberInfo> member = new ArrayList<MemberInfo>();
	private ApiDao dao;
	private SQLiteDao daoSql;
	private LoadingDialog dialog;
	private int countMember = 0;

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_UPDATE:
				update();
				selectImage();
				break;
			case MESSAGE_IMAGE_UPDATE:
				selectImage();
				adapter.notifyDataSetChanged();
				break;
			case MESSAGE_ERROR:
				Toast.makeText(getApplicationContext(),
						ErrorConstants.networkError(), Toast.LENGTH_SHORT)
						.show();
			default:
				break;
			}
		};
	};

	@Override
	protected void onResume() {
		super.onResume();
		countMember = 0;
		if (adapter != null) {
			adapter.clear();
		}
		initMember();
		if (group != null) {
			dao.selectGroup(group.getId());
			dialog = new LoadingDialog();
			dialog.show(getFragmentManager(), "dialog");
		}
	}

	private void initMember() {
		members = new ArrayList<MemberInfo>();
		admin = new ArrayList<MemberInfo>();
		trainer = new ArrayList<MemberInfo>();
		member = new ArrayList<MemberInfo>();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_member_list);
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
		Intent intent = getIntent();
		String groupId = intent.getStringExtra("group_id");
		dao.selectGroup(groupId);
		dialog = new LoadingDialog(MessageConstants.getting());
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
					if (permission.getMemberDataCheck()) {
						intentMemberInfo(members.get(position));
					}
				}
			});
		}
	}

	private void update() {
		getActionBar().setTitle(group.getName());
		members = new ArrayList<MemberInfo>();
		members = group.getMembers();
		adapter = new GroupMemberListAdapter(getApplicationContext(),
				R.layout.activity_group_member_list, members);
		listView.setAdapter(adapter);
		setMemberList();
	}

	private void setMemberList() {
		int id = members.get(0).getPermissionId();
		Permission permission = daoSql.selectPermission(id);
		if (!permission.getJoinStatus()) {
			members.remove(0);
		}
		Iterator<MemberInfo> i = members.iterator();
		while (i.hasNext()) {
			id = i.next().getPermissionId();
			permission = daoSql.selectPermission(id);
			if (!permission.getJoinStatus()) {
				i.remove();
			}
		}
		setMembers();
	}

	private void setMembers() {
		for (MemberInfo member : members) {
			if (permission.getId() == PermissionConstants.groupAdmin()) {
				admin.add(member);
			} else if (permission.getId() == PermissionConstants.trainer()) {
				trainer.add(member);
			} else if (permission.getId() == PermissionConstants.member()) {
				this.member.add(member);
			}
		}
		members.clear();
		for (MemberInfo member : admin) {
			members.add(member);
		}
		for (MemberInfo member : trainer) {
			members.add(member);
		}
		for (MemberInfo member : member) {
			members.add(member);
		}
	}

	private void intentMemberInfo(MemberInfo info) {
		Intent intent = new Intent(GroupMemberListActivity.this,
				GroupMemberInfoActivity.class);
		List<MemberInfo> list = new ArrayList<MemberInfo>();
		list.add(info);
		intent.putExtra("user_id", info.getUserId());
		intent.putExtra("user_name", info.getUserName());
		intent.putExtra("permission_id", permission.getId());
		intent.putExtra("group_id", group.getId());
		intent.putExtra("member_permission_id", info.getPermissionId());
		byte[] image = BitmapUtil.bitmapToBlob(info.getImage());
		intent.putExtra("image", image);
		startActivity(intent);
	}

	private void selectImage() {
		Log.d("activity", "count " + countMember);
		Log.d("activity", "size " + members.size());
		if (countMember >= members.size()) {
			return;
		}
		MemberInfo member = members.get(countMember);
		Log.d("activity", "image name " + member.getImageName());
		if (member.getImageName() != null) {
			dao.selectImage(member.getImageName());
		} else {
			countMember++;
			selectImage();
		}
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
		// setMessage(MESSAGE_IMAGE_UPDATE);
	}

	@Override
	public void complete(Bitmap image) {
		Log.d("activity", "complete setImage " + image + " count "
				+ countMember);
		MemberInfo member = members.get(countMember);
		member.setImage(image);
		countMember++;
		// selectImage();
		setMessage(MESSAGE_IMAGE_UPDATE);
	}

	@Override
	public void incomplete() {
		setMessage(MESSAGE_ERROR);
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

}
