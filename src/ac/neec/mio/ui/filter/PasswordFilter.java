package ac.neec.mio.ui.filter;

import android.text.InputFilter;
import android.text.Spanned;

/**
 * パスワードの文字制限を実装するクラス
 *
 */
public class PasswordFilter implements InputFilter {

	/**
	 * 最大文字制限
	 */
	private static final int MAX_LENGTH = 20;

	public CharSequence filter(CharSequence source, int start, int end,
			Spanned dest, int dstart, int dend) {
		if (dend <= MAX_LENGTH) {
			return source;
		} else {
			return "";
		}
	}
}
