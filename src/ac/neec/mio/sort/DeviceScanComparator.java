package ac.neec.mio.sort;

import java.util.Comparator;

import ac.neec.mio.ble.DeviceInfo;

public class DeviceScanComparator implements Comparator<DeviceInfo> {

	@Override
	public int compare(DeviceInfo lhs, DeviceInfo rhs) {
		int device1 = Math.abs(lhs.getRssi());
		int device2 = Math.abs(rhs.getRssi());

		if (device1 < device2) {
			return 1;
		} else if (device1 == device2) {
			return 0;
		} else {
			return -1;
		}

	}

}
