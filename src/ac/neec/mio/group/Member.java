package ac.neec.mio.group;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * グループメンバー
 *
 */
public class Member {

	/**
	 * ユーザ名
	 */
	private String userName;
	/**
	 * メンバーのグループ権限
	 */
	private Affiliation affiliation;

	/**
	 * 
	 * @param userName
	 *            メンバー名
	 * @param affiliation
	 *            メンバーのグループ権限
	 */
	public Member(String userName, Affiliation affiliation) {
		this.userName = userName;
		this.affiliation = affiliation;
	}

	/**
	 * メンバー名を取得する
	 * 
	 * @return メンバー名
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * メンバーのグループ権限を取得する
	 * 
	 * @return 権限
	 */
	public Affiliation getAffiliation() {
		return affiliation;
	}

}
