package ac.neec.mio.dao.item.api.parser;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.training.log.TrainingLog;

/**
 * トレーニングログXMLを解析するクラス
 *
 */
public class TrainingLogXmlParser extends XmlParser {

	private static final String HEART_RATE = "Heartrate";
	private static final String TIME = "Time";
	private static final String DIS_X = "DisX";
	private static final String DIS_Y = "DisY";
	private static final String LAP = "Rap";
	private static final String SPLIT = "Sprit";
	private static final String LOG_ID = "ID";
	private static final String TARGET_HEART_RATE = "Targetrate";

	/**
	 * タグ名
	 */
	private String tagName;
	/**
	 * ログリスト
	 */
	private List<TrainingLog> logs;
	/**
	 * 心拍数
	 */
	private int heartRate;
	/**
	 * 計測時間
	 */
	private String time;
	/**
	 * 経度
	 */
	private double disX;
	/**
	 * 緯度
	 */
	private double disY;
	/**
	 * ラップタイム
	 */
	private String lap;
	/**
	 * スプリットタイム
	 */
	private String split;
	/**
	 * ログID
	 */
	private int id;
	/**
	 * 目標心拍数
	 */
	private int targetHeartRate;

	@Override
	protected void startDocument() {
		// log = new TrainingLogItem();
		logs = new ArrayList<TrainingLog>();
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
			// log.addHeartRate(Integer.valueOf(text));
			heartRate = Integer.valueOf(text);
		} else if (tagName.equals(TIME)) {
			// log.addTime(text);
			time = text;
		} else if (tagName.equals(DIS_X)) {
			// log.addDisX(Double.valueOf(text));
			disX = Double.valueOf(text);
		} else if (tagName.equals(DIS_Y)) {
			disY = Double.valueOf(text);
		} else if (tagName.equals(LAP)) {
			// log.addLap(text);
			lap = text;
		} else if (tagName.equals(SPLIT)) {
			// log.addSplit(text);
			split = text;
		} else if (tagName.equals(LOG_ID)) {
			// log.addLogId(Integer.valueOf(text));
			id = Integer.valueOf(text);
		} else if (tagName.equals(TARGET_HEART_RATE)) {
			// log.addTargetHeartRate(Integer.valueOf(text));
			targetHeartRate = Integer.valueOf(text);
		}
	}

	/**
	 * @return TrainingLog型のリスト
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<TrainingLog> getParseObject() {
		return logs;
	}

}
