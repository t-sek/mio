package ac.neec.mio.dao.item.sqlite.parser;

import java.util.ArrayList;
import java.util.List;

import static ac.neec.mio.consts.SQLConstants.*;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.training.category.TrainingCategory;
import ac.neec.mio.training.category.TrainingCategoryFactory;
import android.database.Cursor;

/**
 * トレーニングカテゴリーリストを解析するクラス
 */
public class TrainingCategoryListCursorParser extends CursorParser {

	/**
	 * カテゴリーリスト
	 */
	private List<TrainingCategory> list;

	public TrainingCategoryListCursorParser(Cursor c) {
		super(c);
	}

	@Override
	protected void parse(Cursor c) {
		ProductDataFactory factory = new TrainingCategoryFactory();
		list = new ArrayList<TrainingCategory>();
		int indexTrainingCategoryId = c.getColumnIndex(trainingCategoryId());
		int indexTrainingCategoryName = c
				.getColumnIndex(trainingCategoryName());
		while (c.moveToNext()) {
			list.add((TrainingCategory) factory.create(
					c.getInt(indexTrainingCategoryId),
					c.getString(indexTrainingCategoryName)));
		}
	}

	/**
	 * @return TrainingCategory型のリスト
	 */
	@Override
	public List<TrainingCategory> getObject() {
		return list;
	}

}
