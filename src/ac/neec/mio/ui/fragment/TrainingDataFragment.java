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

/**
 * トップ画面(左)<br>
 * 過去のトレーニングデータ参照画面
 *
 */
public class TrainingDataFragment extends TopBaseFragment implements
		Sourceable, TrainingDataListCallbackListener {
	/**
	 * タブタイトル
	 */
	public static final String TITLE = "データログ";
	/**
	 * 更新メッセージ
	 */
	private static final int MESSAGE_UPDATE = 1;
	/**
	 * 画面ビュー
	 */
	private View view;
	/**
	 * 過去のトレーニングを日付ごとに表示する折りたたみリストビュー
	 */
	private ExpandableListView listView;
	/**
	 * トレーニング手入力画面遷移ボタン
	 */
	private ImageButton buttonInsert;
	/**
	 * 過去のトレーニングリストのアダプター
	 */
	private TrainingDateListAdapter adapter;
	/**
	 * ユーザ情報
	 */
	private User user = User.getInstance();
	/**
	 * WebAPI接続インスタンス
	 */
	private ApiDao dao;
	/**
	 * ローカルデータベース接続インスタンス
	 */
	private SQLiteDao daoSql;
	/**
	 * トレーニング未同期一覧画面遷移ボタン
	 */
	private ImageButton buttonSync;
	/**
	 * トレーニング未同期がある場合バッジを表示する
	 */
	private BadgeView badge;
	/**
	 * 過去のトレーニングリスト
	 */
	private List<List<Training>> trainings = new ArrayList<List<Training>>();
	/**
	 * 画面ハンドラー
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_UPDATE:
				updateTraining((List<Training>) message.obj);
				break;
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
				DateUtil.nowDate(), 10000000, 0, user.getPassword());
		init();
		return view;
	}

	/**
	 * 未同期トレーニングがある場合、バッジを表示する
	 */
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

	/**
	 * 画面の初期化処理をする
	 */
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

	/**
	 * トレーニング詳細画面に遷移する
	 * 
	 * @param groupPosition
	 *            日付インデックス
	 * @param childPosition
	 *            日付ごとのトレーニングインデックス
	 */
	private void intentDetailTrainingData(int groupPosition, int childPosition) {
		Training item = trainings.get(groupPosition).get(childPosition);
		Intent intent = new Intent(getActivity(),
				TrainingDataDetailActivity.class);
		intent.putExtra(SQLConstants.trainingId(), item.getId());
		startActivity(intent);
	}

	/**
	 * 未同期トレーニング一覧画面に遷移する
	 */
	private void intentSyncTraining() {
		badge.hide();
		Intent intent = new Intent(getActivity(),
				SyncTrainingListActivity.class);
		getActivity().startActivity(intent);
	}

	/**
	 * トレーニング手入力画面に遷移する
	 */
	private void intentInsertTraining() {
		Intent intent = new Intent(getActivity(),
				TrainingFreeInsertActivity.class);
		getActivity().startActivity(intent);
	}

	/**
	 * トレーニング更新メッセージを設定し、取得する
	 * 
	 * @param msg
	 *            メッセージ
	 * @param training
	 *            更新するトレーニング
	 * @return メッセージ
	 */
	private Message setMessage(int msg, List<Training> training) {
		Message message = new Message();
		if (training != null) {
			message.obj = training;
		}
		message.what = msg;
		return message;
	}

	/**
	 * トレーニングをリストビューに表示する
	 * 
	 * @param trainings
	 *            表示するトレーニング
	 */
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
