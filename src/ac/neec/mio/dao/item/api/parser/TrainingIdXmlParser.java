package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import android.util.Log;

public class TrainingIdXmlParser extends XmlParser {

	private static final String TRAINING_ID = "training_id";

	private int trainingId;
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
		if (tagName.equals(TRAINING_ID)) {
			trainingId = Integer.valueOf(text);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Integer getParseObject() {
		Log.d("parser", "trainingId " + trainingId);
		return trainingId;
	}

}
