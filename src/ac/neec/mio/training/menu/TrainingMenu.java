package ac.neec.mio.training.menu;

import ac.neec.mio.framework.ProductData;

/**
 * トレーニングメニュークラス
 *
 */
public class TrainingMenu extends ProductData {

	/**
	 * メニューID
	 */
	private int trainingMenuId;
	/**
	 * メニュー名
	 */
	private String trainingName;
	/**
	 * メッツ
	 */
	private float mets;
	/**
	 * カテゴリーID
	 */
	private int trainingCategoryId;
	/**
	 * カラーコード
	 */
	private String color;

	/**
	 * 
	 * @param trainingMenuId
	 *            メニューID
	 * @param trainingName
	 *            メニュー名
	 * @param mets
	 *            メッツ
	 * @param trainingCategoryId
	 *            カテゴリーID
	 * @param color
	 *            カラーコード
	 */
	protected TrainingMenu(int trainingMenuId, String trainingName, float mets,
			int trainingCategoryId, String color) {
		this.trainingMenuId = trainingMenuId;
		this.trainingName = trainingName;
		this.mets = mets;
		this.trainingCategoryId = trainingCategoryId;
		this.color = color;
	}

	/**
	 * メニューIDを取得する
	 * 
	 * @return メニューID
	 */
	public int getTrainingMenuId() {
		return trainingMenuId;
	}

	/**
	 * メニュー名を取得する
	 * 
	 * @return メニュー名
	 */
	public String getTrainingName() {
		return trainingName;
	}

	/**
	 * メッツを取得する
	 * 
	 * @return メッツ
	 */
	public float getMets() {
		return mets;
	}

	/**
	 * カテゴリーIDを取得する
	 * 
	 * @return カテゴリーID
	 */
	public int getTrainingCategoryId() {
		return trainingCategoryId;
	}

	/**
	 * カラーコードを取得する
	 * 
	 * @return カラーコード
	 */
	public String getColor() {
		return color;
	}
}
