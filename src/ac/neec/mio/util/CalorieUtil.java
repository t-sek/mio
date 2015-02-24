package ac.neec.mio.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.training.menu.TrainingMenu;
import ac.neec.mio.training.play.TrainingPlay;
import android.content.Context;

/**
 * カロリーユーティリティークラス
 */
public class CalorieUtil {
	/**
	 * カロリー計算単位
	 */
	private static final float COEFFICIENT = 1.05F;

	/**
	 * 消費カロリーを算出する
	 * 
	 * @param mets
	 *            トレーニングメニューメッツ
	 * @param hour
	 *            運動時間
	 * @param weight
	 *            体重
	 * @return 消費カロリー
	 */
	public static int calcCalorie(float mets, float hour, float weight) {
		return (int) Math.floor(mets * hour * weight * COEFFICIENT);
	}

	/**
	 * 消費カロリーを算出する
	 * 
	 * @param mets
	 *            トレーニングメニューメッツ
	 * @param msecTime
	 *            運動時間(秒)
	 * @param weight
	 *            体重
	 * @return 消費カロリー
	 */
	public static int calcCalorie(float mets, long msecTime, float weight) {
		float hour = TimeUnit.HOURS.convert(msecTime, TimeUnit.MILLISECONDS);
		float min = TimeUnit.MINUTES.convert(msecTime, TimeUnit.MILLISECONDS) % 60;
		float sec = TimeUnit.SECONDS.convert(msecTime, TimeUnit.MILLISECONDS) % 60;
		min += sec / 60;
		hour += min / 60;
		return calcCalorie(mets, hour, weight);
	}

	/**
	 * カロリーを算出する
	 * 
	 * @param list
	 *            トレーニングメニューリスト
	 * @param weight
	 *            体重
	 * @param time
	 *            消費カロリーを取得したい計測時間
	 * @return 消費カロリー
	 */
	public static int calcPlayCalorie(List<TrainingPlay> list, float weight,
			int time) {
		SQLiteDao dao = DaoFacade.getSQLiteDao();
		int calorie = 0;
		for (TrainingPlay trainingPlayItem : list) {
			TrainingMenu menu = dao.selectTrainingMenu(trainingPlayItem
					.getTrainingMenuId());
			calorie += calcCalorie(menu.getMets(), Long.valueOf(time * 1000),
					weight);
			if (trainingPlayItem.getTrainingTime() >= time) {
				return calorie;
			}
			time -= trainingPlayItem.getTrainingTime();
		}
		return calorie;
	}

	/**
	 * カロリーを算出する
	 * 
	 * @param list
	 *            トレーニングメニューリスト
	 * @param weight
	 *            体重
	 * @return 消費カロリー
	 */
	public static int calcCalorie(List<TrainingPlay> list, float weight) {
		SQLiteDao dao = DaoFacade.getSQLiteDao();
		int calorie = 0;
		for (TrainingPlay trainingPlayItem : list) {
			// TrainingMenu menu = DBManager.selectTrainingMenu(trainingPlayItem
			// .getTrainingMenuId());
			TrainingMenu menu = dao.selectTrainingMenu(trainingPlayItem
					.getTrainingMenuId());
			calorie += calcCalorie(menu.getMets(),
					Long.valueOf(trainingPlayItem.getTrainingTime()), weight);
		}
		return calorie;
	}
}
