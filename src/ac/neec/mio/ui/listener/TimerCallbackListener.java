package ac.neec.mio.ui.listener;

/**
 * 計測中の時間を通知するリスナー
 */
public interface TimerCallbackListener {
	/**
	 * 計測時間を通知する
	 * 
	 * @param time
	 *            計測時間
	 */
	void onUpdate(String time);

	/**
	 * ラップタイムを通知する
	 * 
	 * @param time
	 *            ラップタイム
	 */
	void onUpdateLap(String time);
	/**
	 * タイマー終了を通知する
	 */
	void onAlarm();

}
