package ac.neec.mio.ui.activity;

import com.androidformenhancer.helper.ActivityFormHelper;

import ac.neec.mio.R;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.validate.SignUpForm;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;


/*
 * @actor noripoyo
 */
public class SignUpActivity extends Activity {

	private Button buttonSignUp;
	private EditText editUserId;
	private EditText editPassword;
	private EditText editLastName;
	private EditText editFirstName;
	private EditText editMail;
	private EditText editMailRetype;
	private EditText editBirthYear;
	private EditText editBirthMonth;
	private EditText editBirthDay;
	private RadioGroup groupGender;
	private RadioButton male;
	private RadioButton female;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		initFindViews();
		setListener();
		// FormHelper helper = new FormHelper(SignUpForm.class,
		// getApplicationContext());
		// helper.setOnFocusOutValidation();
		// new ActivityFormHelper(SignUpForm.class, this)
		// .setOnFocusOutValidation();

		ActivityFormHelper helper = new ActivityFormHelper(SignUpForm.class,
				this);
		helper.setOnFocusOutValidation();

	}

	private void initFindViews() {
		buttonSignUp = (Button) findViewById(R.id.button_sign_up);
		editUserId = (EditText) findViewById(R.id.editText1);
		editPassword = (EditText) findViewById(R.id.editText6);
		editLastName = (EditText) findViewById(R.id.editText2);
		editFirstName = (EditText) findViewById(R.id.editText3);
		editMail = (EditText) findViewById(R.id.editText4);
		editMailRetype = (EditText) findViewById(R.id.editText5);
		editBirthYear = (EditText) findViewById(R.id.editText7);
		editBirthMonth = (EditText) findViewById(R.id.editText8);
		editBirthDay = (EditText) findViewById(R.id.editText9);
		groupGender = (RadioGroup) findViewById(R.id.rdoGroup);
		male = (RadioButton) findViewById(R.id.radioButton1);
		female = (RadioButton) findViewById(R.id.radioButton2);
	}

	private void setListener() {
		buttonSignUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// validateInsertData();
				intentTop();
			}
		});
	}

	private void intentTop() {
		Intent intent = new Intent(SignUpActivity.this, TopActivity.class);
		startActivity(intent);
		finish();
	}

	private void setGender() {
		String gender;
		RadioButton checkButton = (RadioButton) findViewById(groupGender
				.getCheckedRadioButtonId());
		if (checkButton.equals(male)) {
			gender = Gender.MALE;
		} else if (checkButton.equals(female)) {
			gender = Gender.FEMALE;
		}
	}

	private void validateInsertData() {

	}

	// noripoyo
	//
	// @Override
	// protected void onCreate(Bundle savedInstanceState) {
	// super.onCreate(savedInstanceState);
	// setContentView(R.layout.activity_sign_up);
	//
	// Log.d("sample", "onCreate()の終端");
	// }
	//
	//
	// public void click(View view) {
	// // Toast.makeText(this, "ボタンが押されたよ！", Toast.LENGTH_SHORT).show();
	// DBManager.insertUser("testid", "工学太郎", "miochan", "男", 170, 60, 20, 60,
	// "miomio@gmail.com");
	//
	// // ユーザID、名前、パスワード、性別、身長、体重、年齢、安静時心拍数、メールアドレス
	// }
}
