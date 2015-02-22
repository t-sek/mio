package ac.neec.mio.dao.item.sqlite;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * インサートに失敗したデータを保持しておくクラス
 *
 */
public class RingBuffer implements Runnable {

	/**
	 * 容量
	 */
	private static final int CAPACITY = 20;
	private Buffer listener;
	private Thread thread;

	private static ArrayBlockingQueue<BufferItem> buffer;

	/**
	 * 
	 * @param listener
	 *            コールバックリスナー
	 */
	protected RingBuffer(Buffer listener) {
		this.listener = listener;
		buffer = new ArrayBlockingQueue<BufferItem>(CAPACITY);
	}

	/**
	 * アイテムを設定する
	 * 
	 * @param item
	 *            アイテム
	 */
	protected void set(BufferItem item) {
		buffer.add(item);
		thread = new Thread(this);
		thread.start();
	}

	/**
	 * アイテムを取得する
	 * 
	 * @return アイテム
	 */
	private BufferItem get() {
		return buffer.poll();
	}

	@Override
	public void run() {
		while (buffer.size() != 0) {
			listener.insertBuffer(get());
		}
	}

}
