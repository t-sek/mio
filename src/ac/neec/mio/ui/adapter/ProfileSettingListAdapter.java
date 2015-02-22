package ac.neec.mio.ui.adapter;

import static ac.neec.mio.consts.SignUpConstants.*;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.ble.DeviceInfo;
import ac.neec.mio.training.lap.LapItem;
import ac.neec.mio.user.User;
import ac.neec.mio.consts.PreferenceConstants;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProfileSettingListAdapter extends
		ArrayAdapter<ProfileSettingListItem> {

	private LayoutInflater inflater;
	private List<ProfileSettingListItem> list;

	public ProfileSettingListAdapter(Context context, int resource,
			List<ProfileSettingListItem> list) {
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
			convertView = inflater.inflate(R.layout.item_list_profile_setting,
					null);
		}
		ProfileSettingListItem item = list.get(position);
		TextView textItem = (TextView) convertView.findViewById(R.id.txt_item);
		textItem.setText(item.getName());
		TextView textValue = (TextView) convertView
				.findViewById(R.id.txt_value);
		if (item.getName().equals(height()) || item.getName().equals(weight())) {
			Integer value = Math.round(Float.valueOf(item.getValue()));
			textValue.setText(value.toString());
		} else if (item.getName().equals(password())) {
		} else {
			textValue.setText(item.getValue());
		}
		return convertView;
	}
}
