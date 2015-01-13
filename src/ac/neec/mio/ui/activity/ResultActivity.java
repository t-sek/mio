package ac.neec.mio.ui.activity;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.SQLConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.item.api.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.taining.Training;
import ac.neec.mio.taining.category.TrainingCategory;
import ac.neec.mio.taining.menu.TrainingMenu;
import ac.neec.mio.taining.play.TrainingPlay;
import ac.neec.mio.training.log.TrainingLog;
import ac.neec.mio.ui.dialog.LoadingDialog;
import ac.neec.mio.user.User;
import ac.neec.mio.util.CalorieUtil;
import ac.neec.mio.util.SpeechUtil;
import ac.neec.mio.util.TimeUtil;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.koba.androidrtchart.ColorBar;
import com.koba.androidrtchart.ColorBarItem;
import com.koba.androidrtchart.LineDot;
import com.koba.androidrtchart.LineDotTouchListener;
import com.koba.androidrtchart.LineGraph;

public class ResultActivity extends Activity implements Sourceable {

	private static final int FLAG_TRAINING = 2;
	private static final int FLAG_TRAINING_LOG = 3;
	private static final int FLAG_TRAINING_PLAY = 4;
	private static final int MESSAGE_NETWORK_ERROR = 6;

	private int trainingId;
	private int id;
	private static List<TrainingLog> trainingLogs = new ArrayList<TrainingLog>();
	private static List<TrainingPlay> trainingPlays = new ArrayList<TrainingPlay>();
	protected static Training training;
	protected static TrainingCategory trainingCategory;
	protected static User user = User.getInstance();
	protected List<TrainingLog> fitTrainingLogs = new ArrayList<TrainingLog>();

	private LoadingDialog httpLoadDialog;
	private ApiDao dao;
	private static SQLiteDao daoSql;

