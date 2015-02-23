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

/**
 * グループメンバー一覧画面クラス
 *
 */
public class GroupMemberListActivity extends Activity implements Sourceable {

	/**
	 * 更新メッセージ
	 */
	private static final int MESSAGE_UPDATE = 2;
	/**
	 * アイコン更新メッセージ
	 */
	private static final int MESSAGE_IMAGE_UPDATE = 3;
	/**
	 * エラーメッセージ
	 */
	private static final int MESSAGE_ERROR = 4;

	/**
	 * メンバー一覧
	 */
	private List<MemberInfo> members;
	/**
	 * メンバー一覧を表示するリストビュー
	 * 
	 */
	private ListView listView;
	/**
	 * メンバーリストアダプター
	 */
	private GroupMemberListAdapter adapter;
	/**
	 * グループ情報
	 */
	private GroupInfo group;
	/**
	 * 権限
	 */
	private Permission permission;
	/**
	 * 管理者リスト
	 */
	private List<MemberInfo> admin = new ArrayList<MemberInfo>();
	/**
	 * トレーナーリスト
	 */
	private List<MemberInfo> trainer = new ArrayList<MemberInfo>();
	/**
	 * メンバーリスト
	 */
	private List<MemberInfo> member = new ArrayList<MemberInfo>();
	/**
	 * WebAPI接続インスタンス
	 */
	private ApiDao dao;
	/**
	 * ローカルデータベース接続インスタンス
	 */
	private SQLiteDao daoSql;
	/**
	 * データ取得中ダイアログ
	 */
	private LoadingDialog dialog;
	/**
	 * メンバーのアイコン取得に使用するイテレータ
	 */
	private int countMember = 0;
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

	/**
	 * メンバーリストを初期化する
	 */
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

	/**
	 * 画面の初期化処理をする
	 */
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

	/**
	 * メンバー一覧を更新する
	 */
	private void update() {
		getActionBar().setTitle(group.getName());
		members = new ArrayList<MemberInfo>();
		members = group.getMembers();
		adapter = new GroupMemberListAdapter(getApplicationContext(),
				R.layout.activity_group_member_list, members);
		listView.setAdapter(adapter);
		setMemberList();
	}

	/**
	 * メンバーリストにメンバーを追加する
	 */
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

	/**
	 * 管理者、トレーナー、メンバーごとにメンバーを追加する
	 */
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

	/**
	 * メンバー詳細画面に遷移する
	 * 
	 * @param info
	 *            メンバー情報
	 */
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

	/**
	 * メンバーのアイコンを取得する
	 */
	private void selectImage() {
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

	/**
	 * 画面ハンドラーメッセージを設定する
	 * 
	 * @param msg
	 *            メッセージ
	 * @return メッセージインスタンス
	 */
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
