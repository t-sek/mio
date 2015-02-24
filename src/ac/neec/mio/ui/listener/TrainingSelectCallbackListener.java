package ac.neec.mio.ui.listener;

/**
 * トレーニングカテゴリー、トレーニングメニューをピッカーで設定した結果を通知するリスナー
 */
public interface TrainingSelectCallbackListener {
	/**
	 * 設定した要素を通知する
	 * 
	 * @param element
	 *            設定した要素
	 */
	void onSelected(String element);

	/**
	 * 設定した要素のインデックスを通知する
	 * 
	 * @param index
	 *            設定した要素のインデックス
	 */
	void onSelected(int index);
}
