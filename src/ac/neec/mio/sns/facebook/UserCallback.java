package ac.neec.mio.sns.facebook;

import com.facebook.Response;
import com.facebook.model.GraphUser;

/**
 * facebookログインの通知をするコールバックリスナー
 *
 */
public interface UserCallback {
	/**
	 * ログイン完了を通知する
	 * 
	 * @param user
	 *            ユーザ情報
	 * @param response
	 *            レスポンス
	 */
	void onCompleted(GraphUser user, Response response);
}
