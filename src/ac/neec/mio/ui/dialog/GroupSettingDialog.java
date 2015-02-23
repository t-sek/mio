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

/**
 * グループ作成、グループ編集ダイアログクラス
 */
public class GroupSettingDialog extends DialogFragment {

	/**
	 * グループ作成タイトル
	 */
	public static final String NEW_GROUP = "新規グループ登録";
	/**
	 * グループ編集タイトル
	 */
	public static final String EDIT_GROUP = "グループ編集";

	/**
	 * コールバックリスナー
	 */
	private CallbackListener listener;
	/**
	 * タイトル
	 */
	private String tag;
	/**
	 * ダイアログインスタンス
	 */
	private Dialog dialog;
	/**
	 * ダイアログタイトルを表示するテキストビュー
	 */
	private TextView textTitle;
	/**
	 * 決定ボタン
	 */
	private Button buttonDecided;
	/**
	 * グループID入力フォーム
	 */
	private EditText editId;
	/**
	 * グループ名入力フォーム
	 */
	private EditText editName;
	/**
	 * コメント入力フォーム
	 */
	private EditText editComment;
	/**
	 * グループID
	 */
	private String groupId;
	/**
	 * グループ名
	 */
	private String groupName;
	/**
	 * コメント
	 */
	private String comment;

	/**
	 * 入力項目通知リスナー
	 */
	public interface CallbackListener {
		/**
		 * 入力項目を通知する
		 * 
		 * @param groupId
		 *            グループID
		 * @param groupName
		 *            グループ名
		 * @param comment
		 *            コメント
		 */
		void notifyChenged(String groupId, String groupName, String comment);
	}

	/**
	 * 
	 * @param listener
	 *            コールバックリスナー
	 * @param tag
	 *            タイトル GroupSettingDialogクラスのNEW_GROUPかEDIT_GROUPを設定
	 */
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

	/**
	 * グループIDを設定する
	 * 
	 * @param id
	 *            グループID
	 */
	public void setGroupId(String id) {
		groupId = id;
	}

	/**
	 * ダイアログを設定する
	 */
	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		dialog.setContentView(R.layout.dialog_new_group_setting);
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
	}

	/**
	 * 画面の初期化処理をする
	 */
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

	/**
	 * 入力項目を確認する
	 */
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

	/**
	 * ビューにリスナーを設定する
	 */
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
