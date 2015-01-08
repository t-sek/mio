package ac.neec.mio.dao;

import java.util.List;

import ac.neec.mio.exception.SQLiteInsertException;
import ac.neec.mio.exception.SQLiteTableConstraintException;
import ac.neec.mio.group.Permission;
import ac.neec.mio.taining.Training;
import ac.neec.mio.taining.category.TrainingCategory;
import ac.neec.mio.taining.menu.TrainingMenu;
import ac.neec.mio.taining.play.TrainingPlay;
import ac.neec.mio.training.log.TrainingLog;
import android.database.sqlite.SQLiteOpenHelper;

public interface SQLiteDao {

	/**
	 * トレーニングカテゴリーを追加
	 * 
	 * @param trainingCategoryId
	 *            カテゴリーID
	 * @param trainingCategoryName
	 *            カテゴリー名
	 * @throws SQLiteInsertException
	 *             インサートエラー
	 * @throws SQLiteTableConstraintException
	 *             テーブル制約エラー
	 */
	void insertTrainingCategory(int trainingCategoryId,
			String trainingCategoryName) throws SQLiteInsertException,
			SQLiteTableConstraintException;

	/**
	 * トレーニングメニューを追加
	 * 
	 * @param trainingMenuId
	 *            メニューID
	 * @param trainingName
	 *            メニュー名
	 * @param mets
	 *            メッツ
	 * @param trainingCategoryId
	 *            カテゴリーID
	 * @param color
	 *            運動推移で表示するカラーコード
	 * 
	 * @throws SQLiteInsertException
	 *             インサートエラー
	 * @throws SQLiteTableConstraintException
	 *             テーブル制約エラー
	 */
	void insertTrainingMenu(int trainingMenuId, String trainingName,
			float mets, int trainingCategoryId, String color)
			throws SQLiteInsertException, SQLiteTableConstraintException;

	/**
	 * 指定したカテゴリーIDのカテゴリー情報を取得
	 * 
	 * @param categoryId
	 * @return
	 */
	TrainingCategory selectTrainingCategory(int categoryId);

	/**
	 * 指定したカテゴリー名のカテゴリー情報を取得
	 * 
	 * @param name
	 * @return
	 */
	TrainingCategory selectTrainingCategory(String name);

	/**
	 * 全カテゴリーを取得
	 * 
	 * @return
	 */
	List<TrainingCategory> selectTrainingCategory();

	/**
	 * 指定したカテゴリーIDに属しているトレーニングメニューを取得
	 * 
	 * @param trainingCategoryId
	 *            カテゴリーID
	 * @return
	 */
	List<TrainingMenu> selectTrainingCategoryMenu(int trainingCategoryId);

	/**
	 * 全トレーニングメニューを取得
	 * 
	 * @return
	 */
	List<TrainingMenu> selectTrainingMenu();

	/**
	 * 指定したメニューIDのメニュー情報を取得
	 * 
	 * @param trainingMenuId
	 *            メニューID
	 * @return
	 */
	TrainingMenu selectTrainingMenu(int trainingMenuId);

	/**
	 * 指定したカテゴリーIDの名前を取得
	 * 
	 * @param trainingCategoryId
	 *            カテゴリーID
	 * @return
	 */
	String selectTrainingMenuName(int trainingCategoryId);

	/**
	 * メニュー名からトレーニングメニューを取得
	 * 
	 * @param trainingName
	 * @return
	 */
	TrainingMenu selectTrainingMenu(String menuName);

	/**
	 * 全トレーニングカテゴリーを削除
	 */
	void deleteTrainingCategoryTableAll();

	/**
	 * 全トレーニングメニューを削除
	 */
	void deleteTrainingMenuTableAll();

	/**
	 * トレーニングを追加
	 * 
	 * @param trainingCategoryId
	 *            カテゴリーID
	 * @param userId
	 *            ユーザID
	 * @param date
	 *            実施した日付 yyyy-mm-dd形式
	 * @param startTime
	 *            開始した時間 hh:mm形式
	 * @param playTime
	 *            計測した時間 hh:mm形式
	 * @param targetHeartRate
	 *            目標心拍数
	 * @param TargetCalorie
	 *            目標カロリー
	 * @param consumptionCalorie
	 *            消費カロリー
	 * @param heartRateAvg
	 *            平均心拍数
	 * @param strange
	 *            運動強度
	 * @param distance
	 *            走行距離
	 * 
	 * @throws SQLiteInsertException
	 *             インサートエラー
	 * @throws SQLiteTableConstraintException
	 *             テーブル制約エラー
	 */
	int insertTraining(int trainingCategoryId, String userId, String date,
			String startTime, String playTime, int targetHeartRate,
			int TargetCalorie, int consumptionCalorie, int heartRateAvg,
			int strange, double distance) throws SQLiteInsertException,
			SQLiteTableConstraintException;

