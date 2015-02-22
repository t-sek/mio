package ac.neec.mio.ui.activity;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.ErrorConstants;
import ac.neec.mio.consts.SQLConstants;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.exception.SQLiteInsertException;
import ac.neec.mio.exception.SQLiteTableConstraintException;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.timer.TimerManager;
import ac.neec.mio.training.lap.LapItem;
import ac.neec.mio.training.lap.LapItemFactory;
import ac.neec.mio.training.log.TrainingLog;
import ac.neec.mio.training.menu.TrainingMenu;
import ac.neec.mio.training.play.TrainingPlay;
import ac.neec.mio.ui.dialog.ChangeTrainingSelectDialog;
import ac.neec.mio.ui.dialog.SelectionAlertDialog;
import ac.neec.mio.ui.fragment.BaseFragment;
import ac.neec.mio.ui.fragment.GraphicalMeasurementFragment;
import ac.neec.mio.ui.fragment.LapMeasurementFragment;
import ac.neec.mio.ui.fragment.MapMeasurementFragment;
import ac.neec.mio.ui.fragment.pager.MeasurementPagerAdapter;
import ac.neec.mio.ui.listener.AlertCallbackListener;
import ac.neec.mio.ui.listener.LocationCallbackListener;
import ac.neec.mio.ui.listener.MeasurementCallbackListener;
import ac.neec.mio.ui.listener.NotificationCallbackListener;
import ac.neec.mio.ui.listener.TimerCallbackListener;
import ac.neec.mio.user.User;
import ac.neec.mio.util.CalorieUtil;
import ac.neec.mio.util.DateUtil;
import ac.neec.mio.util.HeartRateUtil;
import ac.neec.mio.util.SpeechUtil;
import ac.neec.mio.util.TimeUtil;
import android.app.DialogFragment;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.koba.androidrtchart.ColorBar;
import com.koba.androidrtchart.ColorBarItem;
import com.koba.androidrtchart.ColorBarListener;
import com.viewpagerindicator.CirclePageIndicator;

