package ac.neec.mio.ui.activity;

import java.util.ArrayList;
import java.util.List;

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
import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.group.MemberInfo;
import ac.neec.mio.group.Permission;
import ac.neec.mio.ui.adapter.GroupInfoListAdapter;
import ac.neec.mio.ui.adapter.item.GroupInfoListItem;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * グループ詳細画面クラス
 *
 */
public class GroupDetailsActivity extends FragmentActivity implements
		CallbackListener, Sourceable, AlertCallbackListener {

	/**
	 * 更新メッセージ
	 */
	private static final int MESSAGE_UPDATE = 20;
	/**
	 * 画像更新メッセージ
	 */
	private static final int MESSAGE_IMAGE_UPDATE = 21;
	/**
	 * エラーメッセージ
	 */
	private static final int MESSAGE_ERROR = 22;
	/**
	 * 不正文字入力エラーメッセージ
	 */
	private static final int MESSAGE_VALIDATE = 23;
	/**
	 * 設定メニュー項目リスト
	 */
	private List<GroupInfoListItem> list = new ArrayList<GroupInfoListItem>();

	/**
	 * グループ名を表示するテキストビュー
	 */
	private TextView textGroupName;
	/**
	 * グループIDを表示するテキストビュー
	 */
	private TextView textGroupId;
	/**
	 * コメントを表示するテキストビュー
	 */
	private TextView textGroupComment;
	/**
	 * 権限名を表示するテキストビュー
	 */
	private TextView textPermissionName;
	/**
	 * 管理者を表示するテキストビュー
	 */
	private TextView textAdminName;
	/**
	 * 
	 */
	private TextView textJoin;
	/**
	 * 加入申請中を表示するテキストビュー
	 */
	private TextView textPending;
	/**
	 * 未加入を表示するテキストビュー
	 */
	private TextView textNotice;
	/**
	 * グループアイコンを表示するイメージビュー
	 */
	private CircleImageView icon;
	/**
	 * 権限レイアウトを定義
	 */
	private LinearLayout layoutPermission;
	/**
	 * グループ退会ボタン
	 */
	private Button buttonNotice;
	/**
	 * 設定リストアダプター
	 */
	private GroupInfoListAdapter adapter;
	/**
	 * データ取得中ダイアログ
	 */
	private LoadingDialog dialog;
	/**
	 * グループ情報
	 */
	private Group group;
	/**
	 * メンバーリスト
	 */
	private List<MemberInfo> members = new ArrayList<MemberInfo>();
	/**
	 * ユーザ情報
	 */
	private User user = User.getInstance();
	/**
	 * WebAPI接続インスタンス
	 */
	private ApiDao dao = DaoFacade.getApiDao(this);
	/**
	 * ローカルデータベース接続インスタンス
	 */
	private SQLiteDao daoSql = DaoFacade.getSQLiteDao();
	/**
	 * 権限
	 */
	private Permission permission;
	/**
	 * グループID
	 */
	private String groupId;
	/**
	 * グループアイコン
	 */
	private Bitmap image;
	/**
	 * メンバー数(加入申請中、未加入含む)
	 */
	private int member;
	/**
	 * 未加入数
	 */
	private int notice;
	/**
	 * 加入申請中数
	 */
	private int pending;
	/**
	 * グループ削除フラグ
	 */
	private boolean deleteGroup;

	/**
	 * 画面ハンドラー
	 */
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

	/**
	 * WebAPIからグループを取得する
	 */
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

	/**
	 * グループ編集ダイアログを表示する
	 */
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

	/**
	 * グループに加入ダイアログを表示する
	 */
	private void showPendingDialog() {
		SelectionAlertDialog dialog = new SelectionAlertDialog(this,
				SettingConstants.messagePending(),
				SettingConstants.messagePositive(),
				SettingConstants.messageNegative(), true);
		dialog.show(getFragmentManager(), "dialog");
	}

	/**
	 * グループに退会ダイアログを表示する
	 */
	private void showWithdrawalDialog() {
		SelectionAlertDialog dialog = new SelectionAlertDialog(this,
				SettingConstants.messageWithdrawal(),
				SettingConstants.messagePositive(),
				SettingConstants.messageNegative(), true);
		dialog.show(getFragmentManager(), "dialog");
	}

	/**
	 * 設定リストを追加する
	 */
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

	/**
	 * 設定リストを設定する
	 */
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

	/**
	 * 権限の変更後、設定リストを更新する
	 */
	private void updateAdapter() {
		adapter.clear();
		setListItem();
		adapter.notifyDataSetChanged();
	}

	/**
	 * 画面の初期化処理をする
	 */
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

	/**
	 * グループを解散、または退会ダイアログを表示する
	 */
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

	/**
	 * 加入申請承認画面に遷移する
	 */
	private void intentPendingMember() {
		initMemberNum();
		Intent intent = new Intent(GroupDetailsActivity.this,
				GroupPermissionChangeActivity.class);
		intent.putExtra("group_id", groupId);
		intent.putExtra("permission_id", PermissionConstants.pending());
		startActivity(intent);
	}

	/**
	 * トレーナー追加画面に遷移する
	 */
	private void intentAddTrainingMember() {
		initMemberNum();
		Intent intent = new Intent(GroupDetailsActivity.this,
				GroupPermissionChangeActivity.class);
		intent.putExtra("group_id", groupId);
		intent.putExtra("permission_id", PermissionConstants.member());
		startActivity(intent);
	}

	/**
	 * メンバー一覧画面に遷移する
	 */
	private void intentMember() {
		initMemberNum();
		Intent intent = new Intent(GroupDetailsActivity.this,
				GroupMemberListActivity.class);
		intent.putExtra("group_id", group.getId());
		intent.putExtra("permissionId", permission.getId());
		startActivity(intent);
	}

	/**
	 * メンバー数を初期化する
	 */
	private void initMemberNum() {
		pending = 0;
		notice = 0;
	}

	/**
	 * WebAPIからアイコンを取得する
	 */
	private void selectImage() {
		if (group.getImageName() != null) {
			dao.selectImage(group.getImageName());
		}
	}

	/**
	 * アイコンを更新する
	 */
	private void updateImage() {
		icon.setImage(image);
	}

	/**
	 * グループ情報を画面に更新する
	 */
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