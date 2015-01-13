package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import android.util.Log;

public class PrintXmlParser extends XmlParser {

	private static final String TAG = "PrintXmlParser";

	@Override
	protected void startDocument() {
		Log.d(TAG, "---------- XML FILE ----------");
	}

	@Override
	protected void endDocument() {
		Log.d(TAG, "---------- XML FILE ----------");
	}

	@Override
	protected void startTag(String text) {
		Log.d(TAG, " < " + text + " > ");
	}

	@Override
	protected void endTag(String text) {
		Log.d(TAG, " </ " + text + " > ");
	}

	@Override
	protected void text(String text) {
		Log.d(TAG, " " + text);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object getParseObject() {
		return null;
	}

}