public class MeasurementActivity extends BleConnectBaseActivity implements
		TimerCallbackListener, AlertCallbackListener, LocationCallbackListener,
		MeasurementCallbackListener, ColorBarListener {

	private static final int MESSAGE_TIME_UPDATE = 1;
	private static final int MESSAGE_NOTIFY_MIN = 2;
	private static final int MESSAGE_CONNECT_TIMEOUT = 3;
	private static final String DIALOG_MESSAGE = "トレーニングを終了しますか？";
	private static final String DIALOG_POSITIVE = "続ける";
	private static final String DIALOG_NEGATIVE = "終了する";

	private List<Fragment> fragments = new ArrayList<Fragment>();

	private Button buttonStart;
	private Button buttonStop;
	private Button buttonLap;
	private TextView textTime;
	private TextView textCalorie;
	private TextView textHeartRate;
	private TextView textDistance;
	private TextView textSpeed;
	private LinearLayout header;
	private LinearLayout footer;
	private ViewPager viewPager;
	private ColorBar colorbar;
	private ImageButton newTrainingButton;
	private ColorBarItem item;
	private MeasurementPagerAdapter adapter;
	private ProductDataFactory lapFactory = new LapItemFactory();

	private User user = User.getInstance();

	private List<NotificationCallbackListener> listeners = new ArrayList<NotificationCallbackListener>();

	private String time;
	private TimerManager timerManager;
	private boolean isMeasurement = false;
	private boolean isHeartRateRecived = false;
	private int id;
	private int trainingCategoryId;
	private int targetHeartRate = 180;
	private TrainingMenu trainingMenu;
	private String startTime;
	private int lastTrainingPlayTime;
	private int nowTrainingPlayTime;
	private int lastTrainingMenuId;
	private LapMeasurementFragment lapFragment;

	private double disX = 0;
	private double disY = 0;
	private double distance = 0;
	private List<Float> speeds = new ArrayList<Float>();
	private SQLiteDao daoSql;

	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_TIME_UPDATE:
				updateTime();
				updateCalorie();
				break;
			case MESSAGE_NOTIFY_MIN:
				notifyMinSpeed();
				break;
			case MESSAGE_CONNECT_TIMEOUT:
				Toast.makeText(getApplicationContext(),
						ErrorConstants.connectTimeout(), Toast.LENGTH_SHORT)
						.show();
				intentDeviceScan();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void receiveHeartRate(String heartRate) {
		int value = Integer.valueOf(heartRate);
		if (isMeasurement) {
			for (NotificationCallbackListener listener : listeners) {
				listener.notifyValue(value);
			}
			textHeartRate.setText(heartRate);
			try {
				daoSql.insertTrainingLog(id, value, disX, disY,
						String.valueOf(TimeUtil.stringToInteger(time)), "0",
						"0", targetHeartRate);
			} catch (SQLiteInsertException e) {
				e.printStackTrace();
			} catch (SQLiteTableConstraintException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_measurement);
		Intent intent = getIntent();
		trainingCategoryId = intent.getIntExtra(
				SQLConstants.trainingCategoryId(), 1);
		int trainingMenuId = intent.getIntExtra(SQLConstants.trainingMenuId(),
				1);
		daoSql = DaoFacade.getSQLiteDao();
		trainingMenu = daoSql.selectTrainingMenu(trainingMenuId);
		initFindViews();
		setListeners();
		setPagerAdapter();
		setTrainingPagerAdapter(trainingCategoryId);
		getActionBar().setTitle(trainingMenu.getTrainingName());
		lastTrainingMenuId = trainingMenu.getTrainingMenuId();
		startTime = TimeUtil.nowDateTime();
		checkBleSupport();
		timerManager = new TimerManager(this);
		timerManager.reset();
		try {
			id = daoSql.insertTraining(trainingCategoryId, user.getId(),
					DateUtil.getDate(0), startTime, null, 0, 0, 0, 0, 0, 0);
		} catch (SQLiteInsertException e) {
			e.printStackTrace();
		} catch (SQLiteTableConstraintException e) {
			e.printStackTrace();
		}
	}

	private void showSelectionAlertDialog() {
		DialogFragment newFragment = new SelectionAlertDialog(this,
				DIALOG_MESSAGE, DIALOG_POSITIVE, DIALOG_NEGATIVE, false);
		newFragment.show(getFragmentManager(), "dialog");
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
				stopMeasurement();
				showSelectionAlertDialog();
				return false;
			}
		}
		return super.dispatchKeyEvent(event);
	}

	private void setTrainingPagerAdapter(final int trainingCategoryId) {
		List<String> trainingName = new ArrayList<String>();
		for (TrainingMenu menu : daoSql
				.selectTrainingCategoryMenu(trainingCategoryId)) {
			trainingName.add(menu.getTrainingName());
		}
	}

	private void setPagerAdapter() {
		adapter = new MeasurementPagerAdapter(getSupportFragmentManager());
		setMeasurementFragment();
		adapter.addFragmentAll(fragments);
		viewPager = (ViewPager) findViewById(R.id.pager_measuremet);
		viewPager.setAdapter(adapter);
		viewPager.setId(R.id.pager_measuremet);
		CirclePageIndicator indicator = (CirclePageIndicator) findViewById(R.id.indicator_measurement);
		indicator.setViewPager(viewPager);
		indicator.setCurrentItem(1);
	}

	private void setMeasurementFragment() {
		BaseFragment fragment1 = new MapMeasurementFragment(
				getSupportFragmentManager(), this);
		BaseFragment fragment2 = new GraphicalMeasurementFragment(this);
		BaseFragment fragment3 = new LapMeasurementFragment();
		lapFragment = (LapMeasurementFragment) fragment3;
		fragments.add(fragment1);
		fragments.add(fragment2);
		fragments.add(fragment3);
		for (Fragment fragment : fragments) {
			listeners.add((NotificationCallbackListener) fragment);
		}
		for (NotificationCallbackListener listener : listeners) {
			listener.trainingId(id, trainingCategoryId);
		}
	}

	private void initFindViews() {
		header = (LinearLayout) findViewById(R.id.include_measurement_header);
		footer = (LinearLayout) findViewById(R.id.include_measurement_footer);
		buttonStart = (Button) footer.findViewById(R.id.button_start);
		buttonStop = (Button) footer.findViewById(R.id.button_stop);
		buttonLap = (Button) footer.findViewById(R.id.btn_finish);
		buttonLap.setVisibility(View.GONE);
		textTime = (TextView) header.findViewById(R.id.txt_time);
		textCalorie = (TextView) header.findViewById(R.id.txt_calorie);
		textHeartRate = (TextView) header
				.findViewById(R.id.txt_measurement_heart_rate);
		textDistance = (TextView) header.findViewById(R.id.txt_distance);
		textSpeed = (TextView) header.findViewById(R.id.txt_speed);
		colorbar = (ColorBar) header.findViewById(R.id.colorbar);
		colorbar.setOnTouchListener(this);
		newTrainingButton = (ImageButton) header
				.findViewById(R.id.btn_new_training);
		newTrainingButton.setVisibility(View.GONE);
		item = new ColorBarItem(1, trainingMenu.getTrainingName(),
				trainingMenu.getColor());
		colorbar.addBarItem(item);
	}

	private void setActionBer(String trainingName) {
		trainingMenu = daoSql.selectTrainingMenu(trainingName);
		getActionBar().setTitle(trainingName);
	}

	private void setStartMeasurement() {
		buttonStart.setVisibility(View.INVISIBLE);
		buttonStop.setVisibility(View.VISIBLE);
		if (trainingMenu == null) {
			trainingMenu = daoSql
					.selectTrainingCategoryMenu(trainingCategoryId).get(0);
		}
		if (daoSql.selectTrainingPlay(id).size() == 0) {
			lastTrainingMenuId = trainingMenu.getTrainingMenuId();
			item = new ColorBarItem(0, trainingMenu.getTrainingName(), daoSql
					.selectTrainingMenu(lastTrainingMenuId).getColor());
			colorbar.addBarItem(item);
		}
		if (!isHeartRateRecived) {
			if (item == null) {
			}
			connectBleDevice();
		} else {
			startMeasurement();
		}
	}

	/**
	 * 停止
	 */
	private void setStopMeasurement() {
		buttonStart.setVisibility(View.VISIBLE);
		buttonStop.setVisibility(View.INVISIBLE);
		isMeasurement = false;
		stopMeasurement();
	}

	/**
	 * リスナー設定
	 */
	private void setListeners() {
		buttonStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setStartMeasurement();
			}
		});
		buttonStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setStopMeasurement();
			}
		});
		buttonLap.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (buttonLap.getText()
						.equals(getResources().getString(
								R.string.btn_measurement_rest))) {
					timerManager.lap();
				} else {
					showSelectionAlertDialog();
				}
			}
		});
		newTrainingButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showTrainingDialog();
			}
		});
	}

	/**
	 * トレーニング追加ダイアログ表示
	 */
	private void showTrainingDialog() {
		// stopMeasurement();
		setStopMeasurement();
		ChangeTrainingSelectDialog dialog = new ChangeTrainingSelectDialog(
				this, trainingCategoryId);
		dialog.show(getSupportFragmentManager(), "dialog");
	}

	/**
	 * トップ画面へ遷移
	 */
	private void intentTop() {
		Intent intent = new Intent(MeasurementActivity.this, TopActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * デバイス設定画面へ遷移
	 */
	private void intentDeviceScan() {
		Intent intent = new Intent(MeasurementActivity.this,
				DeviceSettingActivity.class);
		startActivity(intent);
	}

	/**
	 * 計測結果画面へ遷移
	 */
	private void intentMeasurementResult() {
		Intent intent = new Intent(MeasurementActivity.this,
				ResultActivity.class);
		intent.putExtra(SQLConstants.id(), id);
		intent.putExtra(SQLConstants.trainingCategoryId(), trainingCategoryId);
		startActivity(intent);
		finish();
	}

	/**
	 * 計測停止
	 */
	private void stopMeasurement() {
		if (timerManager != null) {
			timerManager.stop();
		}
		buttonLap.setText(R.string.btn_measurement_end);
		buttonLap.setVisibility(View.VISIBLE);
		SpeechUtil.speech(getApplicationContext(), "アクティビティー、停止");
	}

	/**
	 * 計測開始
	 */
	private void startMeasurement() {
		isMeasurement = true;
		if (timerManager != null) {
			timerManager.start();
		}
		buttonLap.setText(R.string.btn_measurement_rest);
		buttonLap.setVisibility(View.VISIBLE);
		newTrainingButton.setVisibility(View.VISIBLE);
		buttonLap.setText(R.string.btn_measurement_rest);
		SpeechUtil.speech(getApplicationContext(), "アクティビティー、開始");
	}

	/**
	 * タイム更新
	 */
	private void updateTime() {
		textTime.setText(timerManager.getMeasurementTime());
		item.setBarValue(nowTrainingPlayTime);
		colorbar.notifyDataSetChenged();
		for (NotificationCallbackListener listener : listeners) {
			listener.notifyTime(timerManager.getMeasurementTime());
		}
	}

	/**
	 * カロリー更新
	 */
	private void updateCalorie() {
		List<TrainingPlay> play = daoSql.selectTrainingPlay(id);
		int calorie;
		if (play.size() == 0) {
			calorie = CalorieUtil.calcCalorie(trainingMenu.getMets(),
					TimeUtil.timeToLong(TimerManager.getTime()),
					user.getWeight());
		} else {
			calorie = CalorieUtil.calcPlayCalorie(play, user.getWeight(),
					TimeUtil.timeToInteger(TimerManager.getTime()));
		}
		for (NotificationCallbackListener listener : listeners) {
			listener.notifyCalorie(CalorieUtil.calcCalorie(
					trainingMenu.getMets(),
					TimeUtil.timeToLong(TimerManager.getTime()),
					user.getWeight()));
		}
		textCalorie.setText(String.valueOf(calorie));
	}

	private void notifyMinSpeed() {
		float avg = 0;
		for (float speed : speeds) {
			avg += speed;
		}
		avg /= speeds.size();
		Float f = new Float(avg);
		if (!f.equals(Float.NaN)) {
			textSpeed.setText(String.valueOf(avg));
		}
		speeds.clear();
	}

	@Override
	protected void bleConnected() {
		isHeartRateRecived = true;
		startMeasurement();
		isMeasurement = true;
	}

	@Override
	protected void bleDisconnected() {
		isHeartRateRecived = false;
		setStopMeasurement();
		connectBleDevice();
	}

	@Override
	public void onNegativeSelected(String message) {
		// トレーニング終了
		if (id == 0 && trainingMenu == null
				|| textCalorie.getText().equals("---")) {
			intentTop();
			return;
		}
		try {
			daoSql.updateTraining(id, timerManager.getMeasurementTime(),
					Integer.valueOf(textCalorie.getText().toString()),
					targetHeartRate, HeartRateUtil.heartRateAvg(id), distance);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		try {
			daoSql.insertTrainingPlay(id, lastTrainingMenuId,
					nowTrainingPlayTime);
		} catch (SQLiteInsertException e) {
			e.printStackTrace();
		} catch (SQLiteTableConstraintException e) {
			e.printStackTrace();
		}
		bleDisconnect();
		SpeechUtil.speech(getApplicationContext(), "おつかれさまでした");
		intentMeasurementResult();
	}

	@Override
	public void onPositiveSelected(String message) {
		if (isMeasurement) {
			startMeasurement();
		}
	}

	@Override
	public void onUpdate(String time) {
		// タイム更新
		this.time = TimeUtil.longTimeToSmartTime(time);
		Message message = new Message();
		message.what = MESSAGE_TIME_UPDATE;
		handler.sendMessage(message);
		int timeInt = TimeUtil.stringToInteger(this.time);
		nowTrainingPlayTime = timeInt - lastTrainingPlayTime;
		item.setBarValue(nowTrainingPlayTime);
		colorbar.notifyDataSetChenged();
	}

	@Override
	public void onUpdateLap(String time) {
		LapItem item = (LapItem) lapFactory.create(time,
				timerManager.getMeasurementTime(), String.valueOf(distance));
		lapFragment.setLapTime(item);
	}

	@Override
	public void onAlarm() {
	}

	@Override
	public void notifyMin() {
		Message message = new Message();
		message.what = MESSAGE_NOTIFY_MIN;
		handler.sendMessage(message);
	}

	@Override
	public void onConnected() {
		super.onConnected();
		startMeasurement();
	}

	@Override
	public void onCancel() {
		super.onCancel();
	}

	@Override
	protected void requestEnable() {
	}

	@Override
	protected void requestNotEnable() {
		intentTop();
	}

	@Override
	public void onSelected(int trainingMenuId) {
		// トレーニングメニュー追加
		String trainingName = daoSql
				.selectTrainingCategoryMenu(trainingCategoryId)
				.get(trainingMenuId).getTrainingName();
		trainingMenuId = daoSql.selectTrainingMenu(trainingName)
				.getTrainingMenuId();
		if (trainingMenuId == lastTrainingMenuId) {
			setStartMeasurement();
			return;
		}
		setActionBer(trainingName);
		try {
			daoSql.insertTrainingPlay(id, lastTrainingMenuId,
					nowTrainingPlayTime);
		} catch (SQLiteInsertException e) {
			e.printStackTrace();
		} catch (SQLiteTableConstraintException e) {
			e.printStackTrace();
		}
		lastTrainingPlayTime += nowTrainingPlayTime;
		nowTrainingPlayTime = 0;
		TrainingMenu menu = daoSql.selectTrainingMenu(trainingMenuId);
		item = new ColorBarItem(nowTrainingPlayTime, menu.getTrainingName(),
				menu.getColor());
		colorbar.addBarItem(item);
		colorbar.notifyDataSetChenged();
		lastTrainingMenuId = trainingMenuId;
		setStartMeasurement();
	}

	@Override
	public void onPagerMoveRight() {
		viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
	}

	@Override
	public void onUpdateTargetHeartRate(int targetHeartRate) {
		this.targetHeartRate = targetHeartRate;
	}

	@Override
	public void onLocationChanged(Location location, double distance,
			float speed) {
		disX = location.getLatitude();
		disY = location.getLongitude();
		this.distance = distance;
		speeds.add(speed);
		textDistance.setText(String.valueOf(distance));
		textSpeed.setText(String.valueOf(speed));
	}

	@Override
	public void onDialogCancel() {
		startMeasurement();
	}

	@Override
	protected void bleConnectTimeout() {
		Message message = new Message();
		message.what = MESSAGE_CONNECT_TIMEOUT;
		handler.sendMessage(message);
	}

	@Override
	public void onTouch(int value, String comment) {
		Toast.makeText(getApplicationContext(), comment + " " + value + "秒",
				Toast.LENGTH_SHORT).show();
	}

}
