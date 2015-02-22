package ac.neec.mio.ui.activity;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.timer.TimerManager;
import ac.neec.mio.ui.listener.TimerCallbackListener;
import ac.neec.mio.user.User;
import ac.neec.mio.util.HeartRateUtil;
import ac.neec.mio.util.TimeUtil;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;

public class QuietHeartRateMeasurementActivity extends BleConnectBaseActivity
		implements TimerCallbackListener {

	private static final int MESSAGE_UPDATE = 2;
	private static final int MESSAGE_HEART_RATE = 3;
	private static final int MEASUREMENT_TIME = 1500;

	private static final String TEXT = "計測中です";
	private static final String SECTION = ".";

	private TextView textTime;
	private ProgressBar progress;
	private List<Integer> heartRates = new ArrayList<Integer>();
	private User user = User.getInstance();
	private int sectionCount;

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_UPDATE:
				updateTime((String) message.obj);
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiet_heart_rate_measurement);
		initFindViews();
		connectBleDevice();
	}

	private void initFindViews() {
		textTime = (TextView) findViewById(R.id.text_time);
		progress = (ProgressBar) findViewById(R.id.progress);
		progress.setMax(MEASUREMENT_TIME / 100);
		// progress.setIndeterminate(true);
	}

	private void setTimer() {
		TimerManager manager = new TimerManager(this, 15000);
		manager.start();
	}

	private void updateTime(String time) {
		int nowTime = Integer.valueOf(TimeUtil.stringToSec(time));
		// textTime.setText(String.valueOf(nowTime));
		StringBuilder sb = new StringBuilder();
		sb.append(TEXT);
		// String text = TEXT;
		for (int i = 0; i <= sectionCount; i++) {
			// text.concat(SECTION);
			sb.append(SECTION);
			// Log.d("activity", "text " + text);
		}
		if (sectionCount >= 4) {
			sectionCount = 0;
		} else {
			sectionCount++;
		}
		// textTime.setText(text);
		textTime.setText(sb.toString());
		progress.setProgress((MEASUREMENT_TIME / 100) - nowTime);
		// progress.incrementProgressBy(1);
	}

	@Override
	public void onUpdate(String time) {
		Message message = new Message();
		message.what = MESSAGE_UPDATE;
		message.obj = time;
		handler.sendMessage(message);
	}

	@Override
	public void onUpdateLap(String time) {

	}

	@Override
	public void onAlarm() {
		// Intent intent = new Intent(QuietHeartRateMeasurementActivity.this,
		// TopActivity.class);
		// startActivity(intent);
		int quietHeartRate = HeartRateUtil.calcQuietHeartRate(heartRates);
		user.setQuietHeartRate(quietHeartRate);
		finish();
	}

	@Override
	public void notifyMin() {

	}

	@Override
	protected void requestEnable() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void requestNotEnable() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void receiveHeartRate(String heartRate) {
		heartRates.add(Integer.valueOf(heartRate));
		Log.d("measurement", "heartRate " + heartRate);
	}

	@Override
	protected void bleConnected() {
		setTimer();
	}

	@Override
	protected void bleDisconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void bleConnectTimeout() {
		// TODO Auto-generated method stub
		
	}

}
