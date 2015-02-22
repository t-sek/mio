package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.group.GroupInfo;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupInfoListAdapter extends ArrayAdapter<GroupInfoListItem> {

	private LayoutInflater inflater;
	private List<GroupInfoListItem> list;

	public GroupInfoListAdapter(Context context, int resource,
	// List<GroupInfoListItem> objects) {
			List<GroupInfoListItem> objects) {
		super(context, resource, objects);
		list = objects;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		GroupInfoListItem group = list.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_group_list, null);
		}
		String name = group.getOperation();
		TextView textName = (TextView) convertView
				.findViewById(R.id.group_Operation);
		textName.setText(String.valueOf(name));
		if (group.getNum() == 0) {
			convertView.findViewById(R.id.layout_num).setVisibility(
					View.INVISIBLE);
		} else {
			TextView textNum = (TextView) convertView
					.findViewById(R.id.text_num);
			textNum.setText(String.valueOf(group.getNum()));
		}
		return convertView;
	}

	@Override
	public int getCount() {
		return list.size();
	}
}
