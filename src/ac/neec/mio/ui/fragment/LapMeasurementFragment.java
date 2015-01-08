package ac.neec.mio.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.taining.lap.LapItem;
import ac.neec.mio.ui.adapter.LapListAdapter;
import ac.neec.mio.ui.listener.NotificationCallbackListener;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class LapMeasurementFragment extends MeasurementBaseFragment implements
		NotificationCallbackListener {

	private View view;
	private ListView listView;
	private LapListAdapter adapter;
	private List<LapItem> list = new ArrayList<LapItem>();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_rest_measurement, null);
		setAdapter();
		return view;
	}

	private void setAdapter() {
		adapter = new LapListAdapter(getActivity(), R.layout.item_lap, list);
		listView = (ListView) view.findViewById(R.id.list_lap);
		listView.setAdapter(adapter);
	}

	public void setLapTime(LapItem item) {
		// adapter.insert(item, 0);
		adapter.insert(item, adapter.getListSize());
		listView.setSelection(adapter.getListSize());
		// adapter.add(item);
		// adapter.setLapTime(item);
		// adapter.notifyDataSetChanged();
		// list.add(item);
		// adapter.setLapTimeList(list);
	}

	@Override
	public void notifyValue(int value) {

	}

	@Override
	public void notifyCalorie(int value) {

	}

	@Override
	public void notifyRestUpdate() {

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
