package ac.neec.mio.dao.item.sqlite;

import java.util.concurrent.ArrayBlockingQueue;

public class RingBuffer implements Runnable {

	private static final int CAPACITY = 20;
	private Buffer listener;
	private Thread thread;

	private static ArrayBlockingQueue<BufferItem> buffer;

	protected RingBuffer(Buffer listener) {
		this.listener = listener;
		buffer = new ArrayBlockingQueue<BufferItem>(CAPACITY);
	}

	protected void set(BufferItem item) {
		buffer.add(item);
		thread = new Thread(this);
		thread.start();
	}

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
