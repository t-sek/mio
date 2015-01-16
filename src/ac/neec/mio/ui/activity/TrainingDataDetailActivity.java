package ac.neec.mio.ui.activity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.SQLConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.db.DBManager;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.http.item.TrainingItem;
import ac.neec.mio.http.item.TrainingLogItem;
import ac.neec.mio.http.item.TrainingPlayItem;
import ac.neec.mio.taining.category.TrainingCategory;
import ac.neec.mio.taining.menu.TrainingMenu;
import ac.neec.mio.user.User;
import ac.neec.mio.util.CalorieUtil;
import ac.neec.mio.util.ColorUtil;
import ac.neec.mio.util.DateUtil;
import ac.neec.mio.util.TimeUtil;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.koba.androidrtchart.ColorBar;
import com.koba.androidrtchart.ColorBarItem;
import com.koba.androidrtchart.ColorBarListener;
import com.koba.androidrtchart.LineDot;
import com.koba.androidrtchart.LineDotTouchListener;
import com.koba.androidrtchart.LineGraph;

public class TrainingDataDetailActivity extends FragmentActivity implements
		Sourceable {

	interface CallbackListener {
		void onResponseLog();

		void onResponsePlay();
	}

	private static final int MESSAGE_LOG = 1;
	private static final int MESSAGE_PLAY = 2;

	private CallbackListener listener;

	private static TrainingItem training;
	private static TrainingLogItem trainingLog;
	private static List<TrainingPlayItem> trainingPlays = new ArrayList<TrainingPlayItem>();
	private static User user = User.getInstance();
	private ApiDao dao;
	private SQLiteDao daoSql;
	private boolean playSelect;

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_LOG:
				updateLog();
				break;
			case MESSAGE_PLAY:
				updatePlay();
				break;
			default:
				break;
			}
		};
	};

	private void updatePlay() {
		listener.onResponsePlay();
	}

	private void updateLog() {
		listener.onResponseLog();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_training_data_detail);
		if (savedInstanceState == null) {
			PlaceholderFragment f = new PlaceholderFragment();
			getFragmentManager().beginTransaction().add(R.id.container, f)
					.commit();
			listener = f.getListener();
		}
		Intent intent = getIntent();
		training = (TrainingItem) intent.getSerializableExtra(SQLConstants
				.tableTraining());
		getActionBar().setTitle(
				DateUtil.japaneseFormat(training.getDate()) + " "
						+ DateUtil.timeJapaneseFormat(training.getStartTime()));
		daoSql = DaoFacade.getSQLiteDao(getApplicationContext());
		dao = DaoFacade.getApiDao(getApplicationContext(), this);
		dao.selectTrainingPlay(user.getId(), training.getTrainingId());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.detail_training_date, menu);
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

	private void intentMapData() {
		Intent intent = new Intent(TrainingDataDetailActivity.this,
				MapDataActivity.class);
		intent.putExtra(SQLConstants.trainingId(), training.getTrainingId());
		startActivity(intent);
		// finish();
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment implements
			LineDotTouchListener, CallbackListener, ColorBarListener {

		private LineGraph graph;
		private TextView textDetailName;
		private TextView textDetailTime;
		private TextView textDetailMets;
		private TextView textDetailCalorie;
		private TextView textDetailDistance;
		private TextView textDatasTime;
		private TextView textDatasHeartRate;
		private TextView textDatasCalorie;

		private ColorBar colorbar;
		private SQLiteDao daoSql;

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_result,
					container, false);
			daoSql = DaoFacade.getSQLiteDao(getActivity()
					.getApplicationContext());
			initFindViews(rootView);
			setViewText();
			return rootView;
		}

		private void initFindViews(View rootView) {
			graph = (LineGraph) rootView.findViewById(R.id.line_graph);
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
			colorbar = (ColorBar) rootView.findViewById(R.id.colorbar);
			colorbar.setOnTouchListener(this);
		}

		private void setLineGraph() {
			graph.setLineDotTouchListener(this);
			graph.setLineDotLmit(trainingLog.getSize());
			graph.setTargetValue(training.getTargetHeartRate());
			for (int i = 0; i < trainingLog.getSize(); i++) {
				graph.addLineDot(trainingLog.getHeartRate(i));
			}
			graph.calucurateAverageValue();
			graph.notifyDataSetChenged();
		}

		private void setColorBar() {
			ColorBarItem item;
			for (TrainingPlayItem playItem : trainingPlays) {
				// String name = DBManager.selectTrainingMenuName(playItem
				// .getTrainingMenuId());
				String name = daoSql.selectTrainingMenuName(playItem
						.getTrainingMenuId());
				String color = null;
				// TrainingMenu menu = DBManager.selectTrainingMenu(playItem
				// .getTrainingMenuId());
				TrainingMenu menu = daoSql.selectTrainingMenu(playItem
						.getTrainingMenuId());
				if (menu != null) {
					color = menu.getColor();
				} else {
					color = ColorUtil.DEFAULT_COLOR;
				}
				item = new ColorBarItem(playItem.getTrainingTime(), name, color);
				colorbar.addBarItem(item);
			}

		}

		private void setViewText() {
			// TrainingCategory category = DBManager
			// .selectTrainingCategory(training.getCategoryId());
			TrainingCategory category = daoSql.selectTrainingCategory(training
					.getCategoryId());
			textDetailName.setText(category.getTrainingCategoryName());
			String time = TimeUtil.integerToString(Integer.valueOf(training
					.getPlayTime()));
			textDetailTime.setText(time);
			textDetailCalorie.setText(String.valueOf(training.getCal()));
			textDetailDistance.setText(String.valueOf(training.getDistance()));
		}

		protected CallbackListener getListener() {
			return this;
		}

		@Override
		public void onTouch(int index, LineDot dot) {
			textDatasHeartRate.setText(String.valueOf(trainingLog
					.getHeartRate(index)));
			textDatasTime.setText(trainingLog.getTime(index));
			int time;
			try {
				time = TimeUtil.stringToInteger(trainingLog.getTime(index));
			} catch (Exception e) {
				time = Integer.valueOf(trainingLog.getTime(index));
			}
			textDatasCalorie.setText(String.valueOf(CalorieUtil
					.calcPlayItemCalorie(getActivity().getApplicationContext(),
							trainingPlays,
							// user.getBodily().getWeight(),
							// Integer.valueOf(user.getWeight()), time)));
							user.getWeight(), time)));
		}

		@Override
		public void onResponseLog() {
			setLineGraph();
		}

		@Override
		public void onResponsePlay() {
			if (trainingPlays.size() != 0) {
				// TrainingMenu menu =
				// DBManager.selectTrainingMenu(trainingPlays
				// .get(0).getTrainingMenuId());
				TrainingMenu menu = daoSql.selectTrainingMenu(trainingPlays
						.get(0).getTrainingMenuId());
				if (menu != null) {
					textDetailMets.setText(String.valueOf(menu.getMets()));
				}
				setColorBar();
			}
		}

		@Override
		public void onTouch(int index, String comment) {
			Log.e("activity", "onTouch " + index);
		}

	}

	private Message setMessage(int msg) {
		Message message = new Message();
		message.what = msg;
		return message;
	}

	@Override
	public void complete() {
		if (!playSelect) {
			playSelect = true;
			try {
				trainingPlays = dao.getResponse();
			} catch (XmlParseException e) {
				e.printStackTrace();
			} catch (XmlReadException e) {
				e.printStackTrace();
			}
			handler.sendMessage(setMessage(MESSAGE_PLAY));
			dao.selectTrainingLog(user.getId(), training.getTrainingId());
		} else {
			try {
				trainingLog = dao.getResponse();
			} catch (XmlParseException e) {
				e.printStackTrace();
			} catch (XmlReadException e) {
				e.printStackTrace();
			}
			handler.sendMessage(setMessage(MESSAGE_LOG));
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
