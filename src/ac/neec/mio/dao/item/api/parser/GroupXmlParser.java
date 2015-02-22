package ac.neec.mio.dao.item.api.parser;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.consts.PermissionConstants;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.GroupFactory;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.group.Member;
import ac.neec.mio.group.MemberInfo;
import ac.neec.mio.group.Permission;
import android.util.Log;

/**
 * グループ情報XMLを解析するクラス
 *
 */
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
	public static final String SMALL_IMAGE = "small_image";
	public static final String COUNT = "Count";
	public static final String MEMBER = "menber";
	public static final String USER_TAG = "User";
	public static final String USERS_TAG = "Users";
	public static final String ERROR = "error";
	/**
	 * メンバーリスト
	 */
	private List<MemberInfo> members;
	/**
	 * タグ名
	 */
	private String tagName;
	/**
	 * グループ情報
	 */
	private GroupInfo info;
	/**
	 * 管理者ID
	 */
	private String userId;
	/**
	 * 管理者名
	 */
	private String adminName;
	/**
	 * グループID
	 */
	private String groupId;
	/**
	 * メンバーのパーミッションID
	 */
	private int permissionId;
	/**
	 * グループ名
	 */
	private String groupName;
	/**
	 * メンバー名
	 */
	private String userName;
	/**
	 * ユーザ名、管理者名
	 */
	private String name;
	/**
	 * コメント
	 */
	private String comment;
	/**
	 * 作成日
	 */
	private String created;
	/**
	 * グループアイコン名
	 */
	private String groupImage;
	/**
	 * メンバーアイコン名
	 */
	private String imageName;
	/**
	 * メンバー数
	 */
	private int count;

	@Override
	protected void startDocument() {
		members = new ArrayList<MemberInfo>();
	}

	@Override
	protected void endDocument() {
		info = new GroupInfo(groupId, groupName, comment, count, userId,
				adminName, created, members, groupImage);
	}

	@Override
	protected void startTag(String text) throws XmlParseException {
		tagName = text;
		if (text.equals(ERROR)) {
			throw new XmlParseException();
		}
	}

	@Override
	protected void endTag(String text) {
		if (text.equals(MEMBER)) {
			members.add(new MemberInfo(userId, userName, permissionId,
					imageName));
			imageName = null;
		} else if (text.equals(USER_TAG)) {
			userName = name;
		} else if (text.equals(USERS_TAG)) {
			groupImage = imageName;
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
			if (permissionId == PermissionConstants.groupAdmin()) {
				adminName = name;
			}
		} else if (tagName.equals(GROUP_NAME)) {
			groupName = text;
		} else if (tagName.equals(USER_ID)) {
			userId = text;
		} else if (tagName.equals(USER_NAME)) {
			name = text;
		} else if (tagName.equals(COMMENT)) {
			comment = text;
		} else if (tagName.equals(CREATED)) {
			created = text;
		} else if (tagName.equals(COUNT)) {
			count = Integer.valueOf(text);
		} else if (tagName.equals(SMALL_IMAGE)) {
			imageName = text;
		}
	}

	/**
	 * @return GroupInfo型
	 */
	@SuppressWarnings("unchecked")
	@Override
	public GroupInfo getParseObject() {
		return info;
	}

}
