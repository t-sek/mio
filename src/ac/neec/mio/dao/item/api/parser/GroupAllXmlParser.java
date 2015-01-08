package ac.neec.mio.dao.item.api.parser;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.group.Group;

public class GroupAllXmlParser extends XmlParser {

	private static final String GROUP = "Group";
	private static final String ID = "group_id";
	private static final String NAME = "group_name";
	private static final String COMMENT = "group_comment";
	private static final String CREATED = "created";

	private List<Group> list;
	private String tagName;
	private String id;
	private String name;
	private String comment;
	private String created;

	@Override
	protected void startDocument() {
		list = new ArrayList<Group>();
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
		if (text.equals(GROUP)) {
			list.add(new Group(id, name, null, comment, created));
		}
	}

	@Override
	protected void text(String text) {
		if (tagName.equals(ID)) {
			id = text;
		} else if (tagName.equals(NAME)) {
			name = text;
		} else if (tagName.equals(COMMENT)) {
			comment = text;
		} else if (tagName.equals(CREATED)) {
			created = text;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getParseObject() {
		return list;
	}

}
