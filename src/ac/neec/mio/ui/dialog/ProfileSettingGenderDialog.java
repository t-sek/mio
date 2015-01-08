package ac.neec.mio.ui.dialog;

import ac.neec.mio.R;
import ac.neec.mio.ui.listener.CallbackListener;
import ac.neec.mio.user.User;
import ac.neec.mio.user.gender.Gender;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ProfileSettingGenderDialog extends DialogFragment {

	private Dialog dialog;
	private CallbackListener listener;
	private String gender;

	private RadioGroup radioGroup;
	private RadioButton male;
	private RadioButton female;

	public ProfileSettingGenderDialog(CallbackListener listener, String gender) {
		Log.d("", "dialog");
		this.listener = listener;
		this.gender = gender;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new Dialog(getActivity());
		setDialog();
		init();
		return dialog;
	}

	private void init() {
		radioGroup = (RadioGroup) dialog.findViewById(R.id.group_gender);
		male = (RadioButton) dialog.findViewById(R.id.radio_male);
		female = (RadioButton) dialog.findViewById(R.id.radio_female);
		if (gender.equals(Gender.MALE)) {
			radioGroup.check(male.getId());
		} else if (gender.equals(Gender.FEMALE)) {
			radioGroup.check(female.getId());
		}
	}

	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_profile_setting_gender);
		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.CENTER;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
	}

}
