package ac.neec.mio.timer;

import java.sql.Time;

import ac.neec.mio.ui.listener.TimerCallbackListener;
import ac.neec.mio.util.TimeUtil;

/**
 * Watchクラスを管理するクラス
 *
 */
public class TimerManager implements Observer {

	/**
	 * コールバックリスナー
	 */
	private TimerCallbackListener listener;
	/**
	 * Watchクラスのインスタンス
	 */
	private Watch watch;
	/**
	 * 計測中のタイム
	 */
	private static Time time;
	/**
	 * 最終ラップタイム
	 */
	private String lastTime;

	/**
	 * 
	 * @param listener
	 *            コールバックリスナー
	 */
	public TimerManager(TimerCallbackListener listener) {
		this.listener = listener;
		time = new Time(0);
		watch = Timer.getInstance();
	}

	/**
	 * 
	 * @param listener
	 *            コールバックリスナー
	 * @param time
	 *            計測時間
	 */
	public TimerManager(TimerCallbackListener listener, long time) {
		this.listener = listener;
		watch = new StopWatch(time);
		watch.measurement(this);
	}

	/**
	 * スタートする
	 */
	public void start() {
		watch.measurement(this);
	}

	/**
	 * ストップする
	 */
	public void stop() {
		watch.stop();
	}

	/**
	 * ラップを記録する
	 */
	public void lap() {
		watch.lap();
	}

	/**
	 * リセットする
	 */
	public void reset() {
		watch.reset();
	}

	/**
	 * mm:ssに成形する
	 * 
	 */
	private String setMeasurementTime(int time) {
		int h = (time / 3600);
		int m = ((time - 3600 * h) / 60);
		int s = (time - 3600 * h - 60 * m);
		TimerManager.time = new Time(h, m, s);
		if (s == 0) {
			listener.notifyMin();
		}
		return TimerManager.time.toString();
	}

	/**
	 * 計測中のタイムを取得する
	 * 
	 * @return 計測中のタイム
	 */
	public static Time getTime() {
		return time;
	}

	/**
	 * 計測時間をString型(hh:mm)で取得する
	 * 
	 * @return 計測時間
	 */
	public String getMeasurementTime() {
		StringBuilder sb = new StringBuilder();
		if (time.getMinutes() < 10 && time.getHours() == 0) {
			sb.append("0");
		}
		sb.append(time.getMinutes() + time.getHours() * 60);
		sb.append(":");
		if (time.getSeconds() < 10) {
			sb.append("0");
		}
		sb.append(time.getSeconds());
		return sb.toString();
	}

	/**
	 * 計測時間をint型で取得する
	 * 
	 * @return 計測時間
	 */
	public int getMeasurementIntTime() {
		return TimeUtil.stringToInteger(getMeasurementTime());
	}

	/**
	 * 計測時間をlong型で取得する
	 * 
	 * @return 計測時間
	 */
	public static long getSecTime() {
		return time.getSeconds() + time.getMinutes() * 60 + time.getHours()
				* 3600;
	}

	@Override
	public void onUpdate(long time) {
		setMeasurementTime((int) time);
		if (lastTime == null) {
			lastTime = getMeasurementTime();
		}
		if (!lastTime.equals(getMeasurementTime())) {
			listener.onUpdate(setMeasurementTime((int) time / 1000));
		}
		lastTime = getMeasurementTime();
	}

	@Override
	public void onAlarm() {
		listener.onAlarm();
	}

	@Override
	public void onUpdateLap(long time) {
		StringBuilder sb = new StringBuilder();
		long sec = time / 1000;
		if (sec / 60 >= 1) {
			long min = sec / 60;
			if (min <= 10) {
				sb.append("0");
			}
			sb.append(min);
			sb.append(":");
			sec -= min * 60;
		} else {
			sb.append("00:");
		}
		if (sec <= 10) {
			sb.append("0");
		}
		sb.append(sec);
		listener.onUpdateLap(sb.toString());
	}
}
