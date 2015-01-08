package ac.neec.mio.taining.menu;

import ac.neec.mio.training.framework.ProductData;

public class TrainingMenu extends ProductData {

	private int trainingMenuId;
	private String trainingName;
	private float mets;
	private int trainingCategoryId;
	private String color;

	protected TrainingMenu(int trainingMenuId, String trainingName, float mets,
			int trainingCategoryId, String color) {
		this.trainingMenuId = trainingMenuId;
		this.trainingName = trainingName;
		this.mets = mets;
		this.trainingCategoryId = trainingCategoryId;
		this.color = color;
	}

	public int getTrainingMenuId() {
		return trainingMenuId;
	}

	public String getTrainingName() {
		return trainingName;
	}

	public float getMets() {
		return mets;
	}

	public int getTrainingCategoryId() {
		return trainingCategoryId;
	}

	public String getColor() {
		return color;
	}
}
