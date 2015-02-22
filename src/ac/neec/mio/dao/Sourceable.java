package ac.neec.mio.dao;

import java.io.InputStream;

import android.graphics.Bitmap;

/**
 * 通信結果を通知するインターフェース
 *
 */
public interface Sourceable {
	/**
	 * 通信完了
	 */
	void complete();

	/**
	 * 画像取得完了
	 * 
	 * @param image
	 *            画像
	 */
	void complete(Bitmap image);

	/**
	 * ネットワークエラー
	 */
	void incomplete();

	/**
	 * URLに不正文字列
	 */
	void validate();
}
