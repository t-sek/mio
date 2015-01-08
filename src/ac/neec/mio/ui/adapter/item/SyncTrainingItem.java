package ac.neec.mio.ui.adapter.item;

import ac.neec.mio.util.DateUtil;

public class SyncTrainingItem {
	private int id;
	private String date;
	private String trainingCategory;

	public SyncTrainingItem(int id, String date, String trainingCategory) {
		this.id = id;
		this.date = date;
		this.trainingCategory = trainingCategory;
	}

	public int getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public String getTrainingCategory() {
		return trainingCategory;
	}

	@Override
	public String toString() {

		return DateUtil.japaneseFormat(date) + " " + trainingCategory;
	}
}
