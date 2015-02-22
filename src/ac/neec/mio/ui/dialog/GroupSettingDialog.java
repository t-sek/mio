package ac.neec.mio.ui.dialog;

import com.google.android.gms.internal.co;

import ac.neec.mio.R;
import ac.neec.mio.filter.JapaneseInputFilter;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class GroupSettingDialog extends DialogFragment {

	public static final String NEW_GROUP = "新規グループ登録";
	public static final String EDIT_GROUP = "グループ編集";

	private CallbackListener listener;
	private String tag;
	private Dialog dialog;
	private TextView textTitle;
	private Button buttonDecided;
	private EditText editId;
	private EditText editName;
	private EditText editComment;
	private String groupId;
	private String groupName;
	private String comment;

	public interface CallbackListener {
		void notifyChenged(String groupId, String groupName, String comment);
	}

	public GroupSettingDialog(CallbackListener listener, String tag) {
		this.listener = listener;
		this.tag = tag;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new Dialog(getActivity());
		setDialog();
		Bundle bundle = getArguments();
		if (bundle != null) {
			groupName = bundle.getString("group_name");
			comment = bundle.getString("comment");
		}
		initFindViews();
		setListeners();
		return dialog;
	}

	public void setGroupId(String id) {
		groupId = id;
	}

	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		dialog.setContentView(R.layout.dialog_new_group_setting);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
	}

	private void initFindViews() {
		InputFilter[] filters = new InputFilter[] { new JapaneseInputFilter() };
		textTitle = (TextView) dialog.findViewById(R.id.dialog_group_title);
		textTitle.setText(tag);
		buttonDecided = (Button) dialog.findViewById(R.id.btn_dialog_decided);
		editId = (EditText) dialog.findViewById(R.id.edit_group_id);
		editId.setFilters(filters);
		if (tag.equals(EDIT_GROUP)) {
			editId.setHint(groupId);
			editId.setFocusableInTouchMode(false);
		}
		editName = (EditText) dialog.findViewById(R.id.edit_group_name);
		editName.setText(groupName);
		editComment = (EditText) dialog.findViewById(R.id.edit_group_comment);
		editComment.setText(comment);
	}

	private void checkInsertData() {
		String id;
		if (tag.equals(EDIT_GROUP)) {
			id = editId.getHint().toString();
		} else {
			id = editId.getText().toString();
		}
		String name = editName.getText().toString();
		String comment = editComment.getText().toString();
		if (id != null && name != null && comment != null) {
			listener.notifyChenged(id, name, comment);
		}
	}

	private void setListeners() {
		buttonDecided.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checkInsertData();
				dismiss();
			}
		});
	}

}