	/**
	 * トレーニングログを追加
	 * 
	 * @param id
	 *            現在計測中の暫定トレーニングID
	 * @param heartRate
	 *            心拍数
	 * @param disX
	 *            位置情報X座標
	 * @param disY
	 *            位置情報X座標
	 * @param time
	 *            計測時間
	 * @param lap
	 *            ラップタイム
	 * @param split
	 *            スプリットタイム
	 * @param targetHeartRate
	 *            目標心拍数
	 * @throws SQLiteInsertException
	 *             インサートエラー
	 * @throws SQLiteTableConstraintException
	 *             テーブル制約エラー
	 */
	void insertTrainingLog(int id, int heartRate, double disX, double disY,
			String time, String lap, String split, int targetHeartRate)
			throws SQLiteInsertException, SQLiteTableConstraintException;

	/**
	 * トレーニングプレイを追加
	 * 
	 * @param id
	 *            現在計測中の暫定トレーニングID
	 * @param trainingMenuId
	 *            トレーニングメニューID
	 * @param trainingTime
	 *            メニューを運動した時間
	 * @throws SQLiteInsertException
	 *             インサートエラー
	 * @throws SQLiteTableConstraintException
	 *             テーブル制約エラー
	 */
	void insertTrainingPlay(int id, int trainingMenuId, int trainingTime)
			throws SQLiteInsertException, SQLiteTableConstraintException;

	/**
	 * トレーニングを更新
	 * 
	 * @param id
	 * @param trainingCategoryId
	 * @param date
	 * @param startTime
	 * @param playTime
	 * @param targetHeartRate
	 * @param targetCalorie
	 * @param consumptionCalorie
	 * @param heartRateAvg
	 * @param strange
	 * @param distance
	 */
	void updateTraining(int id, String playTime, int consumptionCalorie,
			int heartRateAvg, double distance);

	/**
	 * 全トレーニングを取得
	 * 
	 * @return
	 */
	List<Training> selectTraining();

	/**
	 * トレーニングを取得
	 * 
	 * @param id
	 */
	Training selectTraining(int id);

	/**
	 * トレーニングログを取得
	 * 
	 * @param id
	 * @return
	 */
	List<TrainingLog> selectTrainingLog(int id);

	/**
	 * トレーニングプレイを取得
	 * 
	 * @param id
	 */
	List<TrainingPlay> selectTrainingPlay(int id);

	/**
	 * 指定したトレーニングIDの心拍数の推移を取得
	 * 
	 * @param trainingId
	 */
	void selectHeartRate(int trainingId);

	/**
	 * 全トレーニングを削除
	 */
	void deleteTrainingAll();

	/**
	 * トレーニングを削除
	 * 
	 * @param id
	 *            トレーニングID
	 */
	void deleteTraining(int id);

	/**
	 * 全トレーニングログを削除
	 */
	void deleteTrainingLogAll();

	/**
	 * トレーニングログを削除
	 * 
	 * @param id
	 *            ログID
	 */
	void deleteTrainingLog(int id);

	/**
	 * 全トレーニングプレイを削除
	 */
	void deleteTrainingPlayAll();

	/**
	 * トレーニングプレイを削除
	 * 
	 * @param id
	 *            プレイID
	 */
	void deleteTrainingPlay(int id);

	/**
	 * グループの権限一覧を登録
	 * 
	 * @param perm
	 *            グループ権限
	 * @throws SQLiteInsertException
	 *             インサートエラー
	 * @throws SQLiteTableConstraintException
	 *             テーブル制約エラー
	 */
	void insertPermission(Permission perm) throws SQLiteInsertException,
			SQLiteTableConstraintException;

	/**
	 * グループ権限一覧を取得
	 * 
	 * @return グループ権限リスト
	 */
	List<Permission> selectPermission();

	/**
	 * グループ権限を取得
	 * 
	 * @param permissionId
	 *            パーミッションID
	 * @return
	 */
	Permission selectPermission(int permissionId);

	/**
	 * グループ権限一覧を削除
	 */
	void deletePermission();

}
