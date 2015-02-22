package ac.neec.mio.consts;

import ac.neec.mio.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * リソースからデータベースの項目名・テーブル名を取得するクラス
 *
 */
public class SQLConstants extends AppConstants {

	/**
	 * facebookAPIキー
	 * 
	 * @return 項目名
	 */
	public static String facebookApiKey() {
		return resources.getString(R.string.facebook_api_key);
	}

	/**
	 * データベース名を取得
	 * 
	 * @return データベース名
	 */
	public static String dbName() {
		return resources.getString(R.string.db_name);
	}

	/**
	 * バージョンを取得
	 * 
	 * @return バージョン
	 */
	public static int dbVersion() {
		return resources.getInteger(R.integer.db_version);
	}

	/**
	 * CREATE文を定義しているsqlファイルのパスを取得
	 * 
	 * @return ファイルパス
	 */
	public static String sqlCreatePath() {
		return resources.getString(R.string.path_sql_create);
	}

	/**
	 * トレーニングテーブル名
	 * 
	 * @return テーブル名
	 */
	public static String tableTraining() {
		return resources.getString(R.string.table_training);
	}

	/**
	 * トレーニングメニューテーブル名
	 * 
	 * @return テーブル名
	 */
	public static String tableTrainingMenu() {
		return resources.getString(R.string.table_training_menu);
	}

	/**
	 * トレーニングカテゴリーテーブル名
	 * 
	 * @return テーブル名
	 */
	public static String tableTrainingCategory() {
		return resources.getString(R.string.table_training_category);
	}

	/**
	 * トレーニングログテーブル名
	 * 
	 * @return テーブル名
	 */
	public static String tableTrainingLog() {
		return resources.getString(R.string.table_training_log);
	}

	/**
	 * トレーニングプレイテーブル名
	 * 
	 * @return テーブル名
	 */
	public static String tableTrainingPlay() {
		return resources.getString(R.string.table_training_play);
	}

	/**
	 * パーミッションテーブル名
	 * 
	 * @return テーブル名
	 */
	public static String tablePermission() {
		return resources.getString(R.string.table_permission);
	}

	/**
	 * グループテーブル名
	 * 
	 * @return テーブル名
	 */
	public static String tableGroup() {
		return resources.getString(R.string.table_group);
	}

	/**
	 * ブループメンバーテーブル名
	 * 
	 * @return テーブル名
	 */
	public static String tableGroupMember() {
		return resources.getString(R.string.table_group_member);
	}

	/**
	 * 加入テーブル名
	 * 
	 * @return テーブル名
	 */
	public static String tableAffiliation() {
		return resources.getString(R.string.table_affiliation);
	}

	/**
	 * ユーザID
	 * 
	 * @return 項目名
	 */
	public static String userId() {
		return resources.getString(R.string.column_user_id);
	}

	/**
	 * ユーザ名
	 * 
	 * @return 項目名
	 */
	public static String userName() {
		return resources.getString(R.string.column_user_name);
	}

	/**
	 * パスワード
	 * 
	 * @return 項目名
	 */
	public static String password() {
		return resources.getString(R.string.column_password);
	}

	/**
	 * 性別
	 * 
	 * @return 項目名
	 */
	public static String gender() {
		return resources.getString(R.string.column_gender);
	}

	/**
	 * 身長
	 * 
	 * @return 項目名
	 */
	public static String height() {
		return resources.getString(R.string.column_height);
	}

	/**
	 * 体重
	 * 
	 * @return 項目名
	 */
	public static String weight() {
		return resources.getString(R.string.column_weight);
	}

	/**
	 * 年齢
	 * 
	 * @return 項目名
	 */
	public static String age() {
		return resources.getString(R.string.column_age);
	}

	/**
	 * 安静時心拍数
	 * 
	 * @return 項目名
	 */
	public static String quietHeartRate() {
		return resources.getString(R.string.column_quiet_heart_rate);
	}

	/**
	 * メールアドレス
	 * 
	 * @return 項目名
	 */
	public static String mail() {
		return resources.getString(R.string.column_mail);
	}

	/**
	 * 心拍数ID
	 * 
	 * @return 項目名
	 */
	public static String heartRateId() {
		return resources.getString(R.string.column_heart_rate_id);
	}

	/**
	 * 心拍数
	 * 
	 * @return 項目名
	 */
	public static String heartRate() {
		return resources.getString(R.string.column_heart_rate);
	}

	/**
	 * ログID
	 * 
	 * @return 項目名
	 */
	public static String logId() {
		return resources.getString(R.string.column_log_id);
	}

	/**
	 * 時間
	 * 
	 * @return 項目名
	 */
	public static String time() {
		return resources.getString(R.string.column_time);
	}

