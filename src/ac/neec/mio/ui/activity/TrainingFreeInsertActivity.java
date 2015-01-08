package ac.neec.mio.ui.activity;

import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.item.api.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.pref.UtilPreference;
import ac.neec.mio.taining.Training;
import ac.neec.mio.taining.category.TrainingCategory;
import ac.neec.mio.ui.dialog.LoadingDialog;
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
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class TrainingFreeInsertActivity extends Activity implements Sourceable,
		OnClickListener, TrainingSelectCallbackListener,
		ProfileBirthCallbackListener, TimeChangedListener, NumChangedListener {

	private static final int MESSAGE_TOAST = 1;
	private static final int FLAG_TRAINING = 3;

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

	private User user = User.getInstance();
	private LoadingDialog dialogLoading = new LoadingDialog();
	private ApiDao dao;
	private SQLiteDao daoSql;
	private int daoFlag;

	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_TOAST:
				Toast.makeText(getApplicationContext(), "入力に誤りがあります",
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
		dao = DaoFacade.getApiDao(getApplicationContext(), this);
		daoSql = DaoFacade.getSQLiteDao(getApplicationContext());
		textCategory.setText(daoSql.selectTrainingCategory().get(0)
				.getTrainingCategoryName());
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
	}

	private void setListener() {
		date.setOnClickListener(this);
		category.setOnClickListener(this);
		startTime.setOnClickListener(this);
		playTime.setOnClickListener(this);
		calorie.setOnClickListener(this);
		distance.setOnClickListener(this);
		button.setOnClickListener(this);
	}

	private void setNowDate() {
		textStartHour.setText(TimeUtil.nowDateHour());
		textStartMin.setText(TimeUtil.nowDateMin());
		textYear.setText(DateUtil.nowYear());
		textMonth.setText(DateUtil.nowMonth());
		textDay.setText(DateUtil.nowDay());
	}

	private void showCategoryDialog() {
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
		new TrainingTimeInsertDialog(this, TrainingTimeInsertDialog.PLAY_TIME)
				.show(getFragmentManager(), "dialog");
	}

	private void showDateDialog() {
		new TrainingTimeInsertDialog(this, TrainingTimeInsertDialog.START_TIME)
				.show(getFragmentManager(), "dialog");
	}

	private void showCalorieDialog() {
		new TrainingNumInsertDialog(this, TrainingNumInsertDialog.CALORIE)
				.show(getFragmentManager(), "dialog");
	}

	private void showDistanceDialog() {
		new TrainingNumInsertDialog(this, TrainingNumInsertDialog.DISTANCE)
				.show(getFragmentManager(), "dialog");
	}

	private void insert() {
		daoFlag = FLAG_TRAINING;
		String date = DateUtil.dateFormat(textYear.getText().toString(),
				textMonth.getText().toString(), textDay.getText().toString());
		String startTime = textStartHour.getText().toString()
				+ textStartMin.getText().toString();
		String playTime = TimeUtil.timetoSec(textPlayHour.getText().toString(),
				textPlayMin.getText().toString());
		Log.d("activity", "hour " + textPlayHour.getText());
		Log.d("activity", "min " + textPlayMin.getText());
		Log.d("activity", "playTime " + playTime);
		TrainingCategory category = daoSql.selectTrainingCategory(textCategory
				.getText().toString());
		int categoryId = category.getTrainingCategoryId();
		dao.insertTraining(user.getId(), date, startTime, playTime, 0, 0, 0,
				null, Integer.valueOf(textCalorie.getText().toString()),
				categoryId, Double.valueOf(textDistance.getText().toString()));
		dialogLoading = new LoadingDialog();
		dialogLoading.show(getFragmentManager(), "dialog");
	}

	@Override
	public void complete() {
		if (daoFlag != FLAG_TRAINING) {
			try {
				Training t = dao.getResponse();
				Log.e("activity", "res playtime " + t.getPlayTime());
			} catch (XmlParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (XmlReadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		dialogLoading.dismiss();
		int trainingId = 0;
		try {
			trainingId = dao.getResponse();
			daoFlag = 0;
			dao.selectTraining(user.getId(), trainingId);
			Log.d("activity", "trainingId " + trainingId);
		} catch (XmlParseException e) {
			e.printStackTrace();
		} catch (XmlReadException e) {
			e.printStackTrace();
		}
		if (trainingId == 0) {
			Toast.makeText(getApplicationContext(), "保存に失敗しました",
					Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(getApplicationContext(), "保存しました",
					Toast.LENGTH_SHORT).show();
			finish();
		}
	}

	@Override
	public void incomplete() {
		// TODO Auto-generated method stub

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
			showDateDialog();
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
		default:
			break;
		}
	}

	@Override
	public void onSelected(String element) {
		textCategory.setText(element);
	}

	@Override
	public void onSelected(int index) {
		// TODO Auto-generated method stub

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
			textDistance.setText(num);
			break;
		default:
			break;
		}
	}
}