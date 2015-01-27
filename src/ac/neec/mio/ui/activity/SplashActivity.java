package ac.neec.mio.ui.activity;

import java.io.InputStream;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.AppConstants;
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
import ac.neec.mio.group.Permission;
import ac.neec.mio.pref.AppPreference;
import ac.neec.mio.sns.facebook.Login;
import ac.neec.mio.taining.category.TrainingCategory;
import ac.neec.mio.taining.menu.TrainingMenu;
import ac.neec.mio.timer.TimerManager;
import ac.neec.mio.ui.fragment.FirstSignUpSelectFragment;
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
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashActivity extends FragmentActivity implements
		AnimationListener, TimerCallbackListener, Sourceable {

	private static final int FLAG_CATEGORY = 2;
	private static final int FLAG_MENU = 3;
	private static final int FLAG_USER = 4;
	private static final int FLAG_PERM = 5;
	private static final int MESSAGE_INTENT = 1;
	private static final int MESSAGE_UPDATE = 6;
	private static final int WEIT_TIME = 100;

	private static final String SECTION = ".";

	private TimerManager manager;
	private TextView title;
	private ImageView buttonMeasurement;
	private Button buttonSignUp;
	private Button buttonLogin;
	private Button buttonFacebookLogin;
	private TextView textMessage;
	private ApiDao dao;
	private SQLiteDao daoSql;
	private int daoFlag;
	private User user = User.getInstance();
	private Bundle bundle;
	private Login login;
	private Bitmap image;
	private int sectionCount;

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_INTENT:
				animationHeart();
				break;
			case MESSAGE_UPDATE:
				updateTime((String) message.obj);
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
		title.setTypeface(face);
		setListeners();
		AppPreference.init(getApplicationContext());
		AppConstants.setResorces(getResources());
		AppConstants.setContext(getApplicationContext());
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
		// downloadTrainingCategory();
		startAnimation();
		manager = new TimerManager(this);
		manager.start();
	}

	private void initFindViews() {
		title = (TextView) findViewById(R.id.text_title);
		buttonMeasurement = (ImageView) findViewById(R.id.btn_measurement_setting);
		buttonSignUp = (Button) findViewById(R.id.btn_sign_up);
		buttonLogin = (Button) findViewById(R.id.btn_login);
		buttonFacebookLogin = (Button) findViewById(R.id.btn_facebook_login);
		textMessage = (TextView) findViewById(R.id.text_message);
	}

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
				Log.d("activity", "login button");
				intentLogin();
			}
		});
		buttonFacebookLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("activity", "login button");
				facebookLogin();
			}
		});
	}

	private void animationHeart() {
		manager.stop();
		textMessage.setText("完了!");
		Animation anim = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.title_heart);
		anim.setAnimationListener(this);
		buttonMeasurement.startAnimation(anim);
	}

	private void intentTop() {
		Intent intent = new Intent(SplashActivity.this, TopActivity.class);
		startActivity(intent);
		finish();
	}

	private void intentSignUp() {
		Intent intent = new Intent(SplashActivity.this,
				UserSignUpActivity.class);
		// FirstSignUpSelectActivity.class);
		startActivity(intent);
		Log.d("activity", "signup name " + user.getName());
		// finish();
	}

	private void facebookLogin() {
		login = new Login(this, Login.LOGIN, bundle);
		login.logoutFacebook();
		login.connectFacebookAuth();
	}

	private void intentLogin() {
		Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
		startActivity(intent);
		// finish();
	}

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
			text = "ユーザー情報を取得中";
			break;
		case FLAG_PERM:
			text = "ユーザー情報を取得中";
			break;
		default:
			break;
		}
		int nowTime = Integer.valueOf(TimeUtil.stringToSec(time));
		StringBuilder sb = new StringBuilder();
		sb.append(text);
		for (int i = 0; i <= sectionCount; i++) {
			sb.append(SECTION);
		}
		if (sectionCount >= 4) {
			sectionCount = 0;
		} else {
			sectionCount++;
		}
		textMessage.setText(sb.toString());
	}

	private void downloadTrainingCategory() {
		daoFlag = FLAG_CATEGORY;
		dao.selectTrainingCategory();
	}

	private void downloadTrainingMenu() {
		daoFlag = FLAG_MENU;
		dao.selectTrainingMenu();
	}

	private void downloadUserInfo() {
		daoFlag = FLAG_USER;
		dao.selectUser(getApplicationContext(), user.getId(),
				user.getPassword());
	}

	private void downloadUserImage(String image) {
		if (image != null) {
			dao.selectImage(image);
		} else {
			startAnimation();
		}
	}

	private void downloadPermition() {
		daoFlag = FLAG_PERM;
		dao.selectPermition();
	}

	private void startAnimation() {
		Message message = new Message();
		message.what = MESSAGE_INTENT;
		handler.sendMessage(message);
	}

	private void popupSelectForm() {
		Fragment newFragment = new FirstSignUpSelectFragment();
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		// transaction.replace(R.id.container_play, newFragment);
		// transaction.add(R.id.container_play, newFragment);
		transaction.setCustomAnimations(R.anim.ans_start, R.anim.ans_end);
		transaction.replace(R.id.container_select, newFragment);
		transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		transaction.addToBackStack(null);
		transaction.commit();
	}

	@Override
	public void onAnimationStart(Animation animation) {
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (LoginState.getState() != LoginState.LOGIN) {
			// intentSignUp();
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

	@Override
	public void notifyMin() {
		// dao.cancel();
		// Log.d("activity", "category " +
		// daoSql.selectTrainingCategory().size());
		// Log.d("activity", "menu " + daoSql.selectTrainingMenu().size());
		// startAnimation();
	}

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
		if (login != null) {
			Log.d("activity", "intent name " + user.getName());
			login.helperOnActivityResult(requestCode, resultCode, data);
			intentSignUp();
		}
	}

	private void setMyGroupList(UserInfo info) {
		daoSql.deleteAffiliation();
		List<Affiliation> affiliations = info.getAffiliations();
		List<Group> groups = info.getGroups();
		for (int i = 0; i < affiliations.size(); i++) {
			if (affiliations.get(i).getPermition().getId() == PermissionConstants
					.notice()) {
				break;
			}
			try {
				daoSql.insertAffiliation(affiliations.get(i).getGroupId(),
						affiliations.get(i).getPermition().getId());
				daoSql.insertGroup(groups.get(i).getId(), groups.get(i)
						.getGroupName(), groups.get(i).getComment(), groups
						.get(i).getUserId(), groups.get(i).getCreated());
			} catch (SQLiteInsertException e) {
				e.printStackTrace();
			} catch (SQLiteTableConstraintException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void complete() {
		Log.d("activity", "complete");
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
				Log.d("activity", "user ");
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
		startAnimation();
	}

	@Override
	public void complete(InputStream response) {
	}

	@Override
	public void complete(Bitmap image) {
		user.setImage(image);
		startAnimation();
	}

	@Override
	public void progressUpdate(int value) {
		Log.d("activity", "value " + value);
	}
}
