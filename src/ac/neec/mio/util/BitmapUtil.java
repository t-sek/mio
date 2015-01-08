package ac.neec.mio.util;

import android.graphics.AvoidXfermode.Mode;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;

public class BitmapUtil {

	public static Bitmap rectImage(Bitmap image) {
		// 画像サイズ取得
		int width = image.getWidth();
		int height = image.getHeight();
		// リサイズ後サイズ
		int w = 100;
		int h = 100;
		// 切り取り領域となるbitmap生成
		Bitmap clipArea = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		// 角丸矩形を描写
		Canvas c = new Canvas(clipArea);
		c.drawRoundRect(new RectF(0, 0, w, h), 10, 10, new Paint(
				Paint.ANTI_ALIAS_FLAG));
		// 角丸画像となるbitmap生成
		Bitmap newImage = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
		// 切り取り領域を描写
		Canvas canvas = new Canvas(newImage);
		Paint paint = new Paint();
		canvas.drawBitmap(clipArea, 0, 0, paint);
		// 切り取り領域内にオリジナルの画像を描写
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		canvas.drawBitmap(image, new Rect(0, 0, width, height), new Rect(0, 0,
				w, h), paint);
		// // viewにset
		// ImageView imageView = (ImageView)
		// context.findViewById(R.id.imageview);
		// imageView.setImageDrawable(new BitmapDrawable(newImage));

		return newImage;
	}
	
	public static Bitmap resize(Bitmap image,int width,int height){
		return Bitmap.createScaledBitmap(image, width, height, true);
	}

	public static Bitmap getBitmapClippedCircle(Bitmap bitmap, float radius) {

		final int width = bitmap.getWidth();
		final int height = bitmap.getHeight();
		final Bitmap outputBitmap = Bitmap.createBitmap(width, height,
				Config.ARGB_8888);

		final Path path = new Path();
		Log.e("util", "height " + height);
		Log.e("util", "width " + width);
		Log.e("util", "radius " + radius);
		path.addCircle((float) (width / 2), (float) (height / 2),
		// (float) Math.min(width, (height / 2)), Path.Direction.CCW);
				radius, Path.Direction.CCW);

		final Canvas canvas = new Canvas(outputBitmap);
		canvas.clipPath(path);
		canvas.drawBitmap(bitmap, 0, 0, null);
		return outputBitmap;
	}
}
