package ac.neec.mio.ble;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class AppService extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	 @Override
	  public void onDestroy() {
	    super.onDestroy();
	  }
}
