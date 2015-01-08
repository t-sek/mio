/*
 * Copyright (C) 2013 youten
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ac.neec.mio.ble;

/**
 * BLE UUID Strings
 */
public class BleUuid {
	// 180A Device Information
	public static final String SERVICE_DEVICE_INFORMATION = "0000180a-0000-1000-8000-00805f9b34fb";
	public static final String CHAR_SYSTEM_ID = "00002a23-0000-1000-8000-00805f9b34fb";
	public static final String CHAR_MODEL_NUMBER_STRING = "00002a24-0000-1000-8000-00805f9b34fb";
	public static final String CHAR_SERIAL_NUMBEAR_STRING = "00002a25-0000-1000-8000-00805f9b34fb";
	public static final String CHRA_FIRMWARE_REVISION_STRING = "00002a26-0000-1000-8000-00805f9b34fb";
	public static final String CHAR_HARDWARE_REVISION_STRING = "00002a27-0000-1000-8000-00805f9b34fb";
	public static final String CHAR_SOFTWARE_REVISION_STRING = "00002a28-0000-1000-8000-00805f9b34fb";
	public static final String CHAR_MANUFACTURER_NAME_STRING = "00002a29-0000-1000-8000-00805f9b34fb";
	// 180D Heart Rate Service
	public static final String SERVICE_HEART_RATE = "0000180d-0000-1000-8000-00805f9b34fb";
	public static final String CHAR_HEART_RATE_MEASUREMENT = "00002A37-0000-1000-8000-00805f9b34fb";
	public static final String CHAR_BODY_SENSOR_LOCATION = "00002A38-0000-1000-8000-00805f9b34fb";
	// 180F Battery Service
	public static final String SERVICE_BATTERY_SERVICE = "0000180F-0000-1000-8000-00805f9b34fb";
	public static final String CHAR_BATTERY_LEVEL = "00002a19-0000-1000-8000-00805f9b34fb";
	// 2902 Client Disprictor
	public static final String DIS_CLIENT_CHARACTERISTIC_CONFIGURATION = "00002902-0000-1000-8000-00805f9b34fb";
	// SIG unregisted UUID Service
	public static final String UNKNOWN_MIO_1 = "6c721826-5bf1-4f649170-381c08ec57ee";
	public static final String UNKNOWN_MIO_2 = "6c722a0b-5bf1-4f649170-381c08ec57ee";


}
