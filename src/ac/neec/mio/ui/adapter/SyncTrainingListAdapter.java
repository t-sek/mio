package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.ui.adapter.item.SyncTrainingItem;
import android.content.Context;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.manuelpeinado.multichoiceadapter.MultiChoiceArrayAdapter;

public class SyncTrainingListAdapter extends
		MultiChoiceArrayAdapter<SyncTrainingItem> {
	private LayoutInflater inflater;
	private List<SyncTrainingItem> items = new ArrayList<SyncTrainingItem>();
	private CallbackListener listener;

	public interface CallbackListener {
		void onUpload(List<SyncTrainingItem> items);

		void onDelete(List<SyncTrainingItem> items);
	}

	public SyncTrainingListAdapter(Bundle savedInstanceState, Context context,
			CallbackListener listener, List<SyncTrainingItem> items) {
		super(savedInstanceState, context,
				R.layout.mca__simple_list_item_checkable_1, android.R.id.text1,
				items);
		this.listener = listener;
		this.items = items;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public boolean onCreateActionMode(ActionMode mode, Menu menu) {
		MenuInflater inflater = mode.getMenuInflater();
		inflater.inflate(R.menu.sync_training_select, menu);
		return true;
	}

	@Override
	protected View getViewImpl(int position, View convertView, ViewGroup parent) {
		View view = super.getViewImpl(position, convertView, parent);
		view.setBackgroundResource(R.drawable.sync_training_list);
		return view;
	}

	public void uncheckAll() {
		for (int i = 0; i < getCount(); i++) {
			setItemChecked(i, false);
		}
	}

	private List<SyncTrainingItem> setClickList() {
		List<SyncTrainingItem> clickList = new ArrayList<SyncTrainingItem>();
		for (int i = 0; i < items.size(); i++) {
			if (isChecked(i)) {
				clickList.add(items.get(i));
			}
		}
		return clickList;
	}

	@Override
	public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
		if (item.getItemId() == R.id.action_upload) {
			listener.onUpload(setClickList());
			return true;
		}
		if (item.getItemId() == R.id.action_delete) {
			listener.onDelete(setClickList());
			return true;
		}
		return false;
	}

	@Override
	public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
		return false;
	}
}
