package ac.neec.mio.group;

import ac.neec.mio.framework.ProductData;

/**
 * 権限
 *
 */
public class Permission extends ProductData {

	/**
	 * 権限ID
	 */
	private int id;
	/**
	 * 権限名
	 */
	private String name;
	/**
	 * 強制退会処理
	 */
	private boolean compelWithdrawal;
	/**
	 * グループ解散
	 */
	private boolean dissolution;
	/**
	 * 権限変更
	 */
	private boolean permissionChange;
	/**
	 * グループ情報変更
	 */
	private boolean groupInfoChange;
	/**
	 * メンバー加入申請
	 */
	private boolean memberAddManage;
	/**
	 * メンバーのデータ閲覧
	 */
	private boolean memberDataCheck;
	/**
	 * メンバー一覧の閲覧
	 */
	private boolean memberListView;
	/**
	 * グループ情報の閲覧
	 */
	private boolean groupInfoView;
	/**
	 * グループ退会
	 */
	private boolean withdrawal;
	/**
	 * 正式メンバー
	 */
	private boolean joinStatus;
	/**
	 * グループのお知らせ
	 */
	private boolean groupNews;

	/**
	 * 
	 * @param id
	 *            権限ID
	 * @param name
	 *            権限名
	 * @param compelWithdrawal
	 *            強制退会処理
	 * @param dissolution
	 *            グループ解散
	 * @param permissionChange
	 *            権限変更
	 * @param groupInfoChange
	 *            グループ情報変更
	 * @param memberAddManage
	 *            メンバー加入申請
	 * @param memberDataCheck
	 *            メンバーデータ閲覧
	 * @param memberListView
	 *            メンバー一覧の閲覧
	 * @param groupInfoView
	 *            グループ情報の閲覧
	 * @param withdrawal
	 *            グループ退会
	 * @param joinStatus
	 *            正式メンバー
	 * @param groupNews
	 *            グループのお知らせ
	 */
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
	 * 権限IDを取得する
	 * 
	 * @return 権限ID
	 */
	public int getId() {
		return id;
	}

	/**
	 * 権限名を取得する
	 * 
	 * @return 権限名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 強制退会処理を取得する
	 * 
	 * @return 強制退会処理
	 */
	public boolean getCompelWithdrawal() {
		return compelWithdrawal;
	}

	/**
	 * グループ解散を取得する
	 * 
	 * @return グループ解散
	 */
	public boolean getDissolution() {
		return dissolution;
	}

	/**
	 * 権限変更を取得する
	 * 
	 * @return 権限変更
	 */
	public boolean getPermissionChange() {
		return permissionChange;
	}

	/**
	 * グループ情報変更を取得する
	 * 
	 * @return グループ情報変更
	 */
	public boolean getGroupInfoChange() {
		return groupInfoChange;
	}

	/**
	 * メンバー加入申請を取得する
	 * 
	 * @return メンバー加入申請
	 */
	public boolean getMemberAddManage() {
		return memberAddManage;
	}

	/**
	 * メンバーのデータ閲覧を取得する
	 * 
	 * @return メンバーのデータ閲覧
	 */
	public boolean getMemberDataCheck() {
		return memberDataCheck;
	}

	/**
	 * メンバー一覧の閲覧を取得する
	 * 
	 * @return メンバー一覧の閲覧
	 */
	public boolean getMemberListView() {
		return memberListView;
	}

	/**
	 * グループ情報の閲覧を取得する
	 * 
	 * @return グループ情報の閲覧
	 */
	public boolean getGroupInfoView() {
		return groupInfoView;
	}

	/**
	 * グループ退会を取得する
	 * 
	 * @return グループ退会
	 */
	public boolean getWithdrawal() {
		return withdrawal;
	}

	/**
	 * 正式メンバーを取得する
	 * 
	 * @return 正式メンバー
	 */
	public boolean getJoinStatus() {
		return joinStatus;
	}

	/**
	 * グループのお知らせを取得する
	 * 
	 * @return グループのお知らせ
	 */
	public boolean getGroupNews() {
		return groupNews;
	}

}
