package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.http.item.DateNumItem;

public class TrainingDateNumXmlParser extends XmlParser {

	private static final String NUM = "Num";

	private String date;
	private String tagName;
	private DateNumItem item;

	@Override
	protected void startDocument() {
	}

	@Override
	protected void endDocument() {
	}

	@Override
	protected void startTag(String text) {
		tagName = text;
	}

	@Override
	protected void endTag(String text) {
	}

	@Override
	protected void text(String text) {
		if (tagName.equals(NUM)) {
			item = new DateNumItem(date, Integer.valueOf(text));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected DateNumItem getParseObject() {
		return item;
	}

}
