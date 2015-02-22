package ac.neec.mio.timer;

/**
 * 時間を通知する
 *
 */
public interface Observer {
	/**
	 * 時間更新を通知する
	 * 
	 * @param time
	 *            時間
	 */
	void onUpdate(long time);

	/**
	 * タップタイム更新を通知する
	 * 
	 * @param time
	 *            ラップタイム
	 */
	void onUpdateLap(long time);

	/**
	 * タイマー終了を通知する
	 */
	void onAlarm();
}
