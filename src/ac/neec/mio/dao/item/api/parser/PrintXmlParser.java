package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import android.util.Log;

public class PrintXmlParser extends XmlParser {

	private static final String TAG = "PrintXmlParser";

	@Override
	protected void startDocument() {
		Log.d(TAG, "startDocument");
	}

	@Override
	protected void endDocument() {
		Log.d(TAG, "endDocument");
	}

	@Override
	protected void startTag(String text) {
		Log.d(TAG, "startTag : " + text);
	}

	@Override
	protected void endTag(String text) {
		Log.d(TAG, "endTag : " + text);
	}

	@Override
	protected void text(String text) {
		Log.d(TAG, "text : " + text);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Object getParseObject() {
		return null;
	}

}
