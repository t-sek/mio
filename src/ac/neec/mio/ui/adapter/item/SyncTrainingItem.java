package ac.neec.mio.ui.adapter.item;

import ac.neec.mio.util.DateUtil;

/**
 * 未同期トレーニングリストビューの項目を定義するクラス
 */
public class SyncTrainingItem {
	/**
	 * トレーニングID
	 */
	private int id;
	/**
	 * 日付
	 */
	private String date;
	/**
	 * トレーニングカテゴリー名
	 */
	private String trainingCategory;

	/**
	 * 
	 * @param id
	 *            トレーニングID
	 * @param date
	 *            日付
	 * @param trainingCategory
	 *            トレーニングカテゴリー名
	 */
	public SyncTrainingItem(int id, String date, String trainingCategory) {
		this.id = id;
		this.date = date;
		this.trainingCategory = trainingCategory;
	}

	/**
	 * トレーニングIDを取得する
	 * 
	 * @return トレーニングID
	 */
	public int getId() {
		return id;
	}

	/**
	 * 日付を取得する
	 * 
	 * @return 日付
	 */
	public String getDate() {
		return date;
	}

	/**
	 * トレーニングカテゴリー名を取得する
	 * 
	 * @return トレーニングカテゴリー名
	 */
	public String getTrainingCategory() {
		return trainingCategory;
	}

	@Override
	public String toString() {
		return DateUtil.japaneseFormat(date) + " " + trainingCategory;
	}
}
