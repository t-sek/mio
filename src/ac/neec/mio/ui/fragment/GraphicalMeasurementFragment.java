package ac.neec.mio.ui.fragment;

import java.util.Random;

import ac.neec.mio.R;
import ac.neec.mio.db.DBManager;
import ac.neec.mio.taining.Training;
import ac.neec.mio.ui.dialog.TargetHeartRateSettingDislog;
import ac.neec.mio.ui.listener.MeasurementCallbackListener;
import ac.neec.mio.ui.listener.TargetHeartRateSettingListener;
import ac.neec.mio.util.TimeUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.koba.androidrtchart.ChangeLogger;
import com.koba.androidrtchart.RateMeter;

public class GraphicalMeasurementFragment extends MeasurementBaseFragment
		implements MeasurementCallbackListener, TargetHeartRateSettingListener {

	private static final int DEFAULT_TARGET_HEART_RATE = 180;

	private static int CALORIE_FIGHT = 600;
	private static int CALORIE_RATING_UNIT;

	private Training training;
	private int trainingId;
	private int categoryId;
	private RateMeter rateMeter;
	private ChangeLogger logger;
	private ImageButton buttonTargetHeartRateSetting;

	private MeasurementCallbackListener listener;

	private int testCalorie = 0;
	private int targetHeartRate = DEFAULT_TARGET_HEART_RATE;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_graphical_measurement,
				container, false);
		init(view);
		setTachoMeter();

		return view;
	}

	public GraphicalMeasurementFragment(MeasurementCallbackListener listener) {
		this.listener = listener;
	}

	private void setTachoMeter() {
		if (rateMeter != null) {
			rateMeter.meterTargetSetting(DEFAULT_TARGET_HEART_RATE);
			rateMeter.notifyDataSetChenged();
		}
	}

	private void showTargetHeartRateSetting() {
		int targetValue = rateMeter.getMeterTargetValue();
		Log.e("activity", "targetValue " + targetValue);
		new TargetHeartRateSettingDislog(this, targetValue).show(getActivity()
				.getSupportFragmentManager(), "dialog");
	}

	private void init(View view) {
		rateMeter = (RateMeter) view.findViewById(R.id.ratemeter);
		logger = (ChangeLogger) view.findViewById(R.id.logger);
		buttonTargetHeartRateSetting = (ImageButton) view
				.findViewById(R.id.btn_target_heart_rate_setting);
		buttonTargetHeartRateSetting.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showTargetHeartRateSetting();
			}
		});
	}

	@Override
	public void notifyValue(int value) {
		rateMeter.setMeterValAnimate(value);
		logger.addLogData(value);
	}

	@Override
	public void notifyCalorie(int value) {
	}

	@Override
	public void notifyRestUpdate() {

	}

	@Override
	public void trainingId(int trainingId, int categoryId) {
		// training = DBManager.selectTraining(trainingId);
		this.categoryId = categoryId;
		setTachoMeter();

	}

	@Override
	public void notifyTime(String value) {
		int time = TimeUtil.stringToInteger(value);
		// item.setBarValue(time);
		// colorbar.notifyDataSetChenged();
	}

	@Override
	public void onSelected(int trainingMenuId) {
		// item = new ColorBarItem(0, "", codes[trainingMenuId]);
		// colorbar.addBarItem(item);
		// colorbar.notifyDataSetChenged();

	}

	@Override
	public void onUpdate(int targetHeartRate) {
		rateMeter.meterTargetSetting(targetHeartRate);
		rateMeter.notifyDataSetChenged();
	}

	@Override
	public void onDecided(int targetHeartRate) {
		Log.e("activity", "onDecided targetValue " + targetHeartRate);
		this.targetHeartRate = targetHeartRate;
		listener.onUpdateTargetHeartRate(targetHeartRate);
		rateMeter.meterTargetSetting(targetHeartRate);
		rateMeter.setMeterValAnimate(rateMeter.getMeterMinValue());
		rateMeter.notifyDataSetChenged();
	}

	@Override
	public void onCancel() {
		rateMeter.meterTargetSetting(targetHeartRate);
		rateMeter.setMeterValAnimate(rateMeter.getMeterMinValue());
		rateMeter.notifyDataSetChenged();
	}

	@Override
	public void onUpdateTargetHeartRate(int targetHeartRate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDialogCancel() {
		// TODO Auto-generated method stub
		
	}
}
