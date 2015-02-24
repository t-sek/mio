package ac.neec.mio.ui.listener;

/**
 * 計測画面でのトレーニングメニュー変更、目標心拍数変更を通知する
 */
public interface MeasurementCallbackListener {
	/**
	 * トレーニングメニュー変更を通知する
	 * 
	 * @param trainingMenuId
	 *            変更されたトレーニングメニュー
	 */
	void onSelected(int trainingMenuId);

	/**
	 * 設定ダイアログが閉じたことを通知する
	 */
	void onDialogCancel();

	/**
	 * 目標心拍数変更を通知する
	 * 
	 * @param targetHeartRate
	 *            変更された目標心拍数
	 */
	void onUpdateTargetHeartRate(int targetHeartRate);
}
