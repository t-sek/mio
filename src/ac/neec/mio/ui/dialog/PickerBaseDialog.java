package ac.neec.mio.ui.dialog;

import ac.neec.mio.ui.picker.DrumPicker;
import ac.neec.mio.ui.picker.DrumPickerListener;
import android.app.DialogFragment;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.LinearLayout;

/**
 * ダイアログで使うピッカーを定義する
 */
public abstract class PickerBaseDialog extends DialogFragment implements
		DrumPickerListener, OnTouchListener {

	/**
	 * スクロールビューの子ビュー<br>
	 * ピッカーの要素を並べる
	 */
	protected LinearLayout line;
	/**
	 * スクロールビューの子ビュー<br>
	 * ピッカーの要素を並べる
	 */
	protected LinearLayout line1;
	/**
	 * スクロールビューの子ビュー<br>
	 * ピッカーの要素を並べる
	 */
	protected LinearLayout line2;
	/**
	 * スクロールビューの子ビュー<br>
	 * ピッカーの要素を並べる
	 */
	protected LinearLayout line3;
	/**
	 * メインのスクロールビュー<br>
	 * ピッカークラス
	 */
	protected DrumPicker picker;
	/**
	 * メインのスクロールビュー<br>
	 * ピッカークラス
	 */
	protected DrumPicker picker1;
	/**
	 * メインのスクロールビュー<br>
	 * ピッカークラス
	 */
	protected DrumPicker picker2;
	/**
	 * メインのスクロールビュー<br>
	 * ピッカークラス
	 */
	protected DrumPicker picker3;
	/**
	 * １つの要素あたりの高さを取得するためのビュー
	 */
	protected View unit;
	/**
	 * 要素の単位高さ
	 */
	protected int unitHeight;
	/**
	 * スクロールの行き先Y座標
	 */
	protected int to_0, to_1, to_2;
	/**
	 * スクロール間隔の時間(ミリ秒)
	 */
	protected final int REPEAT_INTERVAL = 10;
	/**
	 * 待機時間
	 */
	protected final int MESSAGE_WHAT = 100;
	/**
	 * 移動中を示すフラグ
	 */
	protected boolean isRepeat_0, isRepeat_1, isRepeat_2;

}
