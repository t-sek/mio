package ac.neec.mio.ui.fragment;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;

import ac.neec.mio.R;
import ac.neec.mio.R.id;
import ac.neec.mio.R.layout;
import ac.neec.mio.map.MapBaseFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MapDataFragment extends MapBaseFragment {

	private int id;
	private GoogleMap map;
	private SupportMapFragment mapFragment;


	public MapDataFragment(int id) {
		this.id = id;
	}

	@Override
	public void onResume() {
		super.onResume();
		map = ((SupportMapFragment) getActivity().getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (savedInstanceState != null && mapFragment == null) {
			mapFragment = (SupportMapFragment) getActivity()
					.getSupportFragmentManager().getFragment(
							savedInstanceState, "main_fragment");
		}
	}


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_map_data, container,
				false);

		return view;
	}

}
