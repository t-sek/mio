package ac.neec.mio.dao.item.api;

import java.io.InputStream;
import java.util.List;

import org.apache.http.NameValuePair;
import org.json.JSONArray;

import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.dao.item.api.parser.GroupAllXmlParser;
import ac.neec.mio.dao.item.api.parser.GroupXmlParser;
import ac.neec.mio.dao.item.api.parser.PermitionXmlParser;
import ac.neec.mio.dao.item.api.parser.PrintXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingCategoryXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingIdXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingLogXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingMenuXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingPlayXmlParser;
import ac.neec.mio.dao.item.api.parser.TrainingXmlParser;
import ac.neec.mio.dao.item.api.parser.UserInfoXmlParser;
import ac.neec.mio.dao.item.api.parser.UserWeightsXmlParser;
import ac.neec.mio.dao.item.api.parser.XmlParser;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import android.content.Context;

public class ApiItemDao extends HttpsDao {

	private XmlParser parser;

	protected ApiItemDao(Context context, Sourceable listener) {
		super(context, listener);
	}

	@Override
	protected void notifyResponse(InputStream response) {
		parser.setResponse(response);
	}

	@Override
	public void selectUser(Context context, String userId, String password) {
		String url = SpoITApi.selectUser(userId, password);
		parser = new UserInfoXmlParser(context);
		// parser = new PrintXmlParser();
		executeApi(url);
	}

	@Override
	public void selectWeight(Context context, String userId, String password,
			String date1, String date2) {
		String url = SpoITApi.selectWeight(userId, password, date1, date2);
		parser = new UserWeightsXmlParser();
		// parser = new PrintXmlParser();
		executeApi(url);
	}

	@Override
	public void insertUser(Context context, String userId, String name,
			String birth, String gender, String height, String mail,
			String password, String weight) {
		String url = SpoITApi.insertUser(userId, name, birth, gender, height,
				mail, password, weight);
		parser = new UserInfoXmlParser(context);
		executeApi(url);
	}

	@Override
	public void insertUserImage(Context context, String userId,
			String password, String name, String type, String tmpName,
			int error, int size) {
		String url = SpoITApi.insertUserImage();
		parser = new PrintXmlParser();
		List<NameValuePair> postData = SpoITApi.insertUserImagePostData(userId,
				password, name, type, tmpName, String.valueOf(error),
				String.valueOf(size));
		executePostImage(url, postData);
		// JSONArray json = null;
		// try {
		// json = new Image(userId, password, name, type, tmpName, error, size)
		// .getJson();
		// } catch (ImagePostMessageException e) {
		// e.printStackTrace();
		// }
		// // Log.d("dao", "json " + json.toString());
		// executePostImageApi(url, json);
	}

	@Override
	public void updateUser(Context context, String userId, String name,
			String birth, String height, String mail, String password) {
		String url = SpoITApi.updateUser(userId, name, birth, height, mail,
				password);
		parser = new UserInfoXmlParser(context);
		executeApi(url);
	}

	@Override
	public void updateUserWeight(Context context, String userId,
			String password, String weight) {
		String url = SpoITApi.insertUserWeight(userId, password, weight);
		parser = new UserInfoXmlParser(context);
		executeApi(url);
	}

	@Override
	public void updateUserQuietHeartRate(Context context, String userId,
			String password, String quietHeartRate) {
		String url = SpoITApi.insertUserQuietHeartRate(userId, password,
				quietHeartRate);
		parser = new UserInfoXmlParser(context);
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
			String date, String userId, String password) {
		String url = SpoITApi.insertGroup(groupId, groupName, comment, date,
				userId, password);
		parser = new GroupXmlParser();
		executeApi(url);
	}

	@Override
	public void insertGroupAffiliation(String userId, String groupId,
			int permitionId, String password) {
		String url = SpoITApi.insertGroupAffiliation(userId, groupId,
				permitionId, password);
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
	public void insertTrainingLog(int trainingId, int heartRate, double disX,
			double disY, String time, String lapTime, String splitTime,
			int trainingLogId, int targetHeartRate) {
		String url = SpoITApi.insertTrainingLog(trainingId, heartRate, disX,
				disY, time, lapTime, splitTime, trainingLogId, targetHeartRate);
		parser = new TrainingLogXmlParser();
		executeApi(url);
	}

	@Override
	public void insertTrainingPlay(int trainingId, int playId,
			int trainingMenuId, String trainingTime) {
		String url = SpoITApi.insertTrainingPlay(trainingId, playId,
				trainingMenuId, trainingTime);
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
	public void selectTrainingId(String userId, String date) {
		String url = SpoITApi.selectTrainingId(userId, date);
		parser = new TrainingIdXmlParser();
		executeApi(url);
	}

	@Override
	public void selectTrainingLog(String userId, int trainingId) {
		String url = SpoITApi.selectTrainingLog(userId, trainingId);
		parser = new TrainingLogXmlParser();
		executeApi(url);
	}

	@Override
	public void selectTrainingPlay(String userId, int trainingId) {
		String url = SpoITApi.selectTrainingPlay(userId, trainingId);
		parser = new TrainingPlayXmlParser();
		executeApi(url);
	}

	@Override
	public void selectTraining(String userId, String date) {
		String url = SpoITApi.selectTrainingDate(userId, date);
		parser = new TrainingXmlParser();
		executeApi(url);
	}

	@Override
	public void selectTraining(String userId, int trainingId, String password) {
		String url = SpoITApi.selectTraining(userId, trainingId, password);
		parser = new TrainingXmlParser();
		executeApi(url);
	}

	@Override
	public void selectTrainingNum(String userId, String date) {
	}

	@Override
	public void selectTrainingAll(String userId) {
		// String url = SpoITApi.selectTrainingAll(userId);
		// parser = new TrainingXmlParser();
		// executeApi(url);
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

	private void executePostImageApi(String url, JSONArray json) {
		super.executePostImage(url, json);
	}

	private void executeImageApi(String url) {
		super.executeImage(url);
	}

}
