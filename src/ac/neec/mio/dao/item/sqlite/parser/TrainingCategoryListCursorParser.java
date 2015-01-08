package ac.neec.mio.dao.item.sqlite.parser;

import java.util.ArrayList;
import java.util.List;

import static ac.neec.mio.consts.Constants.*;
import ac.neec.mio.taining.category.TrainingCategory;
import ac.neec.mio.taining.category.TrainingCategoryFactory;
import ac.neec.mio.training.framework.ProductDataFactory;
import android.database.Cursor;

public class TrainingCategoryListCursorParser extends CursorParser {

	private List<TrainingCategory> list;

	public TrainingCategoryListCursorParser(Cursor c) {
		super(c);
	}

	@Override
	protected void parse(Cursor c) {
		ProductDataFactory factory = new TrainingCategoryFactory();
		list = new ArrayList<TrainingCategory>();
		int indexTrainingCategoryId = c
				.getColumnIndex(trainingCategoryId());
		int indexTrainingCategoryName = c
				.getColumnIndex(trainingCategoryName());
		while (c.moveToNext()) {
			list.add((TrainingCategory) factory.create(
					c.getInt(indexTrainingCategoryId),
					c.getString(indexTrainingCategoryName)));
		}
	}

	@Override
	public List<TrainingCategory> getObject() {
		return list;
	}

}
