package ac.neec.mio.timer;

import android.util.Log;

/**
 * タイマーを実装したクラス
 *
 */
public class Timer extends Watch implements Runnable {

	/**
	 * Timerクラスのインスタンス
	 */
	private static Timer instance = new Timer();

	/**
	 * 停止フラグ
	 */
	private boolean isStoped;
	/**
	 * 計測時間
	 */
	private long measurementTime = 0;
	/**
	 * ラップタイム
	 */
	private long lapStartTime = 0;

	private Timer() {
	}

	/**
	 * Timerクラスのインスタンスを取得する
	 * 
	 * @return インスタンス
	 */
	protected static Timer getInstance() {
		return instance;
	}

	@Override
	protected void start() {
		isStoped = false;
		Thread thread = new Thread(this);
		thread.start();
	}

	@Override
	protected void stop() {
		isStoped = true;
	}

	@Override
	protected void lap() {
		if (lapStartTime == 0) {
			updateLap(measurementTime);
		} else {
			updateLap(measurementTime - lapStartTime);
		}
		lapStartTime = measurementTime;
	}

	@Override
	protected void reset() {
		measurementTime = 0;
		lapStartTime = 0;
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis() - measurementTime;
		while (!isStoped) {
			measurementTime = System.currentTimeMillis() - startTime;
			update(measurementTime);
			try {
				Thread.sleep(300);
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
	}

}
