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

public class MapDataActivity extends FragmentActivity implements Sourceable {

	private static final int MESSAGE_CAMERA = 2;
	private static final int MESSAGE_MARKER = 3;
	private static final int MESSAGE_OPTION = 4;
	private static final int MESSAGE_EMPTY = 5;

	private static GoogleMap map;
	private SupportMapFragment mapFragment;
	private float[] results = new float[1];

	private User user = User.getInstance();
	private int trainingId;
	private int id;
	private String targetUserId;

	private ApiDao dao;
	private SQLiteDao daoSql;

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
	
	private void showEmptyMessage() {
		Toast.makeText(getApplicationContext(), "走行ルートがありません",
				Toast.LENGTH_SHORT).show();
	}

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
		Log.d("activity", "targetUserId "+targetUserId);
		if (targetUserId == null) {
			targetUserId = user.getId();
		}
		if (id != 0) {
			// drawMapLine(DBManager.selectTrainingLog(id));
			drawMapLine(daoSql.selectTrainingLog(id));
		} else if (trainingId != 0) {
			dao.selectTraining(user.getId(), targetUserId, trainingId,
					user.getPassword());
			// dao.selectTrainingLog(user.getId(), trainingId);
		}
		if (savedInstanceState != null && mapFragment == null) {
			mapFragment = (SupportMapFragment) getSupportFragmentManager()
					.getFragment(savedInstanceState, "main_fragment");
		}
	}

	private void moveThisCameraPosition(CameraPosition cameraPos) {
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos));
	}

	private void addMarker(MarkerOptions options) {
		map.addMarker(options);
	}

	private void addPolyline(PolylineOptions straight) {
		map.addPolyline(straight);
	}

	private void moveThisPosition(double disX, double disY) {
		// 現在地に移動
		CameraPosition cameraPos = new CameraPosition.Builder()
				.target(new LatLng(disX, disY)).zoom(17.0f).bearing(0).build();
		if (map != null) {
			handler.sendMessage(setMessage(MESSAGE_CAMERA, cameraPos));
		}
	}

	private void drawLine(double fromX, double fromY, double toX, double toY) {
		LatLng from = new LatLng(fromX, fromY);
		LatLng to = new LatLng(toX, toY);
		PolylineOptions straight = new PolylineOptions().add(from, to)
				.geodesic(false)// 直線
				.color(Color.BLUE).width(10);
		setMessage(MESSAGE_OPTION, straight);
	}

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

	private void addMapMarker(double disX, double disY, int position) {
		// 現在地にマーカーをうつ
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
