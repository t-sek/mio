package ac.neec.mio.dao;

import java.util.List;

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
import android.graphics.Bitmap;

/**
 * ローカルデータベースに接続するインターフェース
 *
 */
public interface SQLiteDao {

	/**
	 * トレーニングカテゴリーを追加する
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
	 * トレーニングメニューを追加する
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
	 * 指定したカテゴリーIDのカテゴリー情報を取得する
	 * 
	 * @param categoryId
	 *            カテゴリーID
	 * @return TrainingCategory型
	 */
	TrainingCategory selectTrainingCategory(int categoryId);

	/**
	 * 指定したカテゴリー名のカテゴリー情報を取得する
	 * 
	 * @param name
	 *            カテゴリー名
	 * @return TrainingCategory型
	 */
	TrainingCategory selectTrainingCategory(String name);

	/**
	 * 全カテゴリーを取得する
	 * 
	 * @return TrainingCategory型リスト
	 */
	List<TrainingCategory> selectTrainingCategory();

	/**
	 * 指定したカテゴリーIDに属しているトレーニングメニューを取得する
	 * 
	 * @param trainingCategoryId
	 *            カテゴリーID
	 * @return TrainingMenu型リスト
	 */
	List<TrainingMenu> selectTrainingCategoryMenu(int trainingCategoryId);

	/**
	 * 全トレーニングメニューを取得する
	 * 
	 * @return TrainingMenu型リスト
	 */
	List<TrainingMenu> selectTrainingMenu();

	/**
	 * 指定したメニューIDのメニュー情報を取得する
	 * 
	 * @param trainingMenuId
	 *            メニューID
	 * @return TrainingMenu型
	 */
	TrainingMenu selectTrainingMenu(int trainingMenuId);

	/**
	 * 指定したカテゴリーIDの名前を取得する
	 * 
	 * @param trainingCategoryId
	 *            カテゴリーID
	 * @return メニュー名
	 */
	String selectTrainingMenuName(int trainingCategoryId);

	/**
	 * メニュー名からトレーニングメニューを取得する
	 * 
	 * @param trainingName
	 *            メニュー名
	 * @return TrainingMenu型
	 */
	TrainingMenu selectTrainingMenu(String menuName);

	/**
	 * 全トレーニングカテゴリーを削除する
	 */
	void deleteTrainingCategoryTableAll();

	/**
	 * 全トレーニングメニューを削除する
	 */
	void deleteTrainingMenuTableAll();

	/**
	 * トレーニングを追加する
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
	 * トレーニングログを追加する
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
	 * トレーニングプレイを追加する
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
	 * トレーニングを更新する
	 * 
	 * @param id
	 *            トレーニングID
	 * @param playTime
	 *            運動時間 sec
	 * @param calorie
	 *            カロリー
	 * @param targetHeartRate
	 *            目標心拍数
	 * @param heartRateAvg
	 *            平均心拍数
	 * @param distance
	 *            走行距離
	 */
	void updateTraining(int id, String playTime, int calorie,
			int targetHeartRate, int heartRateAvg, double distance);

	/**
	 * 全トレーニングを取得する
	 * 
	 * @return Training型リスト
	 */
	List<Training> selectTraining();

	/**
	 * トレーニングを取得する
	 * 
	 * @param id
	 *            トレーニングID
	 */
	Training selectTraining(int id);

	/**
	 * トレーニングログを取得
	 * 
	 * @param id
	 *            トレーニングID
	 * @return TrainingLog型リスト
	 */
	List<TrainingLog> selectTrainingLog(int id);

	/**
	 * トレーニングプレイを取得する
	 * 
	 * @param id
	 *            トレーニングID
	 */
	List<TrainingPlay> selectTrainingPlay(int id);

	/**
	 * 指定したトレーニングIDの心拍数の推移を取得する
	 * 
	 * @param trainingId
	 *            トレーニングID
	 */
	void selectHeartRate(int trainingId);

	/**
	 * 全トレーニングを削除する
	 */
	void deleteTrainingAll();

	/**
	 * トレーニングを削除する
	 * 
	 * @param id
	 *            トレーニングID
	 */
	void deleteTraining(int id);

	/**
	 * 全トレーニングログを削除する
	 */
	void deleteTrainingLogAll();

