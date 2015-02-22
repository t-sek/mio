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

public class TrainingTimeInsertDialog extends DialogFragment implements
		DrumPickerListener {

	public static final int PLAY_TIME = 2;
	public static final int START_TIME = 3;
	public static final int ADD_TRAINING = 4;
	private static final String TAG_HOUR = "h";
	private static final String TAG_MIN = "m";
	private static final String PLAY_UNIT1 = "分";
	private static final String START_UNIT1 = "時";
	private static final String PLAY_UNIT2 = "秒";
	private static final String START_UNIT2 = "分";
	private Dialog dialog;
	private TimeChangedListener listener;
	private DrumPicker pickerHour;
	private DrumPicker pickerMin;
	private TextView textInsert;
	private TextView textHourUnit;
	private TextView textMinUnit;
	private Button button;
	private String hour = "0";
	private String min = "0";
	private String time;
	private int tag;
	private String unit1;
	private String unit2;

	public interface TimeChangedListener {
		void onSelected(int tag, String hour, String min);
	}

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

	private void initPlayTime() {
		pickerHour.setElements(getActivity().getApplicationContext(), TAG_HOUR,
				TimeUtil.getPlayZeroHour());
		pickerMin.setElements(getActivity().getApplicationContext(), TAG_MIN,
				TimeUtil.getPlayNotZeroMin());
		unit1 = PLAY_UNIT1;
		unit2 = PLAY_UNIT2;
	}

	private void initStartTime() {
		pickerHour.setElements(getActivity().getApplicationContext(), TAG_HOUR,
				TimeUtil.getHour());
		pickerMin.setElements(getActivity().getApplicationContext(), TAG_MIN,
				TimeUtil.getMin());
		unit1 = START_UNIT1;
		unit2 = START_UNIT2;
	}

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

	private void insert() {
		listener.onSelected(tag, hour, min);
		dismiss();
	}

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
