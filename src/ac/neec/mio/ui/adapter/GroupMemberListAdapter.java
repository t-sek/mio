package ac.neec.mio.ui.adapter;

import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.group.Member;
import ac.neec.mio.group.MemberInfo;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.sek.circleimageview.CircleImageView;

public class GroupMemberListAdapter extends ArrayAdapter<MemberInfo> {

	private LayoutInflater inflater;
	private List<MemberInfo> members;
	private Resources resources;
	private SQLiteDao daoSql = DaoFacade.getSQLiteDao();

	public GroupMemberListAdapter(Context context, int resource,
			List<MemberInfo> objects) {
		super(context, resource, objects);
		resources = context.getResources();
		members = objects;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		MemberInfo item = members.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_group_member, null);
		}
		String permissionName = daoSql.selectPermission(item.getPermissionId())
				.getName();
		CircleImageView imageView = (CircleImageView) convertView
				.findViewById(R.id.img_profile);
		Bitmap image = BitmapFactory.decodeResource(resources,
				R.drawable.ic_person_black_48dp);
		if (item.getImage() == null) {
			imageView.setImage(image);
		} else {
			imageView.setImage(item.getImage());
		}
		TextView textPermissionName = (TextView) convertView
				.findViewById(R.id.text_permission_name);
		textPermissionName.setText(permissionName);
		TextView textId = (TextView) convertView
				.findViewById(R.id.text_user_id);
		textId.setText(item.getUserId());
		TextView textName = (TextView) convertView
				.findViewById(R.id.text_user_name);
		textName.setText(item.getUserName());
		return convertView;
	}

	@Override
	public int getCount() {
		return members.size();
	}
}
