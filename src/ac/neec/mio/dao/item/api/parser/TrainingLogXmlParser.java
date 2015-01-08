package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.http.item.TrainingLogItem;

public class TrainingLogXmlParser extends XmlParser {

	private static final String HEART_RATE = "Heartrate";
	private static final String TIME = "Time";
	private static final String DIS_X = "DisX";
	private static final String DIS_Y = "DisY";
	private static final String LAP = "Rap";
	private static final String SPLIT = "Sprit";
	private static final String LOG_ID = "ID";
	private static final String TARGET_HEART_RATE = "Targetrate";

	private String tagName;
	private TrainingLogItem log;

	@Override
	protected void startDocument() {
		log = new TrainingLogItem();
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
		if (tagName.equals(HEART_RATE)) {
			log.addHeartRate(Integer.valueOf(text));
		} else if (tagName.equals(TIME)) {
			log.addTime(text);
		} else if (tagName.equals(DIS_X)) {
			log.addDisX(Double.valueOf(text));
		} else if (tagName.equals(DIS_Y)) {
			log.addDisY(Double.valueOf(text));
		} else if (tagName.equals(LAP)) {
			log.addLap(text);
		} else if (tagName.equals(SPLIT)) {
			log.addSplit(text);
		} else if (tagName.equals(LOG_ID)) {
			log.addLogId(Integer.valueOf(text));
		} else if (tagName.equals(TARGET_HEART_RATE)) {
			log.addTargetHeartRate(Integer.valueOf(text));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected TrainingLogItem getParseObject() {
		return log;
	}

}
