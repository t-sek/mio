package ac.neec.mio.user;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Group;
import ac.neec.mio.image.ImageInfo;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.user.gender.GenderFactory;
import ac.neec.mio.user.role.Role;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * ユーザ情報クラス
 */
public class UserInfo {

	/**
	 * グループ権限リスト
	 */
	private List<Affiliation> affiliations = new ArrayList<Affiliation>();
	/**
	 * 所属グループリスト
	 */
	private List<Group> groups = new ArrayList<Group>();
	/**
	 * ユーザID
	 */
	private String userId;
	/**
	 * ユーザ名
	 */
	private String name;
	/**
	 * 生年月日
	 */
	private String birth;
	/**
	 * 性別
	 */
	private Gender gender;
	/**
	 * 身長
	 */
	private float height;
	/**
	 * 体重
	 */
	private float weight;
	
	/**
	 * メールアドレス
	 */
	private String mail;
	/**
	 * アイコン情報
	 */
	private ImageInfo image;
	/**
	 * ロール
	 */
	private Role role;

	/**
	 * 
	 * @param affiliations
	 *            グループ権限リスト
	 * @param groups
	 *            所属グループ
	 * @param userId
	 *            ユーザID
	 * @param name
	 *            ユーザ名
	 * @param birth
	 *            生年月日
	 * @param gender
	 *            性別
	 * @param height
	 *            身長
	 * @param weight
	 *            体重
	 * @param mail
	 *            メールアドレス
	 * @param image
	 *            アイコン情報
	 * @param role
	 *            ロール
	 */
	public UserInfo(List<Affiliation> affiliations, List<Group> groups,
			String userId, String name, String birth, Gender gender,
			float height, float weight, String mail, ImageInfo image, Role role) {
		this.affiliations = affiliations;
		this.groups = groups;
		this.userId = userId;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.mail = mail;
		this.image = image;
		this.role = role;
	}

	/**
	 * グループ権限リストを設定する
	 * 
	 * @param affiliations
	 *            グループ権限リスト
	 */
	public void setAffiliations(List<Affiliation> affiliations) {
		this.affiliations = affiliations;
	}

	/**
	 * 所属グループを設定する
	 * 
	 * @param groups
	 *            所属グループ
	 */
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	/**
	 * アイコン情報を設定する
	 * 
	 * @param info
	 *            アイコン情報
	 */
	public void setImageInfo(ImageInfo info) {
		this.image = info;
	}

	/**
	 * ロールを設定する
	 * 
	 * @param role
	 *            ロール
	 */
	public void setRole(Role role) {
		this.role = role;
	}

	/**
	 * 体重を設定する
	 * 
	 * @param weight
	 *            体重
	 */
	public void setWeight(float weight) {
		this.weight = weight;
	}

	/**
	 * グループ権限を取得する
	 * 
	 * @return グループ権限
	 */
	public List<Affiliation> getAffiliations() {
		return affiliations;
	}

	/**
	 * 所属グループを取得する
	 * 
	 * @return 所属グループ
	 */
	public List<Group> getGroups() {
		return groups;
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
	 * ユーザ名を取得する
	 * 
	 * @return ユーザ名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 生年月日を取得する
	 * 
	 * @return 生年月日
	 */
	public String getBirth() {
		return birth;
	}

	/**
	 * 性別を取得する
	 * 
	 * @return 性別
	 */
	public Gender getGender() {
		return gender;
	}

	/**
	 * 身長を取得する
	 * 
	 * @return 身長
	 */
	public float getHeight() {
		return height;
	}

	/**
	 * 体重を取得する
	 * 
	 * @return 体重
	 */
	public float getWeight() {
		return weight;
	}

	/**
	 * メールアドレスを取得する
	 * 
	 * @return メールアドレス
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * アイコン情報を取得する
	 * 
	 * @return アイコン情報
	 */
	public ImageInfo getImageInfo() {
		return image;
	}

	/**
	 * ロールを取得する
	 * 
	 * @return ロール
	 */
	public Role getRole() {
		return role;
	}

}
