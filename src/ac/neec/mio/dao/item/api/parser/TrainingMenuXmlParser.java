package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.taining.menu.TrainingMenu;
import ac.neec.mio.taining.menu.TrainingMenuFactory;
import ac.neec.mio.training.framework.ProductDataFactory;

public class TrainingMenuXmlParser extends XmlParser {

	private static final String MENU_ID = "TrainingMenuID";
	private static final String METS = "Mets";
	private static final String NAME = "TrainingName";
	private static final String CATEGORY_ID = "CategoryID";
	private static final String COLOR = "Color";

	private List<TrainingMenu> menu;
	private int menuId;
	private float mets;
	private String menuName;
	private int categoryId;
	private String color;
	private ProductDataFactory factory;

	private String tagName;

	@Override
	protected void startDocument() {
		menu = new ArrayList<TrainingMenu>();
		factory = new TrainingMenuFactory();
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
		if (text.equals(COLOR)) {
			menu.add((TrainingMenu) factory.create(menuId, menuName, mets,
					categoryId, color));
		}
	}

	@Override
	protected void text(String text) {
		if (tagName.equals(CATEGORY_ID)) {
			categoryId = Integer.valueOf(text);
		} else if (tagName.equals(NAME)) {
			menuName = text;
		} else if (tagName.equals(METS)) {
			mets = Float.valueOf(text);
		} else if (tagName.equals(MENU_ID)) {
			menuId = Integer.valueOf(text);
		} else if (tagName.equals(COLOR)) {
			color = text;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<TrainingMenu> getParseObject() {
		return menu;
	}

}
