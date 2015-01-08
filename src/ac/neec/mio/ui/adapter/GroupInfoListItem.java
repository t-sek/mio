package ac.neec.mio.ui.adapter;

import android.graphics.Bitmap;

public class GroupInfoListItem {
	private String operation;
	private Bitmap greenIcon;
	
	public GroupInfoListItem(String opr,Bitmap gi){
		this.operation = opr;
		this.greenIcon = gi;
	}
	
	public Bitmap getIcon(){
		return greenIcon;
	}
	
	public String getOperation(){
		return operation;
	}
}
