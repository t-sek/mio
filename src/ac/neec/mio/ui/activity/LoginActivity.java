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

public class LoginActivity extends Activity implements Sourceable {

	private static final int MESSAGE_TOAST = 1;
	private static final int MESSAGE_NETWORK_ERROR = 2;
	private static final int MESSAGE_VALIDATE = 3;

	private EditText editId;
	private EditText editPass;
	private Button buttonLogin;
	private String id;
	private User user = User.getInstance();
	private LoadingDialog dialogLoading = new LoadingDialog(
			MessageConstants.login());
	private ApiDao dao;

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

	private void initFindViews() {
		editId = (EditText) findViewById(R.id.edit_id);
		editPass = (EditText) findViewById(R.id.edit_pass);
		buttonLogin = (Button) findViewById(R.id.button_login);
	}

	private void setListener() {
		buttonLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View V) {// ここからボタン押した時の処理？
				// tx1にIDの内容を取得してみる
				id = String.valueOf(editId.getText());
				// tx2にPassの内容を取得してみる
				// pass = String.valueOf(editPass.getText());

				// intentTop();
				downloadLoginInfo();
			}
		});
	}

	private void downloadLoginInfo() {
		dialogLoading.show(getFragmentManager(), "");
		dao.selectUser(editId.getText().toString(), editPass.getText()
				.toString());
	}

	private void intentTop() {
		boolean res = true;
		if (res == true) {
			Intent intent = new Intent(LoginActivity.this, TopActivity.class);
			intent.putExtra("ID", id);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
					| Intent.FLAG_ACTIVITY_CLEAR_TASK);
			startActivity(intent);
			finish();
		}
	}

	private void intentSignUp() {
		Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
		startActivity(intent);
		finish();
	}

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
			Log.d("activity", "user " + userInfo);
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