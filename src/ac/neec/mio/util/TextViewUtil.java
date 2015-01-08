package ac.neec.mio.util;

import android.graphics.Paint;
import android.util.TypedValue;
import android.widget.TextView;

public abstract class TextViewUtil {

	/**
	 * TextView のテキストサイズを 1 行に収まるように調整。
	 * 
	 */
	public static float resizeTextView(TextView textView, float minTextSize) {
		float textSize = getAdjustTextSize(textView.getWidth(), textView
				.getText().toString(), textView.getTextSize(), minTextSize);

		// テキストサイズ設定 (setTextSize はデフォルトで sp 指定なので TypedValue.COMPLEX_UNIT_PX
		// を指定すること！)
		textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
		return textSize;
	}

	/**
	 * 指定された幅に収まるようなテキストサイズを取得。
	 * 
	 * @param width
	 *            収めたい幅 (px)
	 * @param text
	 *            入れたいテキスト
	 * @param initTextSize
	 *            テキストサイズ初期値 (この値からはじめて徐々に小さくしていく) (px)
	 * @param minTextSize
	 *            最小テキストサイズ (このサイズよりは小さくしない) (px)
	 * 
	 * @return 収まるテキストサイズ (px)
	 */
	private static float getAdjustTextSize(int width, String text,
			float initTextSize, float minTextSize) {
		Paint paint = new Paint();
		float textWidth;

		// テキストサイズ (この値を徐々に小さくしていく)
		float textSize = initTextSize;

		// Paint にテキストを書いてテキスト横幅取得
		paint.setTextSize(textSize);
		textWidth = paint.measureText(text);

		/*
		 * 横幅に収まるまでループ
		 */
		while (width < textWidth) {
			textSize = textSize - 1;

			// 最小サイズ以下になっちゃったら終了
			if (minTextSize > 0 && minTextSize >= textSize) {
				textSize = minTextSize;
				break;
			}

			paint.setTextSize(textSize);
			textWidth = paint.measureText(text);
		}

		return textSize;
	}

}
