package ac.neec.mio.dao.item.sqlite.parser;

import static ac.neec.mio.consts.SQLConstants.*;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.training.Training;
import ac.neec.mio.training.TrainingFactory;
import android.database.Cursor;

/**
 * トレーニングを解析するクラス
 * 
 */
public class TrainingCursorParser extends CursorParser {

	/**
	 * トレーニング
	 */
	private Training training;

	public TrainingCursorParser(Cursor c) {
		super(c);
	}

	@Override
	protected void parse(Cursor c) {
		ProductDataFactory factory = new TrainingFactory();
		int indexId = c.getColumnIndex(id());
		int indexTrainingId = c.getColumnIndex(trainingId());
		int indexTrainingCategoryId = c.getColumnIndex(trainingCategoryId());
		int indexUserId = c.getColumnIndex(userId());
		int indexDate = c.getColumnIndex(date());
		int indexStartTime = c.getColumnIndex(startTime());
		int indexPlayTime = c.getColumnIndex(playTime());
		int indexTargetHeartRate = c.getColumnIndex(targetHeartRate());
		int indexTargetCal = c.getColumnIndex(targetCal());
		int indexConsumptionCal = c.getColumnIndex(calorie());
		int indexHeartRateAvg = c.getColumnIndex(heartRateAvg());
		int indexStrange = c.getColumnIndex(strange());
		int indexDistance = c.getColumnIndex(distance());
		while (c.moveToNext()) {
			training = (Training) factory.create(c.getInt(indexId),
					c.getInt(indexTrainingCategoryId),
					c.getString(indexUserId), c.getString(indexDate),
					c.getString(indexStartTime), c.getString(indexPlayTime),
					c.getInt(indexTargetHeartRate), c.getInt(indexTargetCal),
					c.getInt(indexConsumptionCal), c.getInt(indexHeartRateAvg),
					c.getInt(indexStrange), c.getDouble(indexDistance));
		}
	}

	/**
	 * @return Training型
	 */
	@Override
	public Training getObject() {
		return training;
	}

}
