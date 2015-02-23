package ac.neec.mio.ui.activity;

import static ac.neec.mio.consts.SignUpConstants.*;
import ac.neec.mio.R;
import ac.neec.mio.filter.JapaneseInputFilter;
import ac.neec.mio.user.User;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.util.DateUtil;
import ac.neec.mio.validate.UserSignUpForm;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
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
import com.google.android.gms.plus.model.people.Person.Name;
import com.sek.drumpicker.DrumPicker;
import com.sek.drumpicker.DrumPickerListener;

/**
 * 新規登録画面クラス
 *
 */
public class UserSignUpActivity extends Activity implements DrumPickerListener,
		OnSharedPreferenceChangeListener, OnFocusChangeListener, TextWatcher {

	private static final String TAG_YEAR = "year";
	private static final String TAG_MONTH = "month";
	private static final String TAG_DAY = "day";
	private static final String NEW_LINE = "line.separator";
	/**
	 * ユーザID入力フォーム
	 */
	private EditText editId;
	/**
	 * パスワード入力フォーム
	 */
	private EditText editPass;
	/**
	 * パスワード確認入力フォーム
	 */
	private EditText editPassConf;
	/**
	 * ユーザ名入力フォーム
	 */
	private EditText editName;
	/**
	 * メールアドレス入力フォーム
	 */
	private EditText editMail;
	/**
	 * 入力フォームのフォーカスを保持
	 */
	private int focusEdit;
	/**
	 * 生年月日(年)設定ピッカー
	 */
	private DrumPicker pickerYaer;
	/**
	 * 生年月日(月)設定ピッカー
	 */
	private DrumPicker pickerMonth;
	/**
	 * 生年月日(日)設定ピッカー
	 */
	private DrumPicker pickerDay;
	/**
	 * 生年月日(年)
	 */
	private String year;
	/**
	 * 生年月日(月)
	 */
	private String month;
	/**
	 * 生年月日(日)
	 */
	private String day;
	/**
	 * 性別ラジオグループ
	 */
	private RadioGroup radioGroup;
	/**
	 * 男性ボタン
	 */
	private RadioButton male;
	/**
	 * 女性ボタン
	 */
	private RadioButton female;
	/**
	 * 次画面ボタン
	 */
	private Button buttonIntent;
	/**
	 * ユーザ情報
	 */
	private User user = User.getInstance();
	/**
	 * エラーメッセージ
	 */
	private StringBuilder errorBuilder = new StringBuilder();
	/**
	 * フォームのバリデートチェック
	 */
	private FormHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_sign_up);
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

	/**
	 * バリデートチェックを初期化
	 */
	private void initValidationHelper() {
		helper = new ActivityFormHelper(UserSignUpForm.class, this);
		helper.setOnFocusOutValidation();
		helper.setValidationErrorIconEnabled(false);
	}

	/**
	 * 画面の初期化処理をする
	 */
	private void initFindViews() {
		InputFilter[] filters = new InputFilter[] { new JapaneseInputFilter() };
		editId = (EditText) findViewById(R.id.edit_id);
		editId.setFilters(filters);
		editPass = (EditText) findViewById(R.id.edit_password);
		editPassConf = (EditText) findViewById(R.id.edit_password_conf);
		editName = (EditText) findViewById(R.id.edit_name);
		editMail = (EditText) findViewById(R.id.edit_mail);
		editMail.setFilters(filters);
		pickerYaer = (DrumPicker) findViewById(R.id.picker_year);
		pickerMonth = (DrumPicker) findViewById(R.id.picker_month);
		pickerDay = (DrumPicker) findViewById(R.id.picker_day);
		radioGroup = (RadioGroup) findViewById(R.id.group_gender);
		male = (RadioButton) findViewById(R.id.radio_male);
		female = (RadioButton) findViewById(R.id.radio_female);
		buttonIntent = (Button) findViewById(R.id.button_intent);
		focusEdit = editId.getId();
	}

	/**
	 * ビューにリスナーを設定する
	 */
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
				if (checkValidate()
						&& helper.validate().getAllErrors().size() == 0) {
					storeUpUserData();
					intentBodilySetting();
				}
			}
		});
	}

	/**
	 * 生年月日設定ピッカーを設定する
	 */
	private void setBirthPicker() {
		String[] years = new String[DateUtil.getYears().length];
		years = DateUtil.getYears();
		pickerYaer.setElements(getApplicationContext(), TAG_YEAR, years);
		pickerYaer.setOnDrumPickerListener(this);
		year = years[0];
		String[] months = new String[DateUtil.getMonths().length];
		months = DateUtil.getMonths();
		pickerMonth.setElements(getApplicationContext(), TAG_MONTH, months);
		pickerMonth.setOnDrumPickerListener(this);
		month = months[0];
		updateDay();
		pickerDay.setOnDrumPickerListener(this);
	}

	/**
	 * 日付を更新する
	 */
	private void updateDay() {
		int day = DateUtil.getActualMaximum(Integer.valueOf(year),
				Integer.valueOf(month));
		String[] days = new String[DateUtil.getDays(day).length];
		days = DateUtil.getDays(day);
		this.day = days[0];
		pickerDay.setElements(getApplicationContext(), TAG_DAY, days);
	}

	/**
	 * ユーザ情報を画面に更新する
	 */
	private void setUserData() {
		editId.setText(user.getId());
		editName.setText(user.getName());
		editMail.setText(user.getMail());
		String gender = user.getGender();
		if (gender.equals(Gender.MALE)) {
			radioGroup.check(male.getId());
		} else if (gender.equals(Gender.FEMALE)) {
			radioGroup.check(female.getId());
		}
	}

	/**
	 * ユーザ情報を保存する
	 */
	private void storeUpUserData() {
		user.setId(editId.getText().toString());
		user.setPassword(editPass.getText().toString());
		user.setName(editName.getText().toString());
		user.setMail(editMail.getText().toString());
		user.setBirth(DateUtil.dateFormat(year, month, day));
	}

	/**
	 * 性別にチェックが入っているか確認する
	 */
	private void checkGenderSelected() {
		RadioButton checkButton = (RadioButton) findViewById(radioGroup
				.getCheckedRadioButtonId());
		if (checkButton.equals(male)) {
			user.setGender(Gender.MALE);
		} else if (checkButton.equals(female)) {
			user.setGender(Gender.FEMALE);
		}
	}

	/**
	 * 最低文字数を確認する
	 * 
	 * @return true エラーなし<br>
	 *         false エラーあり
	 */
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
		Log.d("activity", "name " + editName.getText().toString());
		if (editName.getText().toString().length() == 0) {
			errorBuilder.append(name() + "は必ず入力してください");
			errorBuilder.append(System.getProperty(NEW_LINE));
			val = false;
		}
		return val;
	}

	/**
	 * バリデートチェックをする
	 * 
	 * @return true エラーなし<br>
	 *         false エラーあり
	 */
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

	/**
	 * 身体情報設定画面に遷移する
	 */
	private void intentBodilySetting() {
		Intent intent = new Intent(UserSignUpActivity.this,
				UserSignUpBodilyActivity.class);
		startActivity(intent);
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
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
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}

	@Override
	public void afterTextChanged(Editable s) {
		storeUpUserData();
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
