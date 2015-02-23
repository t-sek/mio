package ac.neec.mio.ui.fragment;

import java.io.InputStream;
import java.util.ArrayList;
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
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.ui.activity.GroupDetailsActivity;
import ac.neec.mio.ui.adapter.GroupListAdapter;
import ac.neec.mio.ui.dialog.LoadingDialog;
import ac.neec.mio.ui.listener.SearchNotifyListener;
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
 * 全てのグループ一覧を表示する画面クラス
 */
public class GroupListFragment extends Fragment implements
		SearchNotifyListener, Sourceable {

	/**
	 * タブタイトル
	 */
	private static final String TITLE = "全てのグループ";
	/**
	 * 更新メッセージ
	 */
	private static final int MESSAGE_UPDATE = 1;
	/**
	 * エラーメッセージ
	 */
	private static final int MESSAGE_ERROR = 2;

	/**
	 * グループリストビュー
	 */
	private ListView listView;
	/**
	 * グループリスト
	 */
	private List<Group> list = new ArrayList<Group>();
	/**
	 * グループリストビューのアダプター
	 */
	private GroupListAdapter adapter;
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
	private LoadingDialog dialog = new LoadingDialog(MessageConstants.getting());
	/**
	 * グループアイコン取得イテレータ
	 */
	private int countImage = 0;
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
			case MESSAGE_ERROR:
				networkError();
			default:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		dialog.show(getActivity().getFragmentManager(), "dialog");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_group_list, container,
				false);
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
		dao.selectGroupAll();
		listView = (ListView) view.findViewById(R.id.list_group);
		listView.setEmptyView(view.findViewById(R.id.empty));
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Group group = list.get(position);
				intentGroupDetails(group);
			}
		});
		return view;
	}

	/**
	 * コールバックリスナーを取得する
	 * 
	 * @return コールバックリスナー
	 */
	public SearchNotifyListener getListener() {
		return (SearchNotifyListener) this;
	}

	/**
	 * グループ詳細画面に遷移する
	 * 
	 * @param group
	 *            グループ情報
	 */
	private void intentGroupDetails(Group group) {
		Intent intent = new Intent(getActivity().getApplicationContext(),
				GroupDetailsActivity.class);
		intent.putExtra("Group_Id", group.getId());
		intent.putExtra("permission_id", group.getPermissionId());
		startActivity(intent);
	}

	/**
	 * グループリストを更新する
	 */
	private void update() {
		for (Group group : list) {
			for (Group myGroup : daoSql.selectGroup()) {
				if (group.getId().equals(myGroup.getId())) {
					group.setPermissionId(myGroup.getPermissionId());
				}
			}
		}
		if (getActivity() != null) {
			adapter = new GroupListAdapter(getActivity()
					.getApplicationContext(), R.layout.item_group, list,
					GroupListAdapter.ALL);
			listView.setAdapter(adapter);
		}
	}

	/**
	 * グループアイコンを取得する
	 */
	private void selectImage() {
		if (countImage >= list.size()) {
			return;
		}
		Group group = list.get(countImage);
		if (group.getImageName() != null) {
			dao.selectImage(group.getImageName());
		} else {
			countImage++;
			selectImage();
		}
	}

	/**
	 * ネットワークエラーのトーストを表示する
	 */
	private void networkError() {
		dialog.dismiss();
		Toast.makeText(getActivity().getApplicationContext(),
				ErrorConstants.networkError(), Toast.LENGTH_SHORT).show();
	}

	@Override
	public String toString() {
		return TITLE;
	}

	@Override
	public void onSearchText(String text) {
		adapter.getFilter().filter(text);
	}

	@Override
	public void onClear() {
		if (listView != null) {
			listView.clearTextFilter();
		}
	}

	@Override
	public void onSearchEnd() {
		dao.selectGroupAll();
	}

	@Override
	public void onUpdate() {
		dao.selectGroupAll();
	}

	@Override
	public void complete() {
		dialog.dismiss();
		try {
			list = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
		} catch (XmlReadException e) {
			e.printStackTrace();
		}
		Message message = new Message();
		message.what = MESSAGE_UPDATE;
		handler.sendMessage(message);
	}

	@Override
	public void incomplete() {
		if (dialog != null) {
			dialog.dismiss();
		}
		Message message = new Message();
		message.what = MESSAGE_ERROR;
		handler.sendMessage(message);
	}

	@Override
	public void complete(Bitmap image) {
		list.get(countImage).setImage(image);
		Message message = new Message();
		message.what = MESSAGE_UPDATE;
		handler.sendMessage(message);
		countImage++;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}
}