package ac.neec.mio.dao;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Permission;
import android.content.Context;

public interface ApiDao {
	/**
	 * ユーザ情報を取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 */
	void selectUser(Context context, String userId, String password);

	/**
	 * ユーザを登録
	 * 
	 * @param userId
	 *            ユーザID
	 * @param name
	 *            名前
	 * @param birth
	 *            誕生日 yy-mm-dd形式
	 * @param gender
	 *            性別
	 * @param height
	 *            身長
	 * @param mail
	 *            メールアドレス
	 * @param password
	 *            パスワード
	 * @param weight
	 *            体重
	 */
	void insertUser(Context context, String userId, String name, String birth,
			String gender, String height, String mail, String password,
			String weight);

	/**
	 * ユーザ情報を編集
	 * 
	 * @param userId
	 *            ユーザID
	 * 
	 * @param name
	 *            名前
	 * @param birth
	 *            誕生日 yy-mm-dd形式
	 * @param height
	 *            身長
	 * @param mail
	 *            メールアドレス
	 */
	void updateUser(Context context, String userId, String name, String birth,
			String height, String mail, String password);

	/**
	 * ユーザ情報の体重を追加
	 * 
	 * @param userId
	 *            ユーザID
	 * @param weight
	 *            体重
	 */
	void updateUserWeight(Context context, String userId, String weight);

	/**
	 * ユーザ情報の安静時心拍数を追加
	 * 
	 * @param userId
	 *            ユーザID
	 * @param heartRate
	 *            安静時心拍数
	 */
	void updateUserQuietHeartRate(Context context, String userId,
			String heartRate);

	/**
	 * 全グループを取得
	 */
	void selectGroupAll();

	/**
	 * 所属している全グループを取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 */
	void selectMyGroupAll(String userId, String password);

	/**
	 * グループ情報を取得
	 * 
	 * @param groupId
	 */
	void selectGroup(String groupId);

	/**
	 * パーミッション情報を取得
	 */
	void selectPermition();

	/**
	 * グループを登録
	 * 
	 * @param groupId
	 *            グループID
	 * @param groupName
	 *            グループ名
	 * @param comment
	 *            コメント
	 */
	void insertGroup(String groupId, String groupName, String comment);

	/**
	 * グループに加入
	 * 
	 * @param userId
	 *            ユーザID
	 * @param groupId
	 *            グループID
	 * @param permitionId
	 *            パーミッションID
	 * @param password
	 *            ユーザのパスワード
	 */
	void insertGroupAffiliation(String userId, String groupId, int permitionId,
			String password);

	/**
	 * グループ情報を編集
	 * 
	 * @param groupId
	 * @param groupName
	 * @param comment
	 */
	void updateGroup(String groupId, String groupName, String comment);

	/**
	 * トレーニングログを登録
	 * 
	 * @param trainingId
	 *            トレーニングID
	 * @param heartRate
	 *            心拍数
	 * @param disX
	 *            位置情報X座標
	 * @param disY
	 *            位置情報X座標
	 * @param time
	 *            トレーニングタイム 00:00形式
	 * @param lapTime
	 *            ラップタイム
	 * @param splitTime
	 *            スプリットタイム
	 * @param trainingLogId
	 *            トレーニングログID
	 * @param targetHeartRate
	 *            目標心拍数
	 */
	void insertTrainingLog(int trainingId, int heartRate, double disX,
			double disY, String time, String lapTime, String splitTime,
			int trainingLogId, int targetHeartRate);

	/**
	 * トレーニングプレイを登録
	 * 
	 * @param trainingId
	 *            トレーニングID
	 * @param playId
	 *            プレイID
	 * @param trainingMenuId
	 *            トレーニングメニューID
	 * @param trainingTime
	 *            トレーニングプレイ時間
	 */
	void insertTrainingPlay(int trainingId, int playId, int trainingMenuId,
			String trainingTime);

	/**
	 * トレーニングを登録
	 * 
	 * @param userId
	 *            ユーザID
	 * @param date
	 *            トレーニング日 yyyy-mm-dd形式
	 * @param startTime
	 *            トレーニング開始時間 hh:mm形式
	 * @param playTime
	 *            トレーニング時間
	 * @param targetHeartRate
	 *            目標心拍数
	 * @param targetCal
	 *            目標カロリー
	 * @param heartRateAvg
	 *            平均心拍数
	 * @param targetPlayTime
	 *            目標運動時間
	 * @param cal
	 *            消費カロリー
	 * @param categoryId
	 *            カテゴリーID
	 * @param dis
	 *            走行距離
	 */
	void insertTraining(String userId, String date, String startTime,
			String playTime, int targetHeartRate, int targetCalorie,
			int heartRateAvg, String targetPlayTime, int calorie,
			int categoryId, double distance);

	/**
	 * トレーニングIDを取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @param date
	 *            日付 yyyy-mm-dd形式
	 */
	void selectTrainingId(String userId, String date);

	/**
	 * トレーニングログを取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @param trainingId
	 *            トレーニングID
	 */
	void selectTrainingLog(String userId, int trainingId);

	/**
	 * トレーニングプレイを取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @param trainingId
	 *            トレーニングID
	 */
	void selectTrainingPlay(String userId, int trainingId);

	/**
	 * 指定した日付のトレーニングを取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @param date
	 *            日付 yyyy-mm-dd形式
	 */
	void selectTraining(String userId, String date);

	/**
	 * 指定した日付のトレーニング数を取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @param date
	 *            日付 yyyy-mm-dd形式
	 */
	void selectTrainingNum(String userId, String date);

	/**
	 * トレーニングを取得
	 * 
	 * @param userId
	 *            ユーザID
	 * @param trainingId
	 *            トレーニングID
	 */
	void selectTraining(String userId, int trainingId);

	/**
	 * ユーザの全トレーニングを取得
	 * 
	 * @param userId
	 *            ユーザID
	 */
	void selectTrainingAll(String userId);

	/**
	 * トレーニングカテゴリーを取得
	 */
	void selectTrainingCategory();

	/**
	 * トレーニングメニューを取得
	 */
	void selectTrainingMenu();

	/**
	 * 取得したレスポンスを返す
	 * 
	 * @return レスポンス
	 * @throws XmlParseException
	 *             レスポンスの解析に失敗
	 * @throws XmlReadException
	 *             レスポンスの読み込みに失敗
	 */
	<T> T getResponse() throws XmlParseException, XmlReadException;

	/**
	 * キャンセル
	 */
	void cancel();
}
