package ac.neec.mio.group;

import ac.neec.mio.training.framework.ProductData;

public class Permission extends ProductData{

	private int id;
	private String name;
	private boolean compelWithdrawal;
	private boolean dissolution;
	private boolean permissionChange;
	private boolean groupInfoChange;
	private boolean memberAddManage;
	private boolean memberDataCheck;
	private boolean memberListView;
	private boolean groupInfoView;
	private boolean withdrawal;
	private boolean joinStatus;
	private boolean groupNews;

	public Permission(int id, String name, boolean compelWithdrawal,
			boolean dissolution, boolean permissionChange,
			boolean groupInfoChange, boolean memberAddManage,
			boolean memberDataCheck, boolean memberListView,
			boolean groupInfoView, boolean withdrawal, boolean joinStatus,
			boolean groupNews) {
		this.id = id;
		this.name = name;
		this.compelWithdrawal = compelWithdrawal;
		this.dissolution = dissolution;
		this.permissionChange = permissionChange;
		this.groupInfoChange = groupInfoChange;
		this.memberAddManage = memberAddManage;
		this.memberDataCheck = memberDataCheck;
		this.memberListView = memberListView;
		this.groupInfoView = groupInfoView;
		this.withdrawal = withdrawal;
		this.joinStatus = joinStatus;
		this.groupNews = groupNews;
	}

	/**
	 * パーミッションID
	 * 
	 * @return
	 */
	public int getId() {
		return id;
	}

	/**
	 * パーミッション名
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * 強制退会処理
	 * 
	 * @return
	 */
	public boolean getCompelWithdrawal() {
		return compelWithdrawal;
	}

	/**
	 * グループ解散
	 * 
	 * @return
	 */
	public boolean getDissolution() {
		return dissolution;
	}

	/**
	 * 権限変更
	 * 
	 * @return
	 */
	public boolean getPermissionChange() {
		return permissionChange;
	}

	/**
	 * グループ情報変更
	 * 
	 * @return
	 */
	public boolean getGroupInfoChange() {
		return groupInfoChange;
	}

	/**
	 * メンバー加入申請処理
	 * 
	 * @return
	 */
	public boolean getMemberAddManage() {
		return memberAddManage;
	}

	/**
	 * メンバーのデータ閲覧
	 * 
	 * @return
	 */
	public boolean getMemberDataCheck() {
		return memberDataCheck;
	}

	/**
	 * メンバー一覧の閲覧
	 * 
	 * @return
	 */
	public boolean getMemberListView() {
		return memberListView;
	}

	/**
	 * グループ情報の閲覧
	 * 
	 * @return
	 */
	public boolean getGroupInfoView() {
		return groupInfoView;
	}

	/**
	 * グループ退会
	 * 
	 * @return
	 */
	public boolean getWithdrawal() {
		return withdrawal;
	}

	/**
	 * 正式メンバー
	 * 
	 * @return
	 */
	public boolean getJoinStatus() {
		return joinStatus;
	}

	/**
	 * お知らせ
	 * 
	 * @return
	 */
	public boolean getGroupNews() {
		return groupNews;
	}

}
