package ac.neec.mio.framework;

import ac.neec.mio.group.Permission;

/**
 * ProductDataクラスを継承しているクラスを生成する
 *
 */
public abstract class ProductDataFactory {

	/**
	 * トレーニングを生成する
	 * 
	 * @param id
	 *            トレーニングID
	 * @param trainingMenuId
	 *            メニューID
	 * @param userId
	 *            実施したユーザID
	 * @param date
	 *            実施日
	 * @param startTime
	 *            開始時間
	 * @param playTime
	 *            計測時間
	 * @param targetHeartRate
	 *            目標心拍数
	 * @param targetCal
	 *            目標カロリー
	 * @param consumptionCal
	 *            消費カロリー
	 * @param heartRateAvg
	 *            平均心拍数
	 * @param strange
	 *            運動強度
	 * @param distance
	 *            走行距離
	 * @return Training型
	 */
	public ProductData create(int id, int trainingMenuId, String userId,
			String date, String startTime, String playTime,
			int targetHeartRate, int targetCal, int consumptionCal,
			int heartRateAvg, int strange, double distance) {
		return factoryMethod(id, trainingMenuId, userId, date, startTime,
				playTime, targetHeartRate, targetCal, consumptionCal,
				heartRateAvg, strange, distance);
	}

	/**
	 * トレーニングカテゴリーを生成する
	 * 
	 * @param trainingCategoryId
	 *            カテゴリーID
	 * @param trainingCategoryName
	 *            カテゴリー名
	 * @return TrainingCategory型
	 */
	public ProductData create(int trainingCategoryId,
			String trainingCategoryName) {
		return factoryMethod(trainingCategoryId, trainingCategoryName);
	}

	/**
	 * トレーニングメニューを生成する
	 * 
	 * @param trainingMenuId
	 *            メニューID
	 * @param trainingName
	 *            メニュー名
	 * @param mets
	 *            メッツ
	 * @param trainingCategoryId
	 *            カテゴリーID
	 * @param color
	 *            カラーコード
	 * @return TrainingMenu型
	 */
	public ProductData create(int trainingMenuId, String trainingName,
			float mets, int trainingCategoryId, String color) {
		return factoryMethod(trainingMenuId, trainingName, mets,
				trainingCategoryId, color);
	}

	/**
	 * 性別を生成する
	 * 
	 * @param gender
	 *            性別
	 * @return Gender型
	 */
	public ProductData create(String gender) {
		return factoryMethod(gender);
	}

	/**
	 * ラップを生成する
	 * 
	 * @param lapTime
	 *            ラップタイム
	 * @param splitTime
	 *            スプリットタイム
	 * @param distance
	 *            走行距離
	 * @return LapItem型
	 */
	public ProductData create(String lapTime, String splitTime, String distance) {
		return factoryMethod(lapTime, splitTime, distance);
	}

	/**
	 * トレーニングログを生成する
	 * 
	 * @param logId
	 *            ログID
	 * @param id
	 *            トレーニングID
	 * @param heartRate
	 *            心拍数
	 * @param disX
	 *            経度
	 * @param disY
	 *            緯度
	 * @param time
	 *            計測時間
	 * @param lap
	 *            ラップタイム
	 * @param split
	 *            スプリットタイム
	 * @param targetHeartRate
	 *            目標心拍数
	 * @return TrainingLog型
	 */
	public ProductData create(int logId, int id, int heartRate, double disX,
			double disY, String time, String lap, String split,
			int targetHeartRate) {
		return factoryMethod(logId, id, heartRate, disX, disY, time, lap,
				split, targetHeartRate);
	}

	/**
	 * トレーニングプレイを生成する
	 * 
	 * @param trainingMenuId
	 *            メニューID
	 * @param trainingTime
	 *            トレーニング時間
	 * @return TrainingPlay型
	 */
	public ProductData create(int trainingMenuId, int trainingTime) {
		return factoryMethod(trainingMenuId, trainingTime);
	}

	/**
	 * 権限を生成する
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
	 *            メンバー一覧表示
	 * @param groupInfoView
	 *            グループ情報表示
	 * @param withdrawal
	 *            グループ退会
	 * @param joinStatus
	 *            正式メンバー
	 * @param groupNews
	 *            グループのお知らせ
	 * @return Permission型
	 */
	public ProductData create(int id, String name, int compelWithdrawal,
			int dissolution, int permissionChange, int groupInfoChange,
			int memberAddManage, int memberDataCheck, int memberListView,
			int groupInfoView, int withdrawal, int joinStatus, int groupNews) {
		return factoryMethod(id, name, compelWithdrawal, dissolution,
				permissionChange, groupInfoChange, memberAddManage,
				memberDataCheck, memberListView, groupInfoView, withdrawal,
				joinStatus, groupNews);
	}

