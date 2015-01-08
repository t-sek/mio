package ac.neec.mio.validate;

import ac.neec.mio.R;

import com.androidformenhancer.annotation.AlphaNum;
import com.androidformenhancer.annotation.Email;
import com.androidformenhancer.annotation.IntRange;
import com.androidformenhancer.annotation.IntType;
import com.androidformenhancer.annotation.MinValue;
import com.androidformenhancer.annotation.Required;
import com.androidformenhancer.annotation.Widget;

public class SignUpForm {

	@Widget(id = R.id.editText1, nameResId = R.string.userId)
	@Required
	@AlphaNum
	public String userId;

	@Widget(id = R.id.editText6, nameResId = R.string.pass)
	@Required
	@MinValue(4)
	public String password;

	@Widget(id = R.id.editText2, nameResId = R.string.sei)
	@Required
	public String LastName;

	@Widget(id = R.id.editText3, nameResId = R.string.mei)
	@Required
	public String FirstName;

	@Widget(id = R.id.editText4, nameResId = R.string.mailAdd)
	@Required
	@Email
	public String mail;

	@Widget(id = R.id.editText5, nameResId = R.string.mailCheck)
	@Required
	@Email
	public String mailRetype;

	@Widget(id = R.id.editText7, nameResId = R.string.year)
	@Required
	@IntType
	@IntRange(min = 1900, max = 2100)
	public String birthYear;

	@Widget(id = R.id.editText8, nameResId = R.string.month)
	@Required
	@IntType
	@IntRange(min = 1, max = 12)
	public String birthMonth;

	@Widget(id = R.id.editText9, nameResId = R.string.day)
	@Required
	@IntType
	@IntRange(min = 1, max = 31)
	public String birthDay;

}
