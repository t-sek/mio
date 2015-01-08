package ac.neec.mio.ui.adapter;

import java.util.List;

import ac.neec.mio.R;
import android.app.SearchManager.OnCancelListener;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class SettingListAdapter extends ArrayAdapter<SettingListItem> {

	private Context context;
	private SettingListItem[] settingList = new SettingListItem[3];
	private LayoutInflater inflater;

	public SettingListAdapter(Context context, int resource,
			SettingListItem[] settingList2) {
		super(context, resource, settingList2);
		this.context = context;
		this.settingList = settingList2;

		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.include_training_menu_detail_setting, null);
		}
		TextView textName = (TextView) convertView
				.findViewById(R.id.txt_setting_name);
		textName.setText(settingList[position].getName());
		TextView textData = (TextView) convertView
				.findViewById(R.id.txt_setting_data);
		textData.setText(settingList[position].getData());
		TextView textUnit = (TextView) convertView
				.findViewById(R.id.txt_setting_unit);
		textUnit.setText(settingList[position].getUnit());
		return convertView;
	}

}
