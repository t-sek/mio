package ac.neec.mio.dao.item.api;

import static ac.neec.mio.consts.UrlConstants.*;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class SpoITApi {

	private static final String BLANK = "%20";

	public static String selectUser(String userId, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userInfo());
		sb.append(userId + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	public static String selectImage(String image) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlSelectImage());
		sb.append(image);
		return validateUrl(sb.toString());
	}

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
		return validateUrl(sb.toString());
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
		return validateUrl(sb.toString());
	}

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

	public static String insertUserImage() {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userAddImage());
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	public static List<NameValuePair> insertUserImagePostData(String userId,
			String password, String name, String type, String tmpName,
			String error, String size) {
		List<NameValuePair> postData = new ArrayList<NameValuePair>();
		postData.add(new BasicNameValuePair(postUserId(), userId));
		postData.add(new BasicNameValuePair(postPassword(), password));
		// postData.add(new BasicNameValuePair(postName(), name));
		postData.add(new BasicNameValuePair(postType(), type));
		postData.add(new BasicNameValuePair(postTmpName(), tmpName));
		postData.add(new BasicNameValuePair(postError(), error));
		postData.add(new BasicNameValuePair(postSize(), size));
		return postData;
	}

	public static String selectGroupAll() {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(groupAll());
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	public static String selectGroupAll(String userId, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(userInfo());
		sb.append(userId + section());
		sb.append(password + urlFoot());
		return validateUrl(sb.toString());
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
		return validateUrl(sb.toString());
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
		return validateUrl(sb.toString());
	}

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

	public static String selectTrainingId(String userId, String date) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHead());
		sb.append(trainingInsert());
		sb.append(userId() + userId);
		sb.append(date() + date);
		return validateUrl(sb.toString());
	}

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
		// sb.append(urlHead());
		// sb.append(trainingInsert());
		// sb.append(userId() + userId);
		// sb.append(time() + date);
		// sb.append(startTime() + startTime);
		// sb.append(playTime() + playTime);
		// sb.append(targetHeartRate() + targetHeartRate);
		// sb.append(targetCalorie() + targetCal);
		// sb.append(heartRateAvg() + heartRateAvg);
		// sb.append(targetPlayTime() + targetPlayTime);
		// sb.append(calorie() + calorie);
		// sb.append(categoryId() + categoryId);
		// sb.append(distance() + distance);
		return validateUrl(sb.toString());
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
		return validateUrl(sb.toString());
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
		return validateUrl(sb.toString());
	}

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

	public static String selectTraining(String userId, String targetUserId,
			String date1, String date2, int limit, int offset, String password) {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(trainingsSelect());
		sb.append(userId + section());
		sb.append(targetUserId + section());
		sb.append(date1 + section());
		sb.append(date2 + section());
		sb.append(limit + section());
		sb.append(offset + section());
		sb.append(password);
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	public static String selectTrainingCategory() {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(categorySelectAll());
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	public static String selectTrainingMenu() {
		StringBuilder sb = new StringBuilder();
		sb.append(urlHeadSSL());
		sb.append(menuSelectAll());
		sb.append(urlFoot());
		return validateUrl(sb.toString());
	}

	private static String validateUrl(String url) {
		return url.replaceAll(" ", BLANK);
	}

}
