package ac.neec.mio.ui.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ac.neec.mio.group.Group;
import ac.neec.mio.ui.listener.GroupFilterCallbackListener;
import android.util.Log;
import android.widget.Filter;

public class GroupSearchFilter extends Filter {

	private List<Group> allItems = new ArrayList<Group>();
	private List<Group> allItemsBank = new ArrayList<Group>();
	private ArrayList<Group> items;
	private GroupFilterCallbackListener listener;

	public GroupSearchFilter(GroupFilterCallbackListener listener,
			List<Group> allItems) {
		this.allItems = allItems;
		Log.d("filter", "new all items " + allItems.size());
		this.listener = listener;
	}

	@Override
	protected FilterResults performFiltering(CharSequence constraint) {
		constraint = constraint.toString().toLowerCase();
		FilterResults result = new FilterResults();
		if (constraint != null && constraint.toString().length() > 0) {
			items = new ArrayList<Group>();
			Log.d("filter", "all items " + allItems.size());
			Log.d("filter", "items " + items.size());
			for (Group item : allItems) {
				if (item.getGroupName().toLowerCase().contains(constraint)
						|| item.getId().toLowerCase().contains(constraint)) {
					items.add(item);
				}
				result.count = items.size();
				result.values = items;
			}
		} else {
			synchronized (this) {
				result.count = items.size();
				result.values = items;
			}
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void publishResults(CharSequence constraint, FilterResults results) {
		items = (ArrayList<Group>) results.values;
		// allItems = (ArrayList<Group>) results.values;
		listener.onChenged();
		listener.onClear();
		if (items != null) {
			for (Group group : items) {
				listener.onAddition(group);
			}
		}else{
			for (Group group : allItems) {
				listener.onAddition(group);
			}
		}
		listener.onChenged();
	}
}
