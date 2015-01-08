package ac.neec.mio.timer;

import android.speech.tts.TextToSpeech.OnUtteranceCompletedListener;
import android.util.Log;

public class StopWatch extends Watch implements Runnable {

	private long time;
	private Thread thread;
	private boolean isStoped;

	protected StopWatch(long time) {
		this.time = time;
		thread = new Thread(this);
		isStoped = true;
		thread.start();
	}

	protected void startStopWatch() {
	}

	@Override
	protected void start() {
	}

	@Override
	protected void stop() {
		isStoped = false;
	}

	@Override
	protected void lap() {
	}

	@Override
	protected void reset() {

	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < time) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			update(Math.round(time)-Math.round(System.currentTimeMillis() - startTime));
		}
		if (isStoped) {
			alarm();
		}
	}

}
