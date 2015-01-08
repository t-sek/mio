package ac.neec.mio.http.item;

public class DateNumItem extends ResponceItem{

	private String date;
	private int num;

	public DateNumItem(String date, int num) {
		this.date = date;
		this.num = num;
	}

	public String getDate() {
		return date;
	}

	public int getNum() {
		return num;
	}
}
