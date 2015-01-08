package ac.neec.mio.exception;

import android.database.SQLException;

/**
 * データベースのインサートに失敗しました
 */
public class SQLiteInsertException extends Exception{

	private static final long serialVersionUID = 1L;
	private static final String ERROR_TEXT = "データベースのインサートに失敗しました";
	
	public SQLiteInsertException(){
		super(ERROR_TEXT);
	}

}
