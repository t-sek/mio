package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.group.Member;

public class GroupXmlParser extends XmlParser {

	public static final String GROUP = "Group";
	public static final String GROUP_ID = "group_id";
	public static final String USER_ID = "user_id";
	public static final String PERMITION_ID = "permition_id";
	public static final String PERMITION_NAME = "permition_name";
	public static final String GROUP_NAME = "group_name";
	public static final String USER_NAME = "name";
	public static final String COMMENT = "group_comment";
	public static final String COUNT = "Count";
	public static final String MEMBER = "menber";

	private List<Member> members;
	private String tagName;
	private GroupInfo info;
	private String userId;
	private String groupId;
	private int permitionId;
	private String groupName;
	private String userName;
	private String comment;
	private int count;

	@Override
	protected void startDocument() {
		members = new ArrayList<Member>();
	}

	@Override
	protected void endDocument() {
		info = new GroupInfo(groupId, groupName, comment, count, members);
	}

	@Override
	protected void startTag(String text) {
		tagName = text;
	}

	@Override
	protected void endTag(String text) {
		if (text.equals(MEMBER)) {
			members.add(new Member(userId, userName, groupId, permitionId));
		}
	}

	@Override
	protected void text(String text) {
		if (tagName.equals(USER_ID)) {
			userId = text;
		} else if (tagName.equals(GROUP_ID)) {
			groupId = text;
		} else if (tagName.equals(PERMITION_ID)) {
			permitionId = Integer.valueOf(text);
		} else if (tagName.equals(GROUP_NAME)) {
			groupName = text;
		} else if (tagName.equals(USER_NAME)) {
			userName = text;
		} else if (tagName.equals(COMMENT)) {
			comment = text;
		} else if (tagName.equals(COUNT)) {
			count = Integer.valueOf(text);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public GroupInfo getParseObject() {
		return info;
	}

}
