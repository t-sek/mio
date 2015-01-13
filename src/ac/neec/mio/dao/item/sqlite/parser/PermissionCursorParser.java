package ac.neec.mio.dao.item.sqlite.parser;

import static ac.neec.mio.consts.SQLConstants.*;
import ac.neec.mio.group.Permission;
import ac.neec.mio.group.GroupFactory;
import ac.neec.mio.training.framework.ProductDataFactory;
import android.database.Cursor;

public class PermissionCursorParser extends CursorParser {

	private Permission permission;

	public PermissionCursorParser(Cursor c) {
		super(c);
	}

	@Override
	protected void parse(Cursor c) {
		ProductDataFactory factory = new GroupFactory();
		int indexId = c.getColumnIndex(permissionId());
		int indexName = c.getColumnIndex(name());
		int indexCompleWithdrawal = c.getColumnIndex(compelWithdrawal());
		int indexDissolution = c.getColumnIndex(dissolution());
		int indexPermissionChange = c.getColumnIndex(permissionChange());
		int indexGroupInfoChange = c.getColumnIndex(groupInfoChange());
		int indexMemberAddManage = c.getColumnIndex(memberAddManage());
		int indexMemberDataCheck = c.getColumnIndex(memberDataCheck());
		int indexMemberListView = c.getColumnIndex(memberListView());
		int indexGroupInfoView = c.getColumnIndex(groupInfoView());
		int indexWithdrawal = c.getColumnIndex(withdrawal());
		int indexJoinStatus = c.getColumnIndex(joinStatus());
		int indexGroupNews = c.getColumnIndex(groupNews());
		while (c.moveToNext()) {
			permission = (Permission) factory.create(c.getInt(indexId),
					c.getString(indexName), c.getInt(indexCompleWithdrawal),
					c.getInt(indexDissolution),
					c.getInt(indexPermissionChange),
					c.getInt(indexGroupInfoChange),
					c.getInt(indexMemberAddManage),
					c.getInt(indexMemberDataCheck),
					c.getInt(indexMemberListView),
					c.getInt(indexGroupInfoView), c.getInt(indexWithdrawal),
					c.getInt(indexJoinStatus), c.getInt(indexGroupNews));

		}
		c.close();
	}

	@Override
	public Permission getObject() {
		return permission;
	}

}
