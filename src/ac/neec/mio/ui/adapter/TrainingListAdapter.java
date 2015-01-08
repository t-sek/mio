package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.db.DBManager;
import ac.neec.mio.taining.menu.TrainingMenu;
import ac.neec.mio.ui.listener.TrainingSettingCallbackListener;
import ac.neec.mio.user.User;
import ac.neec.mio.util.CalorieUtil;
import ac.neec.mio.util.HeartRateUtil;
import ac.neec.mio.util.TimeUtil;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TrainingListAdapter extends BaseExpandableListAdapter {

	private static final String LIST_STRONG = "運動強度";
	private static final String LIST_STRONG_UNIT = "%";
	private static final String LIST_TIME = "時間";
	private static final String LIST_TIME_UNIT = " ";
	private static final String LIST_CALORIE = "目標消費カロリー";
	private static final String LIST_CALORIE_UNIT = "kcal";
	private static final String LIST_DATA_INIT = "---";
	private String strongData = LIST_DATA_INIT;
	private String timeData = LIST_DATA_INIT;
	private String calorieData = LIST_DATA_INIT;

//	private User user = User.getInstance();

	private int currentHourPosition = -1;
	private int currentDetailPosition = -1;
	private int currenetChildPosition = -1;
	private int showDialogId;
	private int trainingId;

	private ListView listView;
	private SettingListAdapter settingListAdapter;
	private SettingListItem[] settingList = new SettingListItem[3];
	private View childView;

	private Context context;
	private List<String> groups = new ArrayList<String>();
	private List<List<TrainingMenu>> children = new ArrayList<List<TrainingMenu>>();
	private TrainingSettingCallbackListener listener;
	private LinearLayout[] layoutBank = new LinearLayout[30];
	private View[] viewBank = new View[30];
	private TextView textTargetHeartRate;
	private TextView textCalorie;

	public TrainingListAdapter(Context context, List<String> groups,
			List<List<TrainingMenu>> children,
			TrainingSettingCallbackListener listener, int trainingId) {
		this.context = context;
		this.groups = groups;
		this.children = children;
		this.listener = listener;
		this.trainingId = trainingId;
		initSettingList();
	}

	public View getGenericView() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.list_item_setting, null);
		return view;
	}

	public View getGroupGenericView() {
		View view = LayoutInflater.from(context).inflate(
				R.layout.view_training_group, null);
		return view;
	}

	@Override
	public void onGroupExpanded(int groupPosition) {
		if (currentHourPosition != -1 && currentHourPosition != groupPosition) {
			listener.collapseGroupItem(currentHourPosition);
		}
		currentHourPosition = groupPosition;
		currentDetailPosition = -1;
		initSettingList();
		Arrays.fill(viewBank, null);
		Arrays.fill(viewBank, null);
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		View groupView = getGroupGenericView();
		TextView textName = (TextView) groupView
				.findViewById(R.id.txt_group_name);
		textName.setText(getGroup(groupPosition).toString());
		return groupView;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		initSettingList();
		childView = getGenericView();
		// Animation anim = AnimationUtils.loadAnimation(context,
		// R.anim.list_child_item);
		// childView.startAnimation(anim);
		TextView textName = (TextView) childView
				.findViewById(R.id.txt_setting_name);
		final TrainingMenu menu = children.get(groupPosition)
				.get(childPosition);
		textName.setText(menu.getTrainingName());
		TextView textMets = (TextView) childView
				.findViewById(R.id.txt_setting_mets);
		textMets.setText(String.valueOf(menu.getMets()));
		TextView textDetailMets = (TextView) childView
				.findViewById(R.id.txt_detail_mets);
		textDetailMets.setText(String.valueOf(menu.getMets()));
		textTargetHeartRate = (TextView) childView
				.findViewById(R.id.txt_detail_heart_rate);
		textCalorie = (TextView) childView
				.findViewById(R.id.txt_detail_calorie);
		listView = (ListView) childView.findViewById(R.id.list_setting);
		Button buttonMeasurement = (Button) childView
				.findViewById(R.id.btn_measurement_start);
		buttonMeasurement.setVisibility(View.INVISIBLE);
		buttonMeasurement.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (insertCheck()) {
					insertTraining(menu);
				} else {
					listener.showAlertDialog();
				}
			}
		});
		LinearLayout layout = (LinearLayout) childView
				.findViewById(R.id.include_setting);
		layout.setVisibility(View.GONE);
		layoutBank[childPosition] = layout;
		viewBank[childPosition] = childView;
		setSettingAdapter(listView);
		initSettingList();

		return childView;
	}

	private void insertTraining(TrainingMenu menu) {
		int strong = 0;
		int calorie = 0;
		if (settingList[0].getData() != LIST_DATA_INIT) {
			strong = Integer.valueOf(settingList[0].getData());
		}
		if (settingList[2].getData() != LIST_DATA_INIT) {
			calorie = Integer.valueOf(settingList[2].getData());
		}
		listener.onMeasurement(menu.getTrainingMenuId(),
				Integer.valueOf(textTargetHeartRate.getText().toString()),
				calorie, strong);
//		listener.onMeasurement(DBManager.insertTraining(trainingId,
//				menu.getTrainingMenuId(), user.getId(), null, null, null,
//				Integer.valueOf(textTargetHeartRate.getText().toString()),
//				calorie, 0, 0, strong, 0));
	}

	private void setSettingList() {
		settingList[0] = new SettingListItem(LIST_STRONG, strongData,
				LIST_STRONG_UNIT);
		settingList[1] = new SettingListItem(LIST_TIME, timeData,
				LIST_TIME_UNIT);
		settingList[2] = new SettingListItem(LIST_CALORIE, calorieData,
				LIST_CALORIE_UNIT);
	}

	private void initSettingList() {
		Arrays.fill(settingList, null);
		setSettingList();
	}

	private void setSettingAdapter(ListView listView) {
		settingListAdapter = new SettingListAdapter(context,
				R.layout.include_training_menu_detail_setting, settingList);
		listView = (ListView) childView.findViewById(R.id.list_setting);
		listView.setAdapter(settingListAdapter);
		settingListAdapter.notifyDataSetChanged();

		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				showDialogId = arg2;
				listener.showDialog(settingList[arg2].getName());
			}
		});
	}

	private boolean insertCheck() {
		for (int i = 0; i < settingList.length; i++) {
			Log.e("insertCheck", "data " + settingList[i].getName() + " "
					+ settingList[i].getData());
			if (settingList[i].getData() == LIST_DATA_INIT) {
				return false;
			}
		}
		return true;
	}

	/*
	 * 詳細設定リストを表示する
	 */
	public void setDetailSetting(int groupPosition, int childPosition) {
		this.currenetChildPosition = childPosition;
		if (currentDetailPosition != -1
				&& currentDetailPosition != childPosition) {
			layoutBank[currentDetailPosition].setVisibility(View.GONE);
			viewBank[currentDetailPosition].findViewById(
					R.id.btn_measurement_start).setVisibility(View.INVISIBLE);
			viewBank[currentDetailPosition].findViewById(R.id.txt_setting_mets)
					.setVisibility(View.VISIBLE);
		}

		initSettingList();
		currentDetailPosition = childPosition;
		childView = viewBank[currentDetailPosition];

		layoutBank[childPosition].setVisibility(View.VISIBLE);
		childView.findViewById(R.id.btn_measurement_start).setVisibility(
				View.VISIBLE);
		childView.findViewById(R.id.txt_setting_mets).setVisibility(
				View.INVISIBLE);
		Animation anim = AnimationUtils.loadAnimation(context,
				R.anim.list_detail_setting_item);
		layoutBank[childPosition].startAnimation(anim);
		settingListAdapter.notifyDataSetChanged();
		initDetailData();
	}

	private void initDetailData() {
		textTargetHeartRate = (TextView) viewBank[currenetChildPosition]
				.findViewById(R.id.txt_detail_heart_rate);
		textTargetHeartRate.setText(LIST_DATA_INIT);
		textCalorie = (TextView) viewBank[currenetChildPosition]
				.findViewById(R.id.txt_detail_calorie);
		textCalorie.setText(LIST_DATA_INIT);
	}

	public void updateSettingList(String name, String data) {
		settingList[showDialogId].setData(data);
		Log.e("adapter", "data " + data);
		if (showDialogId == 0 && data != null) {
			textTargetHeartRate = (TextView) viewBank[currenetChildPosition]
					.findViewById(R.id.txt_detail_heart_rate);
//			textTargetHeartRate.setText(String.valueOf(HeartRateUtil
//					.calcTargetHeartRate(user.getAge(), user.getBodily()
//							.getQuietHeartRate(), Float.valueOf(data))));
		} else if (showDialogId == 2) {
		}
		setSettingAdapter(listView);
		showDialogId = 0;
	}

	public void updateSettingList(String name, int hour, int min) {

		settingList[showDialogId].setData(TimeUtil.integerToString(hour, min));

		TextView textCalorie = (TextView) viewBank[currenetChildPosition]
				.findViewById(R.id.txt_detail_calorie);
		TextView textMets = (TextView) viewBank[currenetChildPosition]
				.findViewById(R.id.txt_detail_mets);
		float mets = Float.valueOf((String) textMets.getText());
		if (mets != 0) {
//			textCalorie.setText(String.valueOf(CalorieUtil.calcCalorie(mets,
//					TimeUtil.integerToLong(hour, min, 0), (float) user
//							.getBodily().getWeight())));
		}

		// textTargetHeartRate.setText(CalorieUtil.calcCalorie(mets, (long)
		// hour,
		// user.getBodily().getWeight()));
		// setSettingList();
		setSettingAdapter(listView);
		// initSettingList();
		showDialogId = 0;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public int getGroupCount() {
		return children.size();
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return children.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return groups.get(groupPosition);
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return children.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return groupPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

}
