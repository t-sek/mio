package ac.neec.mio.dao.item.sqlite;

import static ac.neec.mio.consts.SQLConstants.*;
import static ac.neec.mio.util.PermissionUtil.getInt;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import ac.neec.mio.consts.PermissionConstants;
import ac.neec.mio.dao.item.sqlite.parser.AffiliationCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.AffiliationsCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.CursorParser;
import ac.neec.mio.dao.item.sqlite.parser.GroupCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.GroupMemberCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.GroupsCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.PermissionCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.PermissionListCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.TrainingCategoryCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.TrainingCategoryListCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.TrainingCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.TrainingListCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.TrainingLogCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.TrainingMenuCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.TrainingMenuListCursorParser;
import ac.neec.mio.dao.item.sqlite.parser.TrainingPlayCursorParser;
import ac.neec.mio.exception.SQLiteInsertException;
import ac.neec.mio.exception.SQLiteTableConstraintException;
import ac.neec.mio.group.Affiliation;
import ac.neec.mio.group.Group;
import ac.neec.mio.group.MemberInfo;
import ac.neec.mio.group.Permission;
import ac.neec.mio.training.Training;
import ac.neec.mio.training.category.TrainingCategory;
import ac.neec.mio.training.log.TrainingLog;
import ac.neec.mio.training.menu.TrainingMenu;
import ac.neec.mio.training.play.TrainingPlay;
import ac.neec.mio.util.BitmapUtil;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;

/**
 * SQLをContentValuesに設定、解析クラスを生成するクラス
 *
 */
public class SQLItemDao extends SQLiteItemDao {

	protected SQLItemDao() {
	}

	@Override
	public void insertTrainingCategory(int trainingCategoryId,
			String trainingCategoryName) throws SQLiteInsertException,
			SQLiteTableConstraintException {
		ContentValues cv = new ContentValues();
		cv.put(trainingCategoryId(), trainingCategoryId);
		cv.put(trainingCategoryName(), trainingCategoryName);
		super.insert(tableTrainingCategory(), cv);
	}

	@Override
	public void insertTrainingMenu(int trainingMenuId, String trainingName,
			float mets, int trainingCategoryId, String color)
			throws SQLiteInsertException, SQLiteTableConstraintException {
		ContentValues cv = new ContentValues();
		cv.put(trainingMenuId(), trainingMenuId);
		cv.put(trainingName(), trainingName);
		cv.put(mets(), mets);
		cv.put(trainingCategoryId(), trainingCategoryId);
		cv.put(color(), color);
		super.insert(tableTrainingMenu(), cv);
	}

	@Override
	public TrainingCategory selectTrainingCategory(int categoryId) {
		String id = String.valueOf(categoryId);
		Cursor c = super.select(tableTrainingCategory(),
				selectTrainingCategoryTable(), trainingCategoryId(), id);
		CursorParser parser = new TrainingCategoryCursorParser(c);
		return parser.getObject();
	}

	@Override
	public TrainingCategory selectTrainingCategory(String name) {
		Cursor c = super.select(tableTrainingCategory(),
				selectTrainingCategoryTable(), trainingCategoryName(), name);
		CursorParser parser = new TrainingCategoryCursorParser(c);
		return parser.getObject();
	}

	@Override
	public List<TrainingCategory> selectTrainingCategory() {
		Cursor c = super.select(tableTrainingCategory(),
				selectTrainingCategoryTable());
		CursorParser parser = new TrainingCategoryListCursorParser(c);
		return parser.getObject();
	}

	@Override
	public List<TrainingMenu> selectTrainingCategoryMenu(int trainingCategoryId) {
		String id = String.valueOf(trainingCategoryId);
		Cursor c = super.select(tableTrainingMenu(), selectTrainingMenuTable(),
				trainingCategoryId(), id);
		CursorParser parser = new TrainingMenuListCursorParser(c);
		return parser.getObject();
	}

