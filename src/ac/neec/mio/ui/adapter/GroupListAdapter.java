package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.PermissionConstants;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.group.Group;
import ac.neec.mio.ui.filter.GroupSearchFilter;
import ac.neec.mio.ui.listener.GroupFilterCallbackListener;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.sek.circleimageview.CircleImageView;

/**
 * グループ一覧リストビュー設定クラス
 *
 */
public class GroupListAdapter extends ArrayAdapter<Group> implements
		GroupFilterCallbackListener {

	/**
	 * 全グループ
	 */
	public static final int ALL = 1;
	/**
	 * 所属グループ
	 */
	public static final int MY = 2;

	private LayoutInflater inflater;
	/**
	 * 全グループ、所属グループフラグ
	 */
	private int flag;
	/**
	 * グループリスト
	 */
	private List<Group> list = new ArrayList<Group>();
	private Filter filter;
	/**
	 * アイコン
	 */
	private CircleImageView image;
	/**
	 * グループID
	 */
	private TextView textId;
	/**
	 * グループ名
	 */
	private TextView textName;

	/**
	 * 
	 * @param context
	 *            コンテキスト
	 * @param resource
	 *            リソース
	 * @param objects
	 *            グループリスト
	 * @param flag
	 *            全グループ、所属グループフラグ
	 */
	public GroupListAdapter(Context context, int resource, List<Group> objects,
			int flag) {
		super(context, resource, objects);
		this.flag = flag;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		list = objects;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Group group = list.get(position);
		String groupId = group.getId();
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_group, null);
		}
		image = (CircleImageView) convertView.findViewById(R.id.Group_image);
		textId = (TextView) convertView.findViewById(R.id.groupId);
		textName = (TextView) convertView.findViewById(R.id.groupName);
		TextView textPermission = (TextView) convertView
				.findViewById(R.id.text_permission);
		int permissionId = group.getPermissionId();
		if (flag == ALL) {
			if (permissionId == PermissionConstants.groupAdmin()
					|| permissionId == PermissionConstants.member()
					|| permissionId == PermissionConstants.trainer()) {
				textPermission.setText("加入済み");
				textPermission.setVisibility(View.VISIBLE);
			} else if (permissionId == PermissionConstants.pending()) {
				textPermission.setText("加入申請中");
				textPermission.setVisibility(View.VISIBLE);
			}
		}
		Bitmap btm = group.getBitmap();
		if (btm != null) {
			image.setImage(btm);
		}
		if (groupId == null) {
			return convertView;
		}
		textId.setText(groupId);
		String name = group.getGroupName();
		textName.setText(String.valueOf(name));
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
