package ac.neec.mio.dao.item.sqlite.parser;

import java.util.ArrayList;
import java.util.List;

import static ac.neec.mio.consts.SQLConstants.*;
import ac.neec.mio.framework.ProductDataFactory;
import ac.neec.mio.training.menu.TrainingMenu;
import ac.neec.mio.training.menu.TrainingMenuFactory;
import android.database.Cursor;

/**
 * トレーニングメニューリストを解析するクラス
 */
public class TrainingMenuListCursorParser extends CursorParser {

	/**
	 * トレーニングメニューリスト
	 */
	private List<TrainingMenu> list;

	public TrainingMenuListCursorParser(Cursor c) {
		super(c);
	}

	@Override
	protected void parse(Cursor c) {
		ProductDataFactory factory = new TrainingMenuFactory();
		list = new ArrayList<TrainingMenu>();
		int indexTrainingMenuId = c.getColumnIndex(trainingMenuId());
		int indexTrainingName = c.getColumnIndex(trainingName());
		int indexMets = c.getColumnIndex(mets());
		int indexTrainingCategoryId = c.getColumnIndex(trainingCategoryId());
		int indexColor = c.getColumnIndex(color());
		while (c.moveToNext()) {
			list.add((TrainingMenu) factory.create(
					c.getInt(indexTrainingMenuId),
					c.getString(indexTrainingName), c.getFloat(indexMets),
					c.getInt(indexTrainingCategoryId), c.getString(indexColor)));
		}
		c.close();
	}

	/**
	 * @return TrainingMenu型のリスト
	 */
	@Override
	public List<TrainingMenu> getObject() {
		return list;
	}

}
