package ac.neec.mio.group;

import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.training.framework.ProductData;
import ac.neec.mio.user.User;
import android.util.Log;

public class Affiliation extends ProductData {

	private String userId;
	private String groupId;
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

	public String getUserId() {
		return userId;
	}

	public String getGroupId() {
		return groupId;
	}

	public Permission getPermition() {
		return permission;
	}

}
