package ac.neec.mio.ui.fragment;

import java.io.File;
import java.io.InputStream;

import ac.neec.mio.R;
import ac.neec.mio.ui.activity.DeviceSettingActivity;
import ac.neec.mio.ui.activity.GroupListActivity;
import ac.neec.mio.ui.activity.UserDataSettingActivity;
import ac.neec.mio.ui.dialog.SelectionAlertDialog;
import ac.neec.mio.ui.listener.AlertCallbackListener;
import ac.neec.mio.user.User;
import ac.neec.mio.user.gender.Gender;
import ac.neec.mio.util.BitmapUtil;
import ac.neec.mio.util.ExternalAppGallery;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sek.circleimageview.CircleImageView;

/**
 * トップ画面(右)<br>
 * プロフィール画面クラス
 */
public class ProfileFragment extends TopBaseFragment implements
		OnClickListener, AlertCallbackListener {
	/**
	 * タブタイトル
	 */
	public static final String TITLE = "アカウント";
	/**
	 * プロフィールアイコン切り取りリクエスト
	 */
	private static final int REQUEST_CROP_PICK = 3;

	/**
	 * 画面ビュー
	 */
	private View view;
	/**
	 * ユーザ情報設定画面遷移ボタン
	 */
	private ImageButton buttonData;
	/**
	 * グループ情報画面遷移ボタン
	 */
	private ImageButton buttonGroup;
	/**
	 * デバイス設定画面遷移ボタン
	 */
	private ImageButton buttonSetting;
	/**
	 * 背景テーマレイアウト
	 */
	private LinearLayout layoutTheme;
	/**
	 * ユーザ情報設定画面遷移ボタンレイアウト
	 */
	private LinearLayout layoutData;
	/**
	 * グループ情報画面遷移ボタンレイアウト
	 */
	private LinearLayout layoutGroup;
	/**
	 * デバイス設定画面遷移ボタンレイアウト
	 */
	private LinearLayout layoutSetting;
	/**
	 * ユーザアイコン
	 */
	private CircleImageView imageProfile;
	/**
	 * アイコンURI
	 */
	private Uri imageUri;
	/**
	 * ユーザ名を表示するテキストビュー
	 */
	private TextView textName;
	/**
	 * ユーザIDを表示するテキストビュー
	 */
	private TextView textId;
	/**
	 * ユーザ情報
	 */
	private User user = User.getInstance();

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_profile, null);
		initFindViews();
		setListeners();
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		textName.setText(user.getName());
		textId.setText(user.getId());
	}

	/**
	 * 画面の初期化処理をする
	 */
	private void initFindViews() {
		buttonData = (ImageButton) view.findViewById(R.id.button_data);
		buttonGroup = (ImageButton) view.findViewById(R.id.button_group);
		buttonSetting = (ImageButton) view.findViewById(R.id.button_setting);
		layoutTheme = (LinearLayout) view.findViewById(R.id.layout);
		layoutData = (LinearLayout) view.findViewById(R.id.layout_data);
		layoutGroup = (LinearLayout) view.findViewById(R.id.layout_group);
		layoutSetting = (LinearLayout) view.findViewById(R.id.layout_setting);
		imageProfile = (CircleImageView) view.findViewById(R.id.img_profile);
		textName = (TextView) view.findViewById(R.id.text_profile_name);
		textId = (TextView) view.findViewById(R.id.text_profile_id);
		if (user.getImage() != null) {
			setProfileImage(user.getImage());
		} else {
			Bitmap image = BitmapFactory.decodeResource(getResources(),
					R.drawable.people);
			setProfileImage(image);
		}
		if (user.getGender().equals(Gender.FEMALE)) {
			layoutTheme.setBackgroundColor(getResources().getColor(
					R.color.theme_female));
		}
	}

	/**
	 * ビューにリスナーを設定する
	 */
	private void setListeners() {
		buttonData.setOnClickListener(this);
		buttonGroup.setOnClickListener(this);
		buttonSetting.setOnClickListener(this);
		layoutData.setOnClickListener(this);
		layoutGroup.setOnClickListener(this);
		layoutSetting.setOnClickListener(this);
		imageProfile.setOnClickListener(this);
	}

	/**
	 * ユーザ情報設定画面に遷移する
	 */
	private void intentProfileSetting() {
		Intent intent = new Intent(getActivity(), UserDataSettingActivity.class);
		startActivity(intent);
	}

	/**
	 * デバイス設定画面に遷移する
	 */
	private void intentDeviceSetting() {
		Intent intent = new Intent(getActivity(), DeviceSettingActivity.class);
		startActivity(intent);
	}

	/**
	 * グループ情報画面に遷移する
	 */
	private void intentGroupSetting() {
		Intent intent = new Intent(getActivity(), GroupListActivity.class);
		startActivity(intent);
	}

	/**
	 * アイコンを設定する
	 * 
	 * @param image
	 *            アイコン
	 */
	public void setProfileImage(Bitmap image) {
		image = BitmapUtil.getBitmapClippedCircle(image, 100);
		// imageProfile.setImageDrawable(new BitmapDrawable(image));
		imageProfile.setImage(image);
	}

	/**
	 * 取得したデータから画像URIを取得する
	 * 
	 * @param data
	 *            取得したデータ
	 * @return URI 画像URI
	 */
	private Uri getImageUri(Intent data) {
		Uri result = null;
		if (null != data) {
			result = data.getData();
		} else {
			ContentResolver contentResolver = getActivity()
					.getContentResolver();
			Cursor cursor = MediaStore.Images.Media.query(contentResolver,
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null,
					MediaStore.Images.ImageColumns.DATE_TAKEN + " DESC");
			cursor.moveToFirst();
			String id = cursor.getString(cursor
					.getColumnIndexOrThrow(BaseColumns._ID));
			result = Uri.withAppendedPath(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
		}
		return result;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (Activity.RESULT_OK != resultCode) {
			return;
		}
		switch (requestCode) {
		case ExternalAppGallery.REQUEST_GALLERY:
			imageUri = getImageUri(data);
			ExternalAppGallery.performCrop(getActivity(), imageUri);
			break;
		case ExternalAppGallery.REQUEST_CAMERA:
			ExternalAppGallery.openCrop(getActivity(), imageUri);
			break;
		case ExternalAppGallery.REQUEST_CROP:
			Bitmap bitmap = data.getExtras().getParcelable("data");
			bitmap = BitmapUtil.resize(bitmap, 100, 100);
			user.setImage(bitmap);
			imageProfile.setImage(bitmap);
			imageUri = null;
			break;
		default:
			break;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.button_data || v.getId() == R.id.layout_data) {
			intentProfileSetting();
		} else if (v.getId() == R.id.button_group
				|| v.getId() == R.id.layout_group) {
			intentGroupSetting();
		} else if (v.getId() == R.id.button_setting
				|| v.getId() == R.id.layout_setting) {
			intentDeviceSetting();
		} else if (v.getId() == R.id.img_profile) {
			new SelectionAlertDialog(this, "どの画像を使いますか？", "写真を撮る", "写真を選択",
					true).show(getActivity().getFragmentManager(), "");
		}
	}

	@Override
	public String getTitle() {
		return TITLE;
	}

	@Override
	public void onNegativeSelected(String message) {
		// ExternalAppGallery.openGallery(getActivity());
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setType("*/*");
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
		startActivityForResult(
				Intent.createChooser(intent, "Complete action using"),
				ExternalAppGallery.REQUEST_GALLERY);
	}

	@Override
	public void onPositiveSelected(String message) {
		imageUri = ExternalAppGallery.openCamera(getActivity());
	}

}
