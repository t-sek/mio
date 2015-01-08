package ac.neec.mio.ui.fragment;

import java.util.ArrayList;

import ac.neec.mio.R;
import ac.neec.mio.ui.listener.NotificationCallbackListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * @author sekine
 * 
 */
public class DetailMeasurementFragment extends MeasurementBaseFragment
		implements NotificationCallbackListener {

	private static final int HEART_RATE_TARGET = 180;
	private static final int GRAPH_X_MARGIN = 20;
	private static final String BUNDLE_HEART_RATE_KEY = "heart_rate";
	private TextView txtHeartRate;
	// private LineGraph lineGraph;
	private View view;
	private static ArrayList<String> heartRateList = new ArrayList<String>();

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_detail_measurement, null);
		init();
		// setHeartRateGraph();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		setLineGraph();
		resumeHeartRateGraph();

	}

	@Override
	public void onPause() {
		super.onPause();
		// lineGraph.clear();
		Bundle bundle = new Bundle();
		bundle.putStringArrayList(BUNDLE_HEART_RATE_KEY, heartRateList);
	}

	private void resumeHeartRateGraph() {
		// for (String heartRate : heartRateList) {
		// lineGraph.addLineDot(Integer.valueOf(heartRate));
		// }
		// lineGraph.setReCalucPoint(true);
		// lineGraph.notifyDataSetChenged();
	}

	private void setLineGraph() {
		// lineGraph.setTargetValue(HEART_RATE_TARGET);
		// lineGraph.setLineDotLmit(GRAPH_X_MARGIN);
	}

	private void init() {
		txtHeartRate = (TextView) view.findViewById(R.id.txt_heart_rate);
		// lineGraph = (LineGraph) view.findViewById(R.id.linegraph);
	}

	private void addHeartRateList(String data) {
		if (heartRateList.size() >= GRAPH_X_MARGIN) {
			heartRateList.remove(0);
		}
		heartRateList.add(data);
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

	// @Override
	// public void updateHeartRate(String data) {
	// txtHeartRate.setText(data);
	// addHeartRateList(data);
	// // lineGraph.addLineDot(Integer.valueOf(heartRateList.get(heartRateList
	// // .size() - 1)));
	// // lineGraph.notifyDataSetChenged();
	// }
	//
	// @Override
	// public void updateCalorie(int calorie) {
	//
	// }

}
