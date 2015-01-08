package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;

public class UserWeightXmlParser<T> extends XmlParser {

	private static final String WEIGHT = "weight";

	private float weight;
	private String tagName;

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
		if (tagName.equals(WEIGHT)) {
			weight = Float.valueOf(text);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Float getParseObject() {
		return weight;
	}

}
