package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.training.menu.TrainingMenu;
import ac.neec.mio.training.menu.TrainingMenuFactory;

/**
 * トレーニングメニューXMLを解析するクラス
 *
 */
public class TrainingMenuXmlParser extends XmlParser {

	private static final String MENU_ID = "training_menu_id";
	private static final String METS = "mets";
	private static final String NAME = "training_name";
	private static final String CATEGORY_ID = "category_id";
	private static final String COLOR = "color";

	/**
	 * メニューリスト
	 */
	private List<TrainingMenu> menu;
	/**
	 * メニューID
	 */
	private int menuId;
	/**
	 * メッツ
	 */
	private float mets;
	/**
	 * メニュー名
	 */
	private String menuName;
	/**
	 * カテゴリー名
	 */
	private int categoryId;
	/**
	 * カラーコード
	 */
	private String color;
	/**
	 * TrainingMenuクラスを生成するファクトリークラス
	 */
	private ProductDataFactory factory;
	/**
	 * タグ名
	 */
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

	/**
	 * @return TrainingMenu型のリスト
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<TrainingMenu> getParseObject() {
		return menu;
	}

}
