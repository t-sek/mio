package ac.neec.mio.ui.fragment;

import java.math.BigDecimal;

import ac.neec.mio.R;
import ac.neec.mio.map.MapBaseFragment;
import ac.neec.mio.ui.listener.LocationCallbackListener;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;

import com.google.android.gms.drive.internal.GetMetadataRequest;
import com.google.android.gms.internal.es;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

public class MapMeasurementFragment extends MapBaseFragment {

	// private FragmentManager fragmentManager;
	private static View view;
	private SupportMapFragment mapFragment;
	private Button buttonMoveRight;

	private LocationCallbackListener listener;
	private Location lastLocation;
	private double distance = 0;
	private float[] results = new float[1];

	public MapMeasurementFragment(FragmentManager fragmentManager,
			LocationCallbackListener listener) {
		this.listener = listener;
	}

	@Override
	public void onResume() {
		super.onResume();
//		map = ((SupportMapFragment) getActivity().getSupportFragmentManager()
				map = ((SupportMapFragment) getChildFragmentManager()
				.findFragmentById(R.id.map)).getMap();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.e("fragment", "onCreate called!!");
		if (savedInstanceState != null && mapFragment == null) {
			mapFragment = (SupportMapFragment) getActivity()
					.getSupportFragmentManager().getFragment(
							savedInstanceState, "main_fragment");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (view != null) {
			ViewGroup parent = (ViewGroup) view.getParent();
			if (parent != null) {
				parent.removeView(view);
			}
		}
		try {
			view = inflater.inflate(R.layout.fragment_map_measurement,
					container, false);
		} catch (InflateException e) {
		}
		init();
		return view;
	}

	private void init() {
		mapFragment = (SupportMapFragment) getActivity()
				.getSupportFragmentManager().findFragmentById(R.id.map);
		buttonMoveRight = (Button) view.findViewById(R.id.btn_move_right);
		buttonMoveRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onPagerMoveRight();
			}
		});
		// map = ((SupportMapFragment)
		// fragmentManager.findFragmentById(R.id.map))
		// .getMap();
		MapsInitializer.initialize(getActivity().getApplicationContext());
		setLocationListener(getActivity().getApplicationContext());

	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public void onPause() {
		super.onPause();
		onSaveInstanceState(new Bundle());
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (mapFragment != null) {
			getActivity().getSupportFragmentManager().putFragment(outState,
					"map_fragment", mapFragment);
			Log.e("fragment", "onSaveInstanceState " + outState);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		if (mapFragment == null && savedInstanceState != null) {
			mapFragment = (SupportMapFragment) getActivity()
					.getSupportFragmentManager().getFragment(
							savedInstanceState, "map_fragment");
		}
		super.onViewStateRestored(savedInstanceState);
	}

	@Override
	public void onLocationChanged(Location location) {
		super.onLocationChanged(location);
		float speed = 0;
		if (lastLocation != null) {
			Location.distanceBetween(lastLocation.getLatitude(),
					lastLocation.getLongitude(), location.getLatitude(),
					location.getLongitude(), results);
		}
		if (location.hasSpeed()) {
			speed = location.getSpeed();
		}
		distance += Double.valueOf(results[0] / 1000);
		// 小数点第三位で四捨五入
		BigDecimal bd = new BigDecimal(String.valueOf(distance));
		listener.onLocationChanged(location,
				bd.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue(), speed);
		lastLocation = location;
	}

	private void moveToSapporoStation() {
		CameraUpdate cu = CameraUpdateFactory.newLatLngZoom(new LatLng(43.0675,
				141.350784), 15);
		map.moveCamera(cu);
	}

	@Override
	public void notifyValue(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyCalorie(int value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyRestUpdate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyTime(String value) {
		// TODO Auto-generated method stub

	}

	@Override
	public void trainingId(int trainingId, int categoryId) {
		// TODO Auto-generated method stub

	}

}
