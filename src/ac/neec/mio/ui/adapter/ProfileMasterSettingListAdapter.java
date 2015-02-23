package ac.neec.mio.ui.adapter;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.ble.DeviceInfo;
import ac.neec.mio.training.lap.LapItem;
import ac.neec.mio.ui.adapter.item.ProfileSettingListItem;
import ac.neec.mio.user.User;
import ac.neec.mio.consts.PreferenceConstants;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * ユーザ情報リストビュー設定クラス
 *
 */
public class ProfileMasterSettingListAdapter extends
		ArrayAdapter<ProfileSettingListItem> {

	/**
	 * 名前設定メッセージ
	 */
	private static final String NAME = "名前";
	/**
	 * 生年月日設定メッセージ
	 */
	private static final String BIRTH = "生年月日";
	/**
	 * 性別設定メッセージ
	 */
	private static final String GENDER = "性別";
	/**
	 * メールアドレス設定メッセージ
	 */
	private static final String MAIL = "メールアドレス";
	/**
	 * ID設定メッセージ
	 */
	private static final String ID = "ID";
	/**
	 * パスワード設定メッセージ
	 */
	private static final String PASS = "パスワード";
	/**
	 * 設定リスト
	 */
	private static List<ProfileSettingListItem> list = new ArrayList<ProfileSettingListItem>();
	private LayoutInflater inflater;
	/**
	 * ユーザ情報
	 */
	private User user = User.getInstance();

	/**
	 * リストを設定する
	 */
	{
		list.add(new ProfileSettingListItem(NAME, user.getName()));
		list.add(new ProfileSettingListItem(BIRTH, user.getBirth()));
		list.add(new ProfileSettingListItem(GENDER, user.getGender()));
		list.add(new ProfileSettingListItem(MAIL, user.getMail()));
		list.add(new ProfileSettingListItem(ID, user.getId()));
		list.add(new ProfileSettingListItem(PASS, user.getPassword()));

	}

	/**
	 * 
	 * @param context
	 *            コンテキスト
	 * @param resource
	 *            リソース
	 */
	public ProfileMasterSettingListAdapter(Context context, int resource) {
		super(context, resource, list);
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	/**
	 * リスト項目数を取得する
	 * 
	 * @return 項目数
	 */
	public int getListSize() {
		return list.size();
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.item_list_profile_setting,
					null);
		}
		ProfileSettingListItem item = list.get(position);
		TextView textItem = (TextView) convertView.findViewById(R.id.txt_item);
		textItem.setText(item.getName());
		TextView textValue = (TextView) convertView
				.findViewById(R.id.txt_value);
		textValue.setText(item.getValue());
		return convertView;
	}

}
