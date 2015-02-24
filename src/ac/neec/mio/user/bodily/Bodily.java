package ac.neec.mio.user.bodily;

import ac.neec.mio.framework.ProductData;
import ac.neec.mio.user.bodily.weight.Weight;

/**
 * ユーザの身体情報クラス
 */
public class Bodily extends ProductData {

	/**
	 * 身長
	 */
	private float height;
	/**
	 * 体重
	 */
	private Weight weight;
	/**
	 * 安静時心拍数
	 */
	private int quietHeartRate;

	/**
	 * 
	 * @param height
	 *            身長
	 * @param weight
	 *            体重
	 * @param quietHeartRate
	 *            安静時心拍数
	 */
	protected Bodily(float height, Weight weight, int quietHeartRate) {
		this.height = height;
		this.weight = weight;
		this.quietHeartRate = quietHeartRate;
	}

	/**
	 * 身長を取得する
	 * 
	 * @return 身長
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * 体重を取得する
	 * 
	 * @return 体重
	 */
	public Weight getWeight() {
		return weight;
	}

	/**
	 * 安静時心拍数を取得する
	 * 
	 * @return 安静時心拍数
	 */
	public int getQuietHeartRate() {
		return quietHeartRate;
	}

}
