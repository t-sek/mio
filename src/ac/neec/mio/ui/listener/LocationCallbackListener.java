package ac.neec.mio.ui.listener;

import android.location.Location;

/**
 * 計測画面での位置情報変更を通知するリスナー
 */
public interface LocationCallbackListener {
	/**
	 * マップ画面から右のタブに移動することを通知する
	 */
	void onPagerMoveRight();

	/**
	 * 位置情報変更を通知する
	 * 
	 * @param location
	 *            位置情報
	 * @param distance
	 *            走行距離
	 * @param speed
	 *            走行スピード
	 */
	void onLocationChanged(Location location, double distance, float speed);
}
