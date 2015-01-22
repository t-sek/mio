package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.db.DBManager;
import ac.neec.mio.http.item.TrainingItem;
import ac.neec.mio.ui.listener.TrainingDataListCallbackListener;
import ac.neec.mio.util.DateUtil;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class TrainingDateListAdapter extends BaseExpandableListAdapter {

	private Context context;
	private TrainingDataListCallbackListener listener;
	private List<List<TrainingItem>> trainings = new ArrayList<List<TrainingItem>>();

	private int currentHourPosition = 0;
	private SQLiteDao dao;

	public TrainingDateListAdapter(Context context,
			List<List<TrainingItem>> trainings,
			TrainingDataListCallbackListener listener) {
		this.context = context;
		this.trainings = trainings;
		this.listener = listener;
		dao = DaoFacade.getSQLiteDao();
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		if (currentHourPosition != -1 && currentHourPosition != groupPosition) {
			listener.closeGroup(currentHourPosition);
		}
		currentHourPosition = groupPosition;
	}

	public View getChildGenericView() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_list_training, null);
		return view;
	}

	public View getGroupGenericView() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_list_dates, null);
		return view;
	}

	@Override
	public int getGroupCount() {
		return trainings.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return trainings.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return trainings.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return trainings.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return 0;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View view = getGroupGenericView();
		TextView textDate = (TextView) view.findViewById(R.id.txt_date);
		String date = trainings.get(groupPosition).get(0).getDate();
		textDate.setText(DateUtil.japaneseFormat(date));
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = getChildGenericView();
		TrainingItem item = trainings.get(groupPosition).get(childPosition);
		String trainingName = dao.selectTrainingCategory(item.getCategoryId())
				.getTrainingCategoryName();
		TextView textTrainingName = (TextView) view
				.findViewById(R.id.txt_training_name);
		textTrainingName.setText(trainingName);
		try {
			TextView textStartTime = (TextView) view
					.findViewById(R.id.txt_start_time);
			String time = item.getStartTime();
			textStartTime.setText(DateUtil.timeJapaneseFormat(time));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return view;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
