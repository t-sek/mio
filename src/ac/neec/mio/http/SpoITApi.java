package ac.neec.mio.http;

import static ac.neec.mio.consts.UrlConstants.*;

public class SpoITApi {
	// 指定した日付のトレーニング数
	// http://anninsuika.com/mioapi/TrainingInfo.php?func=Select&option=Date&user=testid&date=2014-09-26

	public static String selectUser(String userId, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userInfo());
		sb.append(userId + section());
		sb.append(password);
		sb.append(urlFoot());
		return sb.toString();
	}

	public static String insertUser(String userId, String name, String birth,
			String gender, String height, String mail, String password,
			String weight) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userAdd());
		sb.append(userId + section());
		sb.append(name + section());
		sb.append(birth + section());
		sb.append(gender + section());
		sb.append(height + section());
		sb.append(mail + section());
		sb.append(password + section());
		sb.append(weight);
		sb.append(urlFoot());
		return sb.toString();
	}

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
		return sb.toString();
	}

	public static String insertUserWeight(String userId, String weight) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userWeight());
		sb.append(userId + section());
		sb.append(weight);
		sb.append(urlFoot());
		return sb.toString();
	}

	public static String insertUserQuietHeartRate(String userId,
			String quietHeartRate) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userQuietHeartRate());
		sb.append(userId + section());
		sb.append(quietHeartRate);
		sb.append(urlFoot());
		return sb.toString();
	}

	public static String selectGroupAll() {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(groupAll());
		sb.append(urlFoot());
		// return sb.toString();
		return validateUrl(sb.toString());
	}

	public static String selectGroupAll(String userId, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userInfo());
		sb.append(userId + section());
		sb.append(password + urlFoot());
		return sb.toString();
	}

	public static String selectGroup(String groupId) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(groupInfo());
		sb.append(groupId);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
		// return sb.toString();
	}

	public static String selectPermition() {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(permition());
		sb.append(urlFoot());
		return sb.toString();
	}

	public static String updateGroup(String groupId, String groupName,
			String comment) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(groupEdit());
		sb.append(groupId + section());
		sb.append(groupName + section());
		sb.append(comment);
		sb.append(urlFoot());
		return sb.toString();
	}

	public static String insertGroupAffiliation(String userId, String groupId,
			int permitionId, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(affiliationEdit());
		sb.append(userId + section());
		sb.append(groupId + section());
		sb.append(permitionId + section());
		sb.append(password);
		sb.append(urlFoot());
		return sb.toString();
	}

	public static String insertGroup(String groupId, String groupName,
			String comment) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(groupAdd());
		sb.append(groupId + section());
		sb.append(groupName + section());
		sb.append(comment);
		sb.append(urlFoot());
		return sb.toString();
	}

	public static String selectTrainingId(String userId, String date) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHead());
		sb.append(trainingInsert());
		sb.append(userId() + userId);
		sb.append(date() + date);
		return sb.toString();
	}

	public static String insertTraining(String userId, String date,
			String startTime, String playTime, int targetHeartRate,
			int targetCal, int heartRateAvg, String targetPlayTime,
			int calorie, int categoryId, double distance) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHead());
		sb.append(trainingInsert());
		sb.append(userId() + userId);
		sb.append(time() + date);
		sb.append(startTime() + startTime);
		sb.append(playTime() + playTime);
		sb.append(targetHeartRate() + targetHeartRate);
		sb.append(targetCalorie() + targetCal);
		sb.append(heartRateAvg() + heartRateAvg);
		sb.append(targetPlayTime() + targetPlayTime);
		sb.append(calorie() + calorie);
		sb.append(categoryId() + categoryId);
		sb.append(distance() + distance);
		return sb.toString();
	}

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
		return sb.toString();
	}

	public static String insertTrainingPlay(int trainingId, int playId,
			int trainingMenuId, String trainingTime) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHead());
		sb.append(trainingInsertPlay());
		sb.append(trainingId() + trainingId);
		sb.append(palyId() + playId);
		sb.append(trainingMenuId() + trainingMenuId);
		sb.append(trainingTime() + trainingTime);
		return sb.toString();
	}

	public static String selectTrainingAll(String userId) {
		StringBuilder sb = new StringBuilder();
		sb.append(trainingSelectAll());
		sb.append(userId() + userId);
		return sb.toString();
	}

	public static String selectTraining(String userId, int trainingId) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHead());
		sb.append(trainingSelect());
		sb.append(userId() + userId);
		sb.append(trainingId() + trainingId);
		return sb.toString();
	}

	public static String selectTrainingDate(String userId, String date) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHead());
		sb.append(trainingSelectDate());
		sb.append(userId() + userId);
		sb.append(date() + date);
		return sb.toString();
	}

	public static String selectTrainingLog(String userId, int trainingId) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHead());
		sb.append(trainingSelectLog());
		sb.append(userId() + userId);
		sb.append(trainingId() + trainingId);
		return sb.toString();
	}

	public static String selectTrainingPlay(String userId, int trainingId) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHead());
		sb.append(trainingSelectPlay());
		sb.append(userId() + userId);
		sb.append(trainingId() + trainingId);
		return sb.toString();
	}

	public static String selectTrainingCategory() {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHead());
		sb.append(categorySelectAll());
		return sb.toString();
	}

	public static String selectTrainingMenu() {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHead());
		sb.append(menuSelectAll());
		return sb.toString();
	}

	private static String validateUrl(String url) {
		return url.replaceAll(" ", "%20");
	}

}