	@Override
	public List<TrainingMenu> selectTrainingMenu() {
		Cursor c = super.select(tableTrainingMenu(), selectTrainingMenuTable());
		CursorParser parser = new TrainingMenuListCursorParser(c);
		return parser.getObject();
	}

	@Override
	public TrainingMenu selectTrainingMenu(int trainingMenuId) {
		String id = String.valueOf(trainingMenuId);
		Cursor c = super.select(tableTrainingMenu(), selectTrainingMenuTable(),
				trainingMenuId(), id);
		CursorParser parser = new TrainingMenuCursorParser(c);
		return parser.getObject();
	}

	@Override
	public String selectTrainingMenuName(int trainingCategoryId) {
		String id = String.valueOf(trainingCategoryId);
		Cursor c = super.select(tableTrainingMenu(), selectTrainingMenuTable(),
				trainingCategoryId(), id);
		CursorParser parser = new TrainingMenuCursorParser(c);
		TrainingMenu menu = parser.getObject();
		if (menu == null) {
			return null;
		}
		return menu.getTrainingName();
	}

	@Override
	public TrainingMenu selectTrainingMenu(String trainingName) {
		Cursor c = super.select(tableTrainingMenu(), selectTrainingMenuTable(),
				trainingName(), trainingName);
		CursorParser parser = new TrainingMenuCursorParser(c);
		return parser.getObject();
	}

	@Override
	public void deleteTrainingCategoryTableAll() {
		super.delete(tableTrainingCategory());
	}

	@Override
	public void deleteTrainingMenuTableAll() {
		super.delete(tableTrainingMenu());
	}

	@Override
	public int insertTraining(int trainingCategoryId, String userId,
			String date, String startTime, String playTime,
			int targetHeartRate, int TargetCalorie, int consumptionCalorie,
			int heartRateAvg, int strange, double distance)
			throws SQLiteInsertException, SQLiteTableConstraintException {
		ContentValues cv = new ContentValues();
		cv.put(trainingCategoryId(), trainingCategoryId);
		cv.put(userId(), userId);
		cv.put(date(), date);
		cv.put(startTime(), startTime);
		cv.put(playTime(), playTime);
		cv.put(targetHeartRate(), targetHeartRate);
		cv.put(targetCal(), TargetCalorie);
		cv.put(calorie(), consumptionCalorie);
		cv.put(heartRateAvg(), heartRateAvg);
		cv.put(strange(), strange);
		cv.put(distance(), distance);
		return super.insert(tableTraining(), cv);
	}

	@Override
	public void insertTrainingLog(int id, int heartRate, double disX,
			double disY, String time, String lap, String split,
			int targetHeartRate) throws SQLiteInsertException,
			SQLiteTableConstraintException {
		ContentValues cv = new ContentValues();
		cv.put(id(), id);
		cv.put(heartRate(), heartRate);
		cv.put(disX(), disX);
		cv.put(disY(), disY);
		cv.put(time(), time);
		cv.put(lap(), lap);
		cv.put(split(), split);
		cv.put(targetHeartRate(), targetHeartRate);
		super.insert(tableTrainingLog(), cv);
	}

	@Override
	public void insertTrainingPlay(int id, int trainingMenuId, int trainingTime)
			throws SQLiteInsertException, SQLiteTableConstraintException {
		ContentValues cv = new ContentValues();
		cv.put(id(), id);
		cv.put(trainingMenuId(), trainingMenuId);
		cv.put(trainingTime(), trainingTime);
		super.insert(tableTrainingPlay(), cv);
	}

	@Override
	public void updateTraining(int id, String playTime, int calorie,
			int targetHeartRate, int heartRateAvg, double distance) {
		ContentValues cv = new ContentValues();
		cv.put(playTime(), playTime);
		cv.put(calorie(), calorie);
		cv.put(targetHeartRate(), targetHeartRate);
		cv.put(heartRateAvg(), heartRateAvg);
		cv.put(distance(), distance);
		super.update(tableTraining(), cv, id(), String.valueOf(id));
	}

