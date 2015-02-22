package ac.neec.mio.ui.activity;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.ErrorConstants;
import ac.neec.mio.consts.MessageConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.pref.UtilPreference;
import ac.neec.mio.training.Training;
import ac.neec.mio.training.category.TrainingCategory;
import ac.neec.mio.training.menu.TrainingMenu;
import ac.neec.mio.training.play.TrainingPlay;
import ac.neec.mio.training.play.TrainingPlayFactory;
import ac.neec.mio.ui.dialog.LoadingDialog;
import ac.neec.mio.ui.dialog.ProfileBirthSettingDialog;
import ac.neec.mio.ui.dialog.ProfileBirthSettingDialog.ProfileBirthCallbackListener;
import ac.neec.mio.ui.dialog.TrainingNumInsertDialog;
import ac.neec.mio.ui.dialog.TrainingNumInsertDialog.NumChangedListener;
import ac.neec.mio.ui.dialog.TrainingSelectedDialog;
import ac.neec.mio.ui.dialog.TrainingTimeInsertDialog;
import ac.neec.mio.ui.dialog.TrainingTimeInsertDialog.TimeChangedListener;
import ac.neec.mio.ui.listener.TrainingSelectCallbackListener;
import ac.neec.mio.user.User;
import ac.neec.mio.util.DateUtil;
import ac.neec.mio.util.TimeUtil;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.koba.androidrtchart.ColorBar;
import com.koba.androidrtchart.ColorBarItem;
import com.koba.androidrtchart.ColorBarListener;

