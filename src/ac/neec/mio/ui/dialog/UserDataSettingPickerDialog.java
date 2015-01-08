package ac.neec.mio.ui.dialog;

import com.sek.drumpicker.DrumPicker;
import com.sek.drumpicker.DrumPickerListener;

import ac.neec.mio.R;
import ac.neec.mio.ui.listener.CallbackListener;
import ac.neec.mio.user.User;
import ac.neec.mio.user.gender.Gender;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class UserDataSettingPickerDialog extends DialogFragment implements
		DrumPickerListener {

	private static final String TAG_PICKER = "picker";

	private Dialog dialog;
	private String[] elements;
	private PickerChangedListener listener;
	private DrumPicker picker;
	private TextView textInsert;

	public interface PickerChangedListener {
		void dataChanged();
	}

	public UserDataSettingPickerDialog(PickerChangedListener listener,
			String[] elements) {
		this.listener = listener;
		// elements = new String[elements.length];
		this.elements = elements;
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
		picker.setElements(getActivity().getApplicationContext(), TAG_PICKER,
				elements);
		picker.setOnDrumPickerListener(this);
		picker.setScrollPosition(0);
		textInsert = (TextView) dialog.findViewById(R.id.txt_setting_insert);
	}

	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_user_data_setting_picker);
		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.CENTER;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
	}

	@Override
	public void onScrollChanged(String tag, String element, int index) {
		textInsert.setText(element);
	}

}
