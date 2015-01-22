package ac.neec.mio.dao.item.sqlite;

import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.DaoFactory;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import android.content.Context;

public class SQLiteDaoFactory implements DaoFactory {

	@Override
	public ApiDao createApiItemDao(Sourceable listener) {
		return null;
	}

	@Override
	public SQLiteDao createSQLiteDao() {
		return new SQLItemDao();
	}

}
