package ac.neec.mio.ui.activity;

import com.facebook.Response;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;

import ac.neec.mio.R;
import ac.neec.mio.sns.facebook.Login;
import ac.neec.mio.sns.facebook.UserCallback;
import ac.neec.mio.ui.activity.SignUpActivity;
import ac.neec.mio.ui.activity.SplashActivity;
import ac.neec.mio.user.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FirstSignUpSelectActivity extends Activity {

	private Button buttonSignUp;
	private Button buttonFacebookLogin;
//	private Button buttonLogin;

	private Bundle bundle;
	private Login login;

	private User user = User.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_first_sign_up_select);
		this.bundle = savedInstanceState;
		getActionBar().hide();
		initFindViews();
		setListener();
	}

	private void initFindViews() {
		buttonSignUp = (Button) findViewById(R.id.btn_sign_up);
//		buttonFacebookLogin = (Button) findViewById(R.id.btn_facebook_login);
//		buttonLogin = (Button) findViewById(R.id.btn_login);
	}

	private void setListener() {
		buttonSignUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intentSignUp();
			}
		});
//		buttonFacebookLogin.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				facebookLogin();
//			}
//		});

		// buttonLogin.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// intentLogin();
		// }
		// });
	}

	private void facebookLogin() {
		login = new Login(this, Login.LOGIN, bundle);
		login.logoutFacebook();
		login.connectFacebookAuth();
	}

	private void intentSignUp() {
		Intent intent = new Intent(FirstSignUpSelectActivity.this,
				UserSignUpActivity.class);
		// SignUpActivity.class);
		startActivity(intent);
	}

	private void intentLogin() {
		Intent intent = new Intent(FirstSignUpSelectActivity.this,
				LoginActivity.class);
		startActivity(intent);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("activity", "requestCode " + requestCode);
		Log.d("activity", "resultCode " + resultCode);
		Log.d("activity", "data " + data);
		Log.d("result", "name " + user.getName());
		Log.d("result", "mail " + user.getMail());
		if (login != null) {
			login.helperOnActivityResult(requestCode, resultCode, data);
			intentSignUp();
		}
	}

}
