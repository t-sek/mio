package ac.neec.mio.util;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.training.log.TrainingLog;
import android.content.Context;
import android.util.Log;

/**
 * 心拍数ユーティリティークラス
 */
public class HeartRateUtil {

	/**
	 * 目標心拍数算出(カルボーネン法)
	 * 
	 * @param age
	 *            年齢
	 * @param quietHeartRate
	 *            安静時心拍数
	 * @param strong
	 *            運動強度
	 */
	public static int calcTargetHeartRate(int age, int quietHeartRate,
			float strong) {
		return (int) (((220 - age) - quietHeartRate) * (strong / 100) + quietHeartRate);
	}

	/**
	 * グラフ表示に適したサイズにする
	 * 
	 * @param heartRate
	 *            heart rate list
	 * @param length
	 */
	public static List<TrainingLog> fitHeartRates(List<TrainingLog> heartRate,
			int length) {
		if (heartRate.size() <= length) {
			return heartRate;
		}
		List<TrainingLog> list = new ArrayList<TrainingLog>();
		int spase = heartRate.size() / length;
		for (int i = spase; i < heartRate.size(); i += spase) {
			list.add(heartRate.get(i));
			if (list.size() >= length) {
				return list;
			}
		}
		return list;
	}

	/**
	 * 安静時心拍数を算出する
	 * 
	 * @param heartRates
	 *            heart rate list
	 */
	public static int calcQuietHeartRate(List<Integer> heartRates) {
		int heartRate = 0;
		for (Integer integer : heartRates) {
			heartRate += integer;
		}
		return heartRate / heartRates.size();
	}

	/**
	 * トレーニングIDからそのトレーニングの平均心拍数を算出する
	 * 
	 * @param id
	 *            トレーニングID
	 * @return 平均心拍数
	 */
	public static int heartRateAvg(int id) {
		SQLiteDao dao = DaoFacade.getSQLiteDao();
		int num = 0;
		List<TrainingLog> log = dao.selectTrainingLog(id);
		int size = log.size();
		for (TrainingLog trainingLog : log) {
			num += trainingLog.getHeartRate();
		}
		return num / size;
	}

}
