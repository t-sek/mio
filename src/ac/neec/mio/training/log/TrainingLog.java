package ac.neec.mio.training.log;

import ac.neec.mio.framework.ProductData;

/**
 * トレーニングログ
 *
 */
public class TrainingLog extends ProductData {
	/**
	 * ログID
	 */
	public int logId;
	/**
	 * トレーニングID
	 */
	private int id;
	/**
	 * 心拍数
	 */
	private int heartRate;
	/**
	 * 経度
	 */
	private double disX;
	/**
	 * 緯度
	 */
	private double disY;
	/**
	 * 計測時間
	 */
	private String time;
	/**
	 * ラップタイム
	 */
	private String lap;
	/**
	 * スプリットタイム
	 */
	private String split;
	/**
	 * 目標心拍数
	 */
	private int targetHeartRate;

	/**
	 * 
	 * @param logId
	 *            ログID
	 * @param id
	 *            トレーニングID
	 * @param heartRate
	 *            心拍数
	 * @param disX
	 *            経度
	 * @param disY
	 *            緯度
	 * @param time
	 *            計測時間
	 * @param lap
	 *            ラップタイム
	 * @param split
	 *            スプリットタイム
	 * @param targetHeartRate
	 *            目標心拍数
	 */
	protected TrainingLog(int logId, int id, int heartRate, double disX,
			double disY, String time, String lap, String split,
			int targetHeartRate) {
		this.logId = logId;
		this.id = id;
		this.heartRate = heartRate;
		this.disX = disX;
		this.disY = disY;
		this.time = time;
		this.lap = lap;
		this.split = split;
		this.targetHeartRate = targetHeartRate;
	}

	/**
	 * ログIDを取得する
	 * 
	 * @return ログID
	 */
	public int getLogId() {
		return logId;
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
	 * 心拍数を取得する
	 * 
	 * @return 心拍数
	 */
	public int getHeartRate() {
		return heartRate;
	}

	/**
	 * 経度を取得する
	 * 
	 * @return 経度
	 */
	public double getDisX() {
		return disX;
	}

	/**
	 * 緯度を取得する
	 * 
	 * @return 緯度
	 */
	public double getDisY() {
		return disY;
	}

	/**
	 * 計測時間を取得する
	 * 
	 * @return 計測時間
	 */
	public String getTime() {
		return time;
	}

	/**
	 * ラップタイムを取得する
	 * 
	 * @return ラップタイム
	 */
	public String getLap() {
		return lap;
	}

	/**
	 * スプリットタイムを取得する
	 * 
	 * @return スプリットタイム
	 */
	public String getSplit() {
		return split;
	}

	/**
	 * 目標心拍数を取得する
	 * 
	 * @return 目標心拍数
	 */
	public int getTargetHeartRate() {
		return targetHeartRate;
	}
}
