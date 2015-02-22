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
import ac.neec.mio.ui.dialog.LoadingDialog;
import ac.neec.mio.user.User;
import ac.neec.mio.user.UserInfo;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.util.BodilyUtil;
import ac.neec.mio.util.DateUtil;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class UserSignUpConfActivity extends Activity implements Sourceable {

	private static final int MESSAGE_USER_ERROR = 1;
	private static final int MESSAGE_NETWORK_ERROR = 4;
	private static final int MESSAGE_VALIDATE = 5;
	private static final int DAO_UPDATE_USER = 2;
	private static final int DAO_UPDATE_HEART_RATE = 3;

	private TextView textId;
	private TextView textName;
	private TextView textMail;
	private TextView textBirth;
	private TextView textGender;
	private TextView textHeight;
	private TextView textWeight;
	private TextView textQuietHeartRate;
	private Button buttonSignUp;

	private User user = User.getInstance();
	private LoadingDialog dialogLoading = new LoadingDialog(
			MessageConstants.add());
	private ApiDao dao;
	private int daoFlag;

	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_USER_ERROR:
				dialogLoading.dismiss();
				Toast.makeText(getApplicationContext(),
						ErrorConstants.signUp(), Toast.LENGTH_SHORT).show();
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
		setContentView(R.layout.activity_user_sign_up_conf);
		initFindViews();
		setListener();
		setUserData();
		dao = DaoFacade.getApiDao(this);
	}

	private void initFindViews() {
		textId = (TextView) findViewById(R.id.text_id);
		textName = (TextView) findViewById(R.id.text_name);
		textMail = (TextView) findViewById(R.id.text_mail);
		textBirth = (TextView) findViewById(R.id.text_birth);
		textGender = (TextView) findViewById(R.id.text_gender);
		textHeight = (TextView) findViewById(R.id.text_height);
		textWeight = (TextView) findViewById(R.id.text_weight);
		textQuietHeartRate = (TextView) findViewById(R.id.text_quiet_heart_rate);
		buttonSignUp = (Button) findViewById(R.id.button_sign_up);
	}

	private void setListener() {
		buttonSignUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				uploadUserInfo();
			}
		});
	}

	private void uploadUserInfo() {
		dialogLoading.show(getFragmentManager(), "");
		dao.insertUser(user.getId(),
				user.getName(),
				// DateUtil.dateFormat(User.getInstance().getBirth()),
				user.getBirth(), user.getGender(),
				String.valueOf(Math.round(user.getHeight())), user.getMail(),
				user.getPassword());
		daoFlag = DAO_UPDATE_USER;
	}

	private void setUserData() {
		textId.setText(user.getId());
		textName.setText(user.getName());
		textMail.setText(user.getMail());
		// textBirth.setText(user.getBirth());
		textBirth.setText(DateUtil.japaneseFormat(user.getBirth()));
		if (user.getGender().equals(Gender.MALE)) {
			textGender.setText("男性");
		} else {
			textGender.setText("女性");
		}
		textHeight.setText(String.valueOf(Math.round(user.getHeight())));
		textWeight.setText(BodilyUtil.weightToRound(user.getWeight()));
		textQuietHeartRate.setText(String.valueOf(user.getQuietHeartRate()));
	}

	private void intentTop() {
		Intent intent = new Intent(UserSignUpConfActivity.this,
				TopActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();
	}

	@Override
	public void complete() {
		switch (daoFlag) {
		case DAO_UPDATE_USER:
			UserInfo info = null;
			try {
				info = dao.getResponse();
			} catch (XmlParseException e) {
				e.printStackTrace();
			} catch (XmlReadException e) {
				e.printStackTrace();
			}
			if (info != null) {
				if (info.getUserId().equals(this.user.getId())) {
					// HttpManager.uploadUserQuietHeartRate(
					// getApplicationContext(), this, info.getUserId(),
					// textQuietHeartRate.getText().toString());
					dao.updateUserQuietHeartRate(info.getUserId(), this.user
							.getPassword(), textQuietHeartRate.getText()
							.toString());
					daoFlag = DAO_UPDATE_HEART_RATE;
					this.user.login();
					intentTop();
				}
			} else {
				Message message = new Message();
				message.what = MESSAGE_USER_ERROR;
				handler.sendMessage(message);
			}
			break;
		case DAO_UPDATE_HEART_RATE:
			int heartRate = 0;
			try {
				heartRate = dao.getResponse();
			} catch (XmlParseException e) {
				e.printStackTrace();
			} catch (XmlReadException e) {
				e.printStackTrace();
			}
			dao.updateUserWeight(user.getId(), user.getPassword(),
					BodilyUtil.weightToRound(user.getWeight()));
			if (heartRate != 0) {
			} else {
				Message message = new Message();
				message.what = MESSAGE_USER_ERROR;
				handler.sendMessage(message);
			}
			break;
		default:
			break;
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
