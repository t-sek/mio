package ac.neec.mio.ui.fragment.top;

import ac.neec.mio.R;
import ac.neec.mio.user.User;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.koba.androidrtchart.RateMeter;

public class DeviceDataFragment extends TopBaseFragment {

	public static final String TITLE = "DEVICE";

	// private static final int RATEMETER_PARTS_TARGET = 180;
	private static final int RATEMETER_PARTS_LIMIT = 220;
	private static final int RATEMETER_PARTS_CURRENT = RATEMETER_PARTS_LIMIT - 180;

	private View view;
	private RateMeter rateMeter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_device_data, null);
		setRateMeter();
		return view;
	}

	private void setRateMeter() {

		rateMeter = (RateMeter) view.findViewById(R.id.ratemeter_device);
		// Bitmap icon = BitmapFactory.decodeResource(getResources(),
		// R.drawable.ic_launcher);
		// rateMeter.meterSetting(icon, 220);
		// RATEMETER_PARTS_TARGET = training.getTargetHrartRate();
		if (rateMeter != null) {
//			rateMeter.setMeterVal(RATEMETER_PARTS_LIMIT, RateMeter.PARTS_LIMIT);
//			rateMeter.setMeterVal(User.getInstance().getBodily()
//					.getQuietHeartRate(), RateMeter.PARTS_TARGET);
//			rateMeter.setMeterVal(RATEMETER_PARTS_CURRENT,
//					RateMeter.PARTS_CURRENT);
		}
	}

	public void updateHeartRate(String heartRate) {
//		rateMeter.setMeterValAnimate(Integer.valueOf(heartRate),
//				RateMeter.PARTS_CURRENT);
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

}
