package ac.neec.mio.ui.fragment.top;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.Constants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.item.api.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.http.item.TrainingItem;
import ac.neec.mio.ui.activity.SyncTrainingListActivity;
import ac.neec.mio.ui.activity.TrainingDataDetailActivity;
import ac.neec.mio.ui.activity.TrainingFreeInsertActivity;
import ac.neec.mio.ui.adapter.TrainingDateListAdapter;
import ac.neec.mio.ui.listener.TrainingDataListCallbackListener;
import ac.neec.mio.user.User;
import ac.neec.mio.util.DateUtil;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.readystatesoftware.viewbadger.BadgeView;

public class TrainingDataFragment extends TopBaseFragment implements
		Sourceable, TrainingDataListCallbackListener {
	public static final String TITLE = "データログ";
	private static final int MESSAGE_UPDATE = 1;
	private static final int MESSAGE_PROGRESS_GONE = 2;
	private static final int DATE_NUM = 20;
	public static final int REQUEST_CODE = 20;

	private View view;
	private ExpandableListView listView;
	private ProgressBar progress;
	private Button buttonInsert;
	private TrainingDateListAdapter adapter;
	private User user = User.getInstance();
	private int date = 0;
	private int dateNum = DATE_NUM;
	private ApiDao dao;
	private SQLiteDao daoSql;
	private static boolean created;
	private ImageButton buttonSync;
	private BadgeView badge;

	private List<List<TrainingItem>> trainings = new ArrayList<List<TrainingItem>>();

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_UPDATE:
				update();
				break;
			case MESSAGE_PROGRESS_GONE:
				progressGone();
			default:
				break;
			}
		};
	};

	private void progressGone() {
		progress.setVisibility(View.GONE);
		progress.setProgress(0);
	}

	private void update() {
		adapter.notifyDataSetChanged();
		selectTraining();
	}

	private void selectTraining() {
		if (date > dateNum) {
			dateNum += date;
			handler.sendMessage(setMessage(MESSAGE_PROGRESS_GONE));
			return;
		}
		dao.selectTraining(user.getId(), DateUtil.getDate(date));
		progress.setProgress(date - DATE_NUM * (date / DATE_NUM));
		date++;
	}

	@Override
	public void onResume() {
		super.onResume();
		initSync();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_training_data, null);
		dao = DaoFacade.getApiDao(getActivity().getApplicationContext(), this);
		daoSql = DaoFacade.getSQLiteDao(getActivity().getApplicationContext());
		init();
		if (!created) {
			selectTraining();
			created = true;
		} else {
			progressGone();
		}
		return view;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("fragment", "onActivityResult");
	}

	private void initSync() {
		buttonSync = (ImageButton) view.findViewById(R.id.button_sync);
		buttonSync.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intentSyncTraining();
			}
		});
		badge = new BadgeView(getActivity().getApplicationContext(), buttonSync);
		badge.setText("!");
		if (daoSql.selectTraining().size() != 0) {
			badge.show();
		} else {
			badge.hide();
			buttonSync.setVisibility(View.INVISIBLE);
		}

	}

	private void init() {
		progress = (ProgressBar) view.findViewById(R.id.progress);
		listView = (ExpandableListView) view
				.findViewById(R.id.list_training_data);
		adapter = new TrainingDateListAdapter(getActivity(), trainings, this);
		listView.setAdapter(adapter);
		listView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				intentDetailTrainingData(groupPosition, childPosition);
				return false;
			}
		});
		buttonInsert = (Button) view.findViewById(R.id.button_insert);
		buttonInsert.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intentInsertTraining();
			}
		});
		initSync();
	}

	private void intentDetailTrainingData(int groupPosition, int childPosition) {
		TrainingItem item = trainings.get(groupPosition).get(childPosition);
		Intent intent = new Intent(getActivity(),
				TrainingDataDetailActivity.class);
		intent.putExtra(Constants.trainingId(), item.getTrainingId());
		intent.putExtra(Constants.tableTraining(), item);
		startActivity(intent);
	}

	private void intentSyncTraining() {
		badge.hide();
		Intent intent = new Intent(getActivity(),
				SyncTrainingListActivity.class);
		getActivity().startActivity(intent);
	}

	private void intentInsertTraining() {
		Intent intent = new Intent(getActivity(),
				TrainingFreeInsertActivity.class);
		getActivity().startActivity(intent);
	}

	private Message setMessage(int msg) {
		Message message = new Message();
		message.what = msg;
		return message;
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public void closeGroup(int position) {
		listView.collapseGroup(position);
	}

	@Override
	public void complete() {
		List<TrainingItem> list = null;
		try {
			list = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
			return;
		} catch (XmlReadException e) {
			e.printStackTrace();
			return;
		}
		if (list.size() == 0) {
			selectTraining();
			return;
		}
		String nowDate = DateUtil.splitDate(list.get(0).getDate());
		for (int i = 0; i < trainings.size(); i++) {
			String date = DateUtil.splitDate(trainings.get(i).get(0).getDate());
			if (date.compareTo(nowDate) < 0) {
				trainings.add(i, list);
				adapter.notifyDataSetChanged();
				return;
			}
		}
		trainings.add(trainings.size(), list);
		handler.sendMessage(setMessage(MESSAGE_UPDATE));
	}

	@Override
	public void incomplete() {
		// TODO Auto-generated method stub

	}
}
