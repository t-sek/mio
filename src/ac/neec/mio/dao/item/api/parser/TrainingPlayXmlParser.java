package ac.neec.mio.dao.item.api.parser;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.training.play.TrainingPlay;
import ac.neec.mio.training.play.TrainingPlayFactory;

/**
 * トレーニングプレイXMLを解析するクラス
 *
 */
public class TrainingPlayXmlParser extends XmlParser {

	private static final String TRAINING_MENU_ID = "training_menu_id";
	private static final String TRAINING_TIME = "training_time";

	/**
	 * プレイリスト
	 */
	private List<TrainingPlay> plays;
	/**
	 * TrainingPlayクラスを生成するファクトリークラス
	 */
	private ProductDataFactory factory;
	/**
	 * タグ名
	 */
	private String tagName;
	/**
	 * メニューID
	 */
	private int trainingMenuId;
	/**
	 * メニュー名
	 */
	private int trainingTime;

	@Override
	protected void startDocument() {
		plays = new ArrayList<TrainingPlay>();
		factory = new TrainingPlayFactory();
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
		if (text.equals(TRAINING_TIME)) {
			plays.add((TrainingPlay) factory.create(trainingMenuId,
					trainingTime));
		}
	}

	@Override
	protected void text(String text) {
		if (tagName.equals(TRAINING_MENU_ID)) {
			trainingMenuId = Integer.valueOf(text);
		} else if (tagName.equals(TRAINING_TIME)) {
			trainingTime = Integer.valueOf(text);
		}
	}

	/**
	 * @return TrainingPlay型のリスト
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<TrainingPlay> getParseObject() {
		return plays;
	}

}
