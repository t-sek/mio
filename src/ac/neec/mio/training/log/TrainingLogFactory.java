package ac.neec.mio.training.log;

import java.sql.Time;

import ac.neec.mio.group.Permission;
import ac.neec.mio.training.framework.ProductData;
import ac.neec.mio.training.framework.ProductDataFactory;
import android.graphics.Bitmap;
import android.util.Log;

public class TrainingLogFactory extends ProductDataFactory {

	@Override
	protected ProductData factoryMethod(Number number, Time time) {
		return null;
	}

	@Override
	protected ProductData factoryMethod(int trainingCategoryId,
			String trainingCategoryName) {
		return null;
	}

	@Override
	protected ProductData factoryMethod(int trainingManuId,
			String trainingName, float mets, int trainingCategoryId,
			String color) {
		return null;
	}

	@Override
	protected ProductData factoryMethod(float height, float weight,
			int quietHeartRate) {
		return null;
	}

	@Override
	protected ProductData factoryMethod(String gender) {
		return null;
	}

	@Override
	protected ProductData factoryMethod(int trainingMenuId, String endTime,
			int playTime) {
		return null;
	}

	@Override
	protected ProductData factoryMethod(String lapTime, String splitTime,
			String distance) {
		return null;
	}

	@Override
	protected ProductData factoryMethod(int logId, int id, int heartRate,
			double disX, double disY, String time, String lap, String split,
			int targetHeartRate) {
		return new TrainingLog(logId, id, heartRate, disX, disY, time, lap,
				split, targetHeartRate);
	}

	@Override
	protected ProductData factoryMethod(int id, int trainingMenuId, int userId,
			String date, String startTime, String playTime,
			int targetHeartRate, int targetCal, int consumptionCal,
			int heartRateAvg, int strange, double distance) {
		return null;
	}

	@Override
	protected ProductData factoryMethod(int trainingMenuId, int trainingTime) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(int id, String name,
			int compelWithdrawal, int dissolution, int permissionChange,
			int groupInfoChange, int memberAddManage, int memberDataCheck,
			int memberListView, int groupInfoView, int withdrawal,
			int joinStatus, int groupNews) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(String userId, String groupId,
			Permission permition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(int id, String imageFileName,
			String userId, String groupId, String created, Bitmap image,
			Bitmap bigImage, Bitmap smallImage, Bitmap thumbImage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(int id, String name, String created,
			String updated, int status) {
		// TODO Auto-generated method stub
		return null;
	}
}
