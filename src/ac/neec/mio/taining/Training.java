package ac.neec.mio.taining;

import ac.neec.mio.training.framework.ProductData;

public class Training extends ProductData {

	private int id;
	private int categoryId;
	private String userId;
	private String date;
	private String startTime;
	private String playTime;
	private int targetHeartRate;
	private int targetCal;
	private int calorie;
	private int heartRateAvg;
	private int strange;
	private double distance;

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

	public int getId() {
		return id;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getUserId() {
		return userId;
	}

	public String getDate() {
		return date;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getPlayTime() {
		return playTime;
	}

	public int getTargetHrartRate() {
		return targetHeartRate;
	}

	public int getTargetCal() {
		return targetCal;
	}

	public int getCalorie() {
		return calorie;
	}

	public int getHeartRateAvg() {
		return heartRateAvg;
	}

	public int getStrange() {
		return strange;
	}

	public double getDistance() {
		return distance;
	}

}
