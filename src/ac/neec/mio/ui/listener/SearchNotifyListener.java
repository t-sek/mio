package ac.neec.mio.ui.listener;

/**
 * グループ検索フォームでの入力されたことを通知するリスナー
 */
public interface SearchNotifyListener {
	/**
	 * テキストが入力されたことを通知する
	 * 
	 * @param text
	 *            入力されたテキスト
	 */
	void onSearchText(String text);

	/**
	 * 入力フォームを初期化したことを通知する
	 */
	void onClear();

	/**
	 * 検索終了を通知する
	 */
	void onSearchEnd();

	/**
	 * 検索を開始することを通知する
	 */
	void onUpdate();
}
