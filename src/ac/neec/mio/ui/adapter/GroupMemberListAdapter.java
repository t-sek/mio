package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import com.sek.circleimageview.CircleImageView;

import ac.neec.mio.R;
import ac.neec.mio.group.Member;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupMemberListAdapter extends ArrayAdapter<Member> {

	private LayoutInflater inflater;
	private List<Member> members;
	private Resources resources;

	public GroupMemberListAdapter(Context context, int resource,
			List<Member> objects) {
		super(context, resource, objects);
		resources = context.getResources();
		members = objects;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Member item = members.get(position);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_group_member, null);
		}
		CircleImageView imageView = (CircleImageView) convertView
				.findViewById(R.id.img_profile);
		Bitmap image = BitmapFactory.decodeResource(resources,
				R.drawable.profile_normal);
		imageView.setImage(image);
		TextView textId = (TextView) convertView
				.findViewById(R.id.text_user_id);
		 textId.setText(item.getAffiliation().getUserId());
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
