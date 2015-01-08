package ac.neec.mio.group;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GroupInfo implements Serializable {

	private static final long SERIAL_ID = 1L;

	private String id;
	private String name;
	private String comment;
	private int count;
	private List<Member> members;

	public GroupInfo(String id, String name, String comment, int count,
			List<Member> members) {
		this.id = id;
		this.name = name;
		this.comment = comment;
		this.count = count;
		this.members = members;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public int getCount() {
		return count;
	}

	public List<Member> getMembers() {
		return members;
	}

}
