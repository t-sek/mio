package ac.neec.mio.ui.listener;

public interface SearchNotifyListener {
	void onSearchText(String text);
	void onClear();
	void onSearchEnd();
	void onUpdate();
}
