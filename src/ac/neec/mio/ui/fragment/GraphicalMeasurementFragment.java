package ac.neec.mio.ui.fragment;

import ac.neec.mio.R;
import ac.neec.mio.training.Training;
import ac.neec.mio.ui.dialog.TargetHeartRateSettingDislog;
import ac.neec.mio.ui.listener.MeasurementCallbackListener;
import ac.neec.mio.ui.listener.TargetHeartRateSettingListener;
import ac.neec.mio.util.TimeUtil;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.koba.androidrtchart.ChangeLogger;
import com.koba.androidrtchart.RateMeter;

/**
 * 計測画面(中)画面クラス
 */
public class GraphicalMeasurementFragment extends MeasurementBaseFragment
		implements MeasurementCallbackListener, TargetHeartRateSettingListener {

	/**
	 * 目標心拍数
	 */
	private static final int DEFAULT_TARGET_HEART_RATE = 180;

	/**
	 * 心拍タコメータ
	 */
	private RateMeter rateMeter;
	/**
	 * 心拍遷移グラフ
	 */
	private ChangeLogger logger;
	/**
	 * 目標心拍数変更ボタン
	 */
	private ImageButton buttonTargetHeartRateSetting;
	/**
	 * コールバックリスナー
	 */
	private MeasurementCallbackListener listener;
	/**
	 * 目標心拍数心拍数
	 */
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

	/**
	 * コールバックリスナーを設定する
	 */
	public GraphicalMeasurementFragment(MeasurementCallbackListener listener) {
		this.listener = listener;
	}

	/**
	 * 心拍タコメーターを設定する
	 */
	private void setTachoMeter() {
		if (rateMeter != null) {
			rateMeter.meterTargetSetting(DEFAULT_TARGET_HEART_RATE);
			rateMeter.notifyDataSetChenged();
		}
	}

	/**
	 * 目標心拍数設定ダイアログを表示する
	 */
	private void showTargetHeartRateSetting() {
		int targetValue = rateMeter.getMeterTargetValue();
		new TargetHeartRateSettingDislog(this, targetValue).show(getActivity()
				.getSupportFragmentManager(), "dialog");
	}

	/**
	 * 画面の初期化処理をする
	 * 
	 * @param view
	 *            画面ビュー
	 */
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
		setTachoMeter();
	}

	@Override
	public void notifyTime(String value) {
	}

	@Override
	public void onSelected(int trainingMenuId) {
	}

	@Override
	public void onUpdate(int targetHeartRate) {
		rateMeter.meterTargetSetting(targetHeartRate);
		rateMeter.notifyDataSetChenged();
	}

	@Override
	public void onDecided(int targetHeartRate) {
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
