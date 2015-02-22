package ac.neec.mio.ui.activity;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.ErrorConstants;
import ac.neec.mio.consts.MessageConstants;
import ac.neec.mio.consts.PermissionConstants;
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
import ac.neec.mio.ui.adapter.GroupListPagerAdapter;
import ac.neec.mio.ui.dialog.GroupSettingDialog;
import ac.neec.mio.ui.dialog.GroupSettingDialog.CallbackListener;
import ac.neec.mio.ui.dialog.LoadingDialog;
import ac.neec.mio.ui.listener.SearchNotifyListener;
import ac.neec.mio.user.User;
import ac.neec.mio.user.UserInfo;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class GroupListActivity extends FragmentActivity implements Sourceable,
		CallbackListener {

	private static final int MESSAGE_ADD_ERROR = 7;
	private static final int MESSAGE_VALIDATE = 2;

	private List<Group> list = new ArrayList<Group>();
	private ViewPager viewPager;
	private EditText searchGroup;
	private List<SearchNotifyListener> listeners;
	private ApiDao dao;
	private SQLiteDao daoSql;
	private User user = User.getInstance();
	private List<Affiliation> affiliations = new ArrayList<Affiliation>();
	private LoadingDialog dialog;
	private boolean createGroup;

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

	private void setMyGroupList(UserInfo info) {
		daoSql.deleteAffiliation();
		daoSql.deleteGroup();
		// List<Affiliation> affiliations = info.getAffiliations();
		List<Group> groups = info.getGroups();
		for (Group group : groups) {
			int permissionId = group.getPermissionId();
			if (permissionId != PermissionConstants.notice()
					&& permissionId != PermissionConstants.pending()) {
				Log.d("activity", "delete permission " + permissionId
						+ " group " + group.getGroupName());
				try {
					daoSql.insertAffiliation(group.getId(),
							group.getPermissionId());
					daoSql.insertGroup(group.getId(), group.getGroupName(),
							group.getComment(), group.getUserId(),
							group.getCreated(), group.getPermissionId());
				} catch (SQLiteInsertException e) {
					e.printStackTrace();
				} catch (SQLiteTableConstraintException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private void showNewGroupSettingDialog() {
		new GroupSettingDialog(this, GroupSettingDialog.NEW_GROUP).show(
				getSupportFragmentManager(), "");
	}

	private void intentGroupDetails(String groupId) {
		Intent intent = new Intent(GroupListActivity.this,
				GroupDetailsActivity.class);
		intent.putExtra("Group_Id", groupId);
		startActivity(intent);
	}

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
