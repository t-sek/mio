package ac.neec.mio.dao;

import ac.neec.mio.dao.item.api.ApiDaoFactory;
import ac.neec.mio.dao.item.sqlite.SQLiteDaoFactory;
import android.content.Context;

public class DaoFacade {

	public static ApiDao getApiDao(Context context, Sourceable listener) {
		DaoFactory factory = new ApiDaoFactory();
		return factory.createApiItemDao(context, listener);
	}
	
	public static SQLiteDao getSQLiteDao(Context context){
		DaoFactory factory = new SQLiteDaoFactory();
		return factory.createSQLiteDao(context);
	}
}
