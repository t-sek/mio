package ac.neec.mio.ui.picker;

import android.content.Context;
import android.util.AttributeSet;

public class DrumPicker extends android.widget.ScrollView {
	private String TAG = DrumPicker.class.getSimpleName();

	public DrumPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	private DrumPickerListener listener = null;

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
