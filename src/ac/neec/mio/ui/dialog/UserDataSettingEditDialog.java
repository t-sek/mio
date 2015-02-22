package ac.neec.mio.ui.dialog;

import static ac.neec.mio.consts.SignUpConstants.*;
import ac.neec.mio.R;
import ac.neec.mio.user.User;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserDataSettingEditDialog extends DialogFragment {

	public static final int NAME = 1;
	public static final int MAIL = 2;
	public static final int USER_ID = 3;

	private Dialog dialog;
	private TextView textTitle;
	private EditText edit;
	private Button button;
	private EditChangedListener listener;
	private int item;
	private User user = User.getInstance();

	public interface EditChangedListener {
		void dataChanged();
	}

	public UserDataSettingEditDialog(EditChangedListener listener, int item) {
		this.listener = listener;
		this.item = item;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new Dialog(getActivity());
		setDialog();
		init();
		return dialog;
	}

	private void init() {
		textTitle = (TextView) dialog.findViewById(R.id.dialog_title);
		button = (Button) dialog.findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				storeUpEditData();
				listener.dataChanged();
				dialog.dismiss();
			}
		});
		edit = (EditText) dialog.findViewById(R.id.edit);
		edit.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		switch (item) {
		case NAME:
			edit.setInputType(InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
			edit.setText(user.getName());
			textTitle.setText(name());
			break;
		case MAIL:
			edit.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			edit.setText(user.getMail());
			textTitle.setText(mail());
			break;
		case USER_ID:
//			edit.setInputType(InputType.TYPE_CLASS_PHONE);
			edit.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_SUBJECT);
			// edit.setInputType(InputType.TYPE_CLASS_TEXT);
			edit.setText(user.getId());
			textTitle.setText(userId());
			break;
		default:
			break;
		}
	}

	private void storeUpEditData() {
		switch (item) {
		case NAME:
			user.setName(edit.getText().toString());
			break;
		case MAIL:
			user.setMail(edit.getText().toString());
			break;
		case USER_ID:
			user.setId(edit.getText().toString());
			break;
		default:
			break;
		}

	}

	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_user_data_setting_edit);
		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.CENTER;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
	}

}