public class TrainingFreeInsertActivity extends Activity implements Sourceable,
		OnClickListener, TrainingSelectCallbackListener,
		ProfileBirthCallbackListener, TimeChangedListener, NumChangedListener,
		ColorBarListener {

	private static final int MESSAGE_TOAST = 1;
	private static final int FLAG_TRAINING = 3;
	private static final int FLAG_PLAY = 9;
	private static final int CATEGORY = 4;
	private static final int MENU = 5;
	private static final int PLAY_TIME = 7;
	private static final int ADD_TRAINING = 8;

	private TableRow date;
	private TableRow category;
	private TableRow startTime;
	private TableRow playTime;
	private TableRow calorie;
	private TableRow distance;
	private TextView textYear;
	private TextView textMonth;
	private TextView textDay;
	private TextView textCategory;
	private TextView textStartHour;
	private TextView textStartMin;
	private TextView textPlayHour;
	private TextView textPlayMin;
	private TextView textCalorie;
	private TextView textDistance;
	private Button button;
	private ImageButton buttonBarAdd;
	private ColorBar colorbar;

	private User user = User.getInstance();
	private LoadingDialog dialogLoading = new LoadingDialog(
			MessageConstants.save());
	private ApiDao dao;
	private SQLiteDao daoSql;
	private TrainingMenu menu;
	private List<TrainingPlay> plays = new ArrayList<TrainingPlay>();
	private ProductDataFactory factory = new TrainingPlayFactory();
	private int trainingId;
	private int categoryId;
	private int daoFlag;
	private int dialogFlag;

	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(), (String) message.obj,
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
		setContentView(R.layout.activity_training_free_insert);
		initFindViews();
		setListener();
		setNowDate();
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
		TrainingCategory category = daoSql.selectTrainingCategory().get(0);
		categoryId = category.getTrainingCategoryId();
		textCategory.setText(category.getTrainingCategoryName());
	}

	private void initFindViews() {
		date = (TableRow) findViewById(R.id.date);
		category = (TableRow) findViewById(R.id.category);
		startTime = (TableRow) findViewById(R.id.start_time);
		playTime = (TableRow) findViewById(R.id.play_time);
		calorie = (TableRow) findViewById(R.id.calorie);
		distance = (TableRow) findViewById(R.id.distance);
		textYear = (TextView) findViewById(R.id.text_year);
		textMonth = (TextView) findViewById(R.id.text_month);
		textDay = (TextView) findViewById(R.id.text_day);
		textCategory = (TextView) findViewById(R.id.text_category);
		textStartHour = (TextView) findViewById(R.id.text_start_time_hour);
		textStartMin = (TextView) findViewById(R.id.text_start_time_min);
		textPlayHour = (TextView) findViewById(R.id.text_play_time_hour);
		textPlayMin = (TextView) findViewById(R.id.text_play_time_min);
		textCalorie = (TextView) findViewById(R.id.text_calorie);
		textDistance = (TextView) findViewById(R.id.text_distance);
		button = (Button) findViewById(R.id.button_insert);
		buttonBarAdd = (ImageButton) findViewById(R.id.button_training_add);
		colorbar = (ColorBar) findViewById(R.id.colorbar);
	}

	private void setListener() {
		date.setOnClickListener(this);
		category.setOnClickListener(this);
		startTime.setOnClickListener(this);
		playTime.setOnClickListener(this);
		calorie.setOnClickListener(this);
		distance.setOnClickListener(this);
		button.setOnClickListener(this);
		buttonBarAdd.setOnClickListener(this);
		colorbar.setOnTouchListener(this);
	}

	private void setNowDate() {
		textStartHour.setText(TimeUtil.nowDateHour());
		textStartMin.setText(TimeUtil.nowDateMin());
		textYear.setText(DateUtil.nowYear());
		textMonth.setText(DateUtil.nowMonth());
		textDay.setText(DateUtil.nowDay());
	}

	private void setColorBar(int sec) {
		ColorBarItem item;
		item = new ColorBarItem(sec, menu.getTrainingName(), menu.getColor());
		colorbar.addBarItem(item);
		plays.add((TrainingPlay) factory.create(menu.getTrainingMenuId(), sec));
		updatePlayTime();
	}

	private void updatePlayTime() {
		int time = 0;
		for (TrainingPlay play : plays) {
			time += play.getTrainingTime();
		}
		int min = time / 60;
		int sec = time % 60;
		textPlayHour.setText(String.valueOf(min));
		textPlayMin.setText(String.valueOf(sec));
	}

	private void showMenuDialog() {
		dialogFlag = MENU;
		List<TrainingMenu> menus = daoSql
				.selectTrainingCategoryMenu(categoryId);
		String[] strings = new String[menus.size()];
		int i = 0;
		for (TrainingMenu menu : menus) {
			strings[i] = menu.getTrainingName();
			i++;
		}
		new TrainingSelectedDialog(getApplicationContext(), this, strings,
				UtilPreference.getMenuPicker()).show(getFragmentManager(),
				"dialog");
	}

	private void showCategoryDialog() {
		dialogFlag = CATEGORY;
		List<TrainingCategory> list = daoSql.selectTrainingCategory();
		String[] strings = new String[list.size()];
		int i = 0;
		for (TrainingCategory category : list) {
			strings[i] = category.getTrainingCategoryName();
			i++;
		}
		new TrainingSelectedDialog(getApplicationContext(), this, strings,
				UtilPreference.getCategoryPicker()).show(getFragmentManager(),
				"dialog");
	}

	private void showPlayDialog() {
		// new TrainingTimeInsertDialog(this,
		// TrainingTimeInsertDialog.PLAY_TIME)
		// .show(getFragmentManager(), "dialog");
	}

	private void showStartDialog() {
		new TrainingTimeInsertDialog(this, TrainingTimeInsertDialog.START_TIME)
				.show(getFragmentManager(), "dialog");
	}

	private void showDateDialog() {
		// new ProfileBirthSettingDialog(this, "実施日").show(getFragmentManager(),
		// "dialog");
	}

	private void showCalorieDialog() {
		new TrainingNumInsertDialog(this, TrainingNumInsertDialog.CALORIE)
				.show(getFragmentManager(), "dialog");
	}

	private void showDistanceDialog() {
		new TrainingNumInsertDialog(this, TrainingNumInsertDialog.DISTANCE)
				.show(getFragmentManager(), "dialog");
	}

	private void showAddTrainingDialog() {
		new TrainingTimeInsertDialog(this,
				TrainingTimeInsertDialog.ADD_TRAINING).show(
				getFragmentManager(), "dialog");
	}

	private void insert() {
		daoFlag = FLAG_TRAINING;
		String date = DateUtil.dateFormat(textYear.getText().toString(),
				textMonth.getText().toString(), textDay.getText().toString());
		String startTime = textStartHour.getText().toString()
				+ textStartMin.getText().toString();
		String playTime = TimeUtil.timetoSec(textPlayHour.getText().toString(),
				textPlayMin.getText().toString());
		TrainingCategory category = daoSql.selectTrainingCategory(textCategory
				.getText().toString());
		int categoryId = category.getTrainingCategoryId();
		dao.insertTraining(user.getId(), user.getPassword(), date, startTime,
				playTime, 0, 0, 0, null,
				Integer.valueOf(textCalorie.getText().toString()), categoryId,
				Double.valueOf(textDistance.getText().toString()));
		dialogLoading = new LoadingDialog(MessageConstants.save());
		dialogLoading.show(getFragmentManager(), "dialog");
	}

	@Override
	public void complete() {
		dialogLoading.dismiss();
		if (daoFlag == FLAG_TRAINING) {
			try {
				trainingId = dao.getResponse();
				daoFlag = 0;
				dao.selectTraining(user.getId(), user.getId(), trainingId,
						user.getPassword());
			} catch (XmlParseException e) {
				e.printStackTrace();
			} catch (XmlReadException e) {
				e.printStackTrace();
			}
			if (trainingId == 0) {
				Message message = new Message();
				message.what = MESSAGE_TOAST;
				message.obj = ErrorConstants.upload();
				handler.sendMessage(message);
			} else {
				if (plays.size() != 0) {
					TrainingPlay play = plays.get(0);
					dao.insertTrainingPlay(user.getId(), trainingId,
							play.getPlayId(), play.getTrainingMenuId(),
							play.getTrainingTime(), user.getPassword());
					plays.remove(0);
					daoFlag = FLAG_PLAY;
				}
				Message message = new Message();
				message.what = MESSAGE_TOAST;
				message.obj = MessageConstants.messageSave();
				handler.sendMessage(message);
				finish();
			}
		} else if (daoFlag == FLAG_PLAY) {
			if (plays.size() != 0) {
				TrainingPlay play = plays.get(0);
				dao.insertTrainingPlay(user.getId(), trainingId,
						play.getPlayId(), play.getTrainingMenuId(),
						play.getTrainingTime(), user.getPassword());
				plays.remove(0);
			}
		}
	}

	@Override
	public void incomplete() {
		Message message = new Message();
		message.what = MESSAGE_TOAST;
		message.obj = ErrorConstants.networkError();
		handler.sendMessage(message);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.date:
			showDateDialog();
			break;
		case R.id.category:
			showCategoryDialog();
			break;
		case R.id.start_time:
			showStartDialog();
			break;
		case R.id.play_time:
			showPlayDialog();
			break;
		case R.id.calorie:
			showCalorieDialog();
			break;
		case R.id.distance:
			showDistanceDialog();
			break;
		case R.id.button_insert:
			insert();
			break;
		case R.id.button_training_add:
			showMenuDialog();
			break;
		default:
			break;
		}
	}

	@Override
	public void onSelected(String element) {
		switch (dialogFlag) {
		case CATEGORY:
			textCategory.setText(element);
			TrainingCategory category = daoSql.selectTrainingCategory(element);
			categoryId = category.getTrainingCategoryId();
			break;
		case MENU:
			menu = daoSql.selectTrainingMenu(element);
			showAddTrainingDialog();
			Log.d("activity", "menu " + menu.getTrainingName());
			break;
		default:
			break;
		}
	}

	@Override
	public void onSelected(int index) {
	}

	@Override
	public void dateChanged(String year, String month, String day) {
		textYear.setText(year);
		textMonth.setText(month);
		textDay.setText(day);
	}

	@Override
	public void onSelected(int tag, String hour, String min) {
		switch (tag) {
		case TrainingTimeInsertDialog.PLAY_TIME:
			textPlayHour.setText(hour);
			textPlayMin.setText(min);
			break;
		case TrainingTimeInsertDialog.START_TIME:
			textStartHour.setText(hour);
			textStartMin.setText(min);
			break;
		case TrainingTimeInsertDialog.ADD_TRAINING:
			int time = TimeUtil.timeToSec(Integer.valueOf(hour),
					Integer.valueOf(min));
			setColorBar(time);
			break;
		default:
			break;
		}
	}

	@Override
	public void onSelected(int tag, String num) {
		switch (tag) {
		case TrainingNumInsertDialog.CALORIE:
			textCalorie.setText(num);
			break;
		case TrainingNumInsertDialog.DISTANCE:
			if (num == null) {
				num = "0";
			}
			textDistance.setText(num);
			break;
		default:
			break;
		}
	}

	@Override
	public void complete(Bitmap image) {
	}

	@Override
	public void onTouch(int value, String comment) {
		Toast.makeText(getApplicationContext(), comment + " " + value + "秒",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void validate() {
	}

}