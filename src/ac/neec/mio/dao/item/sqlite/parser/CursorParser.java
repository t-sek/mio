package ac.neec.mio.dao.item.sqlite.parser;

import android.database.Cursor;

public abstract class CursorParser {
	
	
	protected CursorParser(Cursor c){
		parse(c);
	}
	
	protected abstract void parse(Cursor c);
	public abstract <T> T getObject();
}
