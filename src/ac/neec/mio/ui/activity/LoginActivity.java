package ac.neec.mio.ui.activity;

import java.io.InputStream;

import ac.neec.mio.R;
import ac.neec.mio.consts.ErrorConstants;
import ac.neec.mio.consts.MessageConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.ui.dialog.LoadingDialog;
import ac.neec.mio.user.User;
import ac.neec.mio.user.UserInfo;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.user.gender.GenderFactory;
import ac.neec.mio.util.DateUtil;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * ログイン画面クラス
 *
 */
public class LoginActivity extends Activity implements Sourceable {

	/**
	 * ログインエラーメッセージ
	 */
	private static final int MESSAGE_TOAST = 1;
	/**
	 * ネットワークエラーメッセージ
	 */
	private static final int MESSAGE_NETWORK_ERROR = 2;
	/**
	 * 不正文字入力エラーメッセージ
	 */
	private static final int MESSAGE_VALIDATE = 3;

	/**
	 * ユーザID入力フォーム
	 */
	private EditText editId;
	/**
	 * パスワード入力フォーム
	 */
	private EditText editPass;
	/**
	 * ログインボタン
	 */
	private Button buttonLogin;
	/**
	 * ユーザ情報
	 */
	private User user = User.getInstance();
	/**
	 * ログイン中ダイアログ
	 */
	private LoadingDialog dialogLoading = new LoadingDialog(
			MessageConstants.login());
	/**
	 * WebAPI接続インスタンス
	 */
	private ApiDao dao;
	/**
	 * 画面ハンドラー
	 */
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_TOAST:
				dialogLoading.dismiss();
				Toast.makeText(getApplicationContext(), ErrorConstants.login(),
						Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_NETWORK_ERROR:
				dialogLoading.dismiss();
				Toast.makeText(getApplicationContext(),
						ErrorConstants.networkError(), Toast.LENGTH_SHORT)
						.show();
				break;
			case MESSAGE_VALIDATE:
				dialogLoading.dismiss();
				Toast.makeText(getApplicationContext(),
						ErrorConstants.scriptValidate(), Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initFindViews();
		setListener();
		dao = DaoFacade.getApiDao(this);
	}

	/**
	 * 画面の初期化処理をする
	 */
	private void initFindViews() {
		editId = (EditText) findViewById(R.id.edit_id);
		editPass = (EditText) findViewById(R.id.edit_pass);
		buttonLogin = (Button) findViewById(R.id.button_login);
	}

	/**
	 * ビューにリスナーを設定する
	 */
	private void setListener() {
		buttonLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View V) {// ここからボタン押した時の処理？
				// tx1にIDの内容を取得してみる
				// tx2にPassの内容を取得してみる
				// pass = String.valueOf(editPass.getText());

				// intentTop();
				downloadLoginInfo();
			}
		});
	}

	/**
	 * ログインチェックをする
	 */
	private void downloadLoginInfo() {
		dialogLoading.show(getFragmentManager(), "");
		dao.selectUser(editId.getText().toString(), editPass.getText()
				.toString());
	}

	/**
	 * トップ画面に遷移する
	 */
	private void intentTop() {
		boolean res = true;
		if (res == true) {
			Intent intent = new Intent(LoginActivity.this, TopActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		}
	}

	/**
	 * ユーザ情報を保存する
	 * 
	 * @param info
	 *            ユーザ情報
	 */
	private void setUserInfo(UserInfo info) {
		user.setId(info.getUserId());
		user.setName(info.getName());
		user.setPassword(editPass.getText().toString());
		user.setMail(info.getMail());
		user.setBirth(info.getBirth());
		ProductDataFactory factory = new GenderFactory();
		Gender g = (Gender) factory.create(info.getGender().getGender());
		user.setGender(g.getGender());
		user.setHeight(info.getHeight());
		user.setWeight(info.getWeight());
		user.login();
	}

	@Override
	public void complete() {
		dialogLoading.dismiss();
		UserInfo userInfo = null;
		try {
			userInfo = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
		} catch (XmlReadException e) {
			e.printStackTrace();
		}
		if (userInfo == null || userInfo.getUserId() == null) {
			Message message = new Message();
			message.what = MESSAGE_TOAST;
			handler.sendMessage(message);
		} else {
			setUserInfo(userInfo);
			intentTop();
		}
	}

	@Override
	public void incomplete() {
		Message message = new Message();
		message.what = MESSAGE_NETWORK_ERROR;
		handler.sendMessage(message);
	}

	@Override
	public void complete(Bitmap image) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate() {
		Message message = new Message();
		message.what = MESSAGE_VALIDATE;
		handler.sendMessage(message);
	}

}