package ac.neec.mio.group;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

/**
 * グループ
 */
public class Group {

	/**
	 * アイコン
	 */
	private Bitmap image;
	/**
	 * グループID
	 */
	private String id;
	/**
	 * グループ名
	 */
	private String groupName;
	/**
	 * コメント
	 */
	private String comment;
	/**
	 * 管理者ID
	 */
	private String userId;
	/**
	 * 管理者名
	 */
	private String adminName;
	/**
	 * 作成日
	 */
	private String created;
	/**
	 * 権限ID
	 */
	private int permissionId;
	/**
	 * アイコン名
	 */
	private String imageName;

	public Group(String id, String groupName, String imageName, String comment,
			String userId, String adminName, String created) {
		this.id = id;
		this.groupName = groupName;
		this.imageName = imageName;
		this.comment = comment;
		this.userId = userId;
		this.adminName = adminName;
		this.created = created;
	}

	public Group(String id, String groupName, Bitmap bitmap, String comment,
			String userId, String created) {
		this.id = id;
		this.groupName = groupName;
		this.image = bitmap;
		this.comment = comment;
		this.userId = userId;
		this.created = created;
	}

	public Group(String id, String groupName, Bitmap bitmap, String comment,
			String userId, String created, int permissionId) {
		this.id = id;
		this.groupName = groupName;
		this.image = bitmap;
		this.comment = comment;
		this.userId = userId;
		this.created = created;
		this.permissionId = permissionId;
	}

	public Group(String id, String groupName, Bitmap bitmap, String comment,
			String userId, String adminName, String created, int permissionId) {
		this.id = id;
		this.groupName = groupName;
		this.image = bitmap;
		this.comment = comment;
		this.userId = userId;
		this.adminName = adminName;
		this.created = created;
		this.permissionId = permissionId;
	}

	/**
	 * 権限IDを設定する
	 * 
	 * @param permissionId
	 *            権限ID
	 */
	public void setPermissionId(int permissionId) {
		this.permissionId = permissionId;
	}

	/**
	 * グループアイコンを設定する
	 * 
	 * @param image
	 *            アイコン
	 */
	public void setImage(Bitmap image) {
		this.image = image;
	}

	/**
	 * アイコンを取得する
	 * 
	 * @return アイコン
	 */
	public Bitmap getBitmap() {
		return image;
	}

	/**
	 * グループIDを取得する
	 * 
	 * @return グループID
	 */
	public String getId() {
		return id;
	}

	/**
	 * グループ名を取得する
	 * 
	 * @return グループ名
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * コメントを取得する
	 * 
	 * @return コメント
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * ユーザIDを取得する
	 * 
	 * @return ユーザID
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 管理者名を取得する
	 * 
	 * @return 管理者名
	 */
	public String getAdminName() {
		return adminName;
	}

	/**
	 * 作成日を取得する
	 * 
	 * @return 作成日
	 */
	public String getCreated() {
		return created;
	}

	/**
	 * 権限IDを取得する
	 * 
	 * @return 権限ID
	 */
	public int getPermissionId() {
		return permissionId;
	}

	/**
	 * アイコン名を取得する
	 * 
	 * @return アイコン名
	 */
	public String getImageName() {
		return imageName;
	}
}
