package ac.neec.mio.ui.listener;

import android.location.Location;

public interface LocationCallbackListener {
	void onPagerMoveRight();
	void onLocationChanged(Location location, double distance, float speed);
}
