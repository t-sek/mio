package ac.neec.mio.taining.category;

import ac.neec.mio.training.framework.ProductData;

public class TrainingCategory extends ProductData {

	private int trainingCategoryId;
	private String trainingCategoryName;

	protected TrainingCategory(int trainingCategoryId,
			String trainingCategoryName) {
		this.trainingCategoryId = trainingCategoryId;
		this.trainingCategoryName = trainingCategoryName;
	}
	
	public int getTrainingCategoryId(){
		return trainingCategoryId;
	}
	
	public String getTrainingCategoryName(){
		return trainingCategoryName;
	}
}