	/**
	 * トレーニングID
	 * 
	 * @return 項目名
	 */
	public static String trainingId() {
		return resources.getString(R.string.column_training_id);
	}

	/**
	 * 日付
	 * 
	 * @return 項目名
	 */
	public static String date() {
		return resources.getString(R.string.column_date);
	}

	/**
	 * 開始時間
	 * 
	 * @return 項目名
	 */
	public static String startTime() {
		return resources.getString(R.string.column_start_time);
	}

	/**
	 * 運動時間
	 * 
	 * @return 項目名
	 */
	public static String playTime() {
		return resources.getString(R.string.column_play_time);
	}

	/**
	 * 目標心拍数
	 * 
	 * @return 項目名
	 */
	public static String targetHeartRate() {
		return resources.getString(R.string.column_target_heart_rate);
	}

	/**
	 * 目標カロリー
	 * 
	 * @return
	 */
	public static String targetCal() {
		return resources.getString(R.string.column_target_calorie);
	}

	/**
	 * カロリー
	 * 
	 * @return 項目名
	 */
	public static String calorie() {
		return resources.getString(R.string.column_consumption_calorie);
	}

	/**
	 * 平均心拍数
	 * 
	 * @return 項目名
	 */
	public static String heartRateAvg() {
		return resources.getString(R.string.column_heart_rate_avg);
	}

	/**
	 * 運動強度
	 * 
	 * @return 項目名
	 */
	public static String strange() {
		return resources.getString(R.string.column_strange);
	}

	/**
	 * 距離
	 * 
	 * @return 項目名
	 */
	public static String distance() {
		return resources.getString(R.string.column_distance);
	}

	/**
	 * ID
	 * 
	 * @return 項目名
	 */
	public static String id() {
		return resources.getString(R.string.column_id);
	}

	/**
	 * トレーニングメニューID
	 * 
	 * @return 項目名
	 */
	public static String trainingMenuId() {
		return resources.getString(R.string.column_training_menu_id);
	}

	/**
	 * トレーニング名
	 * 
	 * @return 項目名
	 */
	public static String trainingName() {
		return resources.getString(R.string.column_training_name);
	}

	/**
	 * メッツ
	 * 
	 * @return 項目名
	 */
	public static String mets() {
		return resources.getString(R.string.column_mets);
	}

	/**
	 * カラー
	 * 
	 * @return 項目名
	 */
	public static String color() {
		return resources.getString(R.string.column_color);
	}

	/**
	 * トレーニングカテゴリーID
	 * 
	 * @return 項目名
	 */
	public static String trainingCategoryId() {
		return resources.getString(R.string.column_training_category_id);
	}

	/**
	 * トレーニングカテゴリー名
	 * 
	 * @return 項目名
	 */
	public static String trainingCategoryName() {
		return resources.getString(R.string.column_training_category_name);
	}

	/**
	 * 経度
	 * 
	 * @return 項目名
	 */
	public static String disX() {
		return resources.getString(R.string.column_dis_x);
	}

	/**
	 * 緯度
	 * 
	 * @return 項目名
	 */
	public static String disY() {
		return resources.getString(R.string.column_dis_y);
	}

	/**
	 * ラップ
	 * 
	 * @return 項目名
	 */
	public static String lap() {
		return resources.getString(R.string.column_lap);
	}

	/**
	 * スプリット
	 * 
	 * @return 項目名
	 */
	public static String split() {
		return resources.getString(R.string.column_split);
	}

	/**
	 * プレイID
	 * 
	 * @return 項目名
	 */
	public static String playId() {
		return resources.getString(R.string.column_play_id);
	}

	/**
	 * トレーニング時間
	 * 
	 * @return 項目名
	 */
	public static String trainingTime() {
		return resources.getString(R.string.column_training_time);
	}

	/**
	 * パーミッションID
	 * 
	 * @return 項目名
	 */
	public static String permissionId() {
		return resources.getString(R.string.column_permission_id);
	}

	/**
	 * 名前
	 * 
	 * @return 項目名
	 */
	public static String name() {
		return resources.getString(R.string.column_name);
	}

	/**
	 * 強制退会
	 * 
	 * @return 項目名
	 */
	public static String compelWithdrawal() {
		return resources.getString(R.string.column_compel_withdrawal);
	}

	/**
	 * グループ解散
	 * 
	 * @return 項目名
	 */
	public static String dissolution() {
		return resources.getString(R.string.column_dissolution);
	}

	/**
	 * 権限変更
	 * 
	 * @return 項目名
	 */
	public static String permissionChange() {
		return resources.getString(R.string.column_permission_change);
	}

	/**
	 * グループ情報変更
	 * 
	 * @return 項目名
	 */
	public static String groupInfoChange() {
		return resources.getString(R.string.column_group_info_change);
	}

	/**
	 * メンバー加入申請
	 * 
	 * @return 項目名
	 */
	public static String memberAddManage() {
		return resources.getString(R.string.column_member_add_manage);
	}

	/**
	 * メンバーデータ閲覧
	 * 
	 * @return 項目名
	 */
	public static String memberDataCheck() {
		return resources.getString(R.string.column_member_data_check);
	}

	/**
	 * メンバー一覧表示
	 * 
	 * @return 項目名
	 */
	public static String memberListView() {
		return resources.getString(R.string.column_member_list_view);
	}

	/**
	 * グループ情報表示
	 * 
	 * @return 項目名
	 */
	public static String groupInfoView() {
		return resources.getString(R.string.column_group_info_view);
	}

	/**
	 * グループ退会
	 * 
	 * @return 項目名
	 */
	public static String withdrawal() {
		return resources.getString(R.string.column_withdrawal);
	}

	/**
	 * 正式メンバー(グループ一覧での判定)
	 * 
	 * @return 項目名
	 */
	public static String joinStatus() {
		return resources.getString(R.string.column_join_status);
	}

	/**
	 * グループのお知らせの閲覧
	 * 
	 * @return 項目名
	 */
	public static String groupNews() {
		return resources.getString(R.string.column_group_news);
	}

	/**
	 * グループID
	 * 
	 * @return 項目名
	 */
	public static String groupId() {
		return resources.getString(R.string.column_group_id);
	}

	/**
	 * コメント
	 * 
	 * @return 項目名
	 */
	public static String comment() {
		return resources.getString(R.string.column_comment);
	}

	/**
	 * 作成日
	 * 
	 * @return 項目名
	 */
	public static String created() {
		return resources.getString(R.string.column_created);
	}

	/**
	 * 画像
	 * 
	 * @return 項目名
	 */
	public static String image() {
		return resources.getString(R.string.column_image);
	}

	/**
	 * トレーニングテーブルの項目を配列で取得
	 * 
	 * @return String配列 項目
	 */
	public static String[] selectTrainingTable() {
		return new String[] { id(), trainingCategoryId(), userId(), date(),
				startTime(), playTime(), targetHeartRate(), targetCal(),
				calorie(), heartRateAvg(), strange(), distance() };
	}

	/**
	 * トレーニングメニューテーブルの項目を配列で取得
	 * 
	 * @return String配列 項目
	 */
	public static String[] selectTrainingMenuTable() {
		return new String[] { trainingMenuId(), trainingName(), mets(),
				trainingCategoryId(), color() };
	}

	/**
	 * トレーニングカテゴリーテーブルの項目を配列で取得
	 * 
	 * @return String配列 項目
	 */
	public static String[] selectTrainingCategoryTable() {
		return new String[] { trainingCategoryId(), trainingCategoryName() };
	}

	/**
	 * トレーニングログテーブルの項目を配列で取得
	 * 
	 * @return String配列 項目
	 */
	public static String[] selectTrainingLogTable() {
		return new String[] { logId(), id(), heartRate(), disX(), disY(),
				time(), lap(), split(), targetHeartRate() };
	}

	/**
	 * トレーニングプレイテーブルの項目を配列で取得
	 * 
	 * @return String配列 項目
	 */
	public static String[] selectTrainingPlayTable() {
		return new String[] { id(), trainingMenuId(), trainingTime() };
	}

	/**
	 * パーミッションテーブルの項目を配列で取得
	 * 
	 * @return String配列 項目
	 */
	public static String[] selectPermissionTable() {
		return new String[] { permissionId(), name(), compelWithdrawal(),
				dissolution(), permissionChange(), groupInfoChange(),
				memberAddManage(), memberDataCheck(), memberListView(),
				groupInfoView(), withdrawal(), joinStatus(), groupNews() };
	}

	/**
	 * 加入テーブルの項目を配列で取得
	 * 
	 * @return String配列 項目
	 */
	public static String[] selectAffiliationTable() {
		return new String[] { groupId(), permissionId() };
	}

	/**
	 * グループテーブルの項目を配列で取得
	 * 
	 * @return String配列 項目
	 */
	public static String[] selectGroupTable() {
		return new String[] { id(), name(), comment(), userId(), created(),
				permissionId() };
	}

	/**
	 * グループメンバーテーブルの項目を配列で取得
	 * 
	 * @return String配列 項目
	 */
	public static String[] selectGroupMemberTable() {
		return new String[] { groupId(), userId(), name(), permissionId(),
				image() };
	}
}
