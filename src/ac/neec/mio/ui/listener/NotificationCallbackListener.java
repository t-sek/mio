package ac.neec.mio.ui.listener;

/**
 * 計測画面のアクティビティクラスから全タブフラグメントクラスに計測情報を通知するリスナー
 */
public interface NotificationCallbackListener {

	/**
	 * 心拍数を通知する
	 * 
	 * @param value
	 *            心拍数
	 */
	void notifyValue(int value);

	/**
	 * カロリーを通知する
	 * 
	 * @param value
	 *            カロリー
	 */
	void notifyCalorie(int value);

	/**
	 * 計測時間を通知する
	 * 
	 * @param value
	 *            計測時間
	 */
	void notifyTime(String value);

}
