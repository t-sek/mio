package ac.neec.mio.consts;

import ac.neec.mio.R;
import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

/**
 * リソースからWebAPIで使用する文字列を取得するクラス
 *
 */
public class UrlConstants extends AppConstants {

	/**
	 * URLを取得
	 * 
	 * @return http://anninsuika.com/mioapi/
	 */
	public static String urlHead() {
		return resources.getString(R.string.url_head);
	}

	/**
	 * URLを取得
	 * 
	 * @return https://anninsuika.com/Mio/
	 */
	public static String urlHeadSSL() {
		return resources.getString(R.string.url_head_ssl);
	}

	/**
	 * 画像取得URLを取得
	 * 
	 * @return http://anninsuika.com/Mio/img/images/
	 */
	public static String urlSelectImage() {
		return resources.getString(R.string.select_image);
	}

	/**
	 * URL区切りを取得
	 * 
	 * @return /
	 */
	public static String section() {
		return resources.getString(R.string.section);
	}

	/**
	 * URLのおしりを取得
	 * 
	 * @return .xml
	 */
	public static String urlFoot() {
		return resources.getString(R.string.url_foot);
	}

	/**
	 * ユーザ情報取得のURLを取得
	 * 
	 * @return api/userinfo/
	 */
	public static String userInfo() {
		return resources.getString(R.string.user_info);
	}

	/**
	 * ユーザ新規登録のURLを取得
	 * 
	 * @return api/useradd/
	 */
	public static String userAdd() {
		return resources.getString(R.string.user_add);
	}

	/**
	 * ユーザ情報変更のURLを取得
	 * 
	 * @return api/useredit/
	 */
	public static String userEdit() {
		return resources.getString(R.string.user_edit);
	}

	/**
	 * ユーザの体重追加のURLを取得
	 * 
	 * @return api/weightadd/
	 */
	public static String userWeight() {
		return resources.getString(R.string.user_weight);
	}

	/**
	 * ユーザの体重推移のURLを取得
	 * 
	 * @return api/getweight/
	 */
	public static String userGetWeight() {
		return resources.getString(R.string.user_get_weight);
	}

	/**
	 * ユーザの安静時心拍数編集のURLを取得
	 * 
	 * @return api/quiethredit/
	 */
	public static String userQuietHeartRate() {
		return resources.getString(R.string.user_quiet_heart_rate);
	}

	/**
	 * ユーザのプロフィール画像追加のURLを取得
	 * 
	 * @return api/adduserimage
	 */
	public static String userAddImage() {
		return resources.getString(R.string.user_add_image);
	}

	/**
	 * 全グループ取得のURLを取得
	 * 
	 * @return api/groupall
	 */
	public static String groupAll() {
		return resources.getString(R.string.group_all);
	}

	/**
	 * グループ情報取得のURLを取得
	 * 
	 * @return api/groupinfo/
	 */
	public static String groupInfo() {
		return resources.getString(R.string.group_info);
	}

	/**
	 * グループ情報変更のURLを取得
	 * 
	 * @return api/groupedit/
	 */
	public static String groupEdit() {
		return resources.getString(R.string.group_edit);
	}

	/**
	 * グループ削除のURLを取得
	 * 
	 * @return api/groupdelete/
	 */
	public static String groupDelete() {
		return resources.getString(R.string.group_delete);
	}

	/**
	 * グループ新規作成のURLを取得
	 * 
	 * @return api/groupadd/
	 */
	public static String groupAdd() {
		return resources.getString(R.string.group_add);
	}

	/**
	 * グループ権限変更のURLを取得
	 * 
	 * @return api/affiliationedit/
	 */
	public static String affiliationEdit() {
		return resources.getString(R.string.affiliation_edit);
	}

	/**
	 * グループ権限一覧取得のURLを取得
	 * 
	 * @return api/permition/
	 */
	public static String permition() {
		return resources.getString(R.string.permition);
	}

	/**
	 * トレーニング追加のURLを取得
	 * 
	 * @return api/addtraining/
	 */
	public static String trainingInsert() {
		return resources.getString(R.string.training_insert);
	}

	/**
	 * トレーニングログ追加のURLを取得
	 * 
	 * @return POST_TrainingLog.php?func=Insert
	 */
	public static String trainingInsertLog() {
		return resources.getString(R.string.training_insert_log);
	}

	/**
	 * トレーニングログ追加のURLを取得
	 * 
	 * @return api/addplay/
	 */
	public static String trainingInsertPlay() {
		return resources.getString(R.string.training_insert_play);
	}

	/**
	 * トレーニング取得のURLを取得
	 * 
	 * @return api/findtraining/
	 */
	public static String trainingSelect() {
		return resources.getString(R.string.training_select);
	}

	/**
	 * トレーニング複数取得のURLを取得
	 * 
	 * @return api/findtrainings/
	 */
	public static String trainingsSelect() {
		return resources.getString(R.string.trainings_select);
	}

	/**
	 * トレーニングカテゴリー取得のURLを取得
	 * 
	 * @return api/getcategorys
	 */
	public static String categorySelectAll() {
		return resources.getString(R.string.category_select_all);
	}

	/**
	 * トレーニングメニュー取得のURLを取得
	 * 
	 * @return api/getmenues
	 */
	public static String menuSelectAll() {
		return resources.getString(R.string.menu_select_all);
	}

	/**
	 * 時間
	 * 
	 * @return
	 */
	public static String time() {
		return resources.getString(R.string.time);
	}

	/**
	 * トレーニングID
	 * 
	 * @return
	 */
	public static String logTrainingId() {
		return resources.getString(R.string.log_training_id);
	}

	/**
	 * ログID
	 * 
	 * @return
	 */
	public static String trainingLogId() {
		return resources.getString(R.string.training_log_id);
	}

	/**
	 * トレーニングメニューID
	 * 
	 * @return
	 */
	public static String trainingMenuId() {
		return resources.getString(R.string.training_menu_id);
	}

	/**
	 * 開始時間
	 * 
	 * @return
	 */
	public static String startTime() {
		return resources.getString(R.string.start_time);
	}

	/**
	 * 運動時間
	 * 
	 * @return
	 */
	public static String playTime() {
		return resources.getString(R.string.play_time);
	}

	/**
	 * 目標心拍数
	 * 
	 * @return
	 */
	public static String targetHeartRate() {
		return resources.getString(R.string.target_heart_rate);
	}

	/**
	 * 目標カロリー
	 * 
	 * @return
	 */
	public static String targetCalorie() {
		return resources.getString(R.string.target_calorie);
	}

	/**
	 * 平均心拍数
	 * 
	 * @return
	 */
	public static String heartRateAvg() {
		return resources.getString(R.string.heart_rate_avg);
	}

	/**
	 * 目標運動時間
	 * 
	 * @return
	 */
	public static String targetPlayTime() {
		return resources.getString(R.string.target_play_time);
	}

	/**
	 * 心拍数
	 * 
	 * @return
	 */
	public static String heartRate() {
		return resources.getString(R.string.heart_rate);
	}

	/**
	 * 経度
	 * 
	 * @return
	 */
	public static String disX() {
		return resources.getString(R.string.dis_x);
	}

	/**
	 * 緯度
	 * 
	 * @return
	 */
	public static String disY() {
		return resources.getString(R.string.dis_y);
	}

	/**
	 * ラップ
	 * 
	 * @return
	 */
	public static String lapTime() {
		return resources.getString(R.string.lap);
	}

	/**
	 * スプリット
	 * 
	 * @return
	 */
	public static String splitTime() {
		return resources.getString(R.string.split);
	}

}