	@Override
	public List<Training> selectTraining() {
		Cursor c = super.select(tableTraining(), selectTrainingTable());
		CursorParser parser = new TrainingListCursorParser(c);
		return parser.getObject();
	}

	@Override
	public Training selectTraining(int id) {
		Cursor c = super.select(tableTraining(), selectTrainingTable(), id(),
				String.valueOf(id));
		CursorParser parser = new TrainingCursorParser(c);
		return parser.getObject();
	}

	@Override
	public List<TrainingLog> selectTrainingLog(int id) {
		Cursor c = super.select(tableTrainingLog(), selectTrainingLogTable(),
				id(), String.valueOf(id));
		CursorParser parser = new TrainingLogCursorParser(c);
		return parser.getObject();
	}

	@Override
	public List<TrainingPlay> selectTrainingPlay(int id) {
		Cursor c = super.select(tableTrainingPlay(), selectTrainingPlayTable(),
				id(), String.valueOf(id));
		CursorParser parser = new TrainingPlayCursorParser(c);
		return parser.getObject();
	}

	@Override
	public void selectHeartRate(int trainingId) {
	}

	@Override
	public void deleteTrainingLogAll() {
		super.delete(tableTrainingLog());
	}

	@Override
	public void deleteTrainingPlayAll() {
		super.delete(tableTrainingPlay());
	}

	@Override
	public void deleteTrainingAll() {
		super.delete(tableTraining());
	}

	@Override
	public void deleteTraining(int id) {
		super.delete(tableTraining(), id(), String.valueOf(id));
	}

	@Override
	public void deleteTrainingLog(int id) {
		super.delete(tableTrainingLog(), logId(), String.valueOf(id));
	}

	@Override
	public void deleteTrainingPlay(int id) {
		super.delete(tableTrainingPlay(), id(), String.valueOf(id));
	}

	@Override
	public void insertPermission(Permission perm) throws SQLiteInsertException,
			SQLiteTableConstraintException {
		ContentValues cv = new ContentValues();
		cv.put(permissionId(), perm.getId());
		cv.put(name(), perm.getName());
		cv.put(compelWithdrawal(), getInt(perm.getCompelWithdrawal()));
		cv.put(dissolution(), getInt(perm.getDissolution()));
		cv.put(permissionChange(), getInt(perm.getPermissionChange()));
		cv.put(groupInfoChange(), getInt(perm.getGroupInfoChange()));
		cv.put(memberAddManage(), getInt(perm.getMemberAddManage()));
		cv.put(memberDataCheck(), getInt(perm.getMemberDataCheck()));
		cv.put(memberListView(), getInt(perm.getMemberListView()));
		cv.put(groupInfoView(), getInt(perm.getGroupInfoView()));
		cv.put(withdrawal(), getInt(perm.getWithdrawal()));
		cv.put(joinStatus(), getInt(perm.getJoinStatus()));
		cv.put(groupNews(), getInt(perm.getGroupNews()));
		super.insert(tablePermission(), cv);
	}

	@Override
	public List<Permission> selectPermission() {
		Cursor c = super.select(tablePermission(), selectPermissionTable());
		CursorParser parser = new PermissionListCursorParser(c);
		return parser.getObject();
	}

	@Override
	public Permission selectPermission(int permissionId) {
		Cursor c = super.select(tablePermission(), selectPermissionTable(),
				permissionId(), String.valueOf(permissionId));
		CursorParser parser = new PermissionCursorParser(c);
		return parser.getObject();
	}

	@Override
	public void deletePermission() {
		super.delete(tablePermission());
	}

