package ac.neec.mio.dao.item.sqlite;

/**
 * バッファに追加するためのインターフェース
 *
 */
public interface Buffer {
	/**
	 * バッファに追加する
	 * 
	 * @param item
	 */
	void insertBuffer(BufferItem item);
}
