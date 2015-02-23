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

/**
 * ユーザ情報のパスワードを変更するダイアログクラス
 */
public class UserDataSettingPasswordDialog extends DialogFragment {

	/**
	 * ダイアログインスタンス
	 */
	private Dialog dialog;
	/**
	 * 現在のパスワードを入力するフォーム
	 */
	private EditText editOld;
	/**
	 * 新しいのパスワードを入力するフォーム
	 */
	private EditText editNew;
	/**
	 * 新しいのパスワードの確認を入力するフォーム
	 */
	private EditText editNewConf;
	/**
	 * 現在のパスワードが違うエラーを表示するテキストビュー
	 */
	private TextView alartOld;
	/**
	 * 新しいパスワードの確認エラーを表示するテキストビュー
	 */
	private TextView alartNew;
	/**
	 * 決定ボタン
	 */
	private Button button;
	/**
	 * コールバックリスナー
	 */
	private PasswordChangedListener listener;
	/**
	 * ユーザ情報
	 */
	private User user = User.getInstance();

	/**
	 * パスワード変更完了を通知するリスナー
	 */
	public interface PasswordChangedListener {
		/**
		 * パスワード変更完了を通知する
		 */
		void dataChanged();
	}

	/**
	 * 
	 * @param listener
	 *            コールバックリスナー
	 */
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

	/**
	 * 画面の初期化処理をする
	 */
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

	/**
	 * パスワードを保存する
	 */
	private void storeUpEditData() {
		user.setPassword(editNew.getText().toString());
	}

	/**
	 * 入力項目を確認する
	 * 
	 * @return true エラーなし<br>
	 *         false エラーあり
	 */
	private boolean validate() {
		boolean val = true;
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

	/**
	 * ダイアログを設定する
	 */
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
