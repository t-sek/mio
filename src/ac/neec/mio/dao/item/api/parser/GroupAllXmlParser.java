package ac.neec.mio.dao.item.api.parser;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.group.Group;
import android.util.Log;

/**
 * 全グループXMLを解析するクラス
 *
 */
public class GroupAllXmlParser extends XmlParser {

	private static final String GROUP = "Groups";
	private static final String ID = "group_id";
	private static final String NAME = "group_name";
	private static final String COMMENT = "group_comment";
	private static final String USER_ID = "user_id";
	private static final String CREATED = "created";
	private static final String IMAGE_NAME = "small_image";
	private static final String ADMIN_NAME = "name";

	/**
	 * グループリスト
	 */
	private List<Group> list;
	/**
	 * タグ名
	 */
	private String tagName;
	/**
	 * グループID
	 */
	private String id;
	/**
	 * グループ名
	 */
	private String name;
	/**
	 * コメント
	 */
	private String comment;
	/**
	 * 管理者ID
	 */
	private String userId;
	/**
	 * 作成日
	 */
	private String created;
	/**
	 * アイコン名
	 */
	private String imageName;
	/**
	 * 管理者名
	 */
	private String adminName;

	@Override
	protected void startDocument() {
		list = new ArrayList<Group>();
	}

	@Override
	protected void endDocument() {
		list.remove(list.size() - 1);
	}

	@Override
	protected void startTag(String text) {
		tagName = text;
	}

	@Override
	protected void endTag(String text) {
		if (text.equals(GROUP)) {
			list.add(new Group(id, name, imageName, comment, userId, adminName,
					created));
			imageName = null;
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
		} else if (tagName.equals(USER_ID)) {
			userId = text;
		} else if (tagName.equals(CREATED)) {
			created = text;
		} else if (tagName.equals(IMAGE_NAME)) {
			imageName = text;
		} else if (tagName.equals(ADMIN_NAME)) {
			adminName = text;
		}
	}

	/**
	 * @return Group型のリスト
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Group> getParseObject() {
		return list;
	}

}
