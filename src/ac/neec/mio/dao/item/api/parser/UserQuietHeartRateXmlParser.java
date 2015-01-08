package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;

public class UserQuietHeartRateXmlParser extends XmlParser {

	private static final String QUIET_HEART_RATE = "quiet_heartrate";
	
	private String tagName;
	private int quietHeartRate;

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
		if (tagName.equals(QUIET_HEART_RATE)) {
			quietHeartRate = Integer.valueOf(text);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Integer getParseObject() {
		return quietHeartRate;
	}

}
