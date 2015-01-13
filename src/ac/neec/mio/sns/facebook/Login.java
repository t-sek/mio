package ac.neec.mio.sns.facebook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import ac.neec.mio.user.User;
import ac.neec.mio.consts.SQLConstants;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.Session.OpenRequest;
import com.facebook.Session.StatusCallback;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.UiLifecycleHelper;
import com.facebook.android.Facebook;
import com.facebook.model.GraphUser;

public class Login {

	private static final String API_KEY = SQLConstants.facebookApiKey();
	private static final String NAME = "name";
	private static final String GENDER = "gender";
	private static final String EMAIL = "email";
	private static final String FIELDS = "fields";
	public static final int LOGIN = 1;

	private UiLifecycleHelper uiHelper;

	private Activity activity;
	private int type;
	private User user = User.getInstance();
	private UserCallback listener;

	private Session.StatusCallback facebookCallback = new Session.StatusCallback() {
		@SuppressWarnings("deprecation")
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
			Request.executeMeRequestAsync(session,
					new Request.GraphUserCallback() {
						@Override
						public void onCompleted(GraphUser user,
								Response response) {
							// it never gets here...
							if (user != null) {
								parseUserInfo(user.getInnerJSONObject());
							}
						}
					});
		}
	};

	public Login(Activity activity, int type, Bundle bundle) {
		this.activity = activity;
		this.type = type;
		this.listener = listener;
		initFacebook(bundle);
	}

	private void requestUserImage(String userId) {
		// new Request(session, "/me/picture", null, HttpMethod.GET,
		new Request(Session.getActiveSession(), "https://graph.facebook.com/"
				+ userId, null, HttpMethod.GET, new Request.Callback() {
			public void onCompleted(Response response) {
				Log.d("request", "responce " + response);
			}
		}).executeAsync();
	}

	private void parseUserInfo(JSONObject response) {
		try {
			requestUserImage(response.getString("id"));
			// Log.d("JSONSampleActivity", response.getString("id"));
			user.setName(response.getString(NAME));
			user.setGender(response.getString(GENDER));
			user.setMail(response.getString(EMAIL));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Facebook認証に接続する
	 */
	public void connectFacebookAuth() {
		Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
		facebookLogin(setSession());
	}

	/**
	 * セッションを設定する
	 */
	@SuppressWarnings("unused")
	private Session setSession() {
		// Session session = Session.getActiveSession();
		Session session = new Session(activity.getApplicationContext());
		if (session == null) {
			session = new Session(activity.getApplicationContext());
		}
		// リクエストの生成
		OpenRequest openRequest = new OpenRequest(activity)
				.setCallback(facebookCallback);
		// アクティブセッション
		Session.setActiveSession(session);
		// パーミッションの設定
		List<String> permissions = new ArrayList<String>();
		permissions.add(EMAIL);
		openRequest.setPermissions(permissions);
		// 認証要求
		session.openForRead(openRequest);
		return session;
	}

	private void initFacebook(Bundle savedInstanceState) {
		uiHelper = new UiLifecycleHelper(activity, facebookCallback);
		uiHelper.onCreate(savedInstanceState);
	}

	/**
	 * facebookにログインする
	 */
	private void facebookLogin(Session session) {
		Session.setActiveSession(session);

		Bundle parameters = new Bundle();
		parameters.putString("method", "auth.expireSession");
		// String response = request(parameters);
		if (!session.isOpened()) {
			if (session.isClosed()) {
			}
			OpenRequest openRequest = new OpenRequest(activity);
			openRequest.setCallback(facebookCallback);
			// openRequest.setPermissions(permissions)
			session.openForRead(openRequest);
		} else {
			Session.openActiveSession(activity, true, facebookCallback);
		}
	}

	public void logoutFacebook() {
		Session session = Session.getActiveSession();
		if (!session.isClosed()) {
			// セッションとトークン情報を廃棄する。
			session.closeAndClearTokenInformation();
		}
	}

	/**
	 * セッションが開いた時にcallbackする
	 */
	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		// final Session tempSession = session;
		Log.d("login", "state:" + state.isOpened());
		Log.d("login", "session:" + session.isOpened());
		Log.d("login", "accessToken:" + session.getAccessToken());
		for (String perm : session.getPermissions()) {
			Log.d("perm", "perm " + perm);
		}
		user.setFacebookAccessToken(session.getAccessToken());
		if (session.isOpened()) {
			// ログイン
			if (LOGIN == type) {
				// something
			}
		} else if (state.isClosed()) {
		}
	}

	public void helperOnActivityResult(int requestCode, int resultCode,
			Intent data) {
		Log.d("activity", "name " + data.getStringExtra("name"));
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

}
