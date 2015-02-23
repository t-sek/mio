package ac.neec.mio.ui.dialog;

import ac.neec.mio.R;
import ac.neec.mio.util.TimeUtil;
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
 * トレーニング開始時間、トレーニング開始時間、トレーニングメニュー時間をピッカーで設定するダイアログクラス
 *
 */
public class TrainingTimeInsertDialog extends DialogFragment implements
		DrumPickerListener {

	/**
	 * 計測時間フラグ
	 */
	public static final int PLAY_TIME = 2;
	/**
	 * 開始時間フラグ
	 */
	public static final int START_TIME = 3;
	/**
	 * トレーニングメニュー時間
	 */
	public static final int ADD_TRAINING = 4;
	/**
	 * 時間フラグ
	 */
	private static final String TAG_HOUR = "h";
	/**
	 * 分フラグ
	 */
	private static final String TAG_MIN = "m";
	/**
	 * 開始時間分単位
	 */
	private static final String PLAY_UNIT1 = "分";
	/**
	 * 計測時間時間単位
	 */
	private static final String START_UNIT1 = "時";
	/**
	 * 開始時間秒単位
	 */
	private static final String PLAY_UNIT2 = "秒";
	/**
	 * 計測時間分単位
	 */
	private static final String START_UNIT2 = "分";
	/**
	 * ダイアログインスタンス
	 */
	private Dialog dialog;
	/**
	 * コールバックリスナー
	 */
	private TimeChangedListener listener;
	/**
	 * 時間設定ピッカー
	 */
	private DrumPicker pickerHour;
	/**
	 * 分設定ピッカー
	 */
	private DrumPicker pickerMin;
	/**
	 * 選択された数値を表示するテキストビュー
	 */
	private TextView textInsert;
	/**
	 * 左単位を表示するテキストビュー
	 */
	private TextView textHourUnit;
	/**
	 * 右単位を表示するテキストビュー
	 */
	private TextView textMinUnit;
	/**
	 * 決定ボタン
	 */
	private Button button;
	/**
	 * 選択された時間
	 */
	private String hour = "0";
	/**
	 * 選択された分
	 */
	private String min = "0";
	/**
	 * 選択された時間
	 */
	private String time;
	/**
	 * タグ<br>
	 * TrainingTimeInsertDialogクラスのPLAY_TIME、START_TIME、ADD_TRAINING
	 */
	private int tag;
	/**
	 * 左単位
	 */
	private String unit1;
	/**
	 * 右単位
	 */
	private String unit2;

	/**
	 * 設定された時間を通知するリスナー
	 */
	public interface TimeChangedListener {
		/**
		 * 設定された時間を通知する
		 * 
		 * @param tag
		 *            タグ<br>
		 *            TrainingTimeInsertDialogクラスのPLAY_TIME、START_TIME、
		 *            ADD_TRAINING
		 * @param hour
		 *            時間
		 * @param min
		 *            分
		 */
		void onSelected(int tag, String hour, String min);
	}

	/**
	 * 
	 * @param listener
	 *            コールバックリスナー
	 * @param tag
	 *            タグ<br>
	 *            TrainingTimeInsertDialogクラスのPLAY_TIME、START_TIME、 ADD_TRAINING
	 */
	public TrainingTimeInsertDialog(TimeChangedListener listener, int tag) {
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
	 * 計測時間要素をピッカーに設定する
	 */
	private void initPlayTime() {
		pickerHour.setElements(getActivity().getApplicationContext(), TAG_HOUR,
				TimeUtil.getPlayZeroHour());
		pickerMin.setElements(getActivity().getApplicationContext(), TAG_MIN,
				TimeUtil.getPlayNotZeroMin());
		unit1 = PLAY_UNIT1;
		unit2 = PLAY_UNIT2;
	}

	/**
	 * 開始時間要素をピッカーに設定する
	 */
	private void initStartTime() {
		pickerHour.setElements(getActivity().getApplicationContext(), TAG_HOUR,
				TimeUtil.getHour());
		pickerMin.setElements(getActivity().getApplicationContext(), TAG_MIN,
				TimeUtil.getMin());
		unit1 = START_UNIT1;
		unit2 = START_UNIT2;
	}

	/**
	 * 画面の初期化処理をする
	 */
	private void init() {
		pickerHour = (DrumPicker) dialog.findViewById(R.id.picker_hour);
		pickerHour.setOnDrumPickerListener(this);
		pickerMin = (DrumPicker) dialog.findViewById(R.id.picker_min);
		pickerMin.setOnDrumPickerListener(this);
		switch (tag) {
		case PLAY_TIME:
			initPlayTime();
			break;
		case START_TIME:
			initStartTime();
			break;
		case ADD_TRAINING:
			initPlayTime();
			break;
		default:
			break;
		}
		textInsert = (TextView) dialog.findViewById(R.id.txt_setting_insert);
		textHourUnit = (TextView) dialog.findViewById(R.id.text_hour);
		textHourUnit.setText(unit1);
		textMinUnit = (TextView) dialog.findViewById(R.id.text_min);
		textMinUnit.setText(unit2);
		button = (Button) dialog.findViewById(R.id.button_decided);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				insert();
			}
		});
	}

	/**
	 * リスナーに通知し、ダイアログを閉じる
	 */
	private void insert() {
		listener.onSelected(tag, hour, min);
		dismiss();
	}

	/**
	 * ダイアログを設定する
	 */
	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_insert_training_picker);
		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.CENTER;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
	}

	@Override
	public void onScrollChanged(String tag, String element, int index) {
		if (tag == TAG_HOUR) {
			hour = element;
			if (this.tag == PLAY_TIME || this.tag == ADD_TRAINING) {
				if (element.equals("0")) {
					pickerMin.setElements(getActivity(), TAG_MIN,
							TimeUtil.getPlayNotZeroMin());
				} else {
					pickerMin.setElements(getActivity(), TAG_MIN,
							TimeUtil.getMin());
				}
			}
		} else if (tag == TAG_MIN) {
			min = element;
			if (this.tag == PLAY_TIME || this.tag == ADD_TRAINING) {
				if (element.equals("0")) {
					pickerHour.setElements(getActivity(), TAG_MIN,
							TimeUtil.getPlayHour());
				} else {
					pickerHour.setElements(getActivity(), TAG_HOUR,
							TimeUtil.getPlayZeroHour());
				}
			}
		}
		time = hour + unit1 + min + unit2;
		textInsert.setText(time);
	}
}
