package ac.neec.mio.ui.activity;

import ac.neec.mio.R;
import ac.neec.mio.consts.ErrorConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.item.api.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.http.HttpManager;
import ac.neec.mio.http.listener.HttpUserResponseListener;
import ac.neec.mio.ui.dialog.LoadingDialog;
import ac.neec.mio.user.User;
import ac.neec.mio.user.UserInfo;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.util.BodilyUtil;
import ac.neec.mio.util.DateUtil;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

public class UserSignUpConfActivity extends Activity implements Sourceable {

	private static final int MESSAGE_USER_ERROR = 1;
	private static final int MESSAGE_NETWORK_ERROR = 4;
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
	private LoadingDialog dialogLoading = new LoadingDialog();
	private ApiDao dao;
	private int daoFlag;

	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_USER_ERROR:
				dialogLoading.dismiss();
				Toast.makeText(getApplicationContext(), "登録に失敗しました",
						Toast.LENGTH_SHORT).show();
				break;
			case MESSAGE_NETWORK_ERROR:
				dialogLoading.dismiss();
				Toast.makeText(getApplicationContext(), ErrorConstants.networkError(),
						Toast.LENGTH_SHORT).show();
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
		dao = DaoFacade.getApiDao(getApplicationContext(), this);
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
		// HttpManager.uploadUserInfo(this, this, user.getId(), user.getName(),
		// DateUtil.dateFormat(User.getInstance().getBirth()),
		// user.getGender(), String.valueOf(Math.round(user.getHeight())),
		// user.getMail(), user.getPassword(),
		// BodilyUtil.weightToRound(user.getWeight()));
		Log.d("activity", "birth " + User.getInstance().getBirth());
		dao.insertUser(getApplicationContext(), user.getId(),
				user.getName(),
				// DateUtil.dateFormat(User.getInstance().getBirth()),
				user.getBirth(), user.getGender(),
				String.valueOf(Math.round(user.getHeight())), user.getMail(),
				user.getPassword(), BodilyUtil.weightToRound(user.getWeight()));
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

	// @Override
	// public void responseUserInfo(UserInfo user) {
	// dialogLoading.dismiss();
	// if (user != null) {
	// if (user.getUserId().equals(this.user.getId())) {
	// HttpManager.uploadUserQuietHeartRate(getApplicationContext(),
	// this, user.getUserId(), textQuietHeartRate.getText()
	// .toString());
	// this.user.login();
	// intentTop();
	// }
	// } else {
	// Message message = new Message();
	// message.what = MESSAGE_USER_ERROR;
	// handler.sendMessage(message);
	// }
	// }
	//
	// @Override
	// public void responseQuietHeartRate(int heartRate) {
	// dialogLoading.dismiss();
	// Log.d("activiity", "heartRate " + heartRate);
	// if (heartRate != 0) {
	// } else {
	// Message message = new Message();
	// message.what = MESSAGE_USER_ERROR;
	// handler.sendMessage(message);
	// }
	// }

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
					dao.updateUserQuietHeartRate(getApplicationContext(), info
							.getUserId(), textQuietHeartRate.getText()
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
}
