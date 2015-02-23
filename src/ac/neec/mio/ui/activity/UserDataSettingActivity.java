package ac.neec.mio.ui.activity;

import static ac.neec.mio.consts.SignUpConstants.birth;
import static ac.neec.mio.consts.SignUpConstants.gender;
import static ac.neec.mio.consts.SignUpConstants.height;
import static ac.neec.mio.consts.SignUpConstants.logout;
import static ac.neec.mio.consts.SignUpConstants.mail;
import static ac.neec.mio.consts.SignUpConstants.name;
import static ac.neec.mio.consts.SignUpConstants.password;
import static ac.neec.mio.consts.SignUpConstants.quietHeartRate;
import static ac.neec.mio.consts.SignUpConstants.userId;
import static ac.neec.mio.consts.SignUpConstants.weight;

import java.util.ArrayList;
import java.util.List;

import ac.neec.mio.R;
import ac.neec.mio.consts.ErrorConstants;
import ac.neec.mio.consts.PreferenceConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.ui.adapter.ProfileSettingListAdapter;
import ac.neec.mio.ui.adapter.item.ProfileSettingListItem;
import ac.neec.mio.ui.dialog.ProfileBirthSettingDialog;
import ac.neec.mio.ui.dialog.ProfileBirthSettingDialog.ProfileBirthCallbackListener;
import ac.neec.mio.ui.dialog.ProfileBodilySelectDialog;
import ac.neec.mio.ui.dialog.ProfileBodilySelectDialog.ProfileBodilyCallbackListener;
import ac.neec.mio.ui.dialog.UserDataSettingEditDialog;
import ac.neec.mio.ui.dialog.UserDataSettingEditDialog.EditChangedListener;
import ac.neec.mio.ui.dialog.UserDataSettingPasswordDialog;
import ac.neec.mio.ui.dialog.UserDataSettingPasswordDialog.PasswordChangedListener;
import ac.neec.mio.ui.dialog.UserDataSettingPickerDialog.PickerChangedListener;
import ac.neec.mio.user.User;
import ac.neec.mio.util.BodilyUtil;
import ac.neec.mio.util.DateUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * ユーザ情報設定画面クラス
 */
