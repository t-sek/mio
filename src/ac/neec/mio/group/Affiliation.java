package ac.neec.mio.group;

import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.framework.ProductData;
import ac.neec.mio.user.User;
import android.util.Log;

/**
 * グループ権限
 */
public class Affiliation extends ProductData {

	/**
	 * ユーザID
	 */
	private String userId;
	/**
	 * グループID
	 */
	private String groupId;
	/**
	 * 権限
	 */
	private Permission permission;

	protected Affiliation(String userId, String groupId, Permission permission) {
		this.userId = userId;
		this.groupId = groupId;
		this.permission = permission;
	}

	protected Affiliation(String groupId, int permissionId) {
		SQLiteDao dao = DaoFacade.getSQLiteDao();
		this.userId = User.getInstance().getId();
		this.groupId = groupId;
		this.permission = dao.selectPermission(permissionId);
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
	 * グループIDを取得する
	 * 
	 * @return グループID
	 */
	public String getGroupId() {
		return groupId;
	}

	/**
	 * 権限を取得する
	 * 
	 * @return 権限
	 */
	public Permission getPermition() {
		return permission;
	}

}
