package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.taining.lap.LapItem;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LapListAdapter extends ArrayAdapter<LapItem> {

	private List<LapItem> list = new ArrayList<LapItem>();
	private LayoutInflater inflater;

	public LapListAdapter(Context context, int resource, List<LapItem> list) {
		super(context, resource, list);
		this.list = list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getListSize() {
		return list.size();
	}

	public void setLapTime(LapItem item) {
		list.add(item);
		notifyDataSetChanged();
	}

	public void setLapTimeList(List<LapItem> list) {
		// Collections.reverse(list);
		this.list = list;
		notifyDataSetChanged();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_lap, null);
		}
		LapItem item = list.get(position);
		TextView textSplitTime = (TextView) convertView
				.findViewById(R.id.txt_split_time);
		TextView textId = (TextView) convertView.findViewById(R.id.txt_lap_id);
		TextView textLapTime = (TextView) convertView
				.findViewById(R.id.txt_lap_time);
		TextView textDistance = (TextView) convertView
				.findViewById(R.id.txt_distance);
		textSplitTime.setText(item.getSplitTime());
		textId.setText(String.valueOf(item.getId()));
		textLapTime.setText(item.getLapTime());
		textDistance.setText(item.getDistance());

		// textName.setText(list.get(position).getTrainingName());
		// convertView.setBackgroundColor(R.color.theme);
		return convertView;
	}

}
