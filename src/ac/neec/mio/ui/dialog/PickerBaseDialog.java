package ac.neec.mio.ui.dialog;

import ac.neec.mio.ui.picker.DrumPicker;
import ac.neec.mio.ui.picker.DrumPickerListener;
import android.app.DialogFragment;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

public abstract class PickerBaseDialog extends DialogFragment implements
		DrumPickerListener, OnTouchListener {

	// picker init
	protected LinearLayout line;// ScrollViewのChildView
	protected LinearLayout line1;// ScrollViewのChildView
	protected LinearLayout line2;// ScrollViewのChildView
	protected LinearLayout line3;// ScrollViewのChildView
	protected DrumPicker picker;// メインのScrollView
	protected DrumPicker picker1;// メインのScrollView
	protected DrumPicker picker2;// メインのScrollView
	protected DrumPicker picker3;// メインのScrollView
	protected View unit;// これは１つの要素あたりの高さを取得するためのView。
	protected int unitHeight;// 要素の単位高さ
	protected int to_0, to_1, to_2;// スクロールの行き先Y座標
	protected final int REPEAT_INTERVAL = 10;// スクロール間隔の時間(ミリ秒)
	protected final int MESSAGE_WHAT = 100;// この数字は何でもOK
	protected boolean isRepeat_0, isRepeat_1, isRepeat_2;// 移動中ですを示すフラグ。２つのドラムを何か依存させるときとかにも使う

}
