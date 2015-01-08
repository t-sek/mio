package ac.neec.mio.consts;

import ac.neec.mio.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

public class Constants {

	private static Resources resources;

	public static void init(Resources resources) {
		Constants.resources = resources;
	}

	public static String facebookApiKey() {
		return resources.getString(R.string.facebook_api_key);
	}

	public static String buttonLapText() {
		return resources.getString(R.string.btn_measurement_rest);
	}

	public static String dbName() {
		return resources.getString(R.string.db_name);
	}

	public static int dbVersion() {
		return resources.getInteger(R.integer.db_version);
	}

	public static String sqlCreatePath() {
		return resources.getString(R.string.path_sql_create);
	}

	public static String sqlInsertTrainingMenuPath() {
		return resources.getString(R.string.path_sql_insert_training_menu);
	}

	public static String sqlInsertTrainingCategoryPath() {
		return resources.getString(R.string.path_sql_insert_training_category);
	}

	public static String tableUser() {
		return resources.getString(R.string.table_user);
	}

	public static String tableHeartRate() {
		return resources.getString(R.string.table_heart_rate);
	}

	public static String tableHeartRateLog() {
		return resources.getString(R.string.table_heart_rate_log);
	}

	public static String tableTraining() {
		return resources.getString(R.string.table_training);
	}

	public static String tableTrainingMenu() {
		return resources.getString(R.string.table_training_menu);
	}

	public static String tableTrainingCategory() {
		return resources.getString(R.string.table_training_category);
	}

	public static String tableTrainingLog() {
		return resources.getString(R.string.table_training_log);
	}

	public static String tableTrainingPlay() {
		return resources.getString(R.string.table_training_play);
	}

	public static String tablePermission() {
		return resources.getString(R.string.table_permission);
	}

	public static String userId() {
		return resources.getString(R.string.column_user_id);
	}

	public static String userName() {
		return resources.getString(R.string.column_user_name);
	}

	public static String password() {
		return resources.getString(R.string.column_password);
	}

	public static String gender() {
		return resources.getString(R.string.column_gender);
	}

	public static String height() {
		return resources.getString(R.string.column_height);
	}

	public static String weight() {
		return resources.getString(R.string.column_weight);
	}

	public static String age() {
		return resources.getString(R.string.column_age);
	}

	public static String quietHeartRate() {
		return resources.getString(R.string.column_quiet_heart_rate);
	}

	public static String mail() {
		return resources.getString(R.string.column_mail);
	}

	public static String heartRateId() {
		return resources.getString(R.string.column_heart_rate_id);
	}

	public static String heartRate() {
		return resources.getString(R.string.column_heart_rate);
	}

	public static String logId() {
		return resources.getString(R.string.column_log_id);
	}

	public static String time() {
		return resources.getString(R.string.column_time);
	}

	public static String trainingId() {
		return resources.getString(R.string.column_training_id);
	}

	public static String date() {
		return resources.getString(R.string.column_date);
	}

	public static String startTime() {
		return resources.getString(R.string.column_start_time);
	}

	public static String playTime() {
		return resources.getString(R.string.column_play_time);
	}

	public static String targetHeartRate() {
		return resources.getString(R.string.column_target_heart_rate);
	}

	public static String targetCal() {
		return resources.getString(R.string.column_target_calorie);
	}

	public static String consumptionCal() {
		return resources.getString(R.string.column_consumption_calorie);
	}

	public static String heartRateAvg() {
		return resources.getString(R.string.column_heart_rate_avg);
	}

	public static String strange() {
		return resources.getString(R.string.column_strange);
	}

	public static String distance() {
		return resources.getString(R.string.column_distance);
	}

	public static String id() {
		return resources.getString(R.string.column_id);
	}

	public static String trainingMenuId() {
		return resources.getString(R.string.column_training_menu_id);
	}

	public static String trainingName() {
		return resources.getString(R.string.column_training_name);
	}

	public static String mets() {
		return resources.getString(R.string.column_mets);
	}

	public static String color() {
		return resources.getString(R.string.column_color);
	}

	public static String trainingCategoryId() {
		return resources.getString(R.string.column_training_category_id);
	}

	public static String trainingCategoryName() {
		return resources.getString(R.string.column_training_category_name);
	}

	public static String disX() {
		return resources.getString(R.string.column_dis_x);
	}

	public static String disY() {
		return resources.getString(R.string.column_dis_y);
	}

	public static String lap() {
		return resources.getString(R.string.column_lap);
	}

	public static String split() {
		return resources.getString(R.string.column_split);
	}

	public static String playId() {
		return resources.getString(R.string.column_play_id);
	}

	public static String trainingTime() {
		return resources.getString(R.string.column_training_time);
	}

	public static String permissionId() {
		return resources.getString(R.string.column_permission_id);
	}

	public static String name() {
		return resources.getString(R.string.column_name);
	}

	public static String compelWithdrawal() {
		return resources.getString(R.string.column_compel_withdrawal);
	}

	public static String dissolution() {
		return resources.getString(R.string.column_dissolution);
	}

	public static String permissionChange() {
		return resources.getString(R.string.column_permission_change);
	}

	public static String groupInfoChange() {
		return resources.getString(R.string.column_group_info_change);
	}

	public static String memberAddManage() {
		return resources.getString(R.string.column_member_add_manage);
	}

	public static String memberDataCheck() {
		return resources.getString(R.string.column_member_data_check);
	}

	public static String memberListView() {
		return resources.getString(R.string.column_member_list_view);
	}

	public static String groupInfoView() {
		return resources.getString(R.string.column_group_info_view);
	}

	public static String withdrawal() {
		return resources.getString(R.string.column_withdrawal);
	}

	public static String joinStatus() {
		return resources.getString(R.string.column_join_status);
	}

	public static String groupNews() {
		return resources.getString(R.string.column_group_news);
	}

	public static String[] selectUserTable() {
		return new String[] { userId(), userName(), password(), gender(),
				height(), weight(), age(), quietHeartRate(), mail() };
	}

	public static String[] selectTrainingTable() {
		return new String[] { id(), trainingCategoryId(), userId(), date(),
				startTime(), playTime(), targetHeartRate(), targetCal(),
				consumptionCal(), heartRateAvg(), strange(), distance() };
	}

	public static String[] selectHeartRateLogTable() {
		return new String[] { logId(), trainingId(), userId() };
	}

	public static String[] selectHeartRateTable() {
		return new String[] { heartRateId(), trainingId(), time(), heartRate() };
	}

	public static String[] selectTrainingMenuTable() {
		return new String[] { trainingMenuId(), trainingName(), mets(),
				trainingCategoryId(), color() };
	}

	public static String[] selectTrainingCategoryTable() {
		return new String[] { trainingCategoryId(), trainingCategoryName() };
	}

	public static String[] selectTrainingLogTable() {
		return new String[] { logId(), id(), heartRate(), disX(), disY(),
				time(), lap(), split(), targetHeartRate() };
	}

	public static String[] selectTrainingPlayTable() {
		return new String[] { id(), trainingMenuId(), trainingTime() };
	}

	public static String[] selectPermissionTable() {
		return new String[] { permissionId(), name(), compelWithdrawal(),
				dissolution(), permissionChange(), groupInfoChange(),
				memberAddManage(), memberDataCheck(), memberListView(),
				groupInfoView(), withdrawal(), joinStatus(), groupNews() };
	}
}
