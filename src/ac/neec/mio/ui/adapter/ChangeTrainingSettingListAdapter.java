package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.training.menu.TrainingMenu;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * トレーニング追加ダイアログでのトレーニングメニューリストビュー設定クラス
 */
public class ChangeTrainingSettingListAdapter extends
		ArrayAdapter<TrainingMenu> {

	/**
	 * トレーニングメニューリスト
	 */
	private List<TrainingMenu> list = new ArrayList<TrainingMenu>();
	private LayoutInflater inflater;

	/**
	 * 
	 * @param context
	 *            コンテキスト
	 * @param resource
	 *            リソース
	 * @param list
	 *            トレーニングメニューリスト
	 */
	public ChangeTrainingSettingListAdapter(Context context, int resource,
			List<TrainingMenu> list) {
		super(context, resource, list);
		this.list = list;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(
					R.layout.item_new_training_select_list, null);
		}
		TextView textName = (TextView) convertView
				.findViewById(R.id.txt_new_training_menu);
		textName.setText(list.get(position).getTrainingName());
		convertView.setBackgroundColor(R.color.theme);
		return convertView;
	}

}
