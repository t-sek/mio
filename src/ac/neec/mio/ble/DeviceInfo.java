package ac.neec.mio.ble;

import android.bluetooth.BluetoothDevice;

public class DeviceInfo {

	private BluetoothDevice device;
	private int rssi;

	public DeviceInfo(BluetoothDevice device, int rssi) {
		this.device = device;
		this.rssi = rssi;
	}

	public BluetoothDevice getDevice() {
		return device;
	}

	public int getRssi() {
		return rssi;
	}
	
	public String getName(){
		return device.getName();
	}
	
	public String getAddress(){
		return device.getAddress();
	}

}
