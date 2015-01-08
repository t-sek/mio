package ac.neec.mio.taining.lap;

import ac.neec.mio.training.framework.ProductData;

public class LapItem extends ProductData {

	private int id;
	private String lapTime;
	private String splitTime;
	private String distance;

	// private String trainingName;
	// private String time;

	public LapItem(int id, String lapTime, String splitTime, String distance) {
		this.id = id;
		this.lapTime = lapTime;
		this.splitTime = splitTime;
		this.distance = distance;
		// this.trainingName = trainingName;
		// this.time = time;
	}

	public int getId() {
		return id;
	}

	public String getLapTime() {
		return lapTime;
	}

	public String getSplitTime() {
		return splitTime;
	}

	public String getDistance() {
		return distance;
	}

	// public String getTrainingName() {
	// return trainingName;
	// }
	//
	// public String getTime() {
	// return time;
	// }
}
