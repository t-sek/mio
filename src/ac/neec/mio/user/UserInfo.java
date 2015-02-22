package ac.neec.mio.user;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Group;
import ac.neec.mio.image.ImageInfo;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.user.gender.GenderFactory;
import ac.neec.mio.user.role.Role;
import android.os.Parcel;
import android.os.Parcelable;

public class UserInfo {

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

	private Gender createGender(String gender) {
		ProductDataFactory factory = new GenderFactory();
		return (Gender) factory.create(gender);

	}

	public void setAffiliations(List<Affiliation> affiliations) {
		this.affiliations = affiliations;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public void setImageInfo(ImageInfo info) {
		this.image = info;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public void setWeight(float weight) {
		this.weight = weight;
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

}
