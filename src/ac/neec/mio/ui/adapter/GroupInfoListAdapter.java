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
import android.widget.ImageView;
import android.widget.TextView;

public class GroupInfoListAdapter extends ArrayAdapter<GroupInfoListItem> {

	private LayoutInflater inflater;
	private GroupInfoListItem[] list;

	public GroupInfoListAdapter(Context context, int resource,
	// List<GroupInfoListItem> objects) {
			GroupInfoListItem[] objects) {
		super(context, resource, objects);
		list = new GroupInfoListItem[objects.length];
		list = objects;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		GroupInfoListItem group = list[position];
		if (convertView == null) {
			// convertView = inflater.inflate(R.layout.activity_group_list,
			// null);
			convertView = inflater.inflate(R.layout.item_group_list, null);
		}

		// Bitmap btm = group.getIcon();
		// ImageView gpImag =
		// (ImageView)convertView.findViewById(R.id.icon_View);
		// gpImag.setImageBitmap(btm);
		String name = group.getOperation();
		TextView textName = (TextView) convertView
				.findViewById(R.id.group_Operation);
		textName.setText(String.valueOf(name));
		// if (position == 0) {
		// TextView textCount = (TextView) convertView
		// .findViewById(R.id.text_item_count);
		// textCount.setText(String.valueOf(count));
		// }

		return convertView;
	}

	@Override
	public int getCount() {
//		return list.size();
		return list.length;
	}
}
