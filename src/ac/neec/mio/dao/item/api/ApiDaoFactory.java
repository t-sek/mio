package ac.neec.mio.dao.item.api;

import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFactory;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import android.content.Context;

/**
 * ApiDaoインスタンス生成を実装したクラス 
 *
 */
public class ApiDaoFactory implements DaoFactory {

	@Override
	public ApiDao createApiItemDao(Sourceable listener) {
		return new ApiItemDao(listener);
	}

	@Override
	public SQLiteDao createSQLiteDao() {
		return null;
	}

}
