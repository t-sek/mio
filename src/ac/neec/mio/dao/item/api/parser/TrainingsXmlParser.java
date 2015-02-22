package ac.neec.mio.dao.item.api.parser;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.training.Training;
import ac.neec.mio.training.TrainingFactory;

/**
 * トレーニングリストXMLを解析するクラス
 *
 */
public class TrainingsXmlParser extends XmlParser {

	private static final String TITLE = "Training";
	private static final String T_ID = "training_id";
	private static final String DATE = "taked";
	private static final String CATEGORY_ID = "category_id";
	private static final String USER_ID = "user_id";
	private static final String S_TIME = "start_time";
	private static final String P_TIME = "play_time";
	private static final String T_RATE = "target_heartrate_beats";
	private static final String T_CAL = "target_cal";
	private static final String TARGET_PLAY_TIME = "target_play_time";
	private static final String CAL = "cal";
	private static final String AVG_RATE = "heartrate_avg";
	private static final String DISTANCE = "distance";

	/**
	 * タグ名
	 */
	private String tagName;
	/**
	 * トレーニングリスト
	 */
	private List<Training> trainings;
	/**
	 * Trainingクラスを生成するファクトリークラス
	 */
	private ProductDataFactory factory;
	/**
	 * トレーニングID
	 */
	private int trainingId;
	/**
	 * トレーニング実施日
	 */
	private String date;
	/**
	 * カテゴリーID
	 */
	private int categoryId;
	/**
	 * ユーザID
	 */
	private String userId;
	/**
	 * 開始時間
	 */
	private String startTime;
	/**
	 * 計測時間
	 */
	private String playTime;
	/**
	 * 目標心拍数
	 */
	private int targetHeartRate;
	/**
	 * 目標カロリー
	 */
	private int targetCalorie;
	/**
	 * 目標運動時間
	 */
	private int targetPlayTime;
	/**
	 * 消費カロリー
	 */
	private int calorie;
	/**
	 * 平均心拍数
	 */
	private int heartRateAvg;
	/**
	 * 走行距離
	 */
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
						userId, date, startTime, playTime, targetHeartRate, 0,
						calorie, heartRateAvg, 0, distance));
			}
		}
	}

	@Override
	protected void text(String text) {
		if (tagName.equals(T_ID)) {
			trainingId = Integer.valueOf(text);
		} else if (tagName.equals(DATE)) {
			date = text;
		} else if (tagName.equals(CATEGORY_ID)) {
			categoryId = Integer.valueOf(text);
		} else if (tagName.equals(USER_ID)) {
			userId = text;
		} else if (tagName.equals(S_TIME)) {
			startTime = text;
		} else if (tagName.equals(P_TIME)) {
			playTime = text;
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

	/**
	 * @return Training型のリスト
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<Training> getParseObject() {
		return trainings;
	}

}