	/**
	 * トレーニングログを削除する
	 * 
	 * @param id
	 *            トレーニングID
	 */
	void deleteTrainingLog(int id);

	/**
	 * 全トレーニングプレイを削除する
	 */
	void deleteTrainingPlayAll();

	/**
	 * トレーニングプレイを削除する
	 * 
	 * @param id
	 *            トレーニングID
	 */
	void deleteTrainingPlay(int id);

	/**
	 * グループの権限一覧を登録する
	 * 
	 * @param perm
	 *            権限
	 * @throws SQLiteInsertException
	 *             インサートエラー
	 * @throws SQLiteTableConstraintException
	 *             テーブル制約エラー
	 */
	void insertPermission(Permission perm) throws SQLiteInsertException,
			SQLiteTableConstraintException;

	/**
	 * グループ権限一覧を取得する
	 * 
	 * @return 権限リスト
	 */
	List<Permission> selectPermission();

	/**
	 * グループ権限を取得する
	 * 
	 * @param permissionId
	 *            権限ID
	 * @return Permission型
	 */
	Permission selectPermission(int permissionId);

	/**
	 * グループ権限一覧を削除する
	 */
	void deletePermission();

	/**
	 * グループを登録する
	 * 
	 * @param groupId
	 *            グループID
	 * @param name
	 *            グループ名
	 * @param comment
	 *            コメント
	 * @param userId
	 *            作成者ID
	 * @param created
	 *            作成日 yy-mm-dd形式
	 * @param permissionId
	 *            端末ユーザの権限ID
	 * 
	 * @throws SQLiteInsertException
	 *             インサートエラー
	 * @throws SQLiteTableConstraintException
	 *             テーブル制約エラー
	 */
	void insertGroup(String groupId, String name, String comment,
			String userId, String created, int permissionId)
			throws SQLiteInsertException, SQLiteTableConstraintException;

	/**
	 * 全グループを取得する
	 * 
	 * @return Group型リスト
	 */
	List<Group> selectGroup();

	/**
	 * 加入済みグループを取得する
	 * 
	 * @return Group型リスト
	 */
	List<Group> selectGroupJoin();

	/**
	 * グループを取得する
	 * 
	 * @param groupId
	 *            グループID
	 * @return Group型
	 */
	Group selectGroup(String groupId);

	/**
	 * グループメンバーを登録する
	 * 
	 * @param groupId
	 *            グループID
	 * @param userId
	 *            ユーザID
	 * @param userName
	 *            ユーザ名
	 * @param permissionId
	 *            権限ID
	 * @param image
	 *            プロフィール画像
	 */
	void insertGroupMember(String groupId, String userId, String userName,
			int permissionId, Bitmap image) throws SQLiteInsertException,
			SQLiteTableConstraintException;

	/**
	 * グループメンバーを取得する
	 * 
	 * @param groupId
	 *            グループID
	 * @return MemberInfo型リスト
	 */
	List<MemberInfo> selectGroupMember(String groupId);

	/**
	 * 指定した権限IDのグループメンバーを取得する
	 * 
	 * @param groupId
	 *            グループID
	 * @param permissionId
	 *            権限ID
	 * @return MemberInfo型リスト
	 */
	List<MemberInfo> selectGroupMember(String groupId, int permissionId);

	void deleteGroupMember(String groupId);

	void deleteGroupMember();

	/**
	 * 全グループ削除する
	 */
	void deleteGroup();

	/**
	 * 
	 * グループ権限を登録する
	 * 
	 * @param groupId
	 *            グループId
	 * @param permissionId
	 *            権限ID
	 * 
	 * @throws SQLiteInsertException
	 *             インサートエラー
	 * @throws SQLiteTableConstraintException
	 *             テーブル制約エラー
	 */
	void insertAffiliation(String groupId, int permissionId)
			throws SQLiteInsertException, SQLiteTableConstraintException;

	/**
	 * グループ権限を取得する
	 * 
	 * @param groupId
	 *            グループID
	 * @return Affiliation型
	 */
	Affiliation selectAffiliation(String groupId);

	/**
	 * 全グループ権限を取得する
	 * 
	 * @return Affiliation型リスト
	 */
	List<Affiliation> selectAffiliation();

	/**
	 * 全グループ権限を削除する
	 */
	void deleteAffiliation();

}
