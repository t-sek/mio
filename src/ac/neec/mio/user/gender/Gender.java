package ac.neec.mio.user.gender;

import ac.neec.mio.framework.ProductData;

public class Gender extends ProductData {

	public static final String MALE = "男性";
	public static final String FEMALE = "女性";
	public static final String OTHER = "そうでもない";

	private String gender = MALE;

	protected Gender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}
}
