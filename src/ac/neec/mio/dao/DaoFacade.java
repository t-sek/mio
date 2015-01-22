package ac.neec.mio.dao;

import ac.neec.mio.dao.item.api.ApiDaoFactory;
import ac.neec.mio.dao.item.sqlite.SQLiteDaoFactory;
import android.content.Context;

public class DaoFacade {

	public static ApiDao getApiDao(Sourceable listener) {
		DaoFactory factory = new ApiDaoFactory();
		return factory.createApiItemDao(listener);
	}

	public static SQLiteDao getSQLiteDao() {
		DaoFactory factory = new SQLiteDaoFactory();
		return factory.createSQLiteDao();
	}
}
