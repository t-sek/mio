package ac.neec.mio.dao.item.api;

import static ac.neec.mio.consts.UrlConstants.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

/**
 * WebAPI接続URLを生成するクラス
 *
 */
public class SpoITApi {

	private static final String BLANK = "%20";

	/**
	 * ユーザ情報取得URLを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 * @return URL
	 */
	public static String selectUser(String userId, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userInfo());
		sb.append(userId + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * 画像URLを取得する
	 * 
	 * @param image
	 *            画像名
	 * @return URL
	 */
	public static String selectImage(String image) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlSelectImage());
		sb.append(image);
		return validateUrl(sb.toString());
	}

	/**
	 * ユーザの体重推移取得URLを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 * @param date1
	 *            開始日
	 * @param date2
	 *            終了日
	 * @return URL
	 */
	public static String selectWeight(String userId, String password,
			String date1, String date2) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userGetWeight());
		sb.append(userId + section());
		sb.append(date1 + section());
		sb.append(date2 + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * ユーザ新規登録URLを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param name
	 *            名前
	 * @param birth
	 *            誕生日
	 * @param gender
	 *            性別
	 * @param height
	 *            身長
	 * @param mail
	 *            メールアドレス
	 * @param password
	 *            パスワード
	 * @return URL
	 */
	public static String insertUser(String userId, String name, String birth,
			String gender, String height, String mail, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userAdd());
		sb.append(userId + section());
		sb.append(name + section());
		sb.append(birth + section());
		sb.append(gender + section());
		sb.append(height + section());
		sb.append(mail + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * ユーザ情報更新URLを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param name
	 *            名前
	 * @param birth
	 *            誕生日
	 * @param height
	 *            身長
	 * @param mail
	 *            メールアドレス
	 * @param password
	 *            パスワード
	 * @return URL
	 */
	public static String updateUser(String userId, String name, String birth,
			String height, String mail, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userEdit());
		sb.append(userId + section());
		sb.append(name + section());
		sb.append(birth + section());
		sb.append(height + section());
		sb.append(mail + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * ユーザの体重追加URLを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 * @param weight
	 *            体重
	 * @return URL
	 */
	public static String insertUserWeight(String userId, String password,
			String weight) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userWeight());
		sb.append(userId + section());
		sb.append(weight + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * 安静時心拍数追加URLを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 * @param quietHeartRate
	 *            安静時心拍数
	 * @return URL
	 */
	public static String insertUserQuietHeartRate(String userId,
			String password, String quietHeartRate) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userQuietHeartRate());
		sb.append(userId + section());
		sb.append(quietHeartRate + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * 画像追加URLを取得する
	 * 
	 * @return URL
	 */
	public static String insertUserImage() {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userAddImage());
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * 全グループ取得URLを取得する
	 * 
	 * @return URL
	 */
	public static String selectGroupAll() {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(groupAll());
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * ユーザが所属する全グループ取得URLを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 * @return URL
	 */
	public static String selectGroupAll(String userId, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userInfo());
		sb.append(userId + section());
		sb.append(password + urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * グループ情報取得URLを取得する
	 * 
	 * @param groupId
	 *            グループID
	 * @return URL
	 */
	public static String selectGroup(String groupId) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(groupInfo());
		sb.append(groupId);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * パーミッション取得URLを取得する
	 * 
	 * @return URL
	 */
	public static String selectPermition() {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(permition());
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * グループ情報変更URLを取得する
	 * 
	 * @param groupId
	 *            グループID
	 * @param groupName
	 *            グループ名
	 * @param comment
	 *            コメント
	 * @return URL
	 */
	public static String updateGroup(String groupId, String groupName,
			String comment) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(groupEdit());
		sb.append(groupId + section());
		sb.append(groupName + section());
		sb.append(comment);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * グループ削除URLを取得する
	 * 
	 * @param groupId
	 *            グループID
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 * @return URL
	 */
	public static String deleteGroup(String groupId, String userId,
			String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(groupDelete());
		sb.append(groupId + section());
		sb.append(userId + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * グループメンバー権限変更URLを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param targetUserId
	 *            対象ユーザID
	 * @param groupId
	 *            グループID
	 * @param permitionId
	 *            パーミッションID
	 * @param password
	 *            パスワード
	 * @return URL
	 */
	public static String insertGroupAffiliation(String userId,
			String targetUserId, String groupId, int permitionId,
			String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(affiliationEdit());
		sb.append(userId + section());
		sb.append(targetUserId + section());
		sb.append(groupId + section());
		sb.append(permitionId + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * グループ新規登録URLを取得する
	 * 
	 * @param groupId
	 *            グループID
	 * @param groupName
	 *            グループ名
	 * @param comment
	 *            コメント
	 * @param date
	 *            日付
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 * @return URL
	 */
	public static String insertGroup(String groupId, String groupName,
			String comment, String date, String userId, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(groupAdd());
		sb.append(groupId + section());
		sb.append(groupName + section());
		sb.append(comment + section());
		sb.append(date + section());
		sb.append(userId + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * トレーニング追加URLを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 * @param date
	 *            日付
	 * @param startTime
	 *            開始時間
	 * @param playTime
	 *            運動時間
	 * @param targetHeartRate
	 *            目標心拍数
	 * @param targetCal
	 *            目標カロリー
	 * @param heartRateAvg
	 *            平均心拍数
	 * @param targetPlayTime
	 *            目標運動時間
	 * @param calorie
	 *            消費カロリー
	 * @param categoryId
	 *            カテゴリーID
	 * @param distance
	 *            距離
	 * @return URL
	 */
	public static String insertTraining(String userId, String password,
			String date, String startTime, String playTime,
			int targetHeartRate, int targetCal, int heartRateAvg,
			String targetPlayTime, int calorie, int categoryId, double distance) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(trainingInsert());
		sb.append(userId + section());
		sb.append(calorie + section());
		sb.append(categoryId + section());
		sb.append(date + section());
		sb.append(startTime + section());
		sb.append(playTime + section());
		sb.append(targetHeartRate + section());
		sb.append(targetCal + section());
		sb.append(heartRateAvg + section());
		sb.append(targetPlayTime + section());
		sb.append(distance + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * トレーニングログ追加URLを取得する
	 * 
	 * @param trainingId
	 *            トレーニングID
	 * @param heartRate
	 *            心拍数
	 * @param disX
	 *            経度
	 * @param disY
	 *            緯度
	 * @param time
	 *            時間
	 * @param lapTime
	 *            ラップ
	 * @param splitTime
	 *            スプリット
	 * @param trainingLogId
	 *            ログID
	 * @param targetHeartRate
	 *            目標心拍数
	 * @return URL
	 */
	public static String insertTrainingLog(int trainingId, int heartRate,
			double disX, double disY, String time, String lapTime,
			String splitTime, int trainingLogId, int targetHeartRate) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHead());
		sb.append(trainingInsertLog());
		sb.append(logTrainingId() + trainingId);
		sb.append(heartRate() + heartRate);
		sb.append(disX() + disX);
		sb.append(disY() + disY);
		sb.append(time() + time);
		sb.append(lapTime() + lapTime);
		sb.append(splitTime() + splitTime());
		sb.append(trainingLogId() + trainingLogId);
		sb.append(targetHeartRate() + targetHeartRate);
		return validateUrl(sb.toString());
	}

	/**
	 * トレーニングプレイ追加URLを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param trainingId
	 *            トレーニングID
	 * @param playId
	 *            プレイID
	 * @param trainingMenuId
	 *            トレーニングメニューID
	 * @param trainingTime
	 *            トレーニング時間
	 * @param password
	 *            パスワード
	 * @return URL
	 */
	public static String insertTrainingPlay(String userId, int trainingId,
			int playId, int trainingMenuId, int trainingTime, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(trainingInsertPlay());
		sb.append(userId + section());
		sb.append(trainingId + section());
		sb.append(playId + section());
		sb.append(trainingMenuId + section());
		sb.append(trainingTime + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * トレーニング取得URLを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param targetUserId
	 *            対象ユーザID
	 * @param trainingId
	 *            トレーニングID
	 * @param password
	 *            パスワード
	 * @return URL
	 */
	public static String selectTraining(String userId, String targetUserId,
			int trainingId, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(trainingSelect());
		sb.append(userId + section());
		sb.append(targetUserId + section());
		sb.append(trainingId + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * 期間トレーニング取得URLを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param targetUserId
	 *            対象ユーザID
	 * @param date1
	 *            開始日
	 * @param date2
	 *            終了日
	 * @param limit
	 *            取得数
	 * @param offset
	 *            開始位置
	 * @param password
	 *            パスワード
	 * @return URL
	 */
	public static String selectTraining(String userId, String targetUserId,
			String date1, String date2, int limit, int offset, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(trainingsSelect());
		sb.append(userId + section());
		sb.append(targetUserId + section());
		sb.append(date2 + section());
		sb.append(date1 + section());
		sb.append(limit + section());
		sb.append(offset + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * トレーニングカテゴリー取得URLを取得する
	 * 
	 * @return URL
	 */
	public static String selectTrainingCategory() {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(categorySelectAll());
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * トレーニングメニュー取得URLを取得する
	 * 
	 * @return URL
	 */
	public static String selectTrainingMenu() {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(menuSelectAll());
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	/**
	 * URLのバリデートチェックをする
	 * 
	 * @param url
	 *            URL
	 * @return URL
	 */
	private static String validateUrl(String url) {
		return url.replaceAll(" ", BLANK);
	}

}
