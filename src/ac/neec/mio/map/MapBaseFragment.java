package ac.neec.mio.map;

import java.util.ArrayList;

import ac.neec.mio.ui.fragment.BaseFragment;
import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.location.LocationClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapBaseFragment extends BaseFragment implements
		OnConnectionFailedListener, ConnectionCallbacks, LocationListener {
	protected GoogleMap map;
	protected ArrayList<LatLng> markerPoints;
	protected LocationClient locationClient = null;
	protected LatLng lastLocation;
	protected static final LocationRequest REQUEST = LocationRequest.create()
			.setInterval(5000)
			// 5 seconds
			.setFastestInterval(16)
			// 16ms = 60fps
			.setSmallestDisplacement(1)
			.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (locationClient != null) {
			locationClient.disconnect();
		}
	}
	
	

	protected void setLocationListener(Context context) {
		// LocationManagerを取得
		LocationManager locationManager = (LocationManager) getActivity()
				.getSystemService(Context.LOCATION_SERVICE);
		// Criteriaオブジェクトを生成
		Criteria criteria = new Criteria();
		// Accuracyを指定(低精度)
		criteria.setAccuracy(Criteria.ACCURACY_COARSE);
		// PowerRequirementを指定(低消費電力)
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		// スピードの取得
		criteria.setSpeedRequired(true);
		// ロケーションプロバイダの取得
		String provider = locationManager.getBestProvider(criteria, true);
		// LocationListenerを登録
		// locationManager.requestLocationUpdates(provider, 0, 0,
		// (android.location.LocationListener) this);
		locationManager = (LocationManager) getActivity().getSystemService(
				getActivity().LOCATION_SERVICE);
		locationClient = new LocationClient(context, this, this);// OnConnectionFailedListener
		if (locationClient != null) {
			// Google Play Servicesに接続
			locationClient.connect();
		}

	}
	
	protected void moveThisPosition(Location location){
		// 現在地に移動
		CameraPosition cameraPos = new CameraPosition.Builder()
				.target(new LatLng(location.getLatitude(), location
						.getLongitude())).zoom(17.0f).bearing(0).build();
		map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPos));
	}

	@Override
	public void onLocationChanged(Location location) {
		moveThisPosition(location);
		// 線をひく
		if (lastLocation == null) {
			 lastLocation = new LatLng(location.getLatitude(),
			 location.getLongitude());
//			lastLocation = new LatLng(43.0675, 141.350784);
			// 現在地にマーカーをうつ
			MarkerOptions options = new MarkerOptions();
			options.position(new LatLng(location.getLatitude(), location
					.getLongitude()));
			BitmapDescriptor icon = BitmapDescriptorFactory
					.defaultMarker(BitmapDescriptorFactory.HUE_GREEN);
			// BitmapDescriptor icon =
			// BitmapDescriptorFactory.fromResource(R.drawable.ic_logo);
			options.icon(icon);
			map.addMarker(options);
		}
		LatLng nowLocation = new LatLng(location.getLatitude(),
				location.getLongitude());
		PolylineOptions straight = new PolylineOptions()
				.add(lastLocation, nowLocation).geodesic(false)// 直線
				.color(Color.BLUE).width(10);
		map.addPolyline(straight);

		lastLocation = nowLocation;
	}

	@Override
	public void onConnected(Bundle arg0) {
		locationClient.requestLocationUpdates(REQUEST, this); // LocationListener
	}

	@Override
	public void onDisconnected() {
	}

	@Override
	public void onConnectionFailed(ConnectionResult arg0) {
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