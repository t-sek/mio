package ac.neec.mio.group;

import java.sql.Time;

import static ac.neec.mio.util.PermissionUtil.*;
import ac.neec.mio.training.framework.ProductData;
import ac.neec.mio.training.framework.ProductDataFactory;

public class GroupFactory extends ProductDataFactory {

	@Override
	protected ProductData factoryMethod(Number number, Time time) {
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
	protected ProductData factoryMethod(int trainingCategotyId,
			String trainingCategoryName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(int trainingMenuId,
			String trainingName, float mets, int trainingCategoryId,
			String color) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(float height, float weight,
			int quietHeartRate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ProductData factoryMethod(String gender) {
		// TODO Auto-generated method stub
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
	protected ProductData factoryMethod(int logId, int id, int heartRate,
			double disX, double disY, String time, String lap, String split,
			int targetHeartRate) {
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
		return new Permission(id, name, getBool(compelWithdrawal),
				getBool(dissolution), getBool(permissionChange),
				getBool(groupInfoChange), getBool(memberAddManage),
				getBool(memberDataCheck), getBool(memberListView),
				getBool(groupInfoView), getBool(withdrawal),
				getBool(joinStatus), getBool(groupNews));
	}

	@Override
	protected ProductData factoryMethod(String userId, String groupId,
			Permission permition) {
		return new Affiliation(userId, groupId, permition);
	}

}
