package ac.neec.mio.exception;

import java.io.IOException;
import java.lang.Thread.UncaughtExceptionHandler;

import android.util.Log;

/**
 * 通信スレッドエラー
 */
public class NetworkException extends IOException implements
		UncaughtExceptionHandler {

	private static final long serialVersionUID = 1L;
	private static final String TAG = "NetworkException";
	private static final String ERROR_TEXT = "通信スレッドで例外が発生しました";

	public NetworkException() {
		super(ERROR_TEXT);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		Log.e(TAG, "----------例外のお知らせ----------");
		Log.d(TAG, "過去の事例");
		Log.d(TAG, "レスポンスを取得したときの型が不適切");
		Log.d(TAG, "など");
		printStackTrace();
	}

}
