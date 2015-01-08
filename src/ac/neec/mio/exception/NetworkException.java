package ac.neec.mio.exception;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;

import android.util.Log;

/**
 * ネットワーク接続エラー
 */
public class NetworkException extends IOException implements
		UncaughtExceptionHandler {

	private static final long serialVersionUID = 1L;
	private static final String ERROR_TEXT = "ネットワークに接続できませんでした";

	public NetworkException() {
		super(ERROR_TEXT);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.e("NetworkException", ERROR_TEXT + " thread " + thread.getName());
	}

}
