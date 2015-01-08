package ac.neec.mio.dao.item.api.parser;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupFactory;
import ac.neec.mio.group.Permission;
import ac.neec.mio.training.framework.ProductDataFactory;
import ac.neec.mio.user.UserInfo;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.user.gender.GenderFactory;
import android.content.Context;
import android.util.Log;

public class UserInfoXmlParser extends XmlParser {

	private static final String USER_ID = "username";
	private static final String NAME = "name";
	private static final String BIRTH = "age";
	private static final String HEIGHT = "height";
	private static final String WEIGHT = "weight";
	private static final String MAIL = "mail";
	private static final String AFF = "Affiliation";
	private static final String GROUP_ID = "group_id";
	private static final String PERMITION_ID = "permition_id";
	private static final String GROUP = "Group";
	private static final String GROUP_NAME = "group_name";
	private static final String GROUP_COMMENT = "group_comment";
	private static final String GROUP_CREATED = "created";

	// private static final String GENDER = "sex";
	// private static final String GROUP_ID = "group_id";
	private Context context;
	private UserInfo user;
	private String tagName;
	private String name;
	private String userId;
	private String birth;
	private float height;
	private float weight;
	private String mail;
	private String groupId;
	private String groupName;
	private String groupComment;
	private String groupCreated;
	private Permission permission;
	private List<Affiliation> affiliations;
	private List<Group> groups;
	private ProductDataFactory genderFactory;
	private ProductDataFactory groupFactory;
	private SQLiteDao dao;

	public UserInfoXmlParser() {
	}

	public UserInfoXmlParser(Context context) {
		this.context = context;
	}

	@Override
	protected void startDocument() {
		genderFactory = new GenderFactory();
		groupFactory = new GroupFactory();
		affiliations = new ArrayList<Affiliation>();
		groups = new ArrayList<Group>();
		dao = DaoFacade.getSQLiteDao(context);
	}

	@Override
	protected void endDocument() {
		user = new UserInfo(affiliations, groups, userId, name, birth,
				(Gender) genderFactory.create(Gender.MALE), height, weight,
				mail);
	}

	@Override
	protected void startTag(String text) {
		tagName = text;
	}

	@Override
	protected void endTag(String text) {
		if (text.equals(AFF)) {
			affiliations.add((Affiliation) groupFactory.create(userId, groupId,
					permission));
		} else if (text.equals(GROUP)) {
			groups.add((Group) new Group(groupId, groupName, null,
					groupComment, groupCreated));
		}
	}

	@Override
	protected void text(String text) {
		if (tagName.equals(USER_ID)) {
			userId = text;
		} else if (tagName.equals(NAME)) {
			name = text;
		} else if (tagName.equals(BIRTH)) {
			birth = text;
		} else if (tagName.equals(HEIGHT)) {
			height = Float.valueOf(text);
		} else if (tagName.equals(WEIGHT)) {
			weight = Float.valueOf(text);
		} else if (tagName.equals(MAIL)) {
			mail = text;
		} else if (tagName.equals(GROUP_ID)) {
			groupId = text;
		} else if (tagName.equals(PERMITION_ID)) {
			int permissionId = Integer.valueOf(text);
			permission = dao.selectPermission(permissionId);
		} else if (tagName.equals(GROUP_COMMENT)) {
			groupComment = text;
		} else if (tagName.equals(GROUP_NAME)) {
			groupName = text;
		} else if (tagName.equals(GROUP_CREATED)) {
			groupCreated = text;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public UserInfo getParseObject() {
		return user;
	}

}
