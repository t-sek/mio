package ac.neec.mio.training.play;

import ac.neec.mio.framework.ProductData;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * トレーニング中のメニューを記録していくクラス
 *
 */
public class TrainingPlay extends ProductData {

	/**
	 * プレイID
	 */
	private int playId;
	/**
	 * メニューID
	 */
	private int trainingMenuId;
	/**
	 * トレーニング時間
	 */
	private int trainingTime;

	/**
	 * 
	 * @param playId
	 *            プレイID
	 * @param trainingMenuId
	 *            メニューID
	 * @param trainingTime
	 *            トレーニング時間
	 */
	protected TrainingPlay(int playId, int trainingMenuId, int trainingTime) {
		this.playId = playId;
		this.trainingMenuId = trainingMenuId;
		this.trainingTime = trainingTime;
	}

	/**
	 * プレイIDを取得する
	 * 
	 * @return プレイID
	 */
	public int getPlayId() {
		return playId;
	}

	/**
	 * メニューIDを取得する
	 * 
	 * @return メニューID
	 */
	public int getTrainingMenuId() {
		return trainingMenuId;
	}

	/**
	 * トレーニング時間を取得する
	 * 
	 * @return トレーニング時間
	 */
	public int getTrainingTime() {
		return trainingTime;
	}

}
