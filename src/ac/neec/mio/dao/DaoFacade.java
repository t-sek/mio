package ac.neec.mio.dao;

import ac.neec.mio.dao.item.api.ApiDaoFactory;
import ac.neec.mio.dao.item.sqlite.SQLiteDaoFactory;
import android.content.Context;

/**
 * DAOインスタンスを取得する窓口クラス
 *
 */
public class DaoFacade {

	/**
	 * WebAPI接続DAOインスタンスを取得する
	 * 
	 * @param listener
	 *            コールバックリスナー
	 * @return DAOインスタンス
	 */
	public static ApiDao getApiDao(Sourceable listener) {
		DaoFactory factory = new ApiDaoFactory();
		return factory.createApiItemDao(listener);
	}

	/**
	 * ローカルデータベース接続DAOインスタンスを取得する
	 * 
	 * @return DAOインスタンス
	 */
	public static SQLiteDao getSQLiteDao() {
		DaoFactory factory = new SQLiteDaoFactory();
		return factory.createSQLiteDao();
	}
}
