package ac.neec.mio.user.gender;

import ac.neec.mio.framework.ProductData;

/**
 * 性別クラス
 */
public class Gender extends ProductData {

	/**
	 * 男性フラグ
	 */
	public static final String MALE = "男性";
	/**
	 * 女性フラグ
	 */
	public static final String FEMALE = "女性";
	/**
	 * その他フラグ
	 */
	public static final String OTHER = "そうでもない";
	/**
	 * 性別
	 */
	private String gender = MALE;

	/**
	 * 
	 * @param gender
	 *            性別<br>
	 *            GenderクラスのMALE、FEMALE、OTHER
	 */
	protected Gender(String gender) {
		this.gender = gender;
	}

	/**
	 * 性別を取得する
	 * 
	 * @return 性別<br>
	 *         GenderクラスのMALE、FEMALE、OTHER
	 */
	public String getGender() {
		return gender;
	}
}
