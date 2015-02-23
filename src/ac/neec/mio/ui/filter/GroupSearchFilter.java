package ac.neec.mio.ui.filter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import ac.neec.mio.group.Group;
import ac.neec.mio.ui.listener.GroupFilterCallbackListener;
import android.util.Log;
import android.widget.Filter;

/**
 * グループ検索の実装クラス
 */
public class GroupSearchFilter extends Filter {

	/**
	 * 全グループ
	 */
	private List<Group> allItems = new ArrayList<Group>();
	/**
	 * 検索結果グループ
	 */
	private ArrayList<Group> items;
	/**
	 * コールバックリスナー
	 */
	private GroupFilterCallbackListener listener;

	/**
	 * 
	 * @param listener
	 *            コールバックリスナー
	 * @param allItems
	 *            全グループ
	 */
	public GroupSearchFilter(GroupFilterCallbackListener listener,
			List<Group> allItems) {
		this.allItems = allItems;
		this.listener = listener;
	}

	@Override
	protected FilterResults performFiltering(CharSequence constraint) {
		constraint = constraint.toString().toLowerCase();
		FilterResults result = new FilterResults();
		if (constraint != null && constraint.toString().length() > 0) {
			items = new ArrayList<Group>();
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
		} else {
			for (Group group : allItems) {
				listener.onAddition(group);
			}
		}
		listener.onChenged();
	}
}
