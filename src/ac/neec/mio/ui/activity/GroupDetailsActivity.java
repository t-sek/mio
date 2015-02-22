package ac.neec.mio.ui.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.android.gms.internal.bu;
import com.sek.circleimageview.CircleImageView;

import ac.neec.mio.R;
import ac.neec.mio.consts.ErrorConstants;
import ac.neec.mio.consts.MessageConstants;
import ac.neec.mio.consts.PermissionConstants;
import ac.neec.mio.consts.SettingConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.SQLiteInsertException;
import ac.neec.mio.exception.SQLiteTableConstraintException;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.group.MemberInfo;
import ac.neec.mio.group.Permission;
import ac.neec.mio.ui.adapter.GroupInfoListAdapter;
import ac.neec.mio.ui.adapter.GroupInfoListItem;
import ac.neec.mio.ui.adapter.GroupSettingListAdapter;
import ac.neec.mio.ui.dialog.GroupSettingDialog;
import ac.neec.mio.ui.dialog.GroupSettingDialog.CallbackListener;
import ac.neec.mio.ui.dialog.LoadingDialog;
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
import android.widget.Toast;

public class GroupDetailsActivity extends FragmentActivity implements
		CallbackListener, Sourceable, AlertCallbackListener {

	private static final int MESSAGE_UPDATE = 20;
	private static final int MESSAGE_IMAGE_UPDATE = 21;
	private static final int MESSAGE_ERROR = 22;
	private static final int MESSAGE_VALIDATE = 23;
	private List<GroupInfoListItem> list = new ArrayList<GroupInfoListItem>();

	private TextView textGroupName;
	private TextView textGroupId;
	private TextView textGroupComment;
	private TextView textPermissionName;
	private TextView textAdminName;
	private TextView textJoin;
	private TextView textPending;
	private TextView textNotice;
	private CircleImageView icon;
	private LinearLayout layoutPermission;
	private Button buttonNotice;
	private GroupInfoListAdapter adapter;
	private LoadingDialog dialog;
	private Group group;
	private List<MemberInfo> members = new ArrayList<MemberInfo>();
	private User user = User.getInstance();
	private ApiDao dao = DaoFacade.getApiDao(this);
	private SQLiteDao daoSql = DaoFacade.getSQLiteDao();
	private Permission permission;
	private String groupId;
	private Bitmap image;
	private int member;
	private int notice;
	private int pending;
	private boolean deleteGroup;

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_UPDATE:
				update();
				selectImage();
				break;
			case MESSAGE_IMAGE_UPDATE:
				updateImage();
				break;
			case MESSAGE_ERROR:
				Toast.makeText(getApplicationContext(),
						ErrorConstants.networkError(), Toast.LENGTH_SHORT)
						.show();
				break;
			case MESSAGE_VALIDATE:
				Toast.makeText(getApplicationContext(),
						ErrorConstants.validate(), Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onResume() {
		super.onResume();
		selectGroup();
	}

	private void selectGroup() {
		if (groupId != null) {
			dao.selectGroup(groupId);
			dialog = new LoadingDialog();
			dialog.show(getFragmentManager(), "dialog");
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_details);
		Intent intent = getIntent();
		groupId = intent.getStringExtra("Group_Id");
		int permissionId = intent.getIntExtra("permission_id",
				PermissionConstants.notice());
		permissionId = permissionId == 0 ? PermissionConstants.notice()
				: permissionId;
		permission = daoSql.selectPermission(permissionId);
		initFindViews();
		setAdapter();
	}

	private void showEditGroupSettingDialog() {
		GroupSettingDialog dialog = new GroupSettingDialog(this,
				GroupSettingDialog.EDIT_GROUP);
		Bundle bundle = new Bundle();
		bundle.putString("group_name", group.getGroupName());
		bundle.putString("comment", group.getComment());
		dialog.setArguments(bundle);
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
		adapter = new GroupInfoListAdapter(this, R.layout.activity_group_list,
				list);
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
				} else if (item.equals(SettingConstants.memberAdd())) {
					intentPendingMember();
				} else if (item.equals(SettingConstants
						.permissionChangeTrainer())) {
					intentAddTrainingMember();
				}
			}
		});
	}

	private void setListItem() {
		member -= notice + pending;
		if (permission.getGroupInfoChange()) {
			list.add(new GroupInfoListItem(SettingConstants.groupInfoChange(),
					null, 0));
		}
		if (permission.getMemberListView()) {
			list.add(new GroupInfoListItem(SettingConstants.member(), null,
					member));
		}
		if (permission.getPermissionChange()) {
			list.add(new GroupInfoListItem(SettingConstants
					.permissionChangeTrainer(), null, 0));
			if (pending != 0) {
				list.add(new GroupInfoListItem(SettingConstants.memberAdd(),
						null, pending));
			}
		}
		if (!permission.getJoinStatus()) {
			list.add(new GroupInfoListItem(SettingConstants.pending(), null, 0));
		}

	}

	private void updateAdapter() {
		adapter.clear();
		setListItem();
		adapter.notifyDataSetChanged();
	}

	private void initFindViews() {
		textGroupId = (TextView) findViewById(R.id.text_group_id);
		textGroupId.setText(groupId);
		textGroupName = (TextView) findViewById(R.id.text_group_name);
		textGroupComment = (TextView) findViewById(R.id.text_group_comment);
		textPermissionName = (TextView) findViewById(R.id.text_permission);
		textAdminName = (TextView) findViewById(R.id.managerName);
		textJoin = (TextView) findViewById(R.id.text_join);
		textPending = (TextView) findViewById(R.id.text_pending);
		textNotice = (TextView) findViewById(R.id.text_notice);
		icon = (CircleImageView) findViewById(R.id.Group_image);
		layoutPermission = (LinearLayout) findViewById(R.id.layout_member);
		buttonNotice = (Button) findViewById(R.id.button_notice);
		if (permission == null) {
			return;
		}
		if (permission.getJoinStatus()) {
			if (permission.getDissolution()) {
				buttonNotice.setText(SettingConstants.dissolution());
			}
			buttonNotice.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					showNoticeDialog();
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

	private void showNoticeDialog() {
		String message;
		if (permission.getDissolution()) {
			message = SettingConstants.messageDissolution();
		} else {
			message = SettingConstants.messageWithdrawal();
		}
		SelectionAlertDialog dialog = new SelectionAlertDialog(this, message,
				SettingConstants.messagePositive(),
				SettingConstants.messageNegative(), true);
		dialog.show(getFragmentManager(), "dialog");
	}

	private void intentPendingMember() {
		initMemberNum();
		Intent intent = new Intent(GroupDetailsActivity.this,
				GroupPermissionChangeActivity.class);
		intent.putExtra("group_id", groupId);
		intent.putExtra("permission_id", PermissionConstants.pending());
		startActivity(intent);
	}

	private void intentAddTrainingMember() {
		initMemberNum();
		Intent intent = new Intent(GroupDetailsActivity.this,
				GroupPermissionChangeActivity.class);
		intent.putExtra("group_id", groupId);
		intent.putExtra("permission_id", PermissionConstants.member());
		startActivity(intent);
	}

	private void intentMember() {
		initMemberNum();
		Intent intent = new Intent(GroupDetailsActivity.this,
				GroupMemberListActivity.class);
		intent.putExtra("group_id", group.getId());
		intent.putExtra("permissionId", permission.getId());
		startActivity(intent);
	}

	private void initMemberNum() {
		pending = 0;
		notice = 0;
	}

	private void selectImage() {
		if (group.getImageName() != null) {
			dao.selectImage(group.getImageName());
		}
	}

	private void updateImage() {
		icon.setImage(image);
	}

	private void update() {
		if (permission == null) {
			return;
		}
		if (group != null) {
			textGroupName.setText(group.getGroupName());
			textGroupComment.setText(group.getComment());
			textAdminName.setText(group.getAdminName());
			if (permission.getId() == PermissionConstants.notice()) {
				textNotice.setVisibility(View.VISIBLE);
				textJoin.setVisibility(View.INVISIBLE);
			} else if (permission.getId() == PermissionConstants.pending()) {
				textPending.setVisibility(View.INVISIBLE);
				textJoin.setVisibility(View.INVISIBLE);
			} else {
				layoutPermission.setVisibility(View.VISIBLE);
				textPermissionName.setText(permission.getName());
			}
			daoSql.deleteGroupMember(groupId);
			for (MemberInfo member : members) {
				try {
					daoSql.insertGroupMember(groupId, member.getUserId(),
							member.getUserName(), member.getPermissionId(),
							member.getImage());
				} catch (SQLiteInsertException e) {
					e.printStackTrace();
				} catch (SQLiteTableConstraintException e) {
					e.printStackTrace();
				}
			}
		}
		updateAdapter();
	}

	@Override
	public void notifyChenged(String id, String name, String comment) {
		if (comment == null || comment.length() == 0) {
			comment = "%20";
		}
		dao.updateGroup(id, name, comment);
		dialog = new LoadingDialog(MessageConstants.update());
		dialog.show(getFragmentManager(), "dialog");
	}

	@Override
	public void complete() {
		if (dialog != null) {
			dialog.dismiss();
		}
		if (deleteGroup) {
			finish();
			return;
		}
		GroupInfo info = null;
		try {
			info = dao.getResponse();
			members = info.getMembers();
		} catch (XmlParseException e) {
			e.printStackTrace();
		} catch (XmlReadException e) {
			e.printStackTrace();
		}
		if (info == null) {
			return;
		}
		if (info.getMembers().size() == 0) {
			dialog.show(getFragmentManager(), "dialog");
			dao.selectGroup(groupId);
			return;
		}
		if (info.getMembers().size() != 0) {
			member = info.getMembers().size();
		}
		for (MemberInfo member : info.getMembers()) {
			if (member.getPermissionId() == PermissionConstants.notice()) {
				notice++;
			} else if (member.getPermissionId() == PermissionConstants
					.pending()) {
				pending++;
			}
		}
		group = new Group(info.getId(), info.getName(), info.getImageName(),
				info.getComment(), info.getUserId(), info.getAdminName(),
				info.getCreated());
		Message message = new Message();
		message.what = MESSAGE_UPDATE;
		handler.sendMessage(message);
	}

	@Override
	public void complete(Bitmap image) {
		this.image = image;
		Message message = new Message();
		message.what = MESSAGE_IMAGE_UPDATE;
		handler.sendMessage(message);
	}

	@Override
	public void incomplete() {
		Message message = new Message();
		message.what = MESSAGE_ERROR;
		handler.sendMessage(message);
	}

	@Override
	public void onNegativeSelected(String message) {
	}

	@Override
	public void onPositiveSelected(String message) {
		if (message.equals(SettingConstants.messagePending())) {
			dao.insertGroupPending(user.getId(), user.getId(), group.getId(),
					user.getPassword());
			dialog.show(getFragmentManager(), "dialog");
		} else if (message.equals(SettingConstants.messageWithdrawal())) {
			dao.deleteGroupMember(user.getId(), user.getId(), group.getId(),
					user.getPassword());
			deleteGroup = true;
			dialog = new LoadingDialog(MessageConstants.delete());
			dialog.show(getFragmentManager(), "dialog");
		} else if (message.equals(SettingConstants.messageDissolution())) {
			deleteGroup = true;
			dao.deleteGroup(groupId, user.getId(), user.getPassword());
			dialog = new LoadingDialog(MessageConstants.delete());
			dialog.show(getFragmentManager(), "dialog");
		}
	}

	@Override
	public void validate() {
		Message message = new Message();
		message.what = MESSAGE_VALIDATE;
		handler.sendMessage(message);
	}
}