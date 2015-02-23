package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.training.Training;
import ac.neec.mio.ui.listener.TrainingDataListCallbackListener;
import ac.neec.mio.util.DateUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

/**
 * 過去のトレーニングを日付ごとに表示する折りたたみリストビュー設定クラス
 *
 */
public class TrainingDateListAdapter extends BaseExpandableListAdapter {

	/**
	 * コンテキスト
	 */
	private Context context;
	/**
	 * コールバックリスナー
	 */
	private TrainingDataListCallbackListener listener;
	/**
	 * 過去のトレーニング
	 */
	private List<List<Training>> trainings = new ArrayList<List<Training>>();
	/**
	 * 現在開いているタブ
	 */
	private int currentHourPosition = 0;
	/**
	 * タブの色
	 */
	private int colorTab;
	/**
	 * ローカルデータベース接続インスタンス
	 */
	private SQLiteDao dao;

	/**
	 * 
	 * @param context
	 *            コンテキスト
	 * @param trainings
	 *            トレーニングリスト
	 * @param listener
	 *            コールバックリスナー
	 * @param colorTab
	 *            タブの色
	 */
	public TrainingDateListAdapter(Context context,
			List<List<Training>> trainings,
			TrainingDataListCallbackListener listener, int colorTab) {
		this.context = context;
		this.trainings = trainings;
		this.listener = listener;
		this.colorTab = colorTab;
		dao = DaoFacade.getSQLiteDao();
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		if (currentHourPosition != -1 && currentHourPosition != groupPosition) {
			listener.closeGroup(currentHourPosition);
		}
		currentHourPosition = groupPosition;
	}

	/**
	 * 子要素のビューを取得する
	 * 
	 * @return 子要素のビュー
	 */
	public View getChildGenericView() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.item_list_training, null);
		return view;
	}

	/**
	 * 親要素のビューを取得する
	 * 
	 * @return 親要素のビュー
	 */
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
		view.setBackgroundResource(colorTab);
		return view;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		View view = getChildGenericView();
		Training item = trainings.get(groupPosition).get(childPosition);
		String trainingName = dao.selectTrainingCategory(item.getCategoryId())
				.getTrainingCategoryName();
		TextView textTrainingName = (TextView) view
				.findViewById(R.id.txt_training_name);
		textTrainingName.setText(trainingName);
		try {
			TextView textStartTime = (TextView) view
					.findViewById(R.id.txt_start_time);
			String time = item.getStartTime();
			Log.d("adapter", "startTime " + time);
			textStartTime.setText(DateUtil.trainingTimeJapaneseFormat(time));
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
