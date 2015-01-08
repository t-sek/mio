package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.http.item.TrainingPlayItem;

public class TrainingPlayXmlParser extends XmlParser {

	private static final String TRAINING_MENU_ID = "TrainingMenuID";
	private static final String TRAINING_TIME = "TrainingTime";
//	private static final String TITLE = "PlayMenu";
//	private static final String TRAINING_ID = "TrainingID";
//	private static final String PLAY_ID = "PlayID";

	private TrainingPlayItem play;
	private List<TrainingPlayItem> plays;
	private String tagName;

	@Override
	protected void startDocument() {
		play = new TrainingPlayItem();
		plays = new ArrayList<TrainingPlayItem>();
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
			plays.add(play);
			play = new TrainingPlayItem();
		}
	}

	@Override
	protected void text(String text) {
		if (tagName.equals(TRAINING_MENU_ID)) {
			play.setTrainingMenuId(Integer.valueOf(text));
		} else if (tagName.equals(TRAINING_TIME)) {
			play.setTrainingTime(Integer.valueOf(text));
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<TrainingPlayItem> getParseObject() {
		return plays;
	}

}
