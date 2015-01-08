package ac.neec.mio.dao.item.api;

import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFactory;
import ac.neec.mio.dao.SQLiteDao;
import android.content.Context;

public class ApiDaoFactory implements DaoFactory {

	@Override
	public ApiDao createApiItemDao(Context context, Sourceable listener) {
		return new ApiItemDao(context, listener);
	}

	@Override
	public SQLiteDao createSQLiteDao(Context context) {
		return null;
	}

}
