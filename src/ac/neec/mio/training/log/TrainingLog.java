package ac.neec.mio.training.log;

import ac.neec.mio.training.framework.ProductData;
import android.util.Log;

public class TrainingLog extends ProductData {
	private int logId;
	private int id;
	private int heartRate;
	private double disX;
	private double disY;
	private String time;
	private String lap;
	private String split;
	private int targetHeartRate;

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

	public int getLogId() {
		return logId;
	}

	public int getId() {
		return id;
	}

	public int getHeartRate() {
		return heartRate;
	}

	public double getDisX() {
		return disX;
	}

	public double getDisY() {
		return disY;
	}

	public String getTimeString() {
		return time.toString();
	}

	public String getTime() {
		return time;
	}

	public String getLap() {
		return lap;
	}

	public String getSplit() {
		return split;
	}

	public int getTargetHeartRate() {
		return targetHeartRate;
	}
}
