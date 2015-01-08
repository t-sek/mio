package ac.neec.mio.ui.adapter;

public class SettingListItem {

	private String name;
	private String data;
	private String unit;

	public SettingListItem(String name, String data, String unit) {
		this.name = name;
		this.data = data;
		this.unit = unit;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getName() {
		return name;
	}

	public String getData() {
		return data;
	}

	public String getUnit() {
		return unit;
	}
}
