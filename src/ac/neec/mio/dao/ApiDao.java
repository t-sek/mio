package ac.neec.mio.dao;

import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.Permission;
import android.content.Context;

/**
 * WebAPIに接続するインターフェース
 *
 */
public interface ApiDao {
	/**
	 * ユーザ情報を取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 */
	void selectUser(String userId, String password);

	/**
	 * ユーザの体重の推移を取得する
	 * 
	 * @param context
	 *            コンテキスト
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 * @param date1
	 *            開始日 yy-mm-dd形式
	 * @param date2
	 *            終了日 yy-mm-dd形式
	 */
	void selectWeight(String userId, String password, String date1, String date2);

	/**
	 * ユーザを登録する
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
	void insertUser(String userId, String name, String birth, String gender,
			String height, String mail, String password);

	/**
	 * ユーザのプロフィール画像を追加する
	 * 
	 * @param context
	 *            コンテキスト
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 * @param filePath
	 *            ファイルパス
	 */
	void insertUserImage(String userId, String password, String filePath);

	/**
	 * ユーザ情報を編集する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param name
	 *            名前
	 * @param birth
	 *            誕生日 yy-mm-dd形式
	 * @param height
	 *            身長
	 * @param mail
	 *            メールアドレス
	 */
	void updateUser(String userId, String name, String birth, String height,
			String mail, String password);

	/**
	 * ユーザ情報の体重を追加する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param weight
	 *            体重
	 */
	void updateUserWeight(String userId, String password, String weight);

	/**
	 * ユーザ情報の安静時心拍数を追加する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param heartRate
	 *            安静時心拍数
	 */
	void updateUserQuietHeartRate(String userId, String password,
			String heartRate);

	/**
	 * 全グループを取得する
	 */
	void selectGroupAll();

	/**
	 * 所属している全グループを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 */
	void selectMyGroupAll(String userId, String password);

	/**
	 * グループ情報を取得する
	 * 
	 * @param groupId
	 *            グループID
	 */
	void selectGroup(String groupId);

	/**
	 * パーミッション情報を取得する
	 */
	void selectPermition();

	/**
	 * グループを登録する
	 * 
	 * @param groupId
	 *            グループID
	 * @param groupName
	 *            グループ名
	 * @param comment
	 *            コメント
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 */
	void insertGroup(String groupId, String groupName, String comment,
			String userId, String password);

	/**
	 * グループに加入申請する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param targetUserId
	 *            対象ユーザID
	 * @param groupId
	 *            グループID
	 * @param password
	 *            パスワード
	 */
	void insertGroupPending(String userId, String targetUserId, String groupId,
			String password);

	/**
	 * グループにトレーナーを追加する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param targetUserId
	 *            対象ユーザID
	 * @param groupId
	 *            グループID
	 * @param password
	 *            パスワード
	 */
	void insertGroupTrainer(String userId, String targetUserId, String groupId,
			String password);

	/**
	 * グループにメンバーを追加する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param targetUserId
	 *            対象ユーザID
	 * @param groupId
	 *            グループID
	 * @param password
	 *            パスワード
	 */
	void insertGroupMember(String userId, String targetUserId, String groupId,
			String password);

	/**
	 * グループに管理者を追加する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param targetUserId
	 *            対象ユーザID
	 * @param groupId
	 *            グループID
	 * @param password
	 *            パスワード
	 */
	void insertGroupAdmin(String userId, String targetUserId, String groupId,
			String password);

	/**
	 * グループ情報を編集する
	 * 
	 * @param groupId
	 * @param groupName
	 * @param comment
	 */
	void updateGroup(String groupId, String groupName, String comment);

	/**
	 * グループからメンバーを退会する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param targetUserId
	 *            対象ユーザID
	 * @param groupId
	 *            グループID
	 * @param password
	 *            パスワード
	 */
	void deleteGroupMember(String userId, String targetUserId, String groupId,
			String password);

	/**
	 * グループを削除する
	 * 
	 * @param groupId
	 *            グループID
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
	 */
	void deleteGroup(String groupId, String userId, String password);

	/**
	 * トレーニングログを登録する
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
	 * トレーニングプレイを登録する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param trainingId
	 *            トレーニングID
	 * @param playId
	 *            プレイID
	 * @param trainingMenuId
	 *            トレーニングメニューID
	 * @param trainingTime
	 *            トレーニングプレイ時間
	 * @param password
	 *            パスワード
	 */
	void insertTrainingPlay(String userId, int trainingId, int playId,
			int trainingMenuId, int trainingTime, String password);

	/**
	 * トレーニングを登録する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param password
	 *            パスワード
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
	void insertTraining(String userId, String password, String date,
			String startTime, String playTime, int targetHeartRate,
			int targetCalorie, int heartRateAvg, String targetPlayTime,
			int calorie, int categoryId, double distance);

	/**
	 * 指定した期間のトレーニングを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param targetUserId
	 *            対象ユーザID
	 * @param date1
	 *            開始日 yyyy-mm-dd形式
	 * @param date2
	 *            終了日 yyyy-mm-dd形式
	 * @param limit
	 *            取得数
	 * @param offset
	 *            開始点
	 * @param password
	 *            パスワード
	 */
	void selectTraining(String userId, String targetUserId, String date1,
			String date2, int limit, int offset, String password);

	/**
	 * トレーニングを取得する
	 * 
	 * @param userId
	 *            ユーザID
	 * @param targetUserId
	 *            対象ユーザID
	 * @param trainingId
	 *            トレーニングID
	 * @param password
	 *            パスワード
	 */
	void selectTraining(String userId, String targetUserId, int trainingId,
			String password);

	/**
	 * トレーニングカテゴリーを取得する
	 */
	void selectTrainingCategory();

	/**
	 * トレーニングメニューを取得する
	 */
	void selectTrainingMenu();

	/**
	 * 画像を取得する
	 * 
	 * @param image
	 *            サーバーに保存されている画像名
	 */
	void selectImage(String image);

	/**
	 * テスト用メソッド<br>
	 * 取得したXMLをログに出力する
	 * 
	 * @param url
	 */
	void test(String url);

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
	 * 中断する
	 */
	void cancel();
}
