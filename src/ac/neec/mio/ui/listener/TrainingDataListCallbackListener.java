package ac.neec.mio.ui.listener;

/**
 * 折りたたみリストビューの前回開いていたタブを閉じることを通知するリスナー
 */
public interface TrainingDataListCallbackListener {
	/**
	 * 前回開いていたタブを通知する
	 * 
	 * @param position
	 *            前回開いていたタブのインデックス
	 */
	void closeGroup(int position);

}
