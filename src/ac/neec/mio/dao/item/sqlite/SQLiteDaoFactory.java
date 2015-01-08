package ac.neec.mio.dao.item.sqlite;

import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.DaoFactory;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.item.api.Sourceable;
import android.content.Context;

public class SQLiteDaoFactory implements DaoFactory{

	@Override
	public ApiDao createApiItemDao(Context context, Sourceable listener) {
		return null;
	}

	@Override
	public SQLiteDao createSQLiteDao(Context context) {
		return new SQLItemDao(context);
	}

}
