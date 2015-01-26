package ac.neec.mio.taining;

import java.sql.Time;

import ac.neec.mio.group.Permission;
import ac.neec.mio.training.framework.ProductData;
import ac.neec.mio.training.framework.ProductDataFactory;
import ac.neec.mio.user.bodily.weight.Weight;
import android.graphics.Bitmap;

public class TrainingFactory extends ProductDataFactory {

	@Override
	protected ProductData factoryMethod(Number number, Time time) {
		return null;
	}

	@Override
	protected ProductData factoryMethod(int trainingCategotyId,
			String trainingCategoryName) {
		return null;
	}

	@Override
	protected ProductData factoryMethod(int trainingId, String trainingName,
			float mets, int trainingCategoryId, String color) {
		return null;
	}

	@Override
	protected ProductData factoryMethod(float height, Weight weight,
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(String lapTime, String splitTime,
			String distance) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(int id, int trainingId, int heartRate,
			double disX, double disY, String time, String lap, String split,
			int targetHeartRate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(int id, int trainingMenuId, String userId,
			String date, String startTime, String playTime,
			int targetHeartRate, int targetCal, int consumptionCal,
			int heartRateAvg, int strange, double distance) {
		return new Training(id, trainingMenuId, userId, date, startTime,
				playTime, targetHeartRate, targetCal, consumptionCal,
				heartRateAvg, strange, distance);
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
			String userId, String groupId, String created, String image,
			String bigImage, String smallImage, String thumbImage) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(int id, String name, String created,
			String updated, int status) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(int id, String date, float weight) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(String groupId, int permissionId) {
		// TODO Auto-generated method stub
		return null;
	}
}
