package ac.neec.mio.dao.item.api.parser;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Group;
import ac.neec.mio.group.GroupFactory;
import ac.neec.mio.group.Permission;
import ac.neec.mio.image.ImageInfo;
import ac.neec.mio.image.ImageInfoFactory;
import ac.neec.mio.training.framework.ProductDataFactory;
import ac.neec.mio.user.UserInfo;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.user.gender.GenderFactory;
import ac.neec.mio.user.role.Role;
import ac.neec.mio.user.role.RoleFactory;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class UserInfoXmlParser extends XmlParser {

	private static final String USER_TAG = "User";
	private static final String USER_ID = "username";
	private static final String NAME = "name";
	private static final String BIRTH = "age";
	private static final String HEIGHT = "height";
	private static final String WEIGHT = "weight";
	private static final String MAIL = "mail";
	private static final String AFF_TAG = "Affiliation";
	private static final String GROUP_ID = "group_id";
	private static final String PERMITION_ID = "permition_id";
	private static final String GROUP_TAG = "Group";
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
	private String created;
	private int id;
	private String imageFileName;
	private String image;
	private String bigImage;
	private String smallImage;
	private String thumbImage;
	private ImageInfo imageInfo;
	private String updated;
	private int status;
	private Role role;
	private Permission permission;
	private List<Affiliation> affiliations;
	private List<Group> groups;
	private ProductDataFactory genderFactory;
	private ProductDataFactory groupFactory;
	private ProductDataFactory imageFactory;
	private ProductDataFactory roleFactory;
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
		imageFactory = new ImageInfoFactory();
		roleFactory = new RoleFactory();
		affiliations = new ArrayList<Affiliation>();
		groups = new ArrayList<Group>();
		dao = DaoFacade.getSQLiteDao(context);
	}

	@Override
	protected void endDocument() {
		user.setGroups(groups);
		user.setAffiliations(affiliations);
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
					groupComment, created));
			created = null;
		} else if (text.equals(IMAGE_TAG)) {
			// imageInfo = (ImageInfo) imageFactory.create(id, imageFileName,
			// userId, groupId, created, image, bigImage, smallImage,
			// thumbImage);
			user.setImageInfo((ImageInfo) imageFactory.create(id,
					imageFileName, userId, groupId, created, image, bigImage,
					smallImage, thumbImage));
			created = null;
		} else if (text.equals(ROLE_TAG)) {
			// role = (Role) roleFactory
			// .create(id, name, created, updated, status);
			user.setRole((Role) roleFactory.create(id, name, created, updated,
					status));
			name = null;
			created = null;
			updated = null;
		} else if (text.equals(USER_TAG)) {
			user = new UserInfo(affiliations, groups, userId, name, birth,
					(Gender) genderFactory.create(Gender.MALE), height, weight,
					mail, imageInfo, role);
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

	@SuppressWarnings("unchecked")
	@Override
	public UserInfo getParseObject() {
		return user;
	}

}
