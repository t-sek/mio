package ac.neec.mio.group;

import android.graphics.Bitmap;
import android.text.NoCopySpan.Concrete;

public class Group {

	private Bitmap bitmap;
	private String id;
	private String groupName;
	private String comment;
	private String userId;
	private String created;

	public Group(String id, String groupName, Bitmap bitmap, String comment,
			String userId, String created) {
		this.id = id;
		this.groupName = groupName;
		this.bitmap = bitmap;
		this.comment = comment;
		this.userId = userId;
		this.created = created;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public String getId() {
		return id;
	}

	public String getGroupName() {
		return groupName;
	}

	public String getComment() {
		return comment;
	}

	public String getUserId() {
		return userId;
	}

	public String getCreated() {
		return created;
	}
}