	@Override
	public void insertGroup(String groupId, String name, String comment,
			String userId, String created, int permissionId)
			throws SQLiteInsertException, SQLiteTableConstraintException {
		ContentValues cv = new ContentValues();
		cv.put(id(), groupId);
		cv.put(name(), name);
		cv.put(comment(), comment);
		cv.put(userId(), userId);
		cv.put(created(), created);
		cv.put(permissionId(), permissionId);
		super.insert(tableGroup(), cv);
	}

	@Override
	public List<Group> selectGroup() {
		Cursor c = super.select(tableGroup(), selectGroupTable());
		CursorParser parser = new GroupsCursorParser(c);
		List<Group> group = parser.getObject();
		Iterator<Group> i = group.iterator();
		while (i.hasNext()) {
			int permissionId = i.next().getPermissionId();
			if (permissionId == PermissionConstants.notice()) {
				i.remove();
			}
		}
		return group;
	}

	@Override
	public List<Group> selectGroupJoin() {
		List<Group> join = new ArrayList<Group>();
		Cursor c = super.select(tableGroup(), selectGroupTable());
		CursorParser parser = new GroupsCursorParser(c);
		join = parser.getObject();
		Iterator<Group> i = join.iterator();
		while (i.hasNext()) {
			int permissionId = i.next().getPermissionId();
			if (permissionId == PermissionConstants.notice()
					|| permissionId == PermissionConstants.pending()) {
				i.remove();
			}
		}
		return join;
	}

	@Override
	public Group selectGroup(String groupId) {
		Cursor c = super
				.select(tableGroup(), selectGroupTable(), id(), groupId);
		CursorParser parser = new GroupCursorParser(c);
		return parser.getObject();
	}

	@Override
	public List<MemberInfo> selectGroupMember(String groupId) {
		Cursor c = super.select(tableGroupMember(), selectGroupMemberTable(),
				groupId(), groupId);
		CursorParser parser = new GroupMemberCursorParser(c);
		return parser.getObject();
	}

	@Override
	public void insertGroupMember(String groupId, String userId,
			String userName, int permissionId, Bitmap image)
			throws SQLiteInsertException, SQLiteTableConstraintException {
		ContentValues cv = new ContentValues();
		cv.put(groupId(), groupId);
		cv.put(userId(), userId);
		cv.put(name(), userName);
		cv.put(permissionId(), permissionId);
		byte[] blob = BitmapUtil.bitmapToBlob(image);
		cv.put(image(), blob);
		super.insert(tableGroupMember(), cv);
	}

	@Override
	public List<MemberInfo> selectGroupMember(String groupId, int permissionId) {
		Cursor c = super.select(tableGroupMember(), selectGroupMemberTable(),
				groupId(), groupId);
		CursorParser parser = new GroupMemberCursorParser(c);
		return parser.getObject();
	}

	@Override
	public void deleteGroupMember(String groupId) {
		super.delete(tableGroupMember(), groupId(), groupId);
	}

	@Override
	public void deleteGroupMember() {
		super.delete(tableGroupMember());
	}

	@Override
	public void deleteGroup() {
		super.delete(tableGroup());
	}

	@Override
	public void insertAffiliation(String groupId, int permissionId)
			throws SQLiteInsertException, SQLiteTableConstraintException {
		ContentValues cv = new ContentValues();
		cv.put(groupId(), groupId);
		cv.put(permissionId(), permissionId);
		super.insert(tableAffiliation(), cv);
	}

	@Override
	public Affiliation selectAffiliation(String groupId) {
		Cursor c = super.select(tableAffiliation(), selectAffiliationTable(),
				groupId(), groupId);
		CursorParser parser = new AffiliationCursorParser(c);
		return parser.getObject();
	}

	@Override
	public List<Affiliation> selectAffiliation() {
		Cursor c = super.select(tableAffiliation(), selectAffiliationTable());
		CursorParser parser = new AffiliationsCursorParser(c);
		return parser.getObject();
	}

	@Override
	public void deleteAffiliation() {
		super.delete(tableAffiliation());
	}

}
