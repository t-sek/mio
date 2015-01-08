package ac.neec.mio.timer;

public interface Observer {
	void onUpdate(long time);
	void onUpdateLap(long time);
	void onAlarm();
}
