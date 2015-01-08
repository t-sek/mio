package ac.neec.mio.ui.activity;

import static ac.neec.mio.util.SignUpConstants.password;
import static ac.neec.mio.util.SignUpConstants.userId;
import ac.neec.mio.R;
import ac.neec.mio.user.User;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.util.DateUtil;
import ac.neec.mio.validate.UserSignUpForm;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel.Stub;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.androidformenhancer.helper.ActivityFormHelper;
import com.androidformenhancer.helper.FormHelper;
import com.sek.drumpicker.DrumPicker;
import com.sek.drumpicker.DrumPickerListener;

public class UserSignUpActivity extends Activity implements DrumPickerListener,
		OnSharedPreferenceChangeListener, OnFocusChangeListener, TextWatcher {

	private static final String TAG_YEAR = "year";
	private static final String TAG_MONTH = "month";
	private static final String TAG_DAY = "day";
	private static final String NEW_LINE = "line.separator";
	private EditText editId;
	private EditText editPass;
	private EditText editPassConf;
	private EditText editName;
	private EditText editMail;
	private int focusEdit;
	private DrumPicker pickerYaer;
	private DrumPicker pickerMonth;
	private DrumPicker pickerDay;
	private String year;
	private String month;
	private String day;
	private RadioGroup radioGroup;
	private RadioButton male;
	private RadioButton female;
	private Button buttonIntent;
	private User user = User.getInstance();
	private StringBuilder errorBuilder = new StringBuilder();

	private FormHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_sign_up2);
		user.clear();
		initFindViews();
		setBirthPicker();
		setListener();
		setUserData();
		initValidationHelper();
	}

	@Override
	protected void onPause() {
		super.onPause();
		storeUpUserData();
	}

	private void initValidationHelper() {
		helper = new ActivityFormHelper(UserSignUpForm.class, this);
		helper.setOnFocusOutValidation();
		helper.setValidationErrorIconEnabled(false);
	}

	private void initFindViews() {
		editId = (EditText) findViewById(R.id.edit_id);
		editPass = (EditText) findViewById(R.id.edit_password);
		editPassConf = (EditText) findViewById(R.id.edit_password_conf);
		editName = (EditText) findViewById(R.id.edit_name);
		editMail = (EditText) findViewById(R.id.edit_mail);
		pickerYaer = (DrumPicker) findViewById(R.id.picker_year);
		pickerMonth = (DrumPicker) findViewById(R.id.picker_month);
		pickerDay = (DrumPicker) findViewById(R.id.picker_day);
		radioGroup = (RadioGroup) findViewById(R.id.group_gender);
		male = (RadioButton) findViewById(R.id.radio_male);
		female = (RadioButton) findViewById(R.id.radio_female);
		buttonIntent = (Button) findViewById(R.id.button_intent);
		focusEdit = editId.getId();
	}

	private void setListener() {
		user.setListener(this);
		editId.setOnFocusChangeListener(this);
		editId.addTextChangedListener(this);
		editPass.setOnFocusChangeListener(this);
		editPassConf.setOnFocusChangeListener(this);
		editName.setOnFocusChangeListener(this);
		editName.addTextChangedListener(this);
		editMail.setOnFocusChangeListener(this);
		editMail.addTextChangedListener(this);

		buttonIntent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				checkGenderSelected();
				checkEditMinValidate();
				// checkValidate();
				// if (checkValidatxe() || checkEditMinValidate()) {
				// if (!helper.validate().hasError()) {
				if (checkValidate()) {
					storeUpUserData();
					intentBodilySetting();
				}
			}
		});
	}

	private void setBirthPicker() {
		String[] years = new String[DateUtil.getYears().length];
		years = DateUtil.getYears();
		pickerYaer.setElements(getApplicationContext(), TAG_YEAR, years);
		pickerYaer.setOnDrumPickerListener(this);
		year = years[0];
		// pickerYaer.setElementColor(DRUM_COLOR);
		String[] months = new String[DateUtil.getMonths().length];
		months = DateUtil.getMonths();
		pickerMonth.setElements(getApplicationContext(), TAG_MONTH, months);
		pickerMonth.setOnDrumPickerListener(this);
		month = months[0];
		// String[] day = new String[DateUtil.getDays().length];
		// day = DateUtil.getDays();
		// pickerDay.setElements(getApplicationContext(), TAG_DAY, day);
		updateDay();
		pickerDay.setOnDrumPickerListener(this);
	}

	private void updateDay() {
		int day = DateUtil.getActualMaximum(Integer.valueOf(year),
				Integer.valueOf(month));
		String[] days = new String[DateUtil.getDays(day).length];
		days = DateUtil.getDays(day);
		this.day = days[0];
		pickerDay.setElements(getApplicationContext(), TAG_DAY, days);
	}

	private void setUserData() {
		editId.setText(user.getId());
		Log.d("activity", "id " + user.getId());
		editName.setText(user.getName());
		Log.d("activity", "id " + user.getName());
		editMail.setText(user.getMail());
		Log.d("activity", "id " + user.getMail());
		String gender = user.getGender();
		if (gender.equals(Gender.MALE)) {
			radioGroup.check(male.getId());
		} else if (gender.equals(Gender.FEMALE)) {
			radioGroup.check(female.getId());
		}
	}

	private void storeUpUserData() {
		user.setId(editId.getText().toString());
		user.setPassword(editPass.getText().toString());
		user.setName(editName.getText().toString());
		user.setMail(editMail.getText().toString());
		user.setBirth(DateUtil.dateFormat(year, month, day));
	}

	private void checkGenderSelected() {
		RadioButton checkButton = (RadioButton) findViewById(radioGroup
				.getCheckedRadioButtonId());
		if (checkButton.equals(male)) {
			user.setGender(Gender.MALE);
		} else if (checkButton.equals(female)) {
			user.setGender(Gender.FEMALE);
		}
	}

	private boolean checkEditMinValidate() {
		boolean val = true;
		errorBuilder = new StringBuilder();
		errorBuilder.append(System.getProperty(NEW_LINE));
		if (editId.getText().length() < 6) {
			errorBuilder.append(userId() + "は6文字以上入力してください");
			errorBuilder.append(System.getProperty(NEW_LINE));
			val = false;
		}
		if (editPass.getText().length() < 6) {
			errorBuilder.append(password() + "は6文字以上入力してください");
			errorBuilder.append(System.getProperty(NEW_LINE));
			val = false;
		}
		if (!editPass.getText().toString()
				.equals(editPassConf.getText().toString())) {
			errorBuilder.append(password() + "が一致しません");
			errorBuilder.append(System.getProperty(NEW_LINE));
			val = false;
		}
		return val;
	}

	private boolean checkValidate() {
		boolean val = true;
		StringBuilder sb = new StringBuilder();
		sb.append(errorBuilder);
		for (String error : helper.validate().getAllErrors()) {
			sb.append(error);
			sb.append(System.getProperty(NEW_LINE));
		}
		if (sb.length() != 0) {
			val = checkEditMinValidate();
			if (!val) {
				helper.showAlertDialog(sb.toString());
			} else {
				val = true;
			}
		}
		return val;
	}

	private void intentBodilySetting() {
		Intent intent = new Intent(UserSignUpActivity.this,
				UserSignUpBodilyActivity.class);
		startActivity(intent);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// setUserData();
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		Log.d("actiivty", "focus " + "has " + hasFocus + " v " + v.getId());
		if (hasFocus) {
			if (v.getId() == R.id.edit_id) {
				focusEdit = v.getId();
			} else if (v.getId() == R.id.edit_name) {
				focusEdit = v.getId();
			} else if (v.getId() == R.id.edit_mail) {
				focusEdit = v.getId();
			}
		} else {
			if (v.getId() == R.id.edit_password) {
				if (editPass.getText().toString()
						.equals(editPassConf.getText().toString())) {
					user.setPassword(editPass.getText().toString());
				}
			} else if (v.getId() == R.id.edit_password_conf) {
				if (editPass.getText().toString()
						.equals(editPassConf.getText().toString())) {
					user.setPassword(editPassConf.getText().toString());
				}
			}

		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterTextChanged(Editable s) {
		storeUpUserData();
		Log.d("activity", "s " + s);
		// switch (focusEdit) {
		// case R.id.edit_id:
		// Log.d("activity", "id " + s);
		// user.setId(s.toString());
		// break;
		// case R.id.edit_name:
		// Log.d("activity", "name " + s);
		// user.setName(s.toString());
		// break;
		// case R.id.edit_mail:
		// Log.d("activity", "mail " + s);
		// user.setMail(s.toString());
		// break;
		//
		// default:
		// break;
		// }

	}

	@Override
	public void onScrollChanged(String tag, String element, int index) {
		if (tag.equals(TAG_YEAR)) {
			year = element;
			updateDay();
		} else if (tag.equals(TAG_MONTH)) {
			month = element;
			updateDay();
		} else if (tag.equals(TAG_DAY)) {
			day = element;
		}
	}

}
