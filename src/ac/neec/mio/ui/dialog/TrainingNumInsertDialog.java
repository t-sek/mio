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

public class TrainingNumInsertDialog extends DialogFragment implements
		DrumPickerListener {

	public static final int CALORIE = 2;
	public static final int DISTANCE = 3;
	private static final String CALORIE_UNIT = "kcal";
	private static final String DISTANCE_UNIT = "km";
	private static final String TAG_PICKER = "picker";
	private Dialog dialog;
	private NumChangedListener listener;
	private DrumPicker picker;
	private TextView textInsert;
	private Button button;
	private String num;
	private int tag;
	private String unit;

	public interface NumChangedListener {
		void onSelected(int tag, String num);
	}

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

	private void insert() {
		listener.onSelected(tag, num);
		dismiss();
	}

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
