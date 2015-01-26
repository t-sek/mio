package ac.neec.mio.dao.item.api.parser;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.http.item.TrainingItem;
import ac.neec.mio.taining.Training;
import ac.neec.mio.taining.TrainingFactory;
import ac.neec.mio.training.framework.ProductDataFactory;

public class TrainingsXmlParser extends XmlParser {

	private static final String TITLE = "Training";
	private static final String T_ID = "training_id";
	private static final String DATE = "taked";
	private static final String CATEGORY = "category_id";
	private static final String USER_ID = "user_id";
	private static final String S_TIME = "start_time";
	private static final String P_TIME = "play_time";
	private static final String T_RATE = "target_heartrate_beats";
	private static final String T_CAL = "target_cal";
	private static final String TARGET_PLAY_TIME = "target_play_time";
	private static final String CAL = "cal";
	private static final String AVG_RATE = "heartrate_avg";
	private static final String DISTANCE = "distance";

	private String tagName;
	private List<Training> trainings;
	private ProductDataFactory factory;
	private int trainingId;
	private String date;
	private int categoryId;
	private String userId;
	private String startTime;
	private int playTime;
	private int targetHeartRate;
	private int targetCalorie;
	private int targetPlayTime;
	private int calorie;
	private int heartRateAvg;
	private double distance;

	@Override
	protected void startDocument() {
		trainings = new ArrayList<Training>();
		factory = new TrainingFactory();
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
				trainings.add((Training) factory.create(trainingId, categoryId,
						userId, date, date, startTime, playTime,
						targetHeartRate, calorie, heartRateAvg, 0, distance));
			}
		}
	}

	@Override
	protected void text(String text) {
		if (tagName.equals(T_ID)) {
			trainingId = Integer.valueOf(text);
		} else if (tagName.equals(DATE)) {
			date = text;
		} else if (tagName.equals(CATEGORY)) {
			categoryId = Integer.valueOf(categoryId);
		} else if (tagName.equals(USER_ID)) {
			userId = text;
		} else if (tagName.equals(S_TIME)) {
			startTime = text;
		} else if (tagName.equals(P_TIME)) {
			playTime = Integer.valueOf(text);
		} else if (tagName.equals(T_RATE)) {
			targetHeartRate = Integer.valueOf(text);
		} else if (tagName.equals(T_CAL)) {
			targetCalorie = Integer.valueOf(text);
		} else if (tagName.equals(CAL)) {
			calorie = Integer.valueOf(text);
		} else if (tagName.equals(AVG_RATE)) {
			heartRateAvg = Integer.valueOf(text);
		} else if (tagName.equals(DISTANCE)) {
			distance = Double.valueOf(text);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<Training> getParseObject() {
		return trainings;
	}

}
