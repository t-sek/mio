package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.ble.DeviceInfo;
import ac.neec.mio.consts.PreferenceConstants;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * デバイス検索結果リストビュー設定クラス
 *
 */
public class DeviceScanListAdapter extends ArrayAdapter<DeviceInfo> {

	/**
	 * デバイスリスト
	 */
	private List<DeviceInfo> list = new ArrayList<DeviceInfo>();
	private LayoutInflater inflater;

	/**
	 * 
	 * @param context
	 *            コンテキスト
	 * @param resource
	 *            リソース
	 * @param list
	 *            デバイスリスト
	 */
	public DeviceScanListAdapter(Context context, int resource,
			List<DeviceInfo> list) {
		super(context, resource, list);
		this.list = list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * デバイス数を取得する
	 * 
	 * @return デバイス数
	 */
	public int getListSize() {
		return list.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_device_scan, null);
		}
		DeviceInfo item = list.get(position);
		TextView textName = (TextView) convertView
				.findViewById(R.id.txt_device_name);
		if (item.getName() == null) {
			textName.setText(PreferenceConstants.unknownDevice());
		}
		textName.setText(item.getName());
		return convertView;
	}

}
