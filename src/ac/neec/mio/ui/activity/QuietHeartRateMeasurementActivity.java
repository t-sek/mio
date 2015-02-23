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

/**
 * 安静時心拍数計測画面クラス
 *
 */
public class QuietHeartRateMeasurementActivity extends BleConnectBaseActivity
		implements TimerCallbackListener {

	/**
	 * 更新メッセージ
	 */
	private static final int MESSAGE_UPDATE = 2;
	/**
	 * 計測時間
	 */
	private static final int MEASUREMENT_TIME = 1500;
	/**
	 * 計測中メッセージ
	 */
	private static final String TEXT = "計測中です";
	/**
	 * 計測中メッセージ
	 */
	private static final String SECTION = ".";
	/**
	 * 計測中メッセージを表示するテキストビュー
	 */
	private TextView textTime;
	/**
	 * 計測心拍数リスト
	 */
	private List<Integer> heartRates = new ArrayList<Integer>();
	/**
	 * ユーザ情報
	 */
	private User user = User.getInstance();
	/**
	 * 計測中メッセージイテレータ
	 */
	private int sectionCount;
	/**
	 * 画面ハンドラー
	 */
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

	/**
	 * 画面の初期化処理をする
	 */
	private void initFindViews() {
		textTime = (TextView) findViewById(R.id.text_time);
	}

	/**
	 * タイマーを設定する
	 */
	private void setTimer() {
		TimerManager manager = new TimerManager(this, 15000);
		manager.start();
	}

	/**
	 * 計測中メッセージを更新する
	 * 
	 * @param time
	 *            計測時間
	 */
	private void updateTime(String time) {
		int nowTime = Integer.valueOf(TimeUtil.stringToSec(time));
		StringBuilder sb = new StringBuilder();
		sb.append(TEXT);
		for (int i = 0; i <= sectionCount; i++) {
			sb.append(SECTION);
		}
		if (sectionCount >= 4) {
			sectionCount = 0;
		} else {
			sectionCount++;
		}
		textTime.setText(sb.toString());
	}

	/**
	 * 安静時心拍数を算出し、保存する
	 */
	private void setQuietHeartRate() {
		int quietHeartRate = HeartRateUtil.calcQuietHeartRate(heartRates);
		user.setQuietHeartRate(quietHeartRate);
	}

	/**
	 * 心拍数を追加する
	 * 
	 * @param heartRate
	 *            心拍数
	 */
	private void addQuietHeartRate(String heartRate) {
		heartRates.add(Integer.valueOf(heartRate));
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
		setQuietHeartRate();
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
		addQuietHeartRate(heartRate);
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
