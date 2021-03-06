package ac.neec.mio.dao.item.sqlite.parser;

import java.util.ArrayList;
import java.util.List;

import static ac.neec.mio.consts.SQLConstants.*;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.training.Training;
import ac.neec.mio.training.TrainingFactory;
import android.database.Cursor;

/**
 * トレーニングリストを解析するクラス
 */
public class TrainingListCursorParser extends CursorParser {

	/**
	 * トレーニングリスト
	 */
	private List<Training> trainings;

	public TrainingListCursorParser(Cursor c) {
		super(c);
	}

	@Override
	protected void parse(Cursor c) {
		ProductDataFactory factory = new TrainingFactory();
		trainings = new ArrayList<Training>();
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
			trainings.add((Training) factory.create(c.getInt(indexId),
					c.getInt(indexTrainingCategoryId),
					c.getString(indexUserId), c.getString(indexDate),
					c.getString(indexStartTime), c.getString(indexPlayTime),
					c.getInt(indexTargetHeartRate), c.getInt(indexTargetCal),
					c.getInt(indexConsumptionCal), c.getInt(indexHeartRateAvg),
					c.getInt(indexStrange), c.getDouble(indexDistance)));
		}
	}

	/**
	 * @return Training型のリスト
	 */
	@Override
	public List<Training> getObject() {
		return trainings;
	}

}
