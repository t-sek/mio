package ac.neec.mio.http.item;

import ac.neec.mio.util.TimeUtil;
import android.util.Log;

public class TrainingItem extends ResponceItem {

	private int trainingId;
	private String date;
	private int categoryId;
	private String startTime;
	private String playTime;
	private int targetHeartRate;
	private int targetCal;
	private int cal;
	private int heartRateAvg;
	private double distance;


	public void setTrainingId(int trainingId) {
		this.trainingId = trainingId;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public void setStartTime(String startTime) {
		this.startTime = TimeUtil.longTimeToSmartTime(startTime);
	}

	public void setPlayTime(String playTime) {
		this.playTime = playTime;
	}

	public void setTargetHeartRate(int targetHeartRate) {
		this.targetHeartRate = targetHeartRate;
	}

	public void setTargetCal(int targetCal) {
		this.targetCal = targetCal;
	}

	public void setCal(int cal) {
		this.cal = cal;
	}

	public void setHeartRateAvg(int heartRateAvg) {
		this.heartRateAvg = heartRateAvg;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public int getTrainingId() {
		return trainingId;
	}

	public String getDate() {
		return date;
	}

	public int getCategoryId() {
		return categoryId;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getPlayTime() {
		return playTime;
	}

	public int getTargetHeartRate() {
		return targetHeartRate;
	}

	public int getTargetCal() {
		return targetCal;
	}

	public int getCal() {
		return cal;
	}

	public int getHeartRateAvg() {
		return heartRateAvg;
	}

	public double getDistance() {
		return distance;
	}

}
