package ac.neec.mio.validate;

import ac.neec.mio.R;

import com.androidformenhancer.annotation.AlphaNum;
import com.androidformenhancer.annotation.Email;
import com.androidformenhancer.annotation.MinLength;
import com.androidformenhancer.annotation.Required;
import com.androidformenhancer.annotation.Widget;

public class UserSignUpForm {

	@Widget(id = R.id.edit_id, nameResId = R.string.userId)
	@AlphaNum
	@Required
	@MinLength(value = 6)
	public String userId;

	@Widget(id = R.id.edit_password, nameResId = R.string.pass, validateAfter = R.id.edit_id)
	@AlphaNum
	@Required
	@MinLength(value = 6)
	public String password;

	@Widget(id = R.id.edit_password_conf, nameResId = R.string.pass_conf, validateAfter = R.id.edit_password)
	@AlphaNum
	@MinLength(value = 6)
	@Required
	public String passwordConf;

	@Widget(id = R.id.edit_mail, nameResId = R.string.mailAdd, validateAfter = R.id.edit_password_conf)
	@Required
	@Email
	public String mail;

}
