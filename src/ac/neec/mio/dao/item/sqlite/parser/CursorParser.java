package ac.neec.mio.dao.item.sqlite.parser;

import android.database.Cursor;

/**
 * ローカルデータベースから選択したデータを解析するクラス
 *
 */
public abstract class CursorParser {

	/**
	 * 
	 * @param c
	 *            選択されたデータ
	 */
	protected CursorParser(Cursor c) {
		parse(c);
	}

	/**
	 * 解析する
	 * 
	 * @param c
	 *            解析するデータ
	 */
	protected abstract void parse(Cursor c);

	/**
	 * 解析データを取得する
	 * 
	 * @return 解析データ
	 */
	public abstract <T> T getObject();
}
