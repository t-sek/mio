package ac.neec.mio.dao.item.sqlite.parser;

import static ac.neec.mio.consts.SQLConstants.*;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.training.category.TrainingCategory;
import ac.neec.mio.training.category.TrainingCategoryFactory;
import android.database.Cursor;

/**
 * トレーニングカテゴリーを解析するクラス
 *
 */
public class TrainingCategoryCursorParser extends CursorParser {

	/**
	 * カテゴリー
	 */
	private TrainingCategory category;

	public TrainingCategoryCursorParser(Cursor c) {
		super(c);
	}

	@Override
	protected void parse(Cursor c) {
		ProductDataFactory factory = new TrainingCategoryFactory();
		int indexTrainingCategoryId = c.getColumnIndex(trainingCategoryId());
		int indexTrainingCategoryName = c
				.getColumnIndex(trainingCategoryName());
		while (c.moveToNext()) {
			category = (TrainingCategory) factory.create(
					c.getInt(indexTrainingCategoryId),
					c.getString(indexTrainingCategoryName));
		}
		c.close();
	}

	/**
	 * @return TrainingCategory型
	 */
	@Override
	public TrainingCategory getObject() {
		return category;
	}

}
