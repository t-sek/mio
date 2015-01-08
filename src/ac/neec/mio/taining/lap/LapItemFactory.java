package ac.neec.mio.taining.lap;

import java.sql.Time;

import ac.neec.mio.group.Permission;
import ac.neec.mio.training.framework.ProductData;
import ac.neec.mio.training.framework.ProductDataFactory;
import android.util.Log;

public class LapItemFactory extends ProductDataFactory {

	private int id = 1;
	private String lastTrainingName;

	@Override
	protected ProductData factoryMethod(Number number, Time time) {
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
	protected ProductData factoryMethod(int trainingCategotyId,
			String trainingCategoryName) {
		return null;
	}

	@Override
	protected ProductData factoryMethod(String lapTime, String splitTime,
			String distance) {
		ProductData item;
		item = new LapItem(id, lapTime, splitTime, distance);
		id++;
		return item;
	}

	@Override
	protected ProductData factoryMethod(int id, int trainingId, int heartRate,
			double disX, double disY, String time, String lap, String split,
			int targetHeartRate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(int id, int trainingMenuId, int userId,
			String date, String startTime, String playTime,
			int targetHeartRate, int targetCal, int consumptionCal,
			int heartRateAvg, int strange, double distance) {
		// TODO Auto-generated method stub
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

}