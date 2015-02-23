package ac.neec.mio.ui.dialog;

import ac.neec.mio.R;
import ac.neec.mio.util.TimeUtil;
import ac.neec.mio.util.TrainingUtil;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.sek.drumpicker.DrumPicker;
import com.sek.drumpicker.DrumPickerListener;

/**
 * カロリー、走行距離をピッカーで設定するダイアログクラス
 */
public class TrainingNumInsertDialog extends DialogFragment implements
		DrumPickerListener {

	/**
	 * カロリーフラグ
	 */
	public static final int CALORIE = 2;
	/**
	 * 走行距離フラグ
	 */
	public static final int DISTANCE = 3;
	/**
	 * カロリー単位
	 */
	private static final String CALORIE_UNIT = "kcal";
	/**
	 * 走行距離単位
	 */
	private static final String DISTANCE_UNIT = "km";
	/**
	 * タグ
	 */
	private static final String TAG_PICKER = "picker";
	/**
	 * ダイアログインスタンス
	 */
	private Dialog dialog;
	/**
	 * コールバックリスナー
	 */
	private NumChangedListener listener;
	/**
	 * 設定ピッカー
	 */
	private DrumPicker picker;
	/**
	 * 選択された数値を表示するテキストビュー
	 */
	private TextView textInsert;
	/**
	 * 決定ボタン
	 */
	private Button button;
	/**
	 * 選択された数値
	 */
	private String num;
	/**
	 * カロリー、走行距離を表すタグ
	 */
	private int tag;
	/**
	 * 単位
	 */
	private String unit;

	/**
	 * カロリー、走行距離を通知するリスナー
	 */
	public interface NumChangedListener {
		/**
		 * 選択された数値を通知する
		 * 
		 * @param tag
		 *            タグ<br>
		 *            TrainingNumInsertDialogクラスのCALORIE、DISTANCE
		 * @param num
		 *            選択された数値
		 */
		void onSelected(int tag, String num);
	}

	/**
	 * 
	 * @param listener
	 *            コールバックリスナー
	 * @param tag
	 *            タグ<br>
	 *            TrainingNumInsertDialogクラスのCALORIE、DISTANCE
	 */
	public TrainingNumInsertDialog(NumChangedListener listener, int tag) {
		this.listener = listener;
		this.tag = tag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new Dialog(getActivity());
		setDialog();
		init();
		return dialog;
	}

	/**
	 * 画面の初期化処理をする
	 */
	private void init() {
		picker = (DrumPicker) dialog.findViewById(R.id.picker);
		picker.setOnDrumPickerListener(this);
		switch (tag) {
		case CALORIE:
			picker.setElements(getActivity().getApplicationContext(),
					TAG_PICKER, TrainingUtil.getCalorie());
			unit = CALORIE_UNIT;
			break;
		case DISTANCE:
			picker.setElements(getActivity().getApplicationContext(),
					TAG_PICKER, TrainingUtil.getDistance());
			unit = DISTANCE_UNIT;
			break;
		default:
			break;
		}
		textInsert = (TextView) dialog.findViewById(R.id.txt_setting_insert);
		button = (Button) dialog.findViewById(R.id.button_decided);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				insert();
			}
		});
	}

	/**
	 * 通知して、ダイアログを閉じる
	 */
	private void insert() {
		listener.onSelected(tag, num);
		dismiss();
	}

	/**
	 * ダイアログを設定する
	 */
	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_insert_training_num_picker);
		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.CENTER;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
	}

	@Override
	public void onScrollChanged(String tag, String element, int index) {
		textInsert.setText(element + unit);
		num = element;
	}
}
