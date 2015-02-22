package ac.neec.mio.dao.item.sqlite.parser;

import java.util.ArrayList;
import java.util.List;

import static ac.neec.mio.consts.SQLConstants.*;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.training.play.TrainingPlay;
import ac.neec.mio.training.play.TrainingPlayFactory;
import android.database.Cursor;

/**
 * トレーニングプレイを解析するクラス
 */
public class TrainingPlayCursorParser extends CursorParser {

	/**
	 * トレーニングプレイ
	 */
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

	/**
	 * @return TrainingPlay型のリスト
	 */
	@Override
	public List<TrainingPlay> getObject() {
		return list;
	}

}
