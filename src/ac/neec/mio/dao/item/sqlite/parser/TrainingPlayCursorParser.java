package ac.neec.mio.dao.item.sqlite.parser;

import java.util.ArrayList;
import java.util.List;
import static ac.neec.mio.consts.Constants.*;
import ac.neec.mio.taining.play.TrainingPlay;
import ac.neec.mio.taining.play.TrainingPlayFactory;
import ac.neec.mio.training.framework.ProductDataFactory;
import android.database.Cursor;

public class TrainingPlayCursorParser extends CursorParser {

	private List<TrainingPlay> list;

	public TrainingPlayCursorParser(Cursor c) {
		super(c);
	}

	@Override
	protected void parse(Cursor c) {
		list = new ArrayList<TrainingPlay>();
		ProductDataFactory factory = new TrainingPlayFactory();
		int indexTrainingMenuId = c.getColumnIndex(trainingMenuId());
		int indexTrainingTime = c.getColumnIndex(trainingTime());
		while (c.moveToNext()) {
			list.add((TrainingPlay) factory.create(
					c.getInt(indexTrainingMenuId), c.getInt(indexTrainingTime)));
		}
	}

	@Override
	public List<TrainingPlay> getObject() {
		return list;
	}

}
