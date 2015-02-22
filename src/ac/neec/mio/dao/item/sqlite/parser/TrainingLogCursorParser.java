package ac.neec.mio.dao.item.sqlite.parser;

import static ac.neec.mio.consts.SQLConstants.disX;
import static ac.neec.mio.consts.SQLConstants.disY;
import static ac.neec.mio.consts.SQLConstants.heartRate;
import static ac.neec.mio.consts.SQLConstants.id;
import static ac.neec.mio.consts.SQLConstants.lap;
import static ac.neec.mio.consts.SQLConstants.logId;
import static ac.neec.mio.consts.SQLConstants.split;
import static ac.neec.mio.consts.SQLConstants.targetHeartRate;
import static ac.neec.mio.consts.SQLConstants.time;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.training.log.TrainingLog;
import ac.neec.mio.training.log.TrainingLogFactory;
import android.database.Cursor;

/**
 * トレーニングログを解析するクラス
 */
public class TrainingLogCursorParser extends CursorParser {

	/**
	 * トレーニングログ
	 */
	private List<TrainingLog> list;

	public TrainingLogCursorParser(Cursor c) {
		super(c);
	}

	@Override
	protected void parse(Cursor c) {
		ProductDataFactory factory = new TrainingLogFactory();
		list = new ArrayList<TrainingLog>();
		int indexLogId = c.getColumnIndex(logId());
		int indexId = c.getColumnIndex(id());
		int indexHeartRate = c.getColumnIndex(heartRate());
		int indexDisX = c.getColumnIndex(disX());
		int indexDisY = c.getColumnIndex(disY());
		int indexTime = c.getColumnIndex(time());
		int indexLap = c.getColumnIndex(lap());
		int indexSplit = c.getColumnIndex(split());
		int indexTargetHeartRate = c.getColumnIndex(targetHeartRate());
		while (c.moveToNext()) {
			list.add((TrainingLog) factory.create(c.getInt(indexLogId),
					c.getInt(indexId), c.getInt(indexHeartRate),
					Double.valueOf(c.getDouble(indexDisX)),
					Double.valueOf(c.getDouble(indexDisY)),
					c.getString(indexTime), c.getString(indexLap),
					c.getString(indexSplit), c.getInt(indexTargetHeartRate)));
		}
	}

	/**
	 * @return TrainingLog型のリスト
	 */
	@Override
	public List<TrainingLog> getObject() {
		return list;
	}

}
