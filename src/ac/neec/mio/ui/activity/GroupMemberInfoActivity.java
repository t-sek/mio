package ac.neec.mio.ui.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.ErrorConstants;
import ac.neec.mio.consts.PermissionConstants;
import ac.neec.mio.consts.SQLConstants;
import ac.neec.mio.consts.SettingConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Permission;
import ac.neec.mio.http.item.TrainingItem;
import ac.neec.mio.taining.Training;
import ac.neec.mio.ui.adapter.TrainingDateListAdapter;
import ac.neec.mio.ui.dialog.SelectionAlertDialog;
import ac.neec.mio.ui.listener.AlertCallbackListener;
import ac.neec.mio.ui.listener.TrainingDataListCallbackListener;
import ac.neec.mio.user.User;
import ac.neec.mio.util.DateUtil;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class GroupMemberInfoActivity extends Activity implements Sourceable,
		TrainingDataListCallbackListener, AlertCallbackListener {

	private static final int MESSAGE_UPDATE = 1;
	private static final int MESSAGE_PROGRESS_GONE = 2;
	private static final int MESSAGE_NETWORK_ERROR = 3;
	private static final int DATE_NUM = 50;

	private TextView textUserName;
	private TextView textUserId;
	private ExpandableListView listView;
	private ProgressBar progress;
	private Button buttonTrainer;
	private Button buttonNotice;
	private TrainingDateListAdapter adapter;
	private String userId;
	private String userName;
	private String groupId;
	private ApiDao dao;
	private SQLiteDao daoSql;
	private Permission permission;
	private int date = 0;
	private int dateNum = DATE_NUM;
	private User user = User.getInstance();

	private List<List<Training>> trainings = new ArrayList<List<Training>>();

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_UPDATE:
				update();
				break;
			case MESSAGE_PROGRESS_GONE:
				progressGone();
			case MESSAGE_NETWORK_ERROR:
				Toast.makeText(getApplicationContext(),
						ErrorConstants.networkError(), Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		};
	};

	private void progressGone() {
		progress.setVisibility(View.GONE);
		progress.setProgress(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_info);
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
		Intent intent = getIntent();
		userId = intent.getStringExtra("user_id");
		userName = intent.getStringExtra("user_name");
		groupId = intent.getStringExtra("group_id");
		int permissionId = intent.getIntExtra("permission_id", 0);
		if (permissionId == 0) {
			permission = daoSql.selectPermission(PermissionConstants.notice());
		} else {
			permission = daoSql.selectPermission(permissionId);
		}
		// downloadTrainingData();
		initFindViews();
		setListener();
		setUserData();
		setAdapter();
		selectTraining();
	}

	private void update() {
		adapter.notifyDataSetChanged();
		selectTraining();
	}

	private void selectTraining() {
		if (date > dateNum) {
			dateNum += date;
			handler.sendMessage(setMessage(MESSAGE_PROGRESS_GONE));
			return;
		}
		// dao.selectTraining(userId, DateUtil.getDate(date));
		progress.setProgress(date - DATE_NUM * (date / DATE_NUM));
		date++;
	}

	private void initFindViews() {
		textUserName = (TextView) findViewById(R.id.text_user_name);
		textUserId = (TextView) findViewById(R.id.text_user_id);
		listView = (ExpandableListView) findViewById(R.id.list_training_data);
		listView.setEmptyView(findViewById(R.id.empty));
		progress = (ProgressBar) findViewById(R.id.progress);
		progress.setMax(DATE_NUM);
		buttonTrainer = (Button) findViewById(R.id.button_trainer);
		buttonNotice = (Button) findViewById(R.id.button_notice);
		if (!permission.getPermissionChange()) {
			buttonTrainer.setVisibility(View.INVISIBLE);
			buttonNotice.setVisibility(View.INVISIBLE);
		}

	}

	private void setListener() {
		buttonTrainer.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showTrainerDialog();
			}
		});
		buttonNotice.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showNoticeDialog();
			}
		});
	}

	private void showTrainerDialog() {
		SelectionAlertDialog dialog = new SelectionAlertDialog(this,
				SettingConstants.messageTrainer(),
				SettingConstants.messagePositive(),
				SettingConstants.messageNegative(), true);
		dialog.show(getFragmentManager(), "dialog");
	}

	private void showNoticeDialog() {
		SelectionAlertDialog dialog = new SelectionAlertDialog(this,
				SettingConstants.messageNotive(),
				SettingConstants.messagePositive(),
				SettingConstants.messageNegative(), true);
		dialog.show(getFragmentManager(), "dialog");
	}

	private void setAdapter() {
		adapter = new TrainingDateListAdapter(getApplicationContext(),
				trainings, this);
		listView.setAdapter(adapter);
		listView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// downloadTrainingLog(item);
				intentDetailTrainingData(groupPosition, childPosition);
				return false;
			}
		});

	}

	private void intentDetailTrainingData(int groupPosition, int childPosition) {
		Training item = trainings.get(groupPosition).get(childPosition);
		Intent intent = new Intent(GroupMemberInfoActivity.this,
				TrainingDataDetailActivity.class);
		intent.putExtra(SQLConstants.trainingId(), item.getId());
		startActivity(intent);
	}

	private void setUserData() {
		textUserName.setText(userName);
		textUserId.setText(userId);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void closeGroup(int position) {
		listView.collapseGroup(position);
	}

	private Message setMessage(int msg) {
		Message message = new Message();
		message.what = msg;
		return message;
	}

	private void setTraining(List<Training> list) {
		String nowDate = DateUtil.splitDate(list.get(0).getDate());
		for (int i = 0; i < trainings.size(); i++) {
			String date = DateUtil.splitDate(trainings.get(i).get(0).getDate());
			if (date.compareTo(nowDate) < 0) {
				trainings.add(i, list);
				adapter.notifyDataSetChanged();
				return;
			}
		}
		trainings.add(trainings.size(), list);
		handler.sendMessage(setMessage(MESSAGE_UPDATE));
	}

	@Override
	public void complete() {
		try {
			if (dao.getResponse() instanceof List<?>) {
				List<Training> list = null;
				list = dao.getResponse();
				if (list.size() == 0) {
					selectTraining();
					return;
				}
				setTraining(list);
			}
		} catch (XmlParseException e) {
			e.printStackTrace();
			return;
		} catch (XmlReadException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void incomplete() {
		handler.sendMessage(setMessage(MESSAGE_NETWORK_ERROR));
	}

	@Override
	public void complete(InputStream response) {
		// TODO Auto-generated method stub
	}

	@Override
	public void complete(Bitmap image) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onNegativeSelected(String message) {
	}

	@Override
	public void onPositiveSelected(String message) {
		if (message.equals(SettingConstants.messageTrainer())) {
			dao.insertGroupTrainer(user.getId(), userId, groupId, " ");
		} else if (message.equals(SettingConstants.messageNotive())) {
			dao.deleteGroupMember(user.getId(), userId, groupId, " ");
		}

	}

	@Override
	public void progressUpdate(int value) {
		// TODO Auto-generated method stub

	}
}
