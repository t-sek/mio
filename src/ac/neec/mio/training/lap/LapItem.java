package ac.neec.mio.training.lap;

import ac.neec.mio.framework.ProductData;

/**
 * ラップクラス
 *
 */
public class LapItem extends ProductData {

	/**
	 * ラップID
	 */
	private int id;
	/**
	 * ラップタイム
	 */
	private String lapTime;
	/**
	 * スプリットタイム
	 */
	private String splitTime;
	/**
	 * 距離
	 */
	private String distance;

	/**
	 * 
	 * @param id
	 *            ラップID
	 * @param lapTime
	 *            ラップタイム
	 * @param splitTime
	 *            スプリットタイム
	 * @param distance
	 *            距離
	 */
	public LapItem(int id, String lapTime, String splitTime, String distance) {
		this.id = id;
		this.lapTime = lapTime;
		this.splitTime = splitTime;
		this.distance = distance;
	}

	/**
	 * ラップIDを取得する
	 * 
	 * @return ラップID
	 */
	public int getId() {
		return id;
	}

	/**
	 * ラップタイムを取得する
	 * 
	 * @return ラップタイム
	 */
	public String getLapTime() {
		return lapTime;
	}

	/**
	 * スプリットタイムを取得する
	 * 
	 * @return スプリットタイム
	 */
	public String getSplitTime() {
		return splitTime;
	}

	/**
	 * 距離を取得する
	 * 
	 * @return 距離
	 */
	public String getDistance() {
		return distance;
	}

}
