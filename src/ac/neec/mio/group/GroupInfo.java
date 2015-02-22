package ac.neec.mio.group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;

/**
 * グループ情報
 */
public class GroupInfo implements Serializable {

	private static final long SERIAL_ID = 1L;

	/**
	 * グループID
	 */
	private String id;
	/**
	 * グループ名
	 */
	private String name;
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
	 * メンバー数
	 */
	private int count;
	/**
	 * メンバーリスト
	 */
	private List<MemberInfo> members;
	/**
	 * アイコン名
	 */
	private String imageName;
	/**
	 * アイコン
	 */
	private Bitmap image;

	/**
	 * 
	 * @param id
	 *            グループID
	 * @param name
	 *            グループ名
	 * @param comment
	 *            コメント
	 * @param count
	 *            メンバー数
	 * @param userId
	 *            管理者ID
	 * @param adminName
	 *            管理者名
	 * @param created
	 *            作成日
	 * @param members
	 *            メンバーリスト
	 */
	public GroupInfo(String id, String name, String comment, int count,
			String userId, String adminName, String created,
			List<MemberInfo> members) {
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.userId = userId;
		this.adminName = adminName;
		this.created = created;
		this.count = count;
		this.members = members;
	}

	/**
	 * 
	 * @param id
	 *            グループID
	 * @param name
	 *            グループ名
	 * @param comment
	 *            コメント
	 * @param count
	 *            メンバー数
	 * @param userId
	 *            管理者ID
	 * @param adminName
	 *            管理者名
	 * @param created
	 *            作成日
	 * @param members
	 *            メンバーリスト
	 * @param imageName
	 *            アイコン名
	 */
	public GroupInfo(String id, String name, String comment, int count,
			String userId, String adminName, String created,
			List<MemberInfo> members, String imageName) {
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.userId = userId;
		this.adminName = adminName;
		this.created = created;
		this.count = count;
		this.members = members;
		this.imageName = imageName;
	}

	/**
	 * アイコンを設定する
	 * 
	 * @param image
	 *            アイコン
	 */
	public void setImage(Bitmap image) {
		this.image = image;
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
	public String getName() {
		return name;
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
	 * 管理者IDを取得する
	 * 
	 * @return 管理者ID
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
	 * メンバー数を取得する
	 * 
	 * @return メンバー数
	 */
	public int getCount() {
		return count;
	}

	/**
	 * メンバーを取得する
	 * 
	 * @return メンバー
	 */
	public List<MemberInfo> getMembers() {
		return members;
	}

	/**
	 * アイコン名を取得する
	 * 
	 * @return アイコン名
	 */
	public String getImageName() {
		return imageName;
	}

	/**
	 * アイコンを取得する
	 * 
	 * @return アイコン
	 */
	public Bitmap getImage() {
		return image;
	}
}
