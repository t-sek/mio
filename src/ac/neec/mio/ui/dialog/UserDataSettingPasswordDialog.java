package ac.neec.mio.ui.dialog;

import ac.neec.mio.R;
import ac.neec.mio.user.User;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserDataSettingPasswordDialog extends DialogFragment {

	public static final int NAME = 1;
	public static final int MAIL = 2;
	public static final int USER_ID = 3;
	public static final int PASSWORD = 4;

	private Dialog dialog;
	private EditText editOld;
	private EditText editNew;
	private EditText editNewConf;
	private TextView alartOld;
	private TextView alartNew;
	private Button button;
	private PasswordChangedListener listener;
	private User user = User.getInstance();

	public interface PasswordChangedListener {
		void dataChanged();
	}

	public UserDataSettingPasswordDialog(PasswordChangedListener listener) {
		this.listener = listener;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new Dialog(getActivity());
		setDialog();
		init();
		return dialog;
	}

	private void init() {
		editOld = (EditText) dialog.findViewById(R.id.edit_old);
		editNew = (EditText) dialog.findViewById(R.id.edit_new);
		alartOld = (TextView) dialog.findViewById(R.id.alart_old_pass);
		alartOld.setVisibility(View.INVISIBLE);
		alartNew = (TextView) dialog.findViewById(R.id.alart_new_pass);
		alartNew.setVisibility(View.INVISIBLE);
		editNewConf = (EditText) dialog.findViewById(R.id.edit_new_conf);
		button = (Button) dialog.findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validate()) {
					storeUpEditData();
					listener.dataChanged();
					dialog.dismiss();
				}
			}
		});
	}

	private void storeUpEditData() {
		user.setPassword(editNew.getText().toString());
	}

	private boolean validate() {
		boolean val = true;
		Log.d("dialog", "user "+user.getPassword());
		Log.d("dialog", "text "+editOld.getText().toString());
		if (!editOld.getText().toString().equals(user.getPassword())) {
			alartOld.setVisibility(View.VISIBLE);
			val = false;
		} else {
			alartOld.setVisibility(View.GONE);
		}
		if (!editNew.getText().toString()
				.equals(editNewConf.getText().toString())) {
			alartNew.setVisibility(View.VISIBLE);
			val = false;
		} else {
			alartNew.setVisibility(View.INVISIBLE);
		}
		return val;
	}

	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_user_data_setting_password);
		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.CENTER;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
	}

}
