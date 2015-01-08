package ac.neec.mio.util;

import java.util.List;
import java.util.concurrent.TimeUnit;

import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.db.DBManager;
import ac.neec.mio.http.item.TrainingPlayItem;
import ac.neec.mio.taining.menu.TrainingMenu;
import ac.neec.mio.taining.play.TrainingPlay;
import android.content.Context;

public class CalorieUtil {
	private static final float COEFFICIENT = 1.05F;

	public static int calcCalorie(float mets, float hour, float weight) {
		return (int) Math.floor(mets * hour * weight * COEFFICIENT);
	}

	public static int calcCalorie(float mets, long msecTime, float weight) {
		float hour = TimeUnit.HOURS.convert(msecTime, TimeUnit.MILLISECONDS);
		float min = TimeUnit.MINUTES.convert(msecTime, TimeUnit.MILLISECONDS) % 60;
		float sec = TimeUnit.SECONDS.convert(msecTime, TimeUnit.MILLISECONDS) % 60;
		min += sec / 60;
		hour += min / 60;
		return calcCalorie(mets, hour, weight);
	}

	public static int calcPlayItemCalorie(Context context,
			List<TrainingPlayItem> list, float weight, int time) {
		SQLiteDao dao = DaoFacade.getSQLiteDao(context);
		int calorie = 0;
		for (TrainingPlayItem trainingPlayItem : list) {
			// TrainingMenu menu = DBManager.selectTrainingMenu(trainingPlayItem
			// .getTrainingMenuId());
			TrainingMenu menu = dao.selectTrainingMenu(trainingPlayItem
					.getTrainingMenuId());
			if (menu != null) {
				calorie += calcCalorie(menu.getMets(),
						Long.valueOf(time * 1000), weight);
				return 0;
			}
			if (trainingPlayItem.getTrainingTime() >= time) {
				return calorie;
			}
			time -= trainingPlayItem.getTrainingTime();
		}
		return calorie;
	}

	public static int calcPlayCalorie(Context context, List<TrainingPlay> list,
			float weight, int time) {
		SQLiteDao dao = DaoFacade.getSQLiteDao(context);
		int calorie = 0;
		for (TrainingPlay trainingPlayItem : list) {
			// TrainingMenu menu = DBManager.selectTrainingMenu(trainingPlayItem
			// .getTrainingMenuId());
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

	public static int calcCalorie(Context context, List<TrainingPlay> list,
			float weight) {
		SQLiteDao dao = DaoFacade.getSQLiteDao(context);
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
