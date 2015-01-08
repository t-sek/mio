package ac.neec.mio.user.gender;

import ac.neec.mio.training.framework.ProductData;

public class Gender extends ProductData {

	public static final String MALE = "man";
	public static final String FEMALE = "woman";
	public static final String OTHER = "other";

	private String gender = MALE;

	protected Gender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}
}
