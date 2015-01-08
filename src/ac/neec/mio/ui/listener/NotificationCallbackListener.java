package ac.neec.mio.ui.listener;

public interface NotificationCallbackListener {

	void notifyValue(int value);
	void notifyCalorie(int value);
	void notifyRestUpdate();
	void notifyTime(String value);
	void trainingId(int trainingId,int categoryId);
}
