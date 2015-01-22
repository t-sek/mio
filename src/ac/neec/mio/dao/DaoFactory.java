package ac.neec.mio.dao;

import android.content.Context;

public interface DaoFactory {
	ApiDao createApiItemDao(Sourceable listener);
	SQLiteDao createSQLiteDao();

}
