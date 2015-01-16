package ac.neec.mio.ui.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ac.neec.mio.R;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.ui.adapter.GroupListPagerAdapter;
import ac.neec.mio.ui.dialog.GroupSettingDialog;
import ac.neec.mio.ui.dialog.GroupSettingDialog.CallbackListener;
import ac.neec.mio.ui.listener.SearchNotifyListener;
import ac.neec.mio.user.User;
import ac.neec.mio.user.UserInfo;
import ac.neec.mio.util.DateUtil;
import ac.neec.mio.util.TimeUtil;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
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

public class GroupListActivity extends FragmentActivity implements Sourceable,
		CallbackListener {

	private static final int FLAG_USER = 2;
	private static final int FLAG_GROUP = 3;

	private List<Group> list = new ArrayList<Group>();
	private ViewPager viewPager;
	private EditText searchGroup;
	private SearchNotifyListener listener;
	private ApiDao dao;
	private SQLiteDao daoSql;
	private User user = User.getInstance();
	private List<Affiliation> affiliations = new ArrayList<Affiliation>();
	private int daoFlag;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_list);
		dao = DaoFacade.getApiDao(getApplicationContext(), this);
		daoSql = DaoFacade.getSQLiteDao(getApplicationContext());
		viewPager = (ViewPager) findViewById(R.id.pager);
		GroupListPagerAdapter adapter = new GroupListPagerAdapter(
				getSupportFragmentManager());
		listener = adapter.getSearchListener();
		viewPager.setAdapter(adapter);
		daoFlag = FLAG_USER;
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
				listener.onSearchEnd();
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
					listener.onSearchText(s.toString());
				} else {
					listener.onClear();
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
		// SpannableStringBuilder ssb = (SpannableStringBuilder)
		// editSearch.getText();
		// ImageSpan imageSpan = new ImageSpan(this,
		// R.drawable.ic_action_search);
		// ssb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

		// editSearch.setHint(ssb);

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
		GroupInfo group = null;
		try {
			group = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
		} catch (XmlReadException e) {
			e.printStackTrace();
		}
		if (group != null) {
			Log.d("activity", "group " + group.getId());
		} else {
			Log.d("activity", "group " + group);
		}
		listener.onUpdate();
	}

	private void setUser() {
		UserInfo userInfo = null;
		try {
			userInfo = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
		} catch (XmlReadException e) {
			e.printStackTrace();
		}
		affiliations = userInfo.getAffiliations();
	}

	@Override
	public void notifyChenged(String groupId, String groupName, String comment) {
		// HttpManager.uploadNewGroup(getApplicationContext(), this, id, name,
		// comment);
		daoFlag = FLAG_GROUP;
		dao.insertGroup(groupId, groupName, comment, DateUtil.nowDate(),
				user.getId(), user.getPassword());
	}

	@Override
	public void complete() {
		switch (daoFlag) {
		case FLAG_GROUP:
			setGroupList();
			break;
		case FLAG_USER:
			setUser();
		default:
			break;
		}
	}

	@Override
	public void incomplete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void complete(InputStream response) {
		// TODO Auto-generated method stub

	}

	@Override
	public void complete(Bitmap image) {
		// TODO Auto-generated method stub
		
	}

}
