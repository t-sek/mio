package ac.neec.mio.training.category;

import ac.neec.mio.framework.ProductData;

/**
 * トレーニングカテゴリークラス
 *
 */
public class TrainingCategory extends ProductData {

	/**
	 * カテゴリーID
	 */
	private int trainingCategoryId;
	/**
	 * カテゴリー名
	 */
	private String trainingCategoryName;

	/**
	 * 
	 * @param trainingCategoryId
	 *            カテゴリーID
	 * @param trainingCategoryName
	 *            カテゴリー名
	 */
	protected TrainingCategory(int trainingCategoryId,
			String trainingCategoryName) {
		this.trainingCategoryId = trainingCategoryId;
		this.trainingCategoryName = trainingCategoryName;
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
	 * カテゴリー名を取得する
	 * 
	 * @return カテゴリー名
	 */
	public String getTrainingCategoryName() {
		return trainingCategoryName;
	}
}
