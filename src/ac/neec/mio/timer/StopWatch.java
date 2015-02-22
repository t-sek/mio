package ac.neec.mio.timer;

/**
 * ストップウォッチを実装したクラス
 *
 */
public class StopWatch extends Watch implements Runnable {

	/**
	 * 時間
	 */
	private long time;
	/**
	 * 計測スレッド
	 */
	private Thread thread;
	/**
	 * 停止フラグ
	 */
	private boolean isStoped;

	protected StopWatch(long time) {
		this.time = time;
		thread = new Thread(this);
		isStoped = true;
		thread.start();
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
		time = 0;
	}

	@Override
	public void run() {
		long startTime = System.currentTimeMillis();
		while (System.currentTimeMillis() - startTime < time) {
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
			}
			update(Math.round(time)
					- Math.round(System.currentTimeMillis() - startTime));
		}
		if (isStoped) {
			alarm();
		}
	}

}
