package ac.neec.mio.ui.activity;

import java.io.InputStream;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.AppConstants;
import ac.neec.mio.consts.ErrorConstants;
import ac.neec.mio.consts.PermissionConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.SQLiteInsertException;
import ac.neec.mio.exception.SQLiteTableConstraintException;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Group;
import ac.neec.mio.group.MemberInfo;
import ac.neec.mio.group.Permission;
import ac.neec.mio.pref.AppPreference;
import ac.neec.mio.timer.TimerManager;
import ac.neec.mio.training.category.TrainingCategory;
import ac.neec.mio.training.menu.TrainingMenu;
import ac.neec.mio.ui.dialog.SelectionAlertDialog;
import ac.neec.mio.ui.listener.TimerCallbackListener;
import ac.neec.mio.user.LoginState;
import ac.neec.mio.user.User;
import ac.neec.mio.user.UserInfo;
import ac.neec.mio.util.DateUtil;
import ac.neec.mio.util.TimeUtil;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * スプラッシュ画面クラス
 *
 */
public class SplashActivity extends FragmentActivity implements
		AnimationListener, TimerCallbackListener, Sourceable {

	private static final int FLAG_CATEGORY = 2;
	private static final int FLAG_MENU = 3;
	private static final int FLAG_USER = 4;
	private static final int FLAG_PERM = 5;
	private static final int MESSAGE_INTENT = 1;
	private static final int MESSAGE_UPDATE = 6;
	private static final int MESSAGE_ERROR = 9;
	private static final String SECTION = ".";

	/**
	 * タイマーマネージャーインスタンス
	 */
	private TimerManager manager;
	/**
	 * トップ画面遷移ボタン
	 */
	private ImageView buttonMeasurement;
	/**
	 * 新規登録ボタン
	 */
	private Button buttonSignUp;
	/**
	 * ログインボタン
	 */
	private Button buttonLogin;
	/**
	 * facebookログインボタン
	 */
	private Button buttonFacebookLogin;
	/**
	 * 取得中メッセージ
	 */
	private TextView textMessage;
	/**
	 * 取得中メッセージ
	 */
	private TextView textSection;
	/**
	 * 取得完了メッセージ
	 */
	private TextView textComplete;
	/**
	 * WebAPI接続インスタンス
	 */
	private ApiDao dao;
	/**
	 * ローカルデータベース接続インスタンス
	 */
	private SQLiteDao daoSql;
	/**
	 * WebAPI通信フラグ
	 */
	private int daoFlag;
	/**
	 * ユーザ情報
	 */
	private User user = User.getInstance();
	private Bundle bundle;
	/**
	 * 通信メッセージイテレータ
	 */
	private int sectionCount;
	/**
	 * 画面ハンドラー
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_INTENT:
				animationHeart();
				break;
			case MESSAGE_UPDATE:
				updateTime((String) message.obj);
				break;
			case MESSAGE_ERROR:
				textComplete.setText(ErrorConstants.networkError());
				textComplete.setVisibility(View.INVISIBLE);
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		this.bundle = savedInstanceState;
		initFindViews();
		Typeface face = Typeface.createFromAsset(getAssets(),
				"font/Roboto-Regular.ttf");
		setListeners();
		AppPreference.init(getApplicationContext());
		AppConstants.setResorces(getResources());
		AppConstants.setContext(getApplicationContext());
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
		downloadTrainingCategory();
		// downloadUserInfo();
		// intentTop();
		// startAnimation();
		manager = new TimerManager(this);
		manager.start();
	}

	/**
	 * 画面の初期化処理をする
	 */
	private void initFindViews() {
		buttonMeasurement = (ImageView) findViewById(R.id.btn_measurement_setting);
		buttonSignUp = (Button) findViewById(R.id.btn_sign_up);
		buttonLogin = (Button) findViewById(R.id.btn_login);
		buttonFacebookLogin = (Button) findViewById(R.id.btn_facebook_login);
		textMessage = (TextView) findViewById(R.id.text_message);
		textSection = (TextView) findViewById(R.id.text_section);
		textComplete = (TextView) findViewById(R.id.text_complete);
	}

	/**
	 * ビューにリスナーを設定する
	 */
	private void setListeners() {
		buttonMeasurement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		buttonSignUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intentSignUp();
			}
		});
		buttonLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intentLogin();
			}
		});
		buttonFacebookLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				facebookLogin();
			}
		});
	}

	/**
	 * ハートのアニメーションを設定する
	 */
	private void animationHeart() {
		manager.stop();
		textMessage.setVisibility(View.INVISIBLE);
		textSection.setVisibility(View.INVISIBLE);
		textComplete.setVisibility(View.VISIBLE);
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.title_heart);
		anim.setAnimationListener(this);
		buttonMeasurement.startAnimation(anim);
	}

	/**
	 * トップ画面に遷移する
	 */
	private void intentTop() {
		Intent intent = new Intent(SplashActivity.this, TopActivity.class);
		startActivity(intent);
		finish();
	}

	/**
	 * 新規登録画面に遷移する
	 */
	private void intentSignUp() {
		Intent intent = new Intent(SplashActivity.this,
				UserSignUpActivity.class);
		// FirstSignUpSelectActivity.class);
		startActivity(intent);
		// finish();
	}

	/**
	 * ログイン画面に遷移する
	 */
	private void intentLogin() {
		Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
		startActivity(intent);
		// finish();
	}

	/**
	 * 取得中メッセージを更新する
	 * 
	 * @param time
	 *            タイム
	 */
	private void updateTime(String time) {
		String text = null;
		switch (daoFlag) {
		case FLAG_CATEGORY:
			text = "トレーニング情報を取得中";
			break;
		case FLAG_MENU:
			text = "トレーニング情報を取得中";
			break;
		case FLAG_USER:
			text = "プロフィール情報を取得中";
			break;
		case FLAG_PERM:
			text = "プロフィール情報を取得中";
			break;
		default:
			break;
		}
		textMessage.setText(text);
		int nowTime = Integer.valueOf(TimeUtil.stringToSec(time));
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= sectionCount; i++) {
			sb.append(SECTION);
		}
		if (sectionCount >= 4) {
			sectionCount = 0;
		} else {
			sectionCount++;
		}
		textSection.setText(sb.toString());
	}

	/**
	 * WebAPIからトレーニングカテゴリーを取得する
	 */
	private void downloadTrainingCategory() {
		daoFlag = FLAG_CATEGORY;
		dao.selectTrainingCategory();
	}

	/**
	 * WebAPIからトレーニングメニューを取得する
	 */
	private void downloadTrainingMenu() {
		daoFlag = FLAG_MENU;
		dao.selectTrainingMenu();
	}

	/**
	 * WebAPIからユーザ情報を取得する
	 */
	private void downloadUserInfo() {
		daoFlag = FLAG_USER;
		dao.selectUser(user.getId(), user.getPassword());
	}

	/**
	 * WebAPIからユーザアイコンを取得する
	 */
	private void downloadUserImage(String image) {
		if (image != null) {
			dao.selectImage(image);
		} else {
			startAnimation();
		}
	}

	/**
	 * WebAPIから権限を取得する
	 */
	private void downloadPermition() {
		daoFlag = FLAG_PERM;
		dao.selectPermition();
	}

	/**
	 * アニメーションを開始する
	 */
	private void startAnimation() {
		Message message = new Message();
		message.what = MESSAGE_INTENT;
		handler.sendMessage(message);
	}

	@Override
	public void onAnimationStart(Animation animation) {
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (LoginState.getState() != LoginState.LOGIN) {
			// intentSignUp();
			textComplete.setVisibility(View.GONE);
			buttonSignUp.setVisibility(View.VISIBLE);
			buttonLogin.setVisibility(View.VISIBLE);
			buttonFacebookLogin.setVisibility(View.GONE);
			// popupSelectForm();
		} else {
			intentTop();
		}
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}

	@Override
	public void onUpdate(String time) {
		Message message = new Message();
		message.what = MESSAGE_UPDATE;
		message.obj = time;
		handler.sendMessage(message);
	}

	@Override
	public void onAlarm() {
	}

	@Override
	public void onUpdateLap(String time) {
		// TODO Auto-generated method stub

	}

	/**
	 * ローカルデータベースにトレーニングカテゴリーを保存する
	 * 
	 * @param category
	 *            トレーニングカテゴリー
	 */
	private void insertTrainingCategory(List<TrainingCategory> category) {
		daoSql.deleteTrainingCategoryTableAll();
		for (TrainingCategory item : category) {
			try {
				daoSql.insertTrainingCategory(item.getTrainingCategoryId(),
						item.getTrainingCategoryName());
			} catch (SQLiteInsertException e) {
				e.printStackTrace();
			} catch (SQLiteTableConstraintException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ローカルデータベースにトレーニングメニューを保存する
	 * 
	 * @param menu
	 *            トレーニングメニュー
	 */
	private void insertTrainingMenu(List<TrainingMenu> menu) {
		daoSql.deleteTrainingMenuTableAll();
		for (TrainingMenu item : menu) {
			try {
				daoSql.insertTrainingMenu(item.getTrainingMenuId(),
						item.getTrainingName(), item.getMets(),
						item.getTrainingCategoryId(), item.getColor());
			} catch (SQLiteInsertException e) {
				e.printStackTrace();
			} catch (SQLiteTableConstraintException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * ユーザ情報を保存する
	 * 
	 * @param info
	 *            ユーザ情報
	 */
	private void insertUserInfo(UserInfo info) {
		if (info.getUserId() == null) {
			return;
		}
		user.setName(info.getName());
		user.setBirth(info.getBirth());
		user.setHeight(info.getHeight());
		user.setWeight(info.getWeight());
		user.setMail(info.getMail());
		String date = info.getRole().getCreated();
		user.setCreated(DateUtil.splitCreated(date));
	}

	/**
	 * ローカルデータベースに権限を保存する
	 * 
	 * @param perm
	 *            権限
	 */
	private void insertPermition(List<Permission> perm) {
		daoSql.deletePermission();
		try {
			for (Permission permission : perm) {
				daoSql.insertPermission(permission);
			}
		} catch (SQLiteInsertException e) {
			e.printStackTrace();
		} catch (SQLiteTableConstraintException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("activity", "requestCode " + requestCode);
		Log.d("activity", "resultCode " + resultCode);
		Log.d("activity", "data " + data);
		Log.d("result", "name " + user.getName());
		Log.d("result", "mail " + user.getMail());
//		if (login != null) {
//			Log.d("activity", "intent name " + user.getName());
//			login.helperOnActivityResult(requestCode, resultCode, data);
//			intentSignUp();
//		}
	}

	/**
	 * ローカルデータベースに所属しているグループを保存する
	 * 
	 * @param info
	 *            ユーザ情報
	 */
	private void setMyGroupList(UserInfo info) {
		daoSql.deleteAffiliation();
		List<Group> groups = info.getGroups();
		for (Group group : groups) {
			try {
				daoSql.insertAffiliation(group.getId(), group.getPermissionId());
				daoSql.insertGroup(group.getId(), group.getGroupName(),
						group.getComment(), group.getUserId(),
						group.getCreated(), group.getPermissionId());
			} catch (SQLiteInsertException e) {
				e.printStackTrace();
			} catch (SQLiteTableConstraintException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void complete() {
		try {
			switch (daoFlag) {
			case FLAG_CATEGORY:
				List<TrainingCategory> category;
				category = dao.getResponse();
				insertTrainingCategory(category);
				downloadTrainingMenu();
				break;
			case FLAG_MENU:
				List<TrainingMenu> menu;
				menu = dao.getResponse();
				insertTrainingMenu(menu);
				downloadPermition();
				break;
			case FLAG_PERM:
				List<Permission> perm = null;
				perm = dao.getResponse();
				insertPermition(perm);
				downloadUserInfo();
				// startAnimation();
				break;
			case FLAG_USER:
				UserInfo info = null;
				info = dao.getResponse();
				daoSql.deleteGroup();
				setMyGroupList(info);
				insertUserInfo(info);
				downloadUserImage(info.getImageInfo().getSmallImage());
				break;
			default:
				break;
			}
		} catch (XmlParseException e) {
			startAnimation();
			return;
		} catch (XmlReadException e) {
			startAnimation();
			return;

		}
	}

	@Override
	public void incomplete() {
		Message message = new Message();
		message.what = MESSAGE_ERROR;
		handler.sendMessage(message);
		startAnimation();
	}

	@Override
	public void complete(Bitmap image) {
		user.setImage(image);
		startAnimation();
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}

}
