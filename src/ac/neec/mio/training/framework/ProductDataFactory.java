package ac.neec.mio.training.framework;

import java.sql.Time;

import ac.neec.mio.group.Permission;
import ac.neec.mio.user.bodily.weight.Weight;
import android.graphics.Bitmap;

public abstract class ProductDataFactory {

	public ProductData create(Number number, Time time) {
		return factoryMethod(number, time);
	}

	public ProductData create(int id, int trainingMenuId, int userId,
			String date, String startTime, String playTime,
			int targetHeartRate, int targetCal, int consumptionCal,
			int heartRateAvg, int strange, double distance) {
		return factoryMethod(id, trainingMenuId, userId, date, startTime,
				playTime, targetHeartRate, targetCal, consumptionCal,
				heartRateAvg, strange, distance);
	}

	public ProductData create(int trainingCategoryId,
			String trainingCategoryName) {
		return factoryMethod(trainingCategoryId, trainingCategoryName);
	}

	public ProductData create(int trainingMenuId, String trainingName,
			float mets, int trainingCategoryId, String color) {
		return factoryMethod(trainingMenuId, trainingName, mets,
				trainingCategoryId, color);
	}

	public ProductData create(float height, Weight weight, int quietHeartRate) {
		return factoryMethod(height, weight, quietHeartRate);
	}

	public ProductData create(String gender) {
		return factoryMethod(gender);
	}

	public ProductData create(int trainingMenuId, String endTime, int playTime) {
		return factoryMethod(trainingMenuId, endTime, playTime);
	}

	public ProductData create(String lapTime, String splitTime, String distance) {
		return factoryMethod(lapTime, splitTime, distance);
	}

	public ProductData create(int logId, int id, int heartRate, double disX,
			double disY, String time, String lap, String split,
			int targetHeartRate) {
		return factoryMethod(logId, id, heartRate, disX, disY, time, lap,
				split, targetHeartRate);
	}

	public ProductData create(int trainingMenuId, int trainingTime) {
		return factoryMethod(trainingMenuId, trainingTime);
	}

	public ProductData create(int id, String name, int compelWithdrawal,
			int dissolution, int permissionChange, int groupInfoChange,
			int memberAddManage, int memberDataCheck, int memberListView,
			int groupInfoView, int withdrawal, int joinStatus, int groupNews) {
		return factoryMethod(id, name, compelWithdrawal, dissolution,
				permissionChange, groupInfoChange, memberAddManage,
				memberDataCheck, memberListView, groupInfoView, withdrawal,
				joinStatus, groupNews);
	}

	public ProductData create(int id, String name, String created,
			String updated, int status) {
		return factoryMethod(id, name, created, updated, status);
	}

	public ProductData create(String userId, String groupId,
			Permission permition) {
		return factoryMethod(userId, groupId, permition);
	}

	public ProductData create(int id, String imageFileName, String userId,
			String groupId, String created, String image, String bigImage,
			String smallImage, String thumbImage) {
		return factoryMethod(id, imageFileName, userId, groupId, created,
				image, bigImage, smallImage, thumbImage);
	}

	public ProductData create(int id, String date, float weight) {
		return factoryMethod(id, date, weight);
	}

	public ProductData create(String groupId, int permissionId) {
		return factoryMethod(groupId, permissionId);
	}

	protected abstract ProductData factoryMethod(Number number, Time time);

	protected abstract ProductData factoryMethod(int id, int trainingMenuId,
			int userId, String date, String startTime, String playTime,
			int targetHeartRate, int targetCal, int consumptionCal,
			int heartRateAvg, int strange, double distance);

	protected abstract ProductData factoryMethod(int trainingCategotyId,
			String trainingCategoryName);

	protected abstract ProductData factoryMethod(int trainingMenuId,
			String trainingName, float mets, int trainingCategoryId,
			String color);

	protected abstract ProductData factoryMethod(float height, Weight weight,
			int quietHeartRate);

	protected abstract ProductData factoryMethod(String gender);

	protected abstract ProductData factoryMethod(int trainingMenuId,
			String endTime, int playTime);

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