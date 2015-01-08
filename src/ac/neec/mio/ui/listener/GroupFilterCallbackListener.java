package ac.neec.mio.ui.listener;

import ac.neec.mio.group.Group;

public interface GroupFilterCallbackListener {

	void onChenged();
	void onClear();
	void onAddition(Group item);
}
