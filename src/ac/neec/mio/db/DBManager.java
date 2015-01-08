package ac.neec.mio.db;

import java.util.List;

import ac.neec.mio.taining.Training;
import ac.neec.mio.taining.category.TrainingCategory;
import ac.neec.mio.taining.menu.TrainingMenu;
import ac.neec.mio.taining.play.TrainingPlay;
import ac.neec.mio.training.heartrate.HeartRate;
import ac.neec.mio.training.log.TrainingLog;
import ac.neec.mio.consts.Constants;
import ac.neec.mio.consts.PreferenceConstants;
import ac.neec.mio.util.SignUpConstants;
import android.content.Context;

public class DBManager {

//	private static DBOpenHelper helper;

//	public static void create(Context context) {
//		Constants.init(context.getResources());
//		SignUpConstants.init(context.getResources());
//		PreferenceConstants.init(context.getResources());
//		helper = new DBOpenHelper(context);
//	}

//	public static int insertUser(String userId, String userName,
//			String password, String gender, int height, int weight, int age,
//			int quietHeartRate, String mail) {
//		return helper.insertUser(userId, userName, password, gender, height,
//				weight, age, quietHeartRate, mail);
//	}

//	public static int insertTrainingCategory(int categoryId, String categoryName) {
//		return helper.insertTrainingCategory(categoryId, categoryName);
//	}

//	public static int insertTrainingMenu(int trainingMenuId,
//			String trainingName, float mets, int trainingCategoryId,String color) {
//		return helper.insertTrainingMenu(trainingMenuId, trainingName, mets,
//				trainingCategoryId,color);
//	}

//	public static int insertTraining(int trainingId, int trainingCategoryId,
//			String userId, String date, String startTime, String playTime,
//			int targetHeartRate, int TargetCal, int consumptionCal,
//			int heartRateAvg, int strange, double distance) {
//		return helper.insertTraining(trainingId, trainingCategoryId, userId,
//				date, startTime, playTime, targetHeartRate, TargetCal,
//				consumptionCal, heartRateAvg, strange, distance);
//	}

//	public static int insertHeartRate(int trainingId, String time, int heartRate) {
//		return helper.insertHeartRate(trainingId, time, heartRate);
//	}

//	public static int insertTrainingLog(int id, int heartRate, double disX,
//			double disY, String time, String lap, String split,
//			int targetHeartRate) {
//		return helper.insertTrainingLog(id, heartRate, disX, disY, time, lap,
//				split, targetHeartRate);
//	}

//	public static int insertTrainingPlay(int id, int trainingMenuId,
//			int trainingTime) {
//		return helper.insertTrainingPlay(id, trainingMenuId, trainingTime);
//	}

//	public static int deleteTraining(int trainingId) {
//		return helper.deleteTraining(trainingId);
//	}

//	public static int updateTraining(int id, int trainingId,
//			int trainingCategoryId, String date, String startTime,
//			String playTime, int targetHeartRate, int targetCal,
//			int consumptionCal, int heartRateAvg, int strange, double distance) {
//		return helper.updateTraining(id, trainingId, trainingCategoryId, date,
//				startTime, playTime, targetHeartRate, targetCal,
//				consumptionCal, heartRateAvg, strange, distance);
//	}

	// public static void selectUser() {
	// helper.selectUser();
	// }

	// public static void selectTraining() {
	// helper.selectTraining();
	// }

//	public static Training selectTraining(int id) {
//		return helper.selectTraining(id);
//	}

//	public static String selectTrainingMenuName(int trainingCategoryId) {
//		return helper.selectTrainingMenuName(trainingCategoryId);
//	}

//	public static TrainingMenu selectTrainingMenu(String trainingName) {
//		return helper.selectTrainingMenu(trainingName);
//	}

	// public static void selectHeartRateLog() {
	// helper.selectHeartRateLog();
	// }

//	public static List<TrainingLog> selectTrainingLog(int id) {
//		return helper.selectTrainingLog(id);
//	}

//	public static List<HeartRate> selectHeartRate(int trainingId) {
//		return helper.selectHeartRate(trainingId);
//	}

//	public static TrainingCategory selectTrainingCategory(int categoryId) {
//		return helper.selectTrainingCategory(categoryId);
//	}

//	public static List<TrainingCategory> selectTrainingCategory() {
//		return helper.selectTrainingCategory();
//	}

//	public static List<TrainingMenu> selectTrainingCategoryMenu(
//			int trainingCategoryId) {
//		return helper.selectTrainingCategoryMenu(trainingCategoryId);
//	}

//	public static List<TrainingMenu> selectTrainingMenu() {
//		return helper.selectTrainingMenu();
//	}

//	public static TrainingMenu selectTrainingMenu(int trainingMenuId) {
//		return helper.selectTrainingMenu(trainingMenuId);
//	}

//	public static List<TrainingPlay> selectTrainingPlay(int id) {
//		return helper.selectTrainingPlay(id);
//	}

//	public static int deleteTrainingLogTableAll() {
//		return helper.deleteTrainingLogTableAll();
//	}
//
//	public static int deleteTrainingPlayTableAll() {
//		return helper.deleteTrainingPlayTableAll();
//	}
//
//	public static int deleteTrainingCategoryTableAll() {
//		return helper.deleteTrainingCategoryTableAll();
//	}
//
//	public static int deleteTrainingMenuTableAll() {
//		return helper.deleteTrainingMenuTableAll();
//	}

}
