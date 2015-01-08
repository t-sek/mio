package ac.neec.mio.dao.item.sqlite.parser;

import static ac.neec.mio.consts.Constants.disX;
import static ac.neec.mio.consts.Constants.disY;
import static ac.neec.mio.consts.Constants.heartRate;
import static ac.neec.mio.consts.Constants.id;
import static ac.neec.mio.consts.Constants.lap;
import static ac.neec.mio.consts.Constants.logId;
import static ac.neec.mio.consts.Constants.split;
import static ac.neec.mio.consts.Constants.targetHeartRate;
import static ac.neec.mio.consts.Constants.time;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.training.framework.ProductDataFactory;
import ac.neec.mio.training.log.TrainingLog;
import ac.neec.mio.training.log.TrainingLogFactory;
import android.database.Cursor;

public class TrainingLogCursorParser extends CursorParser {

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
					c.getString(indexSplit),
					c.getInt(indexTargetHeartRate)));
		}
	}

	@Override
	public List<TrainingLog> getObject() {
		return list;
	}

}
