package ac.neec.mio.group;

import ac.neec.mio.training.framework.ProductData;

public class Affiliation extends ProductData{

	private String userId;
	private String groupId;
	private Permission permition;

	protected Affiliation(String userId, String groupId, Permission permition) {
		this.userId = userId;
		this.groupId = groupId;
		this.permition = permition;
	}

	public String getUserId() {
		return userId;
	}

	public String getGroupId() {
		return groupId;
	}

	public Permission getPermition() {
		return permition;
	}

}
