package ac.neec.mio.ui.listener;

/**
 * 2択ダイアログの選択結果を通知するリスナー
 */
public interface AlertCallbackListener {
	/**
	 * 許可が選択されたことを通知する
	 * 
	 * @param message
	 *            質問メッセージ
	 */
	void onNegativeSelected(String message);

	/**
	 * 拒否が選択されたことを通知する
	 * 
	 * @param message
	 *            質問メッセージ
	 */
	void onPositiveSelected(String message);
}
