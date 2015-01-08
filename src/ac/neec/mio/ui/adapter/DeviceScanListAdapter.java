package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.ble.DeviceInfo;
import ac.neec.mio.taining.lap.LapItem;
import ac.neec.mio.consts.PreferenceConstants;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class DeviceScanListAdapter extends ArrayAdapter<DeviceInfo> {

	private List<DeviceInfo> list = new ArrayList<DeviceInfo>();
	private LayoutInflater inflater;

	public DeviceScanListAdapter(Context context, int resource,
			List<DeviceInfo> list) {
		super(context, resource, list);
		this.list = list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getListSize() {
		return list.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_device_scan, null);
		}
		DeviceInfo item = list.get(position);
		Log.e("adapter", "device " + item.getRssi());
		TextView textName = (TextView) convertView
				.findViewById(R.id.txt_device_name);
		if (item.getName() == null) {
			textName.setText(PreferenceConstants.unknownDevice());
		}
		textName.setText(item.getName());
		return convertView;
	}

}
