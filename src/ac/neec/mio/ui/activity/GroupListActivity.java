package ac.neec.mio.ui.activity;

import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.ErrorConstants;
import ac.neec.mio.consts.MessageConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.ui.adapter.GroupListPagerAdapter;
import ac.neec.mio.ui.dialog.GroupSettingDialog;
import ac.neec.mio.ui.dialog.GroupSettingDialog.CallbackListener;
import ac.neec.mio.ui.dialog.LoadingDialog;
import ac.neec.mio.ui.listener.SearchNotifyListener;
import ac.neec.mio.user.User;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * グループ一覧タブ画面クラス
 */
public class GroupListActivity extends FragmentActivity implements Sourceable,
		CallbackListener {

	/**
	 * 追加エラーメッセージ
	 */
	private static final int MESSAGE_ADD_ERROR = 7;
	/**
	 * 不正文字入力エラーメッセージ
	 */
	private static final int MESSAGE_VALIDATE = 2;

	/**
	 * タブ
	 */
	private ViewPager viewPager;
	/**
	 * グループ検索を行うテキストフォーム
	 */
	private EditText searchGroup;
	/**
	 * タブ通知リスナー
	 */
	private List<SearchNotifyListener> listeners;
	/**
	 * WebAPI接続インスタンス
	 */
	private ApiDao dao;
	/**
	 * ローカルデータベース接続インスタンス
	 */
	private SQLiteDao daoSql;
	/**
	 * ユーザ情報
	 */
	private User user = User.getInstance();
	/**
	 * データ取得中ダイアログ
	 */
	private LoadingDialog dialog;
	/**
	 * グループ追加フラグ
	 */
	private boolean createGroup;

	/**
	 * 画面ハンドラー
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_ADD_ERROR:
				dialog.dismiss();
				Toast.makeText(getApplicationContext(),
						ErrorConstants.groupAdd(), Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_VALIDATE:
				dialog.dismiss();
				Toast.makeText(getApplicationContext(),
						ErrorConstants.scriptValidate(), Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_list);
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
		// dao.selectUser(user.getId(), user.getPassword());
		viewPager = (ViewPager) findViewById(R.id.pager);
		GroupListPagerAdapter adapter = new GroupListPagerAdapter(
				getSupportFragmentManager());
		listeners = adapter.getSearchListener();
		viewPager.setAdapter(adapter);
		setSearchEdit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.group_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_new:
			showNewGroupSettingDialog();
			break;
		case R.id.action_searth:
			if (searchGroup.getVisibility() == View.GONE) {
				searchGroup.setVisibility(View.VISIBLE);
			} else {
				searchGroup.setVisibility(View.GONE);
				for (SearchNotifyListener listener : listeners) {
					listener.onSearchEnd();
				}
			}
			break;
		}
		return true;
	}

	/**
	 * グループ検索結果をタブに通知する
	 */
	private void setSearchEdit() {
		searchGroup = (EditText) findViewById(R.id.search_group);
		searchGroup.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (!TextUtils.isEmpty(s.toString())) {
					for (SearchNotifyListener listener : listeners) {
						listener.onSearchText(s.toString());
					}

				} else {
					for (SearchNotifyListener listener : listeners) {
						listener.onClear();
					}
				}
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

	}

	/**
	 * グループ作成ダイアログを表示する
	 */
	private void showNewGroupSettingDialog() {
		new GroupSettingDialog(this, GroupSettingDialog.NEW_GROUP).show(
				getSupportFragmentManager(), "");
	}

	/**
	 * タブに更新を通知する
	 */
	private void setGroupList() {
		for (SearchNotifyListener listener : listeners) {
			listener.onUpdate();
		}
	}

	@Override
	public void notifyChenged(String groupId, String groupName, String comment) {
		createGroup = true;
		dao.insertGroup(groupId, groupName, comment, user.getId(),
				user.getPassword());
		dialog = new LoadingDialog(MessageConstants.add());
		dialog.show(getFragmentManager(), "dialog");
	}

	@Override
	public void complete() {
		dialog.dismiss();
		if (createGroup) {
			GroupInfo group = null;
			try {
				group = dao.getResponse();
			} catch (XmlParseException e) {
				Message message = new Message();
				message.what = MESSAGE_ADD_ERROR;
				handler.sendMessage(message);
				return;
			} catch (XmlReadException e) {
				Message message = new Message();
				message.what = MESSAGE_ADD_ERROR;
				handler.sendMessage(message);
				return;
			}
			dao.insertGroupAdmin(user.getId(), user.getId(), group.getId(),
					user.getPassword());
			createGroup = false;
		} else {
			setGroupList();
		}
	}

	@Override
	public void incomplete() {
	}

	@Override
	public void complete(Bitmap image) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate() {
		Message message = new Message();
		message.what = MESSAGE_VALIDATE;
		handler.sendMessage(message);
	}

}
