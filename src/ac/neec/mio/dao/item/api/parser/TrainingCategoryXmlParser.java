package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.training.category.TrainingCategory;
import ac.neec.mio.training.category.TrainingCategoryFactory;
import android.util.Log;

/**
 * トレーニングカテゴリーXMLを解析するクラス
 *
 */
public class TrainingCategoryXmlParser extends XmlParser {

	public static final String ID = "category_id";
	public static final String NAME = "category_name";

	/**
	 * タグ名
	 */
	private String tagName;
	/**
	 * カテゴリー名
	 */
	private int categoryId;
	/**
	 * カテゴリー名
	 */
	private String categoryName;
	/**
	 * TrainingCategoryクラス生成ファクトリークラス
	 */
	private ProductDataFactory factory;
	/**
	 * カテゴリーリスト
	 */
	private List<TrainingCategory> category;

	@Override
	protected void startDocument() {
		factory = new TrainingCategoryFactory();
		category = new ArrayList<TrainingCategory>();
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
		if (text.equals(NAME)) {
			category.add((TrainingCategory) factory.create(categoryId,
					categoryName));
		}
	}

	@Override
	protected void text(String text) {
		if (tagName.equals(ID)) {
			categoryId = Integer.valueOf(text);
		} else if (tagName.equals(NAME)) {
			categoryName = text;
		}
	}

	/**
	 * @return TrainingCategory型のリスト
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected List<TrainingCategory> getParseObject() {
		return category;
	}

}
