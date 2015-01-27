package ac.neec.mio.ui.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.android.gms.internal.bu;

import ac.neec.mio.R;
import ac.neec.mio.consts.PermissionConstants;
import ac.neec.mio.consts.SettingConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.group.Member;
import ac.neec.mio.group.Permission;
import ac.neec.mio.ui.adapter.GroupInfoListAdapter;
import ac.neec.mio.ui.adapter.GroupInfoListItem;
import ac.neec.mio.ui.dialog.GroupSettingDialog;
import ac.neec.mio.ui.dialog.GroupSettingDialog.CallbackListener;
import ac.neec.mio.ui.dialog.SelectionAlertDialog;
import ac.neec.mio.ui.listener.AlertCallbackListener;
import ac.neec.mio.user.User;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class GroupDetailsActivity extends FragmentActivity implements
		CallbackListener, Sourceable, AlertCallbackListener {

	private static final int MESSAGE_UPDATE = 20;

	public static final int INDEX_MEMBER = 0;
	public static final int INDEX_ADD_MEMBER = 1;
	public static final int INDEX_INVITE_MEMBER = 2;

	public static final String PERMISSION_ID = "perm_id";

	private List<GroupInfoListItem> list = new ArrayList<GroupInfoListItem>();

	private TextView textGroupName;
	private TextView textGroupId;
	private TextView textGroupComment;
	private Button buttonNotice;
	private Group group;
	private User user = User.getInstance();
	private boolean settingShowFlag = false;
	private boolean groupChenged;
	private ApiDao dao;
	private SQLiteDao daoSql;
	private Permission permission;
	private String groupId;

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
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_details);

		Intent intent = getIntent();
		groupId = intent.getStringExtra("Group_Id");
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
		group = daoSql.selectGroup(groupId);
		if (group == null) {
			dao.selectGroup(groupId);
			permission = daoSql.selectPermission(PermissionConstants.notice());
		} else {
			Affiliation aff = daoSql.selectAffiliation(groupId);
			permission = aff.getPermition();
		}
		initFindViews();
		update();
		setListItem();
		setAdapter();
		setInfoAdapter();
	}

	private void showEditGroupSettingDialog() {
		GroupSettingDialog dialog = new GroupSettingDialog(this,
				GroupSettingDialog.EDIT_GROUP);
		dialog.show(getSupportFragmentManager(), "");
		dialog.setGroupId(group.getId());
	}

	private void showPendingDialog() {
		SelectionAlertDialog dialog = new SelectionAlertDialog(this,
				SettingConstants.messagePending(),
				SettingConstants.messagePositive(),
				SettingConstants.messageNegative(), true);
		dialog.show(getFragmentManager(), "dialog");
	}

	private void showWithdrawalDialog() {
		SelectionAlertDialog dialog = new SelectionAlertDialog(this,
				SettingConstants.messageWithdrawal(),
				SettingConstants.messagePositive(),
				SettingConstants.messageNegative(), true);
		dialog.show(getFragmentManager(), "dialog");
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
				String item = list.get(position).getOperation();
				if (item.equals(SettingConstants.member())) {
					intentMember();
				} else if (item.equals(SettingConstants.groupInfoChange())) {
					showEditGroupSettingDialog();
				} else if (item.equals(SettingConstants.pending())) {
					showPendingDialog();
				} else if (item.equals(SettingConstants.withdrawal())) {
					showWithdrawalDialog();
				}
			}
		});
	}

	private void setInfoAdapter() {
		ListView infoView = (ListView) findViewById(R.id.list_info);
		LinearLayout layout = (LinearLayout) findViewById(R.id.infoLayout);
		if (!permission.getGroupNews()) {
			layout.setVisibility(View.INVISIBLE);
			return;
		}
		ArrayList<String> infoList = new ArrayList<String>();
		Collections.reverse(infoList);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, infoList);
		infoView.setAdapter(adapter);
	}

	private void setListItem() {
		if (permission.getGroupInfoChange()) {
			list.add(new GroupInfoListItem(SettingConstants.groupInfoChange(),
					null));
		}
		if (permission.getMemberListView()) {
			list.add(new GroupInfoListItem(SettingConstants.member(), null));
		}
		if (permission.getPermissionChange()) {
			list.add(new GroupInfoListItem(SettingConstants
					.permissionChangeTrainer(), null));
		}
		if (!permission.getJoinStatus()) {
			list.add(new GroupInfoListItem(SettingConstants.pending(), null));
		}

	}

	private void initFindViews() {
		textGroupId = (TextView) findViewById(R.id.text_group_id);
		textGroupId.setText(groupId);
		textGroupName = (TextView) findViewById(R.id.text_group_name);
		textGroupComment = (TextView) findViewById(R.id.text_group_comment);
		buttonNotice = (Button) findViewById(R.id.button_notice);
		if (permission.getJoinStatus()) {
			buttonNotice.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				}
			});
		} else {
			buttonNotice.setVisibility(View.INVISIBLE);
		}
	}

	private void deleteGroupMember() {
		dao.deleteGroupMember(user.getId(), user.getId(), groupId,
				user.getPassword());
	}

	private void intentMember() {
		Intent intent = new Intent(GroupDetailsActivity.this,
				GroupMemberListActivity.class);
		intent.putExtra("group_id", group.getId());
		intent.putExtra("permissionId", permission.getId());
		startActivity(intent);
	}

	private void update() {
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

	@Override
	public void complete() {
		GroupInfo info = null;
		try {
			info = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
		} catch (XmlReadException e) {
			e.printStackTrace();
		}
		Log.d("activity", "group created" + info.getCreated());
		Log.d("activity", "group created" + info.getUserId());
		for (Member m : info.getMembers()) {
			Log.d("activity", "group member" + m.getUserName());
		}
		group = new Group(info.getId(), info.getName(), null,
				info.getComment(), info.getUserId(), info.getCreated());
		Message message = new Message();
		message.what = MESSAGE_UPDATE;
		handler.sendMessage(message);
	}

	@Override
	public void complete(InputStream response) {

	}

	@Override
	public void complete(Bitmap image) {
		// TODO Auto-generated method stub

	}

	@Override
	public void incomplete() {

	}

	@Override
	public void onNegativeSelected(String message) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPositiveSelected(String message) {
		if (message.equals(SettingConstants.messagePending())) {
			dao.insertGroupPending(user.getId(), user.getId(), group.getId(),
					user.getPassword());
		} else if (message.equals(SettingConstants.messageWithdrawal())) {
			dao.deleteGroupMember(user.getId(), user.getId(), group.getId(),
					user.getPassword());
		}
	}

	@Override
	public void progressUpdate(int value) {
		// TODO Auto-generated method stub

	}

}