package ac.neec.mio.dao.item.api;

import java.io.InputStream;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;

import ac.neec.mio.consts.PermissionConstants;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.dao.item.api.parser.GroupAllXmlParser;
import ac.neec.mio.dao.item.api.parser.GroupXmlParser;
import ac.neec.mio.dao.item.api.parser.PermitionXmlParser;
import ac.neec.mio.dao.item.api.parser.PrintXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingCategoryXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingIdXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingInfoXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingLogXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingMenuXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingPlayXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingsXmlParser;
import ac.neec.mio.dao.item.api.parser.UserInfoXmlParser;
import ac.neec.mio.dao.item.api.parser.UserWeightsXmlParser;
import ac.neec.mio.dao.item.api.parser.XmlParser;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.util.DateUtil;
import android.content.Context;
import android.graphics.Bitmap;

/**
 * WebAPI接続URL・XML解析クラス生成を実装するクラス
 *
 */
public class ApiItemDao extends HttpsDao {

	private XmlParser parser;

	protected ApiItemDao(Sourceable listener) {
		super(listener);
	}

	@Override
	protected void notifyResponse(InputStream response) {
		parser.setResponse(response);
	}

	@Override
	public void selectUser(String userId, String password) {
		String url = SpoITApi.selectUser(userId, password);
		parser = new UserInfoXmlParser();
		executeApi(url);
	}

	@Override
	public void selectWeight(String userId, String password, String date1,
			String date2) {
		String url = SpoITApi.selectWeight(userId, password, date1, date2);
		parser = new UserWeightsXmlParser();
		executeApi(url);
	}

	@Override
	public void insertUser(String userId, String name, String birth,
			String gender, String height, String mail, String password) {
		String url = SpoITApi.insertUser(userId, name, birth, gender, height,
				mail, password);
		parser = new UserInfoXmlParser();
		executeApi(url);
	}

	@Override
	public void insertUserImage(String userId, String password, String filePath) {
		String url = SpoITApi.insertUserImage();
		parser = new PrintXmlParser();
		executePostImage(url, filePath);
	}

	@Override
	public void updateUser(String userId, String name, String birth,
			String height, String mail, String password) {
		String url = SpoITApi.updateUser(userId, name, birth, height, mail,
				password);
		parser = new UserInfoXmlParser();
		executeApi(url);
	}

	@Override
	public void updateUserWeight(String userId, String password, String weight) {
		String url = SpoITApi.insertUserWeight(userId, password, weight);
		parser = new UserInfoXmlParser();
		executeApi(url);
	}

	@Override
	public void updateUserQuietHeartRate(String userId, String password,
			String quietHeartRate) {
		String url = SpoITApi.insertUserQuietHeartRate(userId, password,
				quietHeartRate);
		parser = new UserInfoXmlParser();
		executeApi(url);
	}

	@Override
	public void selectGroup(String groupId) {
		String url = SpoITApi.selectGroup(groupId);
		parser = new GroupXmlParser();
		executeApi(url);
	}

	@Override
	public void insertGroup(String groupId, String groupName, String comment,
			String userId, String password) {
		String date = DateUtil.nowDate();
		String url = SpoITApi.insertGroup(groupId, groupName, comment, date,
				userId, password);
		parser = new GroupXmlParser();
		executeApi(url);
	}

	@Override
	public void insertGroupPending(String userId, String targetUserId,
			String groupId, String password) {
		String url = SpoITApi.insertGroupAffiliation(userId, targetUserId,
				groupId, PermissionConstants.pending(), password);
		parser = new GroupXmlParser();
		executeApi(url);
	}

	@Override
	public void insertGroupTrainer(String userId, String targetUserId,
			String groupId, String password) {
		String url = SpoITApi.insertGroupAffiliation(userId, targetUserId,
				groupId, PermissionConstants.trainer(), password);
		parser = new GroupXmlParser();
		executeApi(url);
	}

	@Override
	public void insertGroupMember(String userId, String targetUserId,
			String groupId, String password) {
		String url = SpoITApi.insertGroupAffiliation(userId, targetUserId,
				groupId, PermissionConstants.member(), password);
		parser = new GroupXmlParser();
		executeApi(url);
	}

	@Override
	public void insertGroupAdmin(String userId, String targetUserId,
			String groupId, String password) {
		String url = SpoITApi.insertGroupAffiliation(userId, targetUserId,
				groupId, PermissionConstants.groupAdmin(), password);
		parser = new GroupXmlParser();
		executeApi(url);
	}

	@Override
	public void deleteGroupMember(String userId, String targetUserId,
			String groupId, String password) {
		String url = SpoITApi.insertGroupAffiliation(userId, targetUserId,
				groupId, PermissionConstants.notice(), password);
		parser = new GroupXmlParser();
		executeApi(url);
	}

	@Override
	public void selectGroupAll() {
		String url = SpoITApi.selectGroupAll();
		parser = new GroupAllXmlParser();
		executeApi(url);
	}

	@Override
	public void selectMyGroupAll(String userId, String password) {
		String url = SpoITApi.selectGroupAll(userId, password);
		parser = new GroupAllXmlParser();
		executeApi(url);
	}

	@Override
	public void selectPermition() {
		String url = SpoITApi.selectPermition();
		parser = new PermitionXmlParser();
		executeApi(url);
	}

	@Override
	public void updateGroup(String groupId, String groupName, String comment) {
		String url = SpoITApi.updateGroup(groupId, groupName, comment);
		parser = new GroupXmlParser();
		executeApi(url);
	}

	@Override
	public void deleteGroup(String groupId, String userId, String password) {
		String url = SpoITApi.deleteGroup(groupId, userId, password);
		parser = new PrintXmlParser();
		executeApi(url);
	}

	@Override
	public void insertTrainingLog(int trainingId, int heartRate, double disX,
			double disY, String time, String lapTime, String splitTime,
			int trainingLogId, int targetHeartRate) {
		String url = SpoITApi.insertTrainingLog(trainingId, heartRate, disX,
				disY, time, lapTime, splitTime, trainingLogId, targetHeartRate);
		parser = new TrainingLogXmlParser();
		executeApi(url);
	}

	@Override
	public void insertTrainingPlay(String userId, int trainingId, int playId,
			int trainingMenuId, int trainingTime, String password) {
		String url = SpoITApi.insertTrainingPlay(userId, trainingId, playId,
				trainingMenuId, trainingTime, password);
		parser = new TrainingPlayXmlParser();
		executeApi(url);
	}

	@Override
	public void insertTraining(String userId, String password, String date,
			String startTime, String playTime, int targetHeartRate,
			int targetCal, int heartRateAvg, String targetPlayTime,
			int calorie, int categoryId, double distance) {
		String url = SpoITApi.insertTraining(userId, password, date, startTime,
				playTime, targetHeartRate, targetCal, heartRateAvg,
				targetPlayTime, calorie, categoryId, distance);
		parser = new TrainingIdXmlParser();
		executeApi(url);
	}

	@Override
	public void selectTraining(String userId, String targetUserId,
			int trainingId, String password) {
		String url = SpoITApi.selectTraining(userId, targetUserId, trainingId,
				password);
		parser = new TrainingInfoXmlParser();
		executeApi(url);
	}

	@Override
	public void selectTraining(String userId, String targetUserId,
			String date1, String date2, int limit, int offset, String password) {
		String url = SpoITApi.selectTraining(userId, targetUserId, date1,
				date2, limit, offset, password);
		parser = new TrainingsXmlParser();
		executeApi(url);
	}

	@Override
	public void selectTrainingCategory() {
		String url = SpoITApi.selectTrainingCategory();
		parser = new TrainingCategoryXmlParser();
		executeApi(url);
	}

	@Override
	public void selectTrainingMenu() {
		String url = SpoITApi.selectTrainingMenu();
		parser = new TrainingMenuXmlParser();
		executeApi(url);
	}

	@Override
	public void selectImage(String image) {
		String url = SpoITApi.selectImage(image);
		executeImageApi(url);
	}

	@Override
	public void test(String url) {
		parser = new PrintXmlParser();
	}

	@Override
	public <T> T getResponse() throws XmlParseException, XmlReadException {
		return parser.getXmlParseObject();
	}

	private void executeApi(String url) {
		super.execute(url);
	}

	private void executeImageApi(String url) {
		super.executeImage(url);
	}

}
