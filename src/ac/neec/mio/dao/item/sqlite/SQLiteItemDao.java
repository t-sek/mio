package ac.neec.mio.dao.item.sqlite;

import static ac.neec.mio.consts.SQLConstants.dbName;
import static ac.neec.mio.consts.SQLConstants.dbVersion;
import static ac.neec.mio.consts.SQLConstants.sqlCreatePath;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import ac.neec.mio.consts.AppConstants;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.item.sqlite.parser.CursorParser;
import ac.neec.mio.exception.SQLiteInsertException;
import ac.neec.mio.exception.SQLiteTableConstraintException;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * SQLiteとの接続を行うクラス
 *
 */
public abstract class SQLiteItemDao extends SQLiteOpenHelper implements
		SQLiteDao, Buffer {

	/**
	 * データベースインスタンス
	 */
	private SQLiteDatabase db;
	/**
	 * コンテキスト
	 */
	private Context context;
	/**
	 * インサートエラーを保持しておくバッファ
	 */
	private RingBuffer buffer;

	protected SQLiteItemDao() {
		super(AppConstants.getContext(), dbName(), null, dbVersion());
		this.context = AppConstants.getContext();
		db = getWritableDatabase();
		buffer = new RingBuffer(this);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		this.db = db;
		try {
			execCreateSql(sqlCreatePath());
		} catch (IOException e) {
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

	@Override
	public void insertBuffer(BufferItem item) {
		if (item != null) {
			try {
				insert(item.getTableName(), item.getValues());
			} catch (SQLiteInsertException e) {
				e.printStackTrace();
				buffer.set(item);
			} catch (SQLiteTableConstraintException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * データベースへの挿入
	 * 
	 * @param tableName
	 *            テーブル名
	 * 
	 * @param cv
	 *            挿入するデータ
	 * 
	 * @return
	 * @throws SQLiteInsertException
	 *             インサートエラー
	 * @throws SQLiteTableConstraintException
	 *             テーブル制約エラー
	 */
	protected int insert(String tableName, ContentValues cv)
			throws SQLiteInsertException, SQLiteTableConstraintException {
		float id = 0;
		try {
			id = db.insertOrThrow(tableName, null, cv);
		} catch (SQLiteConstraintException e) {
			throw new SQLiteTableConstraintException();
		} catch (SQLException e) {
			throw new SQLiteInsertException();
		}
		// if (id == -1) {
		// BufferItem item = new BufferItem(tableName, cv);
		// buffer.set(item);
		// }
		return (int) id;
	}

	/**
	 * データベースから検索条件に一致するデータ取得
	 * 
	 * @param tableName
	 *            テーブル名
	 * @param selectColumns
	 *            選択するカラム
	 * @param selectColumn
	 *            検索条件となるカラム
	 * @param criteria
	 *            検索条件
	 * @return 検索結果
	 */
	protected Cursor select(String tableName, String[] selectColumns,
			String selectColumn, String criteria) {
		String selection = selectColumn + " = ? ";
		String[] selectionArgs = { criteria };
		return db.query(tableName, selectColumns, selection, selectionArgs,
				null, null, null);
	}

	protected Cursor select(String tableName, String[] selectColumns,
			String selectColumn1, String selectColumn2, String criteria1,
			String critetia2) {
		String selection = selectColumn1 + " = ?" + " and " + selectColumn2
				+ " = ?";
		String[] selectionArgs = { criteria1, critetia2 };
		return db.query(tableName, selectColumns, selection, selectionArgs,
				null, null, null);
	}

	/**
	 * テーブルの全データ取得
	 * 
	 * @param tableName
	 * @param selectColumns
	 * @return
	 */
	protected Cursor select(String tableName, String[] selectColumns) {
		return db.query(tableName, selectColumns, null, null, null, null, null);
	}

	/**
	 * データベースへの更新
	 * 
	 * @param tableName
	 *            テーブル名
	 * @param cv
	 *            更新する値
	 * @param selectColumn
	 *            検索条件となるカラム
	 * @param criteria
	 *            検索条件
	 */
	protected void update(String tableName, ContentValues cv,
			String selectColumn, String criteria) {
		String[] selectionArgs = { criteria };
		String selection = selectColumn + " = ? ";
		db.updateWithOnConflict(tableName, cv, selection, selectionArgs,
				SQLiteDatabase.CONFLICT_ROLLBACK);
	}

	/**
	 * データベースの項目削除
	 * 
	 * @param tableName
	 *            テーブル名
	 * @param selectColumn
	 *            検索条件となるカラム
	 * @param criteria
	 *            検索条件
	 */
	protected void delete(String tableName, String selectColumn, String criteria) {
		String selection = selectColumn + " = ? ";
		String[] selectionArgs = { criteria };
		db.delete(tableName, selection, selectionArgs);
	}

	/**
	 * データベースのテーブル削除
	 * 
	 * @param tableName
	 *            テーブル名
	 */
	protected void delete(String tableName) {
		db.delete(tableName, null, null);
	}

	private void execCreateSql(String assetsDir) throws IOException {
		AssetManager assetManager = context.getResources().getAssets();
		try {
			InputStream is = assetManager.open(assetsDir);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String str;
			StringBuilder sql = new StringBuilder();
			while ((str = br.readLine()) != null) {
				if (str.equals("/")) {
					db.execSQL(sql.toString());
					sql.delete(0, sql.length());
				} else {
					sql.append(str);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
