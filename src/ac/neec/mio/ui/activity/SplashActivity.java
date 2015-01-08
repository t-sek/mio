package ac.neec.mio.ui.activity;

import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.AppConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.item.api.Sourceable;
import ac.neec.mio.exception.SQLiteInsertException;
import ac.neec.mio.exception.SQLiteTableConstraintException;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Permission;
import ac.neec.mio.pref.AppPreference;
import ac.neec.mio.taining.category.TrainingCategory;
import ac.neec.mio.taining.menu.TrainingMenu;
import ac.neec.mio.timer.TimerManager;
import ac.neec.mio.ui.fragment.FirstSignUpSelectFragment;
import ac.neec.mio.ui.listener.TimerCallbackListener;
import ac.neec.mio.user.LoginState;
import ac.neec.mio.user.User;
import ac.neec.mio.user.UserInfo;
import android.content.Intent;
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
	private static final int WEIT_TIME = 100;

	private TimerManager manager;
	private TextView title;
	private ImageView buttonMeasurement;
	private Button buttonSignUp;
	private Button buttonLogin;
	private ApiDao dao;
	private SQLiteDao daoSql;
	private int daoFlag;
	private User user = User.getInstance();

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_INTENT:
				animationHeart();
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
		initFindViews();
		Typeface face = Typeface.createFromAsset(getAssets(),
				"font/Roboto-Regular.ttf");
		title.setTypeface(face);
		setListeners();
		AppPreference.init(getApplicationContext());
		AppConstants.setResorces(getResources());
		dao = DaoFacade.getApiDao(getApplicationContext(), this);
		daoSql = DaoFacade.getSQLiteDao(getApplicationContext());
		downloadTrainingCategory();
		manager = new TimerManager(this, 5000);
		manager.start();
	}

	private void initFindViews() {
		title = (TextView) findViewById(R.id.text_title);
		buttonMeasurement = (ImageView) findViewById(R.id.btn_measurement_setting);
		buttonSignUp = (Button) findViewById(R.id.btn_sign_up);
		buttonLogin = (Button) findViewById(R.id.btn_login);
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
				intentLogin();
			}
		});
	}

	private void animationHeart() {
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
		finish();
	}

	private void intentLogin() {
		Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
		startActivity(intent);
		finish();
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
		for (TrainingMenu m : menu) {
			try {
				daoSql.insertTrainingMenu(m.getTrainingMenuId(),
						m.getTrainingName(), m.getMets(),
						m.getTrainingCategoryId(), m.getColor());
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
	public void complete() {
		manager.stop();
		switch (daoFlag) {
		case FLAG_CATEGORY:
			List<TrainingCategory> category;
			try {
				category = dao.getResponse();
			} catch (XmlParseException e) {
				startAnimation();
				return;
			} catch (XmlReadException e) {
				startAnimation();
				return;
			}
			insertTrainingCategory(category);
			downloadTrainingMenu();
			break;
		case FLAG_MENU:
			List<TrainingMenu> menu;
			try {
				menu = dao.getResponse();
			} catch (XmlParseException e) {
				startAnimation();
				e.printStackTrace();
				return;
			} catch (XmlReadException e) {
				startAnimation();
				e.printStackTrace();
				return;
			}
			insertTrainingMenu(menu);
			// downloadUserInfo();
			downloadPermition();
			// startAnimation();
			break;
		case FLAG_PERM:
			List<Permission> perm = null;
			try {
				perm = dao.getResponse();
			} catch (XmlParseException e) {
				startAnimation();
				e.printStackTrace();
			} catch (XmlReadException e) {
				startAnimation();
				e.printStackTrace();
			}
			insertPermition(perm);
			downloadUserInfo();
			// startAnimation();
			break;
		case FLAG_USER:
			UserInfo info = null;
			try {
				info = dao.getResponse();
			} catch (XmlParseException e) {
				e.printStackTrace();
				startAnimation();
			} catch (XmlReadException e) {
				e.printStackTrace();
				startAnimation();
			}
			insertUserInfo(info);
			startAnimation();
			break;
		default:
			break;
		}
	}

	@Override
	public void incomplete() {
		startAnimation();
	}
}