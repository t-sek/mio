package ac.neec.mio.training.change;

import ac.neec.mio.training.framework.ProductData;

public class ChangeTraining extends ProductData{

	private int trainingMenuId;
	private String startTime;
	private String endTime;
	private int playTime;

	protected ChangeTraining(int trainingMenuId, String endTime, int playTime) {
		this.trainingMenuId = trainingMenuId;
		this.endTime = endTime;
		this.playTime = playTime;
		
	}

	public int getTrainingMenuId() {
		return trainingMenuId;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public int getPlayTime() {
		return playTime;
	}
}
