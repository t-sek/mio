package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.taining.category.TrainingCategory;
import ac.neec.mio.taining.category.TrainingCategoryFactory;
import ac.neec.mio.training.framework.ProductDataFactory;

public class TrainingCategoryXmlParser extends XmlParser {

	public static final String ID = "CategoryID";
	public static final String NAME = "CategoryName";

	private String tagName;
	private int categoryId;
	private String categoryName;
	private ProductDataFactory factory;
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

	@SuppressWarnings("unchecked")
	@Override
	protected List<TrainingCategory> getParseObject() {
		return category;
	}

}
