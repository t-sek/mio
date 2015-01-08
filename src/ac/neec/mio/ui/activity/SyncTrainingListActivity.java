package ac.neec.mio.ui.activity;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.item.api.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.taining.Training;
import ac.neec.mio.taining.play.TrainingPlay;
import ac.neec.mio.training.log.TrainingLog;
import ac.neec.mio.ui.adapter.SyncTrainingListAdapter;
import ac.neec.mio.ui.adapter.SyncTrainingListAdapter.CallbackListener;
import ac.neec.mio.ui.adapter.item.SyncTrainingItem;
import ac.neec.mio.ui.dialog.LoadingDialog;
import ac.neec.mio.ui.dialog.SelectionAlertDialog;
import ac.neec.mio.ui.fragment.top.TrainingDataFragment;
import ac.neec.mio.ui.listener.AlertCallbackListener;
import ac.neec.mio.user.User;
import ac.neec.mio.util.TimeUtil;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class SyncTrainingListActivity extends Activity implements Sourceable,
		CallbackListener, AlertCallbackListener {
	private static final int FLAG_TRAINING = 2;
	private static final int FLAG_TRAINING_LOG = 3;
	private static final int FLAG_TRAINING_PLAY = 4;
	private static final int MESSAGE_ERROR = 5;
	private static final int MESSAGE_NETWORK_ERROR = 6;
	private static final int MESSAGE_UNCLICK = 7;
	private static final int MESSAGE_UPDATE = 8;

	private ListView listView;
	private SyncTrainingListAdapter adapter;
	private LoadingDialog dialog;
	private ApiDao dao;
	private SQLiteDao daoSql;
	private List<SyncTrainingItem> list = new ArrayList<SyncTrainingItem>();
	private User user = User.getInstance();
	private int daoFlag;
	private int trainingId;
	private int id;
	private List<Training> trainings;
	private List<TrainingLog> trainingLogs;
	private List<TrainingPlay> trainingPlays;

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_ERROR:
				showError();
				break;
			case MESSAGE_NETWORK_ERROR:
				showNetworkError();
				break;
			case MESSAGE_UNCLICK:
				uncheckAll();
				break;
			case MESSAGE_UPDATE:
				updateAdapter();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sync_training_list);
		dao = DaoFacade.getApiDao(getApplicationContext(), this);
		daoSql = DaoFacade.getSQLiteDao(getApplicationContext());
		setTrainingList();
		adapter = new SyncTrainingListAdapter(savedInstanceState,
				getApplicationContext(), this, list);
		init();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		adapter.save(outState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// startActivityForResult(getIntent(),
		// TrainingDataFragment.REQUEST_CODE);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.sync_training, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_upload:
			break;
		case R.id.action_delete:
			showDeleteDialog();
			break;
		}
		return true;
	}

	private void setTrainingList() {
		List<Training> trainings = daoSql.selectTraining();
		if (trainings.size() == 0) {
			finish();
		}
		for (Training training : trainings) {
			String category = daoSql.selectTrainingCategory(
					training.getTrainingCategoryId()).getTrainingCategoryName();
			list.add(new SyncTrainingItem(training.getId(), training.getDate(),
					category));
		}
	}

	private void clickLog(int position) {
		Toast.makeText(this, "Item click: " + adapter.getItem(position),
				Toast.LENGTH_SHORT).show();
	}

	private void init() {
		listView = (ListView) findViewById(R.id.list_training);
		listView.setAdapter(adapter);
		adapter.setAdapterView(listView);
		adapter.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				// clickLog(position);
				adapter.setItemChecked(position, true);
			}
		});
		dialog = new LoadingDialog();
	}

	private void insertTraining() {
		daoFlag = FLAG_TRAINING;
		if (trainings.size() == 0) {
			return;
		}
		Training training = trainings.get(0);
		id = training.getId();
		dao.insertTraining(user.getId(), training.getDate(), training
				.getStartTime(), String.valueOf(TimeUtil
				.stringToInteger(training.getPlayTime())), training
				.getTargetHrartRate(), training.getTargetCal(), training
				.getHeartRateAvg(), "0", training.getConsumptionCal(), training
				.getTrainingCategoryId(), training.getDistance());
	}

	private void insertTrainingLog() {
		daoFlag = FLAG_TRAINING_LOG;
		trainingLogs = daoSql.selectTrainingLog(id);
		Log.d("activity", "log size " + trainingLogs.size());
		for (TrainingLog log : trainingLogs) {
			Log.d("activity", "log " + log.getId());
			dao.insertTrainingLog(trainingId, log.getHeartRate(),
					log.getDisX(), log.getDisY(),
					TimeUtil.stringToFormat(log.getTime()), log.getLap(),
					log.getSplit(), log.getLogId(), log.getTargetHeartRate());
		}
	}

	private void insertTrainingPlay() {
		daoFlag = FLAG_TRAINING_PLAY;
		trainingPlays = daoSql.selectTrainingPlay(id);
		for (TrainingPlay play : trainingPlays) {
			dao.insertTrainingPlay(trainingId, play.getPlayId(),
					play.getTrainingMenuId(),
					String.valueOf(play.getTrainingTime()));
		}
	}

	private void deleteTraining() {
		Log.d("activity", "delete " + id);
		daoSql.deleteTraining(id);
		daoSql.deleteTrainingLog(id);
		daoSql.deleteTrainingPlay(id);
	}

	private void uploadItems(List<SyncTrainingItem> items) {
		daoFlag = FLAG_TRAINING;
		trainings = new ArrayList<Training>();
		for (SyncTrainingItem item : items) {
			Training training = daoSql.selectTraining(item.getId());
			trainings.add(training);
		}
		insertTraining();
	}

	private void showDeleteDialog() {
		SelectionAlertDialog dialog = new SelectionAlertDialog(this,
				"トレーニングを削除しますか？", "はい", "いいえ", false);
		dialog.show(getFragmentManager(), "dialog");
	}

	private void showNetworkError() {
		Toast.makeText(getApplicationContext(), "ネットワークに接続できません",
				Toast.LENGTH_SHORT).show();
		uncheckAll();
	}

	private void showError() {
		Toast.makeText(getApplicationContext(), "同期に失敗しました", Toast.LENGTH_SHORT)
				.show();
		uncheckAll();
	}

	private void uncheckAll() {
		adapter.uncheckAll();
	}

	@Override
	public void complete() {
		switch (daoFlag) {
		case FLAG_TRAINING:
			try {
				setMessage(MESSAGE_UNCLICK);
				trainingId = dao.getResponse();
				// trainings.remove(0);
				daoSql.deleteTraining(id);
				trainings = daoSql.selectTraining();
				// updateAdapter();
				setMessage(MESSAGE_UPDATE);
			} catch (XmlParseException e) {
				e.printStackTrace();
				dialog.dismiss();
				setMessage(MESSAGE_ERROR);
				return;
			} catch (XmlReadException e) {
				e.printStackTrace();
				dialog.dismiss();
				showNetworkError();
				setMessage(MESSAGE_ERROR);
				return;
			}
			insertTrainingLog();
		case FLAG_TRAINING_LOG:
			insertTrainingPlay();
			break;
		case FLAG_TRAINING_PLAY:
			deleteTraining();
			if (trainings.size() != 0) {
				insertTraining();
			} else {
				dialog.dismiss();
			}
			break;
		default:
			break;
		}
	}

	private void setMessage(int what) {
		Message message = new Message();
		message.what = what;
		handler.sendMessage(message);
	}

	@Override
	public void incomplete() {
		dialog.dismiss();
		setMessage(MESSAGE_NETWORK_ERROR);
	}

	private void deleteItems(List<SyncTrainingItem> items) {
		uncheckAll();
		for (SyncTrainingItem item : items) {
			int id = item.getId();
			daoSql.deleteTraining(id);
			daoSql.deleteTrainingLog(id);
			daoSql.deleteTrainingPlay(id);
		}
		updateAdapter();
	}

	private void deleteTrainingAll() {
		List<SyncTrainingItem> items = new ArrayList<SyncTrainingItem>();
		for (int i = 0; i < adapter.getCount(); i++) {
			items.add(adapter.getItem(i));
		}
		deleteItems(items);
		finish();
	}

	private void updateAdapter() {
		adapter.clear();
		setTrainingList();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onUpload(List<SyncTrainingItem> items) {
		dialog.show(getFragmentManager(), "dialog");
		uploadItems(items);
	}

	@Override
	public void onDelete(List<SyncTrainingItem> items) {
		deleteItems(items);
	}

	@Override
	public void onNegativeSelected() {

	}

	@Override
	public void onPositiveSelected() {
		deleteTrainingAll();
	}
}