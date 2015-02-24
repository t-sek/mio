package ac.neec.mio.ui.listener;

import ac.neec.mio.group.Group;

/**
 * グループ一覧画面での検索結果を通知するリスナー
 */
public interface GroupFilterCallbackListener {

	/**
	 * 変更されたことを通知する
	 */
	void onChenged();

	/**
	 * 初期化を通知する
	 */
	void onClear();

	/**
	 * 検索結果を通知する
	 * 
	 * @param item
	 *            検索条件と一致するグループ
	 */
	void onAddition(Group item);
}
