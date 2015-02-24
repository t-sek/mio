package ac.neec.mio.ui.picker;

/**
 * ピッカーで設定された情報を通知するリスナー
 */
public interface DrumPickerListener {

	/**
	 * 設定された情報を通知する
	 * 
	 * @param scrollView
	 *            ピッカー情報
	 * @param x
	 *            現在のx座標
	 * @param y
	 *            現在のx座標
	 * @param oldx
	 *            前回のx座標
	 * @param oldy
	 *            前回のy座標
	 */
	void onScrollChanged(DrumPicker scrollView, int x, int y, int oldx, int oldy);
}