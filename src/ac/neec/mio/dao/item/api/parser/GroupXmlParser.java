package ac.neec.mio.dao.item.api.parser;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.GroupFactory;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.group.Member;
import ac.neec.mio.group.Permission;
import ac.neec.mio.training.framework.ProductDataFactory;

public class GroupXmlParser extends XmlParser {

	public static final String GROUP = "Group";
	public static final String GROUP_ID = "group_id";
	public static final String USER_ID = "user_id";
	public static final String PERMITION_ID = "permition_id";
	public static final String PERMITION_NAME = "permition_name";
	public static final String GROUP_NAME = "group_name";
	public static final String USER_NAME = "name";
	public static final String COMMENT = "group_comment";
	public static final String CREATED = "created";
	public static final String COUNT = "Count";
	public static final String MEMBER = "menber";
	private List<Member> members;
	private String tagName;
	private GroupInfo info;
	private String userId;
	private String groupId;
	private int permissionId;
	private String groupName;
	private String userName;
	private String comment;
	private String created;
	private int count;
	private SQLiteDao daoSql = DaoFacade.getSQLiteDao();
	private ProductDataFactory affFactory = new GroupFactory();

	@Override
	protected void startDocument() {
		members = new ArrayList<Member>();
	}

	@Override
	protected void endDocument() {
		info = new GroupInfo(groupId, groupName, comment, count, userId,
				created, members);
	}

	@Override
	protected void startTag(String text) {
		tagName = text;
	}

	@Override
	protected void endTag(String text) {
		if (text.equals(MEMBER)) {
			Permission permission = daoSql.selectPermission(permissionId);
			Affiliation affiliation = (Affiliation) affFactory.create(userId,
					groupId, permission);
			members.add(new Member(userName, affiliation));
		}
	}

	@Override
	protected void text(String text) {
		if (tagName.equals(USER_ID)) {
			userId = text;
		} else if (tagName.equals(GROUP_ID)) {
			groupId = text;
		} else if (tagName.equals(PERMITION_ID)) {
			permissionId = Integer.valueOf(text);
		} else if (tagName.equals(GROUP_NAME)) {
			groupName = text;
		} else if (tagName.equals(USER_ID)) {
			userId = text;
		} else if (tagName.equals(USER_NAME)) {
			userName = text;
		} else if (tagName.equals(COMMENT)) {
			comment = text;
		} else if (tagName.equals(CREATED)) {
			created = text;
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
