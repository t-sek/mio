package ac.neec.mio.group;

import java.io.Serializable;

import android.os.Parcel;
import android.os.Parcelable;

public class Member implements Parcelable {

	public static final String ID = "member_id";
	public static final String NAME = "member_name";
	
	private String userId;
	private String userName;
	private String groupId;
	private int permitionId;

	public Member(String userId, String userName, String groupId,
			int permitionId) {
		this.userId = userId;
		this.userName = userName;
		this.groupId = groupId;
		this.permitionId = permitionId;
	}

	private Member(final Parcel in) {
		userId = in.readString();
		userName = in.readString();
		groupId = in.readString();
		permitionId = in.readInt();
	}

	public String getUserId() {
		return userId;
	}

	public String getUserName() {
		return userName;
	}

	public String getGroupId() {
		return groupId;
	}

	public int getPermitionId() {
		return permitionId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(userId);
		dest.writeString(userName);
		dest.writeString(groupId);
		dest.writeInt(permitionId);
	}

	public static final Parcelable.Creator<Member> CREATOR = new Creator<Member>() {

		@Override
		public Member[] newArray(int size) {
			return new Member[size];
		}

		@Override
		public Member createFromParcel(Parcel source) {
			return new Member(source);
		}
	};
}
