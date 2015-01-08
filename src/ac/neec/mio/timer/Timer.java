package ac.neec.mio.timer;

import android.util.Log;

public class Timer extends Watch implements Runnable {

	private static Timer instance = new Timer();

	private boolean isStoped;
	private long measurementTime = 0;
	private long lapStartTime = 0;

	private Timer() {
	}

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