	protected static boolean isBackPressed;
	private int daoFlag;

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_NETWORK_ERROR:
				showNetworkError();
				break;
			default:
				break;
			}
		};
	};

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		Intent intent = getIntent();
		id = intent.getIntExtra(SQLConstants.id(), 1);
		int categoryId = intent.getIntExtra(SQLConstants.trainingCategoryId(), 1);
		dao = DaoFacade.getApiDao(getApplicationContext(), this);
		daoSql = DaoFacade.getSQLiteDao(getApplicationContext());
		isBackPressed = false;
		trainingLogs = daoSql.selectTrainingLog(id);
		trainingPlays = daoSql.selectTrainingPlay(id);
		training = daoSql.selectTraining(id);
		trainingCategory = daoSql.selectTrainingCategory(categoryId);
		insertTraining();
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		SpeechUtil.speechOnDestroy();
	}

	private void insertTrainingLog() {
		daoFlag = FLAG_TRAINING_LOG;
		for (TrainingLog log : trainingLogs) {
			dao.insertTrainingLog(trainingId, log.getHeartRate(),
					log.getDisX(), log.getDisY(),
					TimeUtil.stringToFormat(log.getTime()), log.getLap(),
					log.getSplit(), log.getLogId(), log.getTargetHeartRate());
		}
	}

	private void insertTrainingPlay() {
		daoFlag = FLAG_TRAINING_PLAY;
		for (TrainingPlay play : trainingPlays) {
			dao.insertTrainingPlay(trainingId, play.getPlayId(),
					play.getTrainingMenuId(),
					String.valueOf(play.getTrainingTime()));
		}
	}

	private void insertTraining() {
		httpLoadDialog = new LoadingDialog();
		httpLoadDialog.show(getFragmentManager(), "dialog");
		daoFlag = FLAG_TRAINING;
		Log.e("result", "date " + training.getDate());
		dao.insertTraining(user.getId(), training.getDate(), training
				.getStartTime(), String.valueOf(TimeUtil
				.stringToInteger(training.getPlayTime())), training
				.getTargetHrartRate(), training.getTargetCal(), training
				.getHeartRateAvg(), "0", training.getConsumptionCal(),
				trainingCategory.getTrainingCategoryId(), training
						.getDistance());
	}

	private void intentMapData() {
		Intent intent = new Intent(ResultActivity.this, MapDataActivity.class);
		intent.putExtra(SQLConstants.id(), trainingLogs.get(0).getId());
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.result, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.action_map:
			intentMapData();
			break;
		}
		return true;
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				intentDashboard();
				return false;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	private void intentDashboard() {
		Intent intent = new Intent(ResultActivity.this, TopActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			LineDotTouchListener {

		private LineGraph graph;
		private ColorBar colorBar;
		private TextView textDetailName;
		private TextView textDetailTime;
		private TextView textDetailMets;
		private TextView textDetailCalorie;
		private TextView textDetailDistance;
		private TextView textDatasTime;
		private TextView textDatasHeartRate;
		private TextView textDatasCalorie;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_result,
					container, false);
			initFindViews(rootView);
			setViewText();
			setColorBar();
			graph.setLineDotTouchListener(this);
			graph.setLineDotLmit(trainingLogs.size());
			graph.setTargetValue(training.getTargetHrartRate());
			for (TrainingLog log : trainingLogs) {
				graph.addLineDot(log.getHeartRate());
			}
			graph.calucurateAverageValue();
			graph.notifyDataSetChenged();
			return rootView;
		}

		private void initFindViews(View rootView) {
			graph = (LineGraph) rootView.findViewById(R.id.line_graph);
			colorBar = (ColorBar) rootView.findViewById(R.id.colorbar);
			textDetailName = (TextView) rootView
					.findViewById(R.id.detail_name_label);
			textDetailTime = (TextView) rootView
					.findViewById(R.id.detail_time_label);
			textDetailMets = (TextView) rootView
					.findViewById(R.id.detail_mets_label);
			textDetailCalorie = (TextView) rootView
					.findViewById(R.id.detail_calorie_label);
			textDetailDistance = (TextView) rootView
					.findViewById(R.id.detail_distance_label);
			textDatasTime = (TextView) rootView
					.findViewById(R.id.datas_time_label);
			textDatasHeartRate = (TextView) rootView
					.findViewById(R.id.datas_heartrate_label);
			textDatasCalorie = (TextView) rootView
					.findViewById(R.id.datas_calorie_label);
		}

		private void setViewText() {
			textDetailName.setText(trainingCategory.getTrainingCategoryName());
			textDetailTime.setText(training.getPlayTime());

			if (trainingPlays.size() != 0) {
				TrainingMenu menu = daoSql.selectTrainingMenu(trainingPlays
						.get(0).getTrainingMenuId());
				textDetailMets.setText(String.valueOf(menu.getMets()));
			}
			textDetailCalorie.setText(String.valueOf(training
					.getConsumptionCal()));
			textDetailDistance.setText(String.valueOf(training.getDistance()));

		}

		private void setColorBar() {
			ColorBarItem item;
			for (TrainingPlay play : trainingPlays) {
				Log.e("", "play " + play.getTrainingMenuId());
				item = new ColorBarItem(play.getTrainingTime(), "", daoSql
						.selectTrainingMenu(play.getTrainingMenuId())
						.getColor());
				colorBar.addBarItem(item);
				colorBar.notifyDataSetChenged();
			}
		}

		@Override
		public void onStart() {
			super.onResume();
		}

		@Override
		public void onActivityCreated(Bundle savedInstanceState) {
			super.onActivityCreated(savedInstanceState);
		}

		@Override
		public void onTouch(int index, LineDot dot) {
			String time = trainingLogs.get(index).getTimeString();
			textDatasTime.setText(TimeUtil.stringToFormat(time));
			textDatasHeartRate.setText(String.valueOf(trainingLogs.get(index)
					.getHeartRate()));
			textDatasCalorie.setText(String.valueOf(CalorieUtil
					.calcPlayCalorie(getActivity().getApplicationContext(),
							trainingPlays, user.getWeight(),
							Integer.valueOf(time))));
		}
	}

	private void deleteTraining() {
		daoSql.deleteTraining(id);
		daoSql.deleteTrainingLog(id);
		daoSql.deleteTrainingPlay(id);
	}

	private void storeUpComplete() {
		httpLoadDialog.dismiss();
		// Toast.makeText(getApplicationContext(), "保存しました", Toast.LENGTH_SHORT)
		// .show();
		deleteTraining();
	}

	private void showNetworkError() {
		httpLoadDialog.dismiss();
		// Toast.makeText(getApplicationContext(), "保存に失敗しました",
		// Toast.LENGTH_SHORT)
		// .show();
	}

	private void setMessage(int what) {
		Message message = new Message();
		message.what = what;
		handler.sendMessage(message);
	}

	@Override
	public void complete() {
		switch (daoFlag) {
		case FLAG_TRAINING:
			try {
				trainingId = dao.getResponse();
				Log.d("result", "trainingId " + trainingId);
			} catch (XmlParseException e) {
				e.printStackTrace();
				setMessage(MESSAGE_NETWORK_ERROR);
				return;
			} catch (XmlReadException e) {
				e.printStackTrace();
				setMessage(MESSAGE_NETWORK_ERROR);
				return;
			}
			insertTrainingLog();
		case FLAG_TRAINING_LOG:
			insertTrainingPlay();
			break;
		case FLAG_TRAINING_PLAY:
			storeUpComplete();
			break;
		default:
			break;
		}
	}

	@Override
	public void incomplete() {
		setMessage(MESSAGE_NETWORK_ERROR);
	}
}
