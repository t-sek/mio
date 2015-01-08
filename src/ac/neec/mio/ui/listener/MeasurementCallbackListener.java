package ac.neec.mio.ui.listener;

public interface MeasurementCallbackListener {
	void onSelected(int trainingMenuId);
	void onDialogCancel();
	void onUpdateTargetHeartRate(int targetHeartRate);
}
