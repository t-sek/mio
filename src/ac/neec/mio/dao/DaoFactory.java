package ac.neec.mio.dao;

import android.content.Context;

/**
 * DAOインスタンスを生成するクラス
 *
 */
public interface DaoFactory {
	/**
	 * WebAPIにアクセスするインスタンスを取得する
	 * 
	 * @param listener
	 *            コールバックリスナー
	 * @return DAOインスタンス
	 */
	ApiDao createApiItemDao(Sourceable listener);

	/**
	 * ローカルデータベースにアクセスするインスタンスを取得する
	 * 
	 * @return DAOインスタンス
	 */
	SQLiteDao createSQLiteDao();

}
