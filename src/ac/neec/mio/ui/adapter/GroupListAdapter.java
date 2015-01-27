package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.group.Group;
import ac.neec.mio.ui.filter.GroupSearchFilter;
import ac.neec.mio.ui.listener.GroupFilterCallbackListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupListAdapter extends ArrayAdapter<Group> implements
		GroupFilterCallbackListener {

	private LayoutInflater inflater;
	private List<Group> list = new ArrayList<Group>();
	private List<Group> listAll = new ArrayList<Group>();
	private Filter filter;

	public GroupListAdapter(Context context, int resource, List<Group> objects) {
		super(context, resource, objects);
		list = objects;
		Collections.copy(objects, listAll);
		Log.d("adapter", "list all " + listAll.size());
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Group group = list.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_group, null);
		}

		// Bitmap btm = group.getBitmap();
		// ImageView gpImag = (ImageView) convertView
		// .findViewById(R.id.Group_image);
		// gpImag.setImageBitmap(btm);

		String id = group.getId();
		TextView txstid = (TextView) convertView.findViewById(R.id.groupId);
		txstid.setText(String.valueOf(id));

		String name = group.getGroupName();
		TextView txstName = (TextView) convertView.findViewById(R.id.groupName);
		txstName.setText(String.valueOf(name));
		if (position % 2 == 0) {
			convertView.setBackgroundColor(getContext().getResources()
					.getColor(R.color.grayTheme));
		} else {
			convertView.setBackgroundColor(getContext().getResources()
					.getColor(R.color.greenTheme));
		}

		return convertView;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Filter getFilter() {
		if (filter == null) {
			filter = new GroupSearchFilter(this, list);
		}
		return filter;
	}

	@Override
	public void onChenged() {
		notifyDataSetChanged();
	}

	@Override
	public void onClear() {
		clear();
	}

	@Override
	public void onAddition(Group item) {
		add(item);
	}

}