public class UserDataSettingActivity extends Activity implements
		PickerChangedListener, EditChangedListener, PasswordChangedListener,
		ProfileBodilyCallbackListener, ProfileBirthCallbackListener,
		OnSharedPreferenceChangeListener, Sourceable {

	private static final int MESSAGE_VALIDATE = 2;

	/**
	 * 身体情報設定リストビュー
	 */
	private ListView listBody;
	/**
	 * ユーザ情報設定リストビュー
	 */
	private ListView listMaster;
	/**
	 * アカウント情報設定リストビュー
	 */
	private ListView listAccount;
	/**
	 * 体重推移画面遷移テキストビュー
	 */
	private TextView textWeightGraph;
	/**
	 * 身体情報設定リスト
	 */
	private List<ProfileSettingListItem> itemBody = new ArrayList<ProfileSettingListItem>();
	/**
	 * ユーザ情報設定リスト
	 */
	private List<ProfileSettingListItem> itemMaster = new ArrayList<ProfileSettingListItem>();
	/**
	 * アカウント情報設定リスト
	 */
	private List<ProfileSettingListItem> itemAccount = new ArrayList<ProfileSettingListItem>();
	/**
	 * ユーザ情報
	 */
	private User user = User.getInstance();
	/**
	 * 身体情報設定リストのアダプター
	 */
	private ProfileSettingListAdapter bodyListAdapter;
	/**
	 * ユーザ情報設定リストのアダプター
	 */
	private ProfileSettingListAdapter masterListAdapter;
	/**
	 * アカウント情報設定リストのアダプター
	 */
	private ProfileSettingListAdapter accountListAdapter;
	/**
	 * WebAPI接続インスタンス
	 */
	private ApiDao dao;
	/**
	 * 画面ハンドラー
	 */
	private Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_VALIDATE:
				Toast.makeText(getApplicationContext(),
						ErrorConstants.scriptValidate(), Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_data_setting);
		user.setListener(this);
		dao = DaoFacade.getApiDao(this);
		init();
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateList();
	}

	/**
	 * 設定リストを初期化する
	 */
	private void clearList() {
		itemBody.clear();
		itemMaster.clear();
		itemAccount.clear();
	}

	/**
	 * 設定リストを設定する
	 */
	private void setList() {
		itemBody.add(new ProfileSettingListItem(height(), String.valueOf(user
				.getHeight())));
		itemBody.add(new ProfileSettingListItem(quietHeartRate(), String
				.valueOf(user.getQuietHeartRate())));
		itemBody.add(new ProfileSettingListItem(weight(), String.valueOf(user
				.getWeight())));
		itemMaster.add(new ProfileSettingListItem(name(), user.getName()));
		itemMaster.add(new ProfileSettingListItem(birth(), DateUtil
				.japaneseFormat(user.getBirth())));
		itemMaster.add(new ProfileSettingListItem(gender(), user.getGender()));
		itemMaster.add(new ProfileSettingListItem(mail(), user.getMail()));
		itemMaster.add(new ProfileSettingListItem(userId(), user.getId()));
		itemMaster.add(new ProfileSettingListItem(password(), user
				.getPassword()));
		itemAccount.add(new ProfileSettingListItem(logout(), ""));
	}

	/**
	 * アダプターを設定する
	 */
	private void setAdapter() {
		setList();
		bodyListAdapter = new ProfileSettingListAdapter(
				getApplicationContext(), R.layout.item_list_profile_setting,
				itemBody);
		listBody.setAdapter(bodyListAdapter);
		masterListAdapter = new ProfileSettingListAdapter(
				getApplicationContext(), R.layout.item_list_profile_setting,
				itemMaster);
		listMaster.setAdapter(masterListAdapter);
		accountListAdapter = new ProfileSettingListAdapter(
				getApplicationContext(), R.layout.item_list_profile_setting,
				itemAccount);
		listAccount.setAdapter(accountListAdapter);
		listBody.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showBodyDialog(position);
			}
		});
		listMaster.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showMasterDialog(position);
			}
		});
		listAccount.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				showLogoutDialog();
			}
		});

	}

	/**
	 * 体重推移画面に遷移する
	 */
	private void intentWeightGraph() {
		Intent intent = new Intent(UserDataSettingActivity.this,
				WeightGraphActivity.class);
		startActivity(intent);
	}

	/**
	 * 画面の初期化処理をする
	 */
	private void init() {
		listBody = (ListView) findViewById(R.id.list_profile_body);
		listMaster = (ListView) findViewById(R.id.list_profile_master);
		listAccount = (ListView) findViewById(R.id.list_account);
		textWeightGraph = (TextView) findViewById(R.id.text_weight_graph);
		textWeightGraph.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intentWeightGraph();
			}
		});
		setAdapter();
	}

	/**
	 * 身体情報設定ダイアログを表示する
	 * 
	 * @param position
	 *            設定項目
	 */
	private void showBodyDialog(int position) {
		switch (position) {
		case 0:
			showProfileBodilyDialog(BodilyUtil.height(),
					ProfileBodilySelectDialog.HEIGHT);
			break;
		case 1:
			showQuietHeartRateDialog();
			break;
		case 2:
			showProfileBodilyDialog(BodilyUtil.weight(),
					ProfileBodilySelectDialog.WEIGHT);
			break;
		default:
			break;
		}
	}

	/**
	 * 設定ダイアログを表示する
	 * 
	 * @param row
	 *            要素
	 * @param section
	 *            設定項目
	 */
	private void showProfileBodilyDialog(String[] row, int section) {
		new ProfileBodilySelectDialog(this, row, section).show(
				getFragmentManager(), "dialog");
	}

	/**
	 * ユーザ情報設定ダイアログを表示する
	 * 
	 * @param position
	 *            設定項目
	 */
	private void showMasterDialog(int position) {
		switch (position) {
		case 0:
			new UserDataSettingEditDialog(this, UserDataSettingEditDialog.NAME)
					.show(getFragmentManager(), "name");
			break;
		case 1:
			new ProfileBirthSettingDialog(this).show(getFragmentManager(),
					"birth");
			break;
		case 3:
			new UserDataSettingEditDialog(this, UserDataSettingEditDialog.MAIL)
					.show(getFragmentManager(), "mail");
			break;
		case 5:
			new UserDataSettingPasswordDialog(this).show(getFragmentManager(),
					"pass");
			break;
		default:
			break;
		}
	}

	/**
	 * ログアウトする
	 */
	private void userLogout() {
		user.logout();
		Intent intent = new Intent(UserDataSettingActivity.this,
				SplashActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_CLEAR_TASK);
		startActivity(intent);
		finish();
	}

	/**
	 * 安静時心拍数設定画面に遷移する
	 */
	private void intentQuietHeartRateMeasurement() {
		Intent intent = new Intent(UserDataSettingActivity.this,
				DeviceScanActivity.class);
		startActivity(intent);
	}

	/**
	 * 安静時心拍数設定確認画面を表示する
	 */
	private void showQuietHeartRateDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("どのように設定しますか？");
		alertDialogBuilder.setPositiveButton("計測する",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						intentQuietHeartRateMeasurement();
					}
				});
		alertDialogBuilder.setNegativeButton("入力する",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						showProfileBodilyDialog(BodilyUtil.quietHeartRate(),
								ProfileBodilySelectDialog.QUIET_HEART_RATE);
					}
				});
		alertDialogBuilder.setCancelable(true);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	/**
	 * ログアウト確認ダイアログを表示する
	 */
	private void showLogoutDialog() {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		alertDialogBuilder.setMessage("ログアウトしますか？");
		alertDialogBuilder.setPositiveButton("はい",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						userLogout();
					}
				});
		alertDialogBuilder.setNegativeButton("いいえ",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
					}
				});
		alertDialogBuilder.setCancelable(true);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	/**
	 * 設定リストを更新する
	 */
	private void updateList() {
		clearList();
		setList();
		bodyListAdapter.notifyDataSetChanged();
		masterListAdapter.notifyDataSetChanged();
	}

	@Override
	public void dataChanged() {
		updateList();
	}

	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		if (key.equals(PreferenceConstants.weight())) {
			dao.updateUserWeight(user.getId(), user.getPassword(),
					String.valueOf(Math.round(user.getWeight())));
		} else if (key.equals(PreferenceConstants.quietHeartRate())) {
			dao.updateUserQuietHeartRate(user.getId(), user.getPassword(),
					String.valueOf(user.getQuietHeartRate()));
		} else {
			dao.updateUser(user.getId(), user.getName(), user.getBirth(),
					String.valueOf(Math.round(user.getHeight())),
					user.getMail(), user.getPassword());
		}
	}

	@Override
	public void complete() {
	}

	@Override
	public void incomplete() {

	}

	@Override
	public void dateChanged(String year, String month, String day) {
		user.setBirth(DateUtil.dateFormat(year, month, day));
		user.setAge(DateUtil.getAge(year, month, day));
		updateList();
	}

	@Override
	public void complete(Bitmap image) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dataChanged(String data) {
		// TODO Auto-generated method stub

	}

	@Override
	public void validate() {
		Message message = new Message();
		message.what = MESSAGE_VALIDATE;
		handler.sendMessage(message);
	}

}
