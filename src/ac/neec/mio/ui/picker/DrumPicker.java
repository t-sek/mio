package ac.neec.mio.ui.picker;

import android.content.Context;
import android.util.AttributeSet;

/**
 * ピッカー実装クラス
 */
public class DrumPicker extends android.widget.ScrollView {

	public DrumPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	/**
	 * コールバックリスナー
	 */
	private DrumPickerListener listener = null;

	/**
	 * リスナーを設定する
	 * 
	 * @param listener
	 *            コールバックリスナー
	 */
	public void setOnScrollViewListener(DrumPickerListener listener) {
		this.listener = listener;
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (listener != null) {
			listener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}
}
