package ac.neec.mio.training.heartrate;

import java.sql.Time;

import ac.neec.mio.training.framework.ProductData;

public class HeartRate extends ProductData {

	private Time time;
	private int heartRate;

	protected HeartRate(int heartRate, Time time) {
		// this.time.setHours(time.getHours());
		this.time = time;
		// this.time.setHours(hour);
		this.heartRate = heartRate;
	}

	public String getTimeString() {
		return time.toString();
	}

	public Time getTime() {
		return time;
	}

	public int getHeartRate() {
		return heartRate;
	}
}
