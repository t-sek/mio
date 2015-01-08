package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.http.item.TrainingItem;
import android.util.Log;

public class TrainingXmlParser extends XmlParser {

	private static final String TITLE = "Training";
	private static final String T_ID = "TrainingID";
	private static final String DATE = "Date";
	private static final String CATEGORY = "Category";
	private static final String S_TIME = "StartTime";
	private static final String P_TIME = "PlayTime";
	private static final String T_RATE = "TargetHeartRate";
	private static final String T_CAL = "TargetCal";
	private static final String CAL = "Cal";
	private static final String AVG_RATE = "HeartRateAVG";
	private static final String DISTANCE = "Distance";

	private String tagName;
	private TrainingItem training;
	private List<TrainingItem> trainings;

	@Override
	protected void startDocument() {
		training = new TrainingItem();
		trainings = new ArrayList<TrainingItem>();
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
		if (text != null) {
			if (text.equals(TITLE)) {
				trainings.add(training);
				training = new TrainingItem();
			}
		}
	}

	@Override
	protected void text(String text) {
		if (tagName.equals(T_ID)) {
			training.setTrainingId(Integer.valueOf(text));
		} else if (tagName.equals(DATE)) {
			training.setDate(text);
		} else if (tagName.equals(CATEGORY)) {
			training.setCategoryId(Integer.valueOf(text));
		} else if (tagName.equals(S_TIME)) {
			training.setStartTime(String.valueOf(text));
		} else if (tagName.equals(P_TIME)) {
			training.setPlayTime(String.valueOf(text));
		} else if (tagName.equals(T_RATE)) {
			training.setTargetHeartRate(Integer.valueOf(text));
		} else if (tagName.equals(T_CAL)) {
			training.setTargetCal(Integer.valueOf(text));
		} else if (tagName.equals(CAL)) {
			training.setCal(Integer.valueOf(text));
		} else if (tagName.equals(AVG_RATE)) {
			training.setHeartRateAvg(Integer.valueOf(text));
		} else if (tagName.equals(DISTANCE)) {
			training.setDistance(Double.valueOf(text));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<TrainingItem> getParseObject() {
		return trainings;
	}

}
