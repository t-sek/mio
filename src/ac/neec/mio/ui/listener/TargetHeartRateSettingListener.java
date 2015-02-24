package ac.neec.mio.ui.listener;

/**
 * 計測画面での目標心拍数設定ダイアログでのイベントを通知するリスナー
 */
public interface TargetHeartRateSettingListener {
	/**
	 * 目標心拍数変更を通知する
	 * 
	 * @param targetHeartRate
	 *            変更したい目標心拍数
	 */
	void onUpdate(int targetHeartRate);

	/**
	 * 目標心拍数を変更しないことを通知する
	 * 
	 * @param targetHeartRate
	 *            現在の目標心拍数
	 */
	void onDecided(int targetHeartRate);

	/**
	 * 変更せず、ダイアログを閉じたことを通知する
	 */
	void onCancel();
}
