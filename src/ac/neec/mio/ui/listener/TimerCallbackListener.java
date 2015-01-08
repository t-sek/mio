package ac.neec.mio.ui.listener;

public interface TimerCallbackListener {
	void onUpdate(String time);
	void onUpdateLap(String time);
	void onAlarm();
	void notifyMin();
}
