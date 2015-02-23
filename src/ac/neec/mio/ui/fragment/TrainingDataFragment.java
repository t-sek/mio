package ac.neec.mio.ui.fragment;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.SQLConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.training.Training;
import ac.neec.mio.ui.activity.SyncTrainingListActivity;
import ac.neec.mio.ui.activity.TrainingDataDetailActivity;
import ac.neec.mio.ui.activity.TrainingFreeInsertActivity;
import ac.neec.mio.ui.adapter.TrainingDateListAdapter;
import ac.neec.mio.ui.listener.TrainingDataListCallbackListener;
import ac.neec.mio.ui.view.BadgeView;
import ac.neec.mio.user.User;
import ac.neec.mio.util.DateUtil;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageButton;

public class TrainingDataFragment extends TopBaseFragment implements
		Sourceable, TrainingDataListCallbackListener {
	public static final String TITLE = "データログ";
	private static final int MESSAGE_UPDATE = 1;
	private static final int MESSAGE_PROGRESS_GONE = 2;
	private static final int DATE_NUM = 50;
	public static final int REQUEST_CODE = 20;

	private View view;
	private ExpandableListView listView;
	// private ProgressBar progress;
	private ImageButton buttonInsert;
	private TrainingDateListAdapter adapter;
	private User user = User.getInstance();
	private int dateNum;
	private ApiDao dao;
	private SQLiteDao daoSql;
	// private static boolean created;
	private ImageButton buttonSync;
	private BadgeView badge;
	private int num;

	private List<List<Training>> trainings = new ArrayList<List<Training>>();

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_UPDATE:
				updateTraining((List<Training>) message.obj);
				break;
			case MESSAGE_PROGRESS_GONE:
				// progressGone();
			default:
				break;
			}
		};
	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_training_data, null);
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
		dao.selectTraining(user.getId(), user.getId(), user.getBirth(),
				DateUtil.nowDate(), dateNum, 0, user.getPassword());
		init();
		return view;
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
		listView = (ExpandableListView) view
				.findViewById(R.id.list_training_data);
		adapter = new TrainingDateListAdapter(getActivity(), trainings, this,
				R.color.theme);
		listView.setAdapter(adapter);
		listView.setEmptyView(view.findViewById(R.id.empty));
		listView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				intentDetailTrainingData(groupPosition, childPosition);
				return false;
			}
		});
		buttonInsert = (ImageButton) view.findViewById(R.id.button_insert);
		buttonInsert.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intentInsertTraining();
			}
		});
		initSync();
	}

	private void intentDetailTrainingData(int groupPosition, int childPosition) {
		Training item = trainings.get(groupPosition).get(childPosition);
		Intent intent = new Intent(getActivity(),
				TrainingDataDetailActivity.class);
		intent.putExtra(SQLConstants.trainingId(), item.getId());
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

	private Message setMessage(int msg, List<Training> training) {
		Message message = new Message();
		if (training != null) {
			message.obj = training;
		}
		message.what = msg;
		return message;
	}

	private void updateTraining(List<Training> trainings) {
		if (trainings.size() == 0) {
			return;
		}
		String lastDate = trainings.get(0).getDate();
		List<Training> lastTraining = new ArrayList<Training>();
		for (Training training : trainings) {
			if (training.getDate().equals(lastDate)) {
				lastTraining.add(training);
			} else {
				this.trainings.add(lastTraining);
				lastTraining = new ArrayList<Training>();
				lastTraining.add(training);
			}
			lastDate = training.getDate();
		}
		this.trainings.add(lastTraining);
		adapter.notifyDataSetChanged();
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
		List<Training> training = null;
		try {
			training = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
			return;
		} catch (XmlReadException e) {
			e.printStackTrace();
			return;
		}
		// updateTraining(training);
		handler.sendMessage(setMessage(MESSAGE_UPDATE, training));
	}

	@Override
	public void incomplete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void complete(Bitmap image) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
	}

}
