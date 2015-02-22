package ac.neec.mio.ui.adapter;

import android.graphics.Bitmap;

public class GroupInfoListItem {
	private String operation;
	private Bitmap greenIcon;
	private int num;

	public GroupInfoListItem(String opr, Bitmap gi, int num) {
		this.operation = opr;
		this.greenIcon = gi;
		this.num = num;
	}

	public Bitmap getIcon() {
		return greenIcon;
	}

	public String getOperation() {
		return operation;
	}

	public int getNum() {
		return num;
	}
}
