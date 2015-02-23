package ac.neec.mio.ui.filter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * メールアドレスの入力チェックを実装したクラス<br>
 * ひらがなをはじく
 */
public class MailFilter implements InputFilter {

	private static final int MAX_LENGTH = 100;

	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		if (source.toString().matches("^[a-zA-Z0-9@¥¥.¥¥_¥¥-]")
				&& dend <= MAX_LENGTH) {
			return source;
		} else {
			return "";
		}
	}
}
