package ac.neec.mio.ui.fragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.ErrorConstants;
import ac.neec.mio.consts.MessageConstants;
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
import ac.neec.mio.ui.activity.GroupDetailsActivity;
import ac.neec.mio.ui.adapter.GroupListAdapter;
import ac.neec.mio.ui.dialog.LoadingDialog;
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
import android.widget.Toast;

/**
 * 所属グループ一覧リストを表示する画面クラス
 */
public class MyGroupListFragment extends Fragment implements Sourceable,
		SearchNotifyListener {

	/**
	 * タブタイトル
	 */
	private static final String TITLE = "参加しているグループ";
	/**
	 * 更新メッセージ
	 */
	private static final int MESSAGE_UPDATE = 1;
	/**
	 * 設定メッセージ
	 */
	private static final int MESSAGE_SHOW_SETTING = 2;
	/**
	 * 取得メッセージ
	 */
	private static final int MESSAGE_SHOW_GETTING = 6;
	/**
	 * 所属グループ一覧を表示するリストビュー
	 */
	private ListView listView;
	/**
	 * 所属グループリスト
	 */
	private List<Group> groups = new ArrayList<Group>();
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
	 * データ取得中ダイアログ
	 */
	private LoadingDialog dialog;
	/**
	 * 画面ハンドラー
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_UPDATE:
				update();
				break;
			case MESSAGE_SHOW_GETTING:
				dialog = new LoadingDialog(MessageConstants.getting());
				dialog.show(getActivity().getFragmentManager(), "dialog");
				break;
			case MESSAGE_SHOW_SETTING:
				dialog = new LoadingDialog(MessageConstants.setting());
				dialog.show(getActivity().getFragmentManager(), "dialog");
				break;
			default:
				break;
			}
		};
	};

	@Override
	public void onResume() {
		super.onResume();
		dao.selectUser(user.getId(), user.getPassword());
		Message message = new Message();
		message.what = MESSAGE_SHOW_GETTING;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_my_group_list,
				container, false);
		listView = (ListView) view.findViewById(R.id.list_my_group);
		listView.setEmptyView(view.findViewById(R.id.empty));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String groupId = groups.get(position).getId();
				intentGroupDetails(groupId);
			}
		});
		dialog = new LoadingDialog(MessageConstants.getting());
		dialog.show(getActivity().getFragmentManager(), "dialog");
		return view;
	}

	/**
	 * グループ詳細画面に遷移する
	 * 
	 * @param groupId
	 *            グループID
	 */
	private void intentGroupDetails(String groupId) {
		Intent intent = new Intent(getActivity().getApplicationContext(),
				GroupDetailsActivity.class);
		intent.putExtra("Group_Id", groupId);
		int permissionId = 0;
		Affiliation affiliation = daoSql.selectAffiliation(groupId);
		List<Affiliation> a = daoSql.selectAffiliation();
		permissionId = affiliation.getPermition().getId();
		intent.putExtra("permission_id", permissionId);
		startActivity(intent);
	}

	/**
	 * リストを更新する
	 */
	private void update() {
		if (dialog != null) {
			dialog.dismiss();
		}
		groups = daoSql.selectGroupJoin();
		GroupListAdapter adapter = null;
		if (getActivity() != null) {
			adapter = new GroupListAdapter(getActivity()
					.getApplicationContext(), R.layout.item_group, groups,
					GroupListAdapter.MY);
		}
		listView.setAdapter(adapter);
	}

	/**
	 * ユーザ情報から所属グループを取得する
	 * 
	 * @param info
	 *            ユーザ情報
	 */
	private void setMyGroupList(UserInfo info) {
		dialog.dismiss();
		daoSql.deleteAffiliation();
		daoSql.deleteGroup();
		if (info == null) {
			return;
		}
		List<Group> groups = info.getGroups();
		for (Group group : groups) {
			try {
				daoSql.insertAffiliation(group.getId(), group.getPermissionId());
				daoSql.insertGroup(group.getId(), group.getGroupName(),
						group.getComment(), group.getUserId(),
						group.getCreated(), group.getPermissionId());
			} catch (SQLiteInsertException e) {
				e.printStackTrace();
			} catch (SQLiteTableConstraintException e) {
				e.printStackTrace();
			}
		}
		Message message = new Message();
		message.what = MESSAGE_UPDATE;
		handler.sendMessage(message);
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
		dao.selectUser(user.getId(), user.getPassword());
		Message message = new Message();
		message.what = MESSAGE_SHOW_GETTING;
		handler.sendMessage(message);
	}

	@Override
	public void complete() {
		if (dialog != null) {
			dialog.dismiss();
		}
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
	public void complete(Bitmap image) {
		// TODO Auto-generated method stub

	}

	@Override
	public void incomplete() {
		if (dialog != null) {
			dialog.dismiss();
		}
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

}