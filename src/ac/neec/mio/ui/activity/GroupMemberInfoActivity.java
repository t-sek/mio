package ac.neec.mio.ui.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.SQLConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Member;
import ac.neec.mio.http.item.TrainingItem;
import ac.neec.mio.ui.adapter.TrainingDateListAdapter;
import ac.neec.mio.ui.listener.TrainingDataListCallbackListener;
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
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class GroupMemberInfoActivity extends Activity implements Sourceable,
		TrainingDataListCallbackListener {

	private static final int MESSAGE_UPDATE = 1;
	private static final int MESSAGE_PROGRESS_GONE = 2;
	private static final int DATE_NUM = 50;

	private TextView textUserName;
	private TextView textUserId;
	private ExpandableListView listView;
	private ProgressBar progress;
	private TrainingDateListAdapter adapter;
	private String userId;
	private String userName;
	private ApiDao dao;
	private int date = 0;
	private int dateNum = DATE_NUM;

	private List<List<TrainingItem>> trainings = new ArrayList<List<TrainingItem>>();

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_UPDATE:
				update();
				break;
			case MESSAGE_PROGRESS_GONE:
				progressGone();
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
		Intent intent = getIntent();
		userId = intent.getStringExtra(Member.ID);
		userName = intent.getStringExtra(Member.NAME);
		dao = DaoFacade.getApiDao(this);
		// downloadTrainingData();
		initFindViews();
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
		dao.selectTraining(userId, DateUtil.getDate(date));
		progress.setProgress(date - DATE_NUM * (date / DATE_NUM));
		date++;
	}

	private void downloadTrainingData() {
		for (int i = 0; i < 50; i++) {
			dao.selectTraining(userId, DateUtil.getDate(i));
			// HttpManager.downloadTraining(getApplicationContext(), this,
			// userId, 0, DateUtil.getDate(i));
		}

	}

	private void initFindViews() {
		textUserName = (TextView) findViewById(R.id.text_user_name);
		textUserId = (TextView) findViewById(R.id.text_user_id);
		listView = (ExpandableListView) findViewById(R.id.list_training_data);
		listView.setEmptyView(findViewById(R.id.empty));
		progress = (ProgressBar) findViewById(R.id.progress);
		progress.setMax(DATE_NUM);
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
		TrainingItem item = trainings.get(groupPosition).get(childPosition);
		Intent intent = new Intent(GroupMemberInfoActivity.this,
				TrainingDataDetailActivity.class);
		intent.putExtra(SQLConstants.trainingId(), item.getTrainingId());
		intent.putExtra(SQLConstants.tableTraining(), item);
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

	@Override
	public void complete() {
		List<TrainingItem> list = null;
		try {
			list = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
			return;
		} catch (XmlReadException e) {
			e.printStackTrace();
			return;
		}
		if (list.size() == 0) {
			selectTraining();
			return;
		}
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
