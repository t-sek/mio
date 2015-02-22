package ac.neec.mio.consts;

import android.content.Context;
import android.content.res.Resources;

/**
 * リソースを管理するクラス
 *
 */
public abstract class AppConstants {

	/**
	 * リソース
	 */
	protected static Resources resources;
	/**
	 * コンテキスト
	 */
	protected static Context context;

	/**
	 * リソースを設定
	 * 
	 * @param resources
	 *            リソース
	 */
	public static void setResorces(Resources resources) {
		AppConstants.resources = resources;
	}

	/**
	 * コンテキストを設定
	 * 
	 * @param context
	 *            コンテキスト
	 */
	public static void setContext(Context context) {
		AppConstants.context = context;
	}

	/**
	 * コンテキストを取得
	 * 
	 * @return コンテキスト
	 */
	public static Context getContext() {
		return context;
	}

}
