package ac.neec.mio.dao.item.sqlite.parser;

import static ac.neec.mio.consts.SQLConstants.*;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.training.menu.TrainingMenu;
import ac.neec.mio.training.menu.TrainingMenuFactory;
import android.database.Cursor;

/**
 * トレーニングメニューを解析するクラス
 *
 */
public class TrainingMenuCursorParser extends CursorParser {

	/**
	 * トレーニングメニュー
	 */
	private TrainingMenu menu;

	public TrainingMenuCursorParser(Cursor c) {
		super(c);
	}

	@Override
	protected void parse(Cursor c) {
		ProductDataFactory factory = new TrainingMenuFactory();
		int indexTrainingMenuId = c.getColumnIndex(trainingMenuId());
		int indexTrainingName = c.getColumnIndex(trainingName());
		int indexMets = c.getColumnIndex(mets());
		int indexTrainingCategoryId = c.getColumnIndex(trainingCategoryId());
		int indexColor = c.getColumnIndex(color());
		while (c.moveToNext()) {
			menu = (TrainingMenu) factory.create(c.getInt(indexTrainingMenuId),
					c.getString(indexTrainingName), c.getFloat(indexMets),
					c.getInt(indexTrainingCategoryId), c.getString(indexColor));
		}
		c.close();
	}

	/**
	 * @return TrainingMenu型
	 */
	@Override
	public TrainingMenu getObject() {
		return menu;
	}

}
