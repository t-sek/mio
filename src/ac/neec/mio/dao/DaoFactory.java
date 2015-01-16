package ac.neec.mio.dao;

import android.content.Context;

public interface DaoFactory {
	ApiDao createApiItemDao(Context context, Sourceable listener);
	SQLiteDao createSQLiteDao(Context context);

}
