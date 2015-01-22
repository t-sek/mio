package ac.neec.mio.ui.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.group.Member;
import ac.neec.mio.group.Permission;
import ac.neec.mio.http.HttpManager;
import ac.neec.mio.http.listener.GroupResponseListener;
import ac.neec.mio.ui.adapter.GroupInfoListAdapter;
import ac.neec.mio.ui.adapter.GroupInfoListItem;
import ac.neec.mio.ui.dialog.GroupSettingDialog;
import ac.neec.mio.ui.dialog.LoadingDialog;
import ac.neec.mio.ui.dialog.GroupSettingDialog.CallbackListener;
import ac.neec.mio.user.User;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;

public class GroupDetailsActivity extends FragmentActivity implements
		CallbackListener {

	private static final int MESSAGE_GROUP_NAME = 20;

	public static final int INDEX_MEMBER = 0;
	public static final int INDEX_ADD_MEMBER = 1;
	public static final int INDEX_INVITE_MEMBER = 2;

	public static final String PERMISSION_ID = "perm_id";

	private GroupInfoListItem[] list = new GroupInfoListItem[3];

	private TextView textGroupName;
	private TextView textGroupId;
	private TextView textGroupComment;
	private Group group;
	private User user = User.getInstance();
	private boolean settingShowFlag = false;
	private boolean groupChenged;
	private ApiDao dao;
	private SQLiteDao daoSql;
	private Permission permission;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_details);

		Intent intent = getIntent();
		String groupId = intent.getStringExtra("Group_Id");
		// int permissionId = intent.getIntExtra(PERMISSION_ID, 0);
		daoSql = DaoFacade.getSQLiteDao();
		// dao.selectGroup(groupId);
		group = daoSql.selectGroup(groupId);
		permission = daoSql.selectAffiliation(groupId).getPermition();
		Log.d("activity", "permission " + permission.getName());
		// permission = daoSql.selectPermission(permissionId);
		initFindViews(groupId);
		updateGroupName();
		setListItem();
		setAdapter();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		Log.d("avtivity", "flag " + settingShowFlag);
		if (settingShowFlag) {
			if (permission.getGroupInfoChange()) {
				getMenuInflater().inflate(R.menu.group_details, menu);
			}
		}
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_edit:
			showEditGroupSettingDialog();
			break;
		}
		return true;
	}

	private void showEditGroupSettingDialog() {
		GroupSettingDialog dialog = new GroupSettingDialog(this,
				GroupSettingDialog.EDIT_GROUP);
		dialog.show(getSupportFragmentManager(), "");
		dialog.setGroupId(group.getId());
	}

	private void setAdapter() {
		GroupInfoListAdapter adapter = new GroupInfoListAdapter(this,
				R.layout.activity_group_list, list);
		ListView listView = (ListView) findViewById(R.id.Group_operation);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				if (position == INDEX_MEMBER) {
					if (permission.getMemberListView()) {
						intentMember();
					}
				} else if (position == INDEX_ADD_MEMBER) {
					if (permission.getMemberAddManage()) {
						intentAddMember();
					}
				} else if (position == INDEX_INVITE_MEMBER) {
					intentInviteMember();
				}
			}
		});
	}

	private void setListItem() {
		list[INDEX_MEMBER] = new GroupInfoListItem("メンバー", null);
		list[INDEX_ADD_MEMBER] = new GroupInfoListItem("加入申請", null);
		list[INDEX_INVITE_MEMBER] = new GroupInfoListItem("メンバー招待", null);
	}

	private void initFindViews(String groupId) {
		textGroupId = (TextView) findViewById(R.id.text_group_id);
		textGroupId.setText(groupId);
		textGroupName = (TextView) findViewById(R.id.text_group_name);
		textGroupComment = (TextView) findViewById(R.id.text_group_comment);
	}

	private void intentMember() {
		Intent intent = new Intent(GroupDetailsActivity.this,
				GroupMemberListActivity.class);
		intent.putExtra("group_id", group.getId());
		intent.putExtra("permissionId", permission.getId());
		startActivity(intent);
	}

	private void intentAddMember() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("加入申請しますか？");
		alertDialogBuilder.setPositiveButton("はい",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dao.insertGroupAffiliation(user.getId(), group.getId(),
								8, user.getPassword());
					}
				});
		alertDialogBuilder.setNegativeButton("いいえ",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		alertDialogBuilder.setCancelable(true);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();

	}

	private void intentInviteMember() {

	}

	private void updateGroupName() {
		if (group != null) {
			textGroupName.setText(group.getGroupName());
			textGroupComment.setText(group.getComment());
		}
	}

	@Override
	public void notifyChenged(String id, String name, String comment) {
		dao.updateGroup(id, name, comment);
		// HttpManager.uploadEditGroup(getApplicationContext(), this, id, name,
		// comment);
	}

}