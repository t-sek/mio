package ac.neec.mio.ui.activity;

import java.io.InputStream;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.R.id;
import ac.neec.mio.R.layout;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.training.TrainingInfo;
import ac.neec.mio.training.log.TrainingLog;
import ac.neec.mio.user.User;
import ac.neec.mio.consts.SQLConstants;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * ルート参照画面クラス
 *
 */
public class MapDataActivity extends FragmentActivity implements Sourceable {

	/**
	 * カメラ更新メッセージ
	 */
	private static final int MESSAGE_CAMERA = 2;
	/**
	 * マーカー更新メッセージ
	 */
	private static final int MESSAGE_MARKER = 3;
	/**
	 * オプション更新メッセージ
	 */
	private static final int MESSAGE_OPTION = 4;
	/**
	 * エラーメッセージ<br>
	 * 走行ルートがない場合
	 */
	private static final int MESSAGE_EMPTY = 5;

	/**
	 * グーグルマップインスタンス
	 */
	private static GoogleMap map;
	/**
	 * 地図レイアウト
	 */
	private SupportMapFragment mapFragment;
	/**
	 * ユーザ情報
	 */
	private User user = User.getInstance();
	/**
	 * WebAPIから取得したトレーニングID
	 * 
	 */
	private int trainingId;
	/**
	 * ローカルデータベースに保存されているトレーニングID
	 */
	private int id;
	/**
	 * トレーニングを実施したユーザID
	 */
	private String targetUserId;
	/**
	 * WebAPI接続インスタンス
	 */
	private ApiDao dao;
	/**
	 * ローカルデータベース接続インスタンス
	 */
	private SQLiteDao daoSql;
	/**
	 * 画面ハンドラー
	 */
	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_CAMERA:
				moveThisCameraPosition((CameraPosition) message.obj);
				break;
			case MESSAGE_MARKER:
				addMarker((MarkerOptions) message.obj);
				break;
			case MESSAGE_OPTION:
				addPolyline((PolylineOptions) message.obj);
				break;
			case MESSAGE_EMPTY:
				showEmptyMessage();
			default:
				break;
			}
		};
	};

	/**
	 * 走行ルートがないメッセージをトーストで表示する
	 */
	private void showEmptyMessage() {
		Toast.makeText(getApplicationContext(), "走行ルートがありません",
				Toast.LENGTH_SHORT).show();
	}

	/**
	 * 画面ハンドラーメッセージを設定する
	 * 
	 * @param msg
	 *            メッセージ
	 * @param o
	 *            ハンドラーに通知するオブジェクト
	 * @return メッセージインスタンス
	 */
	private Message setMessage(int msg, Object o) {
		Message message = new Message();
		message.what = msg;
		message.obj = o;
		return message;
	}

	@Override
	public void onResume() {
		super.onResume();
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_map_data);
		map = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
		trainingId = getIntent().getIntExtra(SQLConstants.trainingId(), 0);
		id = getIntent().getIntExtra(SQLConstants.id(), 0);
		targetUserId = getIntent().getStringExtra("target_user_id");
		if (targetUserId == null) {
			targetUserId = user.getId();
		}
		if (id != 0) {
			drawMapLine(daoSql.selectTrainingLog(id));
		} else if (trainingId != 0) {
			dao.selectTraining(user.getId(), targetUserId, trainingId,
					user.getPassword());
		}
		if (savedInstanceState != null && mapFragment == null) {
			mapFragment = (SupportMapFragment) getSupportFragmentManager()
					.getFragment(savedInstanceState, "main_fragment");
		}
	}

	/**
	 * 指定したカメラ位置に移動する
	 * 
	 * @param cameraPos
	 *            カメラ位置
	 */
	private void moveThisCameraPosition(CameraPosition cameraPos) {
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos));
	}

	/**
	 * 指定位置にマーカーを立てる
	 * 
	 * @param options
	 *            マーカー
	 */
	private void addMarker(MarkerOptions options) {
		map.addMarker(options);
	}

	/**
	 * 線をひく
	 * 
	 * @param straight
	 *            開始位置、終了位置を設定
	 */
	private void addPolyline(PolylineOptions straight) {
		map.addPolyline(straight);
	}

	/**
	 * 指定位置に移動
	 * 
	 * @param disX
	 *            経度
	 * @param disY
	 *            緯度
	 */
	private void moveThisPosition(double disX, double disY) {
		CameraPosition cameraPos = new CameraPosition.Builder()
				.target(new LatLng(disX, disY)).zoom(17.0f).bearing(0).build();
		if (map != null) {
			handler.sendMessage(setMessage(MESSAGE_CAMERA, cameraPos));
		}
	}

	/**
	 * マップにラインをひく
	 * 
	 * @param fromX
	 *            開始位置経度
	 * @param fromY
	 *            開始位置緯度
	 * @param toX
	 *            終了位置経度
	 * @param toY
	 *            終了位置緯度
	 */
	private void drawLine(double fromX, double fromY, double toX, double toY) {
		LatLng from = new LatLng(fromX, fromY);
		LatLng to = new LatLng(toX, toY);
		PolylineOptions straight = new PolylineOptions().add(from, to)
				.geodesic(false)// 直線
				.color(Color.BLUE).width(10);
		setMessage(MESSAGE_OPTION, straight);
	}

	/**
	 * トレーニングログリストから位置情報を抽出し、ラインをかく
	 * 
	 * @param list
	 *            トレーニングログリスト
	 */
	private void drawMapLine(List<TrainingLog> list) {
		if (list.size() == 0) {
			return;
		}
		moveThisPosition(list.get(0).getDisX(), list.get(0).getDisY());
		addMapMarker(list.get(0).getDisX(), list.get(0).getDisY(), 0);
		TrainingLog last = null;
		for (TrainingLog trainingLog : list) {
			if (last != null) {
				drawLine(last.getDisX(), last.getDisY(), trainingLog.getDisX(),
						trainingLog.getDisY());
				last = trainingLog;
			}
		}
	}

	/**
	 * 指定位置にマーカーをうつ
	 * 
	 * @param disX
	 *            経度
	 * @param disY
	 *            緯度
	 * @param position
	 *            開始・終了フラグ
	 */
	private void addMapMarker(double disX, double disY, int position) {
		MarkerOptions options = new MarkerOptions();
		options.position(new LatLng(disX, disY));
		if (position == 0) {
			options.title("スタート地点");
		} else if (position == 1) {
			options.title("ゴール地点");
		}
		BitmapDescriptor icon = null;
		if (position == 0) {
			icon = BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
		} else if (position == 1) {
			icon = BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_RED);
		}
		options.icon(icon);
		if (map != null) {
			handler.sendMessage(setMessage(MESSAGE_MARKER, options));
		}
	}

	@Override
	public void complete() {
		TrainingInfo training = null;
		try {
			training = dao.getResponse();
		} catch (XmlParseException e) {
			e.printStackTrace();
		} catch (XmlReadException e) {
			e.printStackTrace();
		}
		List<TrainingLog> logs = training.getLogs();
		if (logs.size() != 0) {
			TrainingLog item = logs.get(0);
			moveThisPosition(item.getDisX(), item.getDisY());
			addMapMarker(item.getDisX(), item.getDisY(), 0);
			drawMapLine(logs);
		} else {
			setMessage(MESSAGE_EMPTY, null);
		}
	}

	@Override
	public void incomplete() {

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
