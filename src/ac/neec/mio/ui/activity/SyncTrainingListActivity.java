package ac.neec.mio.ui.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.CreateTrainingIdException;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.training.Training;
import ac.neec.mio.training.log.TrainingLog;
import ac.neec.mio.training.play.TrainingPlay;
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
import android.graphics.Bitmap;
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
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
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
			uploadTrainingAll();
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
					training.getCategoryId()).getTrainingCategoryName();
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
		dialog = new LoadingDialog("保存中");
	}

	private void insertTraining() {
		daoFlag = FLAG_TRAINING;
		if (trainings.size() == 0) {
			return;
		}
		Training training = trainings.get(0);
		id = training.getId();
		trainingLogs = daoSql.selectTrainingLog(id);
		trainingPlays = daoSql.selectTrainingPlay(id);
		dao.insertTraining(user.getId(), user.getPassword(),
				training.getDate(), training.getStartTime(), String
						.valueOf(TimeUtil.stringToInteger(training
								.getPlayTime())),
				training.getTargetHrartRate(), training.getTargetCal(),
				training.getHeartRateAvg(), "0", training.getCalorie(),
				training.getCategoryId(), training.getDistance());
		trainings.remove(0);
	}

	private void insertTrainingLog() {
		daoFlag = FLAG_TRAINING_LOG;
		Log.d("activity", "trainingLog size " + trainingLogs.size());
		if (trainingLogs.size() != 0) {
			TrainingLog log = trainingLogs.get(0);
			dao.insertTrainingLog(trainingId, log.getHeartRate(),
					log.getDisX(), log.getDisY(),
					TimeUtil.stringToFormat(log.getTime()), log.getLap(),
					log.getSplit(), log.getLogId(), log.getTargetHeartRate());
			trainingLogs.remove(0);
		}
	}

	private void insertTrainingPlay() {
		daoFlag = FLAG_TRAINING_PLAY;
		if (trainingPlays.size() != 0) {
			TrainingPlay play = trainingPlays.get(0);
			dao.insertTrainingPlay(user.getId(), trainingId, play.getPlayId(),
					play.getTrainingMenuId(), play.getTrainingTime(),
					user.getPassword());
			trainingPlays.remove(0);
		}
	}

	private void deleteTraining() {
		Log.e("activity", "delete id " + id);
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
				trainingId = dao.getResponse();
				if (trainingId == 0) {
					throw new CreateTrainingIdException();
				}
				Log.d("activity", "trainingId " + trainingId);
			} catch (XmlParseException e) {
				e.printStackTrace();
				dialog.dismiss();
				setMessage(MESSAGE_ERROR);
				return;
			} catch (XmlReadException e) {
				e.printStackTrace();
				dialog.dismiss();
				setMessage(MESSAGE_ERROR);
				return;
			} catch (CreateTrainingIdException e) {
				e.printStackTrace();
				dialog.dismiss();
				setMessage(MESSAGE_ERROR);
				return;
			}
			insertTrainingLog();
		case FLAG_TRAINING_LOG:
			if (trainingLogs.size() != 0) {
				insertTrainingLog();
			} else {
				insertTrainingPlay();
			}
			break;
		case FLAG_TRAINING_PLAY:
			Log.e("activity", "play complete ");
			if (trainingPlays.size() != 0) {
				Log.e("activity", "insert play ");
				insertTrainingPlay();
			} else {
				if (trainings.size() != 0) {
					deleteTraining();
					insertTraining();
				} else {
					deleteTraining();
					Log.e("activity", "training finish");
					setMessage(MESSAGE_UNCLICK);
					setMessage(MESSAGE_UPDATE);
					dialog.dismiss();
				}
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

	private List<SyncTrainingItem> setTrainingAll() {
		List<SyncTrainingItem> items = new ArrayList<SyncTrainingItem>();
		for (int i = 0; i < adapter.getCount(); i++) {
			items.add(adapter.getItem(i));
		}
		return items;
	}

	private void uploadTrainingAll() {
		uploadItems(setTrainingAll());
	}

	private void deleteTrainingAll() {
		deleteItems(setTrainingAll());
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
	public void onNegativeSelected(String message) {

	}

	@Override
	public void onPositiveSelected(String message) {
		deleteTrainingAll();
	}

	@Override
	public void complete(Bitmap image) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

}