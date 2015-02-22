package ac.neec.mio.dao.item.sqlite;

import android.content.ContentValues;

/**
 * バッファの型を定義するクラス
 *
 */
public class BufferItem {

	/**
	 * テーブル名
	 */
	private String tableName;
	/**
	 * 挿入したい値
	 */
	private ContentValues values;

	/**
	 * 
	 * @param tableName テーブル名
	 * @param values 挿入したい値
	 */
	public BufferItem(String tableName, ContentValues values) {
		this.tableName = tableName;
		this.values = values;
	}

	/**
	 * テーブル名を取得する
	 * 
	 * @return テーブル名
	 */
	public String getTableName() {
		return tableName;
	}

	/**
	 * 値を取得する
	 * 
	 * @return 値
	 */
	public ContentValues getValues() {
		return values;
	}
}
