package ac.neec.mio.user;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Group;
import ac.neec.mio.image.ImageInfo;
import ac.neec.mio.training.framework.ProductDataFactory;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.user.gender.GenderFactory;
import ac.neec.mio.user.role.Role;
import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo implements Parcelable {

	private List<Affiliation> affiliations = new ArrayList<Affiliation>();
	private List<Group> groups = new ArrayList<Group>();
	private String userId;
	private String name;
	private String birth;
	private Gender gender;
	private float height;
	private float weight;
	private String mail;
	private ImageInfo image;
	private Role role;

	public UserInfo(List<Affiliation> affiliations, List<Group> groups,
			String userId, String name, String birth, Gender gender,
			float height, float weight, String mail, ImageInfo image, Role role) {
		this.affiliations = affiliations;
		this.groups = groups;
		this.userId = userId;
		this.name = name;
		this.birth = birth;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.mail = mail;
		this.image = image;
		this.role = role;
	}

	public UserInfo(final Parcel in) {
		userId = in.readString();
		name = in.readString();
		birth = in.readString();
		gender = createGender(in.readString());
		height = in.readFloat();
		weight = in.readFloat();
		mail = in.readString();
	}

	private Gender createGender(String gender) {
		ProductDataFactory factory = new GenderFactory();
		return (Gender) factory.create(gender);

	}

	public List<Affiliation> getAffiliations() {
		return affiliations;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public String getUserId() {
		return userId;
	}

	public String getName() {
		return name;
	}

	public String getBirth() {
		return birth;
	}

	public Gender getGender() {
		return gender;
	}

	public float getHeight() {
		return height;
	}

	public float getWeight() {
		return weight;
	}

	public String getMail() {
		return mail;
	}

	public ImageInfo getImageInfo() {
		return image;
	}

	public Role getRole() {
		return role;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(userId);
		dest.writeString(name);
		dest.writeString(birth);
		dest.writeString(mail);
		dest.writeString(gender.getGender());
		dest.writeFloat(height);
		dest.writeFloat(weight);
	}

	public static final Parcelable.Creator<UserInfo> CREATOR = new Creator<UserInfo>() {

		@Override
		public UserInfo[] newArray(int size) {
			return new UserInfo[size];
		}

		@Override
		public UserInfo createFromParcel(Parcel source) {
			return new UserInfo(source);
		}
	};

}
