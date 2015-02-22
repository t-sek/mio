package ac.neec.mio.group;

import static ac.neec.mio.util.PermissionUtil.getBool;

import java.sql.Time;

import ac.neec.mio.framework.ProductData;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.user.bodily.weight.Weight;
import android.graphics.Bitmap;

/**
 * Groupクラスを生成するクラス
 */
public class GroupFactory extends ProductDataFactory {

	@Override
	protected ProductData factoryMethod(int id, int trainingMenuId,
			String userId, String date, String startTime, String playTime,
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
	protected ProductData factoryMethod(String gender) {
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
		return new Affiliation(groupId, permissionId);
	}

}
