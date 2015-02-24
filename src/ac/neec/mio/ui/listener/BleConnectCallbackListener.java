package ac.neec.mio.ui.listener;

/**
 * BluetoothLE対応デバイスとの接続状態を通知するリスナー
 */
public interface BleConnectCallbackListener {
	/**
	 * 接続されていることを通知する
	 */
	void onConnected();

	/**
	 * 切断されたことを通知する
	 */
	void onCancel();
}
