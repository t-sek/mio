package ac.neec.mio.ui.listener;

public interface TrainingSettingCallbackListener {

	void onMeasurement(int trainingId);

	void onMeasurement(int trainingMenuId, int targetHrartRate, int calorie,
			int strong);

	void showDialog(String name);

	void showAlertDialog();

	void collapseGroupItem(int position);

	void onDecided(String name, String data);

	void onDecided(String name, int hour, int min);
}
