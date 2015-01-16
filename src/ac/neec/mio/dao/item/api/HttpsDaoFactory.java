package ac.neec.mio.dao.item.api;

import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFactory;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import android.content.Context;

public class HttpsDaoFactory implements DaoFactory {

	@Override
	public ApiDao createApiItemDao(Context context, Sourceable listener) {
		return new ApiItemDao(context, listener);
	}

	@Override
	public SQLiteDao createSQLiteDao(Context context) {
		return null;
	}

}