	/**
	 * ロールを生成する
	 * 
	 * @param id
	 *            ロールID
	 * @param name
	 *            ロール名
	 * @param created
	 *            アカウント作成日
	 * @param updated
	 *            アカウント更新日
	 * @param status
	 *            ステータス
	 * @return Role型
	 */
	public ProductData create(int id, String name, String created,
			String updated, int status) {
		return factoryMethod(id, name, created, updated, status);
	}

	/**
	 * グループ権限を生成する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param groupId
	 *            グループID
	 * @param permission
	 *            権限
	 * @return Affiliation型
	 */
	public ProductData create(String userId, String groupId,
			Permission permition) {
		return factoryMethod(userId, groupId, permition);
	}

	/**
	 * 画像情報を生成する
	 * 
	 * @param id
	 *            画像ID
	 * @param imageFileName
	 *            画像ファイル名
	 * @param userId
	 *            ユーザID
	 * @param groupId
	 *            グループID
	 * @param created
	 *            作成日
	 * @param image
	 *            画像
	 * @param bigImage
	 *            画像(大)
	 * @param smallImage
	 *            画像(小)
	 * @param thumbImage
	 *            画像(オリジナル)
	 * @return ImageInfo型
	 */
	public ProductData create(int id, String imageFileName, String userId,
			String groupId, String created, String image, String bigImage,
			String smallImage, String thumbImage) {
		return factoryMethod(id, imageFileName, userId, groupId, created,
				image, bigImage, smallImage, thumbImage);
	}

	/**
	 * 体重を生成する
	 * 
	 * @param id
	 *            体重ID
	 * @param date
	 *            記録日
	 * @param weight
	 *            体重
	 * @return Weight型
	 */
	public ProductData create(int id, String date, float weight) {
		return factoryMethod(id, date, weight);
	}

	/**
	 * グループ権限を生成する
	 * 
	 * @param groupId
	 *            グループID
	 * @param permissionId
	 *            権限ID
	 * @return Affiliation型
	 */
	public ProductData create(String groupId, int permissionId) {
		return factoryMethod(groupId, permissionId);
	}

	/**
	 * トレーニングを生成する
	 * 
	 * @param id
	 *            トレーニングID
	 * @param trainingMenuId
	 *            メニューID
	 * @param userId
	 *            実施したユーザID
	 * @param date
	 *            実施日
	 * @param startTime
	 *            開始時間
	 * @param playTime
	 *            計測時間
	 * @param targetHeartRate
	 *            目標心拍数
	 * @param targetCal
	 *            目標カロリー
	 * @param consumptionCal
	 *            消費カロリー
	 * @param heartRateAvg
	 *            平均心拍数
	 * @param strange
	 *            運動強度
	 * @param distance
	 *            走行距離
	 * @return Training型
	 */
	protected abstract ProductData factoryMethod(int id, int trainingMenuId,
			String userId, String date, String startTime, String playTime,
			int targetHeartRate, int targetCal, int consumptionCal,
			int heartRateAvg, int strange, double distance);

	protected abstract ProductData factoryMethod(int trainingCategotyId,
			String trainingCategoryName);

	protected abstract ProductData factoryMethod(int trainingMenuId,
			String trainingName, float mets, int trainingCategoryId,
			String color);

	protected abstract ProductData factoryMethod(String gender);

	protected abstract ProductData factoryMethod(String lapTime,
			String splitTime, String distance);

	protected abstract ProductData factoryMethod(int logId, int id,
			int heartRate, double disX, double disY, String time, String lap,
			String split, int targetHeartRate);

	protected abstract ProductData factoryMethod(int trainingMenuId,
			int trainingTime);

	protected abstract ProductData factoryMethod(int id, String name,
			int compelWithdrawal, int dissolution, int permissionChange,
			int groupInfoChange, int memberAddManage, int memberDataCheck,
			int memberListView, int groupInfoView, int withdrawal,
			int joinStatus, int groupNews);

	protected abstract ProductData factoryMethod(String userId, String groupId,
			Permission permission);

	protected abstract ProductData factoryMethod(int id, String imageFileName,
			String userId, String groupId, String created, String image,
			String bigImage, String smallImage, String thumbImage);

	protected abstract ProductData factoryMethod(int id, String name,
			String created, String updated, int status);

	protected abstract ProductData factoryMethod(int id, String date,
			float weight);

	protected abstract ProductData factoryMethod(String groupId,
			int permissionId);

}