package ac.neec.mio.training;

import ac.neec.mio.framework.ProductData;

/**
 * トレーニング情報クラス
 *
 */
public class Training extends ProductData {

	/**
	 * トレーニングID
	 */
	private int id;
	/**
	 * カテゴリーID
	 */
	private int categoryId;
	/**
	 * トレーニングを実施したユーザID
	 */
	private String userId;
	/**
	 * 実施日
	 */
	private String date;
	/**
	 * 開始時間
	 */
	private String startTime;
	/**
	 * 計測時間
	 */
	private String playTime;
	/**
	 * 最後の目標心拍数
	 */
	private int targetHeartRate;
	/**
	 * 目標カロリー
	 */
	private int targetCal;
	/**
	 * 消費カロリー
	 */
	private int calorie;
	/**
	 * 平均心拍数
	 */
	private int heartRateAvg;
	/**
	 * メニューの運動強度
	 */
	private int strange;
	/**
	 * トレーニング中の走行距離
	 */
	private double distance;

	/**
	 * 
	 * @param id
	 *            トレーニングID
	 * @param categoryId
	 *            カテゴリーID
	 * @param userId
	 *            トレーニングを実施したユーザID
	 * @param date
	 *            実施日
	 * @param startTime
	 *            開始時間
	 * @param playTime
	 *            計測時間
	 * @param targetHeartRate
	 *            最後の目標心拍数
	 * @param targetCal
	 *            目標カロリー
	 * @param calorie
	 *            消費カロリー
	 * @param heartRateAvg
	 *            平均心拍数
	 * @param strange
	 *            メニューの運動強度
	 * @param distance
	 *            トレーニング中の走行距離
	 */
	protected Training(int id, int categoryId, String userId, String date,
			String startTime, String playTime, int targetHeartRate,
			int targetCal, int calorie, int heartRateAvg, int strange,
			double distance) {
		this.id = id;
		this.categoryId = categoryId;
		this.userId = userId;
		this.date = date;
		this.startTime = startTime;
		this.playTime = playTime;
		this.targetHeartRate = targetHeartRate;
		this.targetCal = targetCal;
		this.calorie = calorie;
		this.heartRateAvg = heartRateAvg;
		this.strange = strange;
		this.distance = distance;
	}

	/**
	 * トレーニングIDを取得する
	 * 
	 * @return トレーニングID
	 */
	public int getId() {
		return id;
	}

	/**
	 * カテゴリーIDを取得する
	 * 
	 * @return カテゴリーID
	 */
	public int getCategoryId() {
		return categoryId;
	}

	/**
	 * トレーニングを実施したユーザIDを取得する
	 * 
	 * @return ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 実施日を取得する
	 * 
	 * @return 実施日
	 */
	public String getDate() {
		return date;
	}

	/**
	 * 開始時間を取得する
	 * 
	 * @return 開始時間
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * 計測時間を取得する
	 * 
	 * @return 計測時間
	 */
	public String getPlayTime() {
		return playTime;
	}

	/**
	 * 最後の目標心拍数を取得する
	 * 
	 * @return 目標心拍数
	 */
	public int getTargetHrartRate() {
		return targetHeartRate;
	}

	/**
	 * 目標カロリーを取得する
	 * 
	 * @return 目標カロリー
	 */
	public int getTargetCal() {
		return targetCal;
	}

	/**
	 * 消費カロリーを取得する
	 * 
	 * @return 消費カロリー
	 */
	public int getCalorie() {
		return calorie;
	}

	/**
	 * 平均心拍数を取得する
	 * 
	 * @return 平均心拍数
	 */
	public int getHeartRateAvg() {
		return heartRateAvg;
	}

	/**
	 * メニューの運動強度を取得する
	 * 
	 * @return 運動強度
	 */
	public int getStrange() {
		return strange;
	}

	/**
	 * トレーニング中の走行距離を取得する
	 * 
	 * @return 走行距離
	 */
	public double getDistance() {
		return distance;
	}

}
