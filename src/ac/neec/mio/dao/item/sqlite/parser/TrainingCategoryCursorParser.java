package ac.neec.mio.dao.item.sqlite.parser;
import static ac.neec.mio.consts.SQLConstants.*;
import ac.neec.mio.taining.category.TrainingCategory;
import ac.neec.mio.taining.category.TrainingCategoryFactory;
import ac.neec.mio.training.framework.ProductDataFactory;
import android.database.Cursor;

public class TrainingCategoryCursorParser extends CursorParser{

	private TrainingCategory category;
	
	public TrainingCategoryCursorParser(Cursor c) {
		super(c);
	}

	@Override
	protected void parse(Cursor c) {
		ProductDataFactory factory = new TrainingCategoryFactory();
		int indexTrainingCategoryId = c
				.getColumnIndex(trainingCategoryId());
		int indexTrainingCategoryName = c
				.getColumnIndex(trainingCategoryName());
		while (c.moveToNext()) {
			category = (TrainingCategory) factory.create(
					c.getInt(indexTrainingCategoryId),
					c.getString(indexTrainingCategoryName));
		}
		c.close();
	}

	@Override
	public TrainingCategory getObject() {
		return category;
	}

}
