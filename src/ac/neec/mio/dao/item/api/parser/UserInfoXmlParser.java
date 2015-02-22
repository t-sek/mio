package ac.neec.mio.dao.item.api.parser;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupFactory;
import ac.neec.mio.group.Permission;
import ac.neec.mio.image.ImageInfo;
import ac.neec.mio.image.ImageInfoFactory;
import ac.neec.mio.user.UserInfo;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.user.gender.GenderFactory;
import ac.neec.mio.user.role.Role;
import ac.neec.mio.user.role.RoleFactory;

/**
 * ユーザ情報XMLを解析するクラス
 *
 */
public class UserInfoXmlParser extends XmlParser {

	private static final String USER_TAG = "User";
	private static final String WEIGHT_TAG = "Weight";
	private static final String USER_ID = "username";
	private static final String NAME = "name";
	private static final String BIRTH = "age";
	private static final String HEIGHT = "height";
	private static final String WEIGHT = "weight";
	private static final String MAIL = "mail";
	private static final String AFF_TAG = "Affiliation";
	private static final String GROUP_ID = "group_id";
	private static final String PERMITION_ID = "permition_id";
	private static final String GROUP_TAG = "Groups";
	private static final String GROUP_NAME = "group_name";
	private static final String GROUP_COMMENT = "group_comment";
	private static final String CREATED = "created";
	private static final String IMAGE_TAG = "Image";
	private static final String ID = "id";
	private static final String IMAGE_FILE_NAME = "img_file_name";
	private static final String IMAGE = "image";
	private static final String BIG_IMAGE = "big_image";
	private static final String SMALL_IMAGE = "small_image";
	private static final String THUMB_IMAGE = "thumb_image";
	private static final String ROLE_TAG = "Role";
	private static final String UPDATED = "updated";
	private static final String STATUS = "status";

	/**
	 * ユーザ情報
	 */
	private UserInfo user;
	/**
	 * タグ名
	 */
	private String tagName;
	/**
	 * ユーザ名
	 */
	private String name;
	/**
	 * ユーザID
	 */
	private String userId;
	/**
	 * 生年月日
	 */
	private String birth;
	/**
	 * 身長
	 */
	private float height;
	/**
	 * 体重
	 */
	private float weight;
	/**
	 * メールアドレス
	 */
	private String mail;
	/**
	 * 所属グループID
	 */
	private String groupId;
	/**
	 * 所属グループ名
	 */
	private String groupName;
	/**
	 * 所属グループコメント
	 */
	private String groupComment;
	/**
	 * アカウント作成日
	 */
	private String created;
	/**
	 * ID
	 */
	private int id;
	/**
	 * アイコン名
	 */
	private String imageFileName;
	/**
	 * アイコン名
	 */
	private String image;
	/**
	 * 大アイコン名
	 */
	private String bigImage;
	/**
	 * 小アイコン名
	 */
	private String smallImage;
	/**
	 * オリジナルアイコン名
	 */
	private String thumbImage;
	/**
	 * アイコン情報
	 */
	private ImageInfo imageInfo;
	/**
	 * プロフィール更新日
	 */
	private String updated;
	/**
	 * 所属グループ権限ID
	 */
	private int permissionId;
	/**
	 * アカウントステータス
	 */
	private int status;
	/**
	 * ロール
	 */
	private Role role;
	/**
	 * 所属グループ権限
	 */
	private Permission permission;
	/**
	 * 所属グループ権限リスト
	 */
	private List<Affiliation> affiliations;
	/**
	 * 所属グループリスト
	 */
	private List<Group> groups;
	/**
	 * Genderクラスを生成するファクトリークラス
	 */
	private ProductDataFactory genderFactory;
	/**
	 * Groupクラスを生成するファクトリークラス
	 */
	private ProductDataFactory groupFactory;
	/**
	 * ImageInfoクラスを生成するファクトリークラス
	 */
	private ProductDataFactory imageFactory;
	/**
	 * Roleクラスを生成するファクトリークラス
	 */
	private ProductDataFactory roleFactory;
	/**
	 * ローカルデータベースにアクセスするためのインスタンス
	 */
	private SQLiteDao dao;

	@Override
	protected void startDocument() {
		genderFactory = new GenderFactory();
		groupFactory = new GroupFactory();
		imageFactory = new ImageInfoFactory();
		roleFactory = new RoleFactory();
		affiliations = new ArrayList<Affiliation>();
		groups = new ArrayList<Group>();
		dao = DaoFacade.getSQLiteDao();
	}

	@Override
	protected void endDocument() throws XmlParseException {
		try {
			user.setGroups(groups);
			user.setAffiliations(affiliations);
		} catch (NullPointerException e) {
			throw new XmlParseException();
		}
	}

	@Override
	protected void startTag(String text) {
		tagName = text;
	}

	@Override
	protected void endTag(String text) {
		if (text.equals(AFF_TAG)) {
			affiliations.add((Affiliation) groupFactory.create(userId, groupId,
					permission));
		} else if (text.equals(GROUP_TAG)) {
			groups.add((Group) new Group(groupId, groupName, null,
					groupComment, userId, created, permissionId));
			created = null;
		} else if (text.equals(IMAGE_TAG)) {
			if (user.getImageInfo() == null) {
				user.setImageInfo((ImageInfo) imageFactory.create(id,
						imageFileName, userId, groupId, created, image,
						bigImage, smallImage, thumbImage));
				created = null;
			}
		} else if (text.equals(ROLE_TAG)) {
			user.setRole((Role) roleFactory.create(id, name, created, updated,
					status));
			name = null;
			created = null;
			updated = null;
		} else if (text.equals(USER_TAG)) {
			user = new UserInfo(affiliations, groups, userId, name, birth,
					(Gender) genderFactory.create(Gender.MALE), height, weight,
					mail, imageInfo, role);
		} else if (text.equals(WEIGHT_TAG)) {
			user.setWeight(weight);
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
			permissionId = Integer.valueOf(text);
			permission = dao.selectPermission(permissionId);
		} else if (tagName.equals(GROUP_COMMENT)) {
			groupComment = text;
		} else if (tagName.equals(GROUP_NAME)) {
			groupName = text;
		} else if (tagName.equals(CREATED)) {
			created = text;
		} else if (tagName.equals(ID)) {
			id = Integer.valueOf(text);
		} else if (tagName.equals(IMAGE_FILE_NAME)) {
			imageFileName = text;
		} else if (tagName.equals(IMAGE)) {
			image = text;
		} else if (tagName.equals(BIG_IMAGE)) {
			bigImage = text;
		} else if (tagName.equals(SMALL_IMAGE)) {
			smallImage = text;
		} else if (tagName.equals(THUMB_IMAGE)) {
			thumbImage = text;
		} else if (tagName.equals(UPDATED)) {
			updated = text;
		} else if (tagName.equals(STATUS)) {
			status = Integer.valueOf(text);
		} else if (tagName.equals(UPDATED)) {
			updated = text;
		} else if (tagName.equals(status)) {
			status = Integer.valueOf(text);
		}
	}

	/**
	 * @return UserInfo型
	 */
	@SuppressWarnings("unchecked")
	@Override
	public UserInfo getParseObject() {
		return user;
	}

}
