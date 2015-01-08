package ac.neec.mio.timer;

import java.sql.Time;

import ac.neec.mio.ui.listener.TimerCallbackListener;
import ac.neec.mio.util.TimeUtil;

public class TimerManager implements Observer {

	private TimerCallbackListener listener;
	private Watch watch;
	private static Time time;
	private String lastTime;

	public TimerManager(TimerCallbackListener listener) {
		this.listener = listener;
		time = new Time(0);
		watch = Timer.getInstance();
	}

	public TimerManager(TimerCallbackListener listener, long time) {
		this.listener = listener;
		watch = new StopWatch(time);
		watch.measurement(this);
	}

	public void start() {
		watch.measurement(this);
	}

	public void stop() {
		watch.stop();
	}

	public void lap() {
		watch.lap();
	}

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

	public static Time getTime() {
		return time;
	}

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

	public int getMeasurementIntTime() {
		return TimeUtil.stringToInteger(getMeasurementTime());
	}

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
		// if (!sb.toString().equals(ZERO)) {
		listener.onUpdateLap(sb.toString());
		// }

	}
}
