package ac.neec.mio.ui.listener;

public interface TargetHeartRateSettingListener {
	void onUpdate(int targetHeartRate);
	void onDecided(int targetHeartRate);
	void onCancel();
}
