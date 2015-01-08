package ac.neec.mio.exception;

/**
 * データベースのテーブル制約に誤りがあります
 */
public class SQLiteTableConstraintException extends Exception {

	private static final long serialVersionUID = 1L;
	private static final String ERROR_TEXT = "データベースのテーブル制約に誤りがあります";

	public SQLiteTableConstraintException() {
		super(ERROR_TEXT);
	}

}
