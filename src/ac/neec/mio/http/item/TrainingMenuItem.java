package ac.neec.mio.http.item;

public class TrainingMenuItem extends ResponceItem {

	public static final String MENU = "TrainingMenu";
	public static final String M_ID = "TrainingMenuID";
	public static final String NAME = "TrainingName";
	public static final String METS = "Mets";
	public static final String C_ID = "CategoryID";
	public static final String COLOR = "Color";

	private int trainingMenuId;
	private String trainingName;
	private float mets;
	private int trainingCategoryId;
	private String color;

	public void setTrainingMenuId(int trainingMenuId) {
		this.trainingMenuId = trainingMenuId;
	}

	public void setTrainingName(String trainingName) {
		this.trainingName = trainingName;
	}

	public void setMets(float mets) {
		this.mets = mets;
	}

	public void setTrainingCategoryId(int trainingCategoryId) {
		this.trainingCategoryId = trainingCategoryId;
	}

	public void setColor(String color) {
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
