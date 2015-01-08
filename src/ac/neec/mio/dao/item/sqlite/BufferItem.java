package ac.neec.mio.dao.item.sqlite;

import android.content.ContentValues;

public class BufferItem {

	private String tableName;
	private ContentValues values;

	public BufferItem(String tableName, ContentValues values) {
		this.tableName = tableName;
		this.values = values;
	}

	public String getTableName() {
		return tableName;
	}

	public ContentValues getValues() {
		return values;
	}
}
