package ac.neec.mio.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import ac.neec.mio.exception.ImageFileNotFoundException;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * 画像ユーティリティクラス
 */
public class BitmapUtil {

	/**
	 * Bitmap型をDrawable型に変換する
	 * 
	 * @param res
	 *            リソース
	 * @param image
	 *            Bitmap型画像
	 * @return Drawable型画像
	 * 
	 */
	public static Drawable bitmapToDrawable(Resources res, Bitmap image) {
		return new BitmapDrawable(res, image);
	}

	/**
	 * Bitmap型をbyte型配列に変換する
	 * 
	 * @param image
	 *            Bitmap型画像
	 * @return byte型配列
	 */
	public static byte[] bitmapToBlob(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		if (image == null) {
			return null;
		}
		image.compress(CompressFormat.PNG, 100, baos);
		byte[] blob = baos.toByteArray();
		return blob;
	}

	/**
	 * File型からBitmap型に変換する
	 * 
	 * @param file
	 *            ファイル
	 * @return Bitmap型画像
	 * @throws ImageFileNotFoundException
	 *             ファイルに画像がないエラー
	 */
	public static Bitmap fileToBitmap(File file)
			throws ImageFileNotFoundException {
		FileInputStream fis;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			throw new ImageFileNotFoundException();
		}
		return BitmapFactory.decodeStream(fis);
	}

	/**
	 * byte型配列からBitmap型に変換する
	 * 
	 * @param image
	 *            byte型配列
	 * @return Bitmap型画像
	 */
	public static Bitmap blobToBitmap(byte[] image) {
		if (image != null) {
			return BitmapFactory.decodeByteArray(image, 0, image.length);
		}
		return null;
	}

	/**
	 * InputStream型からBitmap型に変換する
	 * 
	 * @param image
	 *            InputStream型
	 * @return Bitmap型画像
	 */
	public static Bitmap streamToBitmap(InputStream image) {
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		int read = 0;
		byte[] b = null;
		try {
			while ((read = image.read()) != -1) {
				stream.write(read);
			}
			b = stream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				stream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return BitmapFactory.decodeByteArray(b, 0, b.length);
	}

	/**
	 * 画像のサイズを変更する
	 * 
	 * @param image
	 *            変更する画像
	 * @param width
	 *            変更したい横サイズ
	 * @param height
	 *            変更したい縦サイズ
	 * @return 変更後画像
	 */
	public static Bitmap resize(Bitmap image, int width, int height) {
		return Bitmap.createScaledBitmap(image, width, height, true);
	}

	/**
	 * 画像を丸くする
	 * 
	 * @param bitmap
	 *            丸くする画像
	 * @param radius
	 *            半径
	 * @return 変更後画像
	 */
	public static Bitmap getBitmapClippedCircle(Bitmap bitmap, float radius) {
		final int width = bitmap.getWidth();
		final int height = bitmap.getHeight();
		final Bitmap outputBitmap = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);
		final Path path = new Path();
		path.addCircle((float) (width / 2), (float) (height / 2), radius,
				Path.Direction.CCW);
		final Canvas canvas = new Canvas(outputBitmap);
		canvas.clipPath(path);
		canvas.drawBitmap(bitmap, 0, 0, null);
		return outputBitmap;
	}
}
