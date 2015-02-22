package ac.neec.mio.ui.activity;

import java.util.ArrayList;
import java.util.List;

import com.sek.circleimageview.CircleImageView;

import ac.neec.mio.R;
import ac.neec.mio.consts.ErrorConstants;
import ac.neec.mio.consts.MessageConstants;
import ac.neec.mio.consts.PermissionConstants;
import ac.neec.mio.consts.SQLConstants;
import ac.neec.mio.consts.SettingConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.DaoFacade;
import ac.neec.mio.dao.SQLiteDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.SQLiteInsertException;
import ac.neec.mio.exception.SQLiteTableConstraintException;
import ac.neec.mio.exception.XmlParseException;
import ac.neec.mio.exception.XmlReadException;
import ac.neec.mio.group.GroupInfo;
import ac.neec.mio.group.MemberInfo;
import ac.neec.mio.group.Permission;
import ac.neec.mio.training.Training;
import ac.neec.mio.ui.adapter.MemberMenuListAdapter;
import ac.neec.mio.ui.adapter.TrainingDateListAdapter;
import ac.neec.mio.ui.dialog.LoadingDialog;
import ac.neec.mio.ui.dialog.SelectionAlertDialog;
import ac.neec.mio.ui.listener.AlertCallbackListener;
import ac.neec.mio.ui.listener.TrainingDataListCallbackListener;
import ac.neec.mio.user.User;
import ac.neec.mio.user.UserInfo;
import ac.neec.mio.util.BitmapUtil;
import ac.neec.mio.util.DateUtil;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class GroupMemberInfoActivity extends Activity implements Sourceable,
		TrainingDataListCallbackListener, AlertCallbackListener {

	private static final int MESSAGE_UPDATE = 1;
	private static final int MESSAGE_NETWORK_ERROR = 3;
	private static final int DATE_NUM = 50000;

	private TextView textUserName;
	private TextView textUserId;
	private TextView textPermissionName;
	private TextView textMenu;
	private ListView listMenu;
	private CircleImageView imageProfile;
	private ExpandableListView listTraining;
	private LoadingDialog dialog = new LoadingDialog();
	private TrainingDateListAdapter adapter;
	private String userId;
	private String userName;
	private String groupId;
	private ApiDao dao;
	private Bitmap image;
	private SQLiteDao daoSql;
	private Permission permission;
	private Permission permissionMember;
	private boolean permissionChange;
	private User user = User.getInstance();
	private List<Training> response = new ArrayList<Training>();
	private List<List<Training>> trainings = new ArrayList<List<Training>>();
	private List<String> menus = new ArrayList<String>();
	private boolean finish;

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_UPDATE:
				dialog = new LoadingDialog();
				dialog.show(getFragmentManager(), "dialog");
				setTraining();
				update();
				dialog.dismiss();
				break;
			case MESSAGE_NETWORK_ERROR:
				Toast.makeText(getApplicationContext(),
						ErrorConstants.networkError(), Toast.LENGTH_SHORT)
						.show();
				break;
			default:
				break;
			}
		};
	};

	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_member_info);
		dao = DaoFacade.getApiDao(this);
		daoSql = DaoFacade.getSQLiteDao();
		Intent intent = getIntent();
		userId = intent.getStringExtra("user_id");
		userName = intent.getStringExtra("user_name");
		groupId = intent.getStringExtra("group_id");
		int permissionId = intent.getIntExtra("permission_id",
				PermissionConstants.notice());
		int memberPermissionId = intent.getIntExtra("member_permission_id",
				PermissionConstants.notice());
		byte[] b = intent.getByteArrayExtra("image");
		image = BitmapUtil.blobToBitmap(b);
		permissionMember = daoSql.selectPermission(memberPermissionId);
		permission = daoSql.selectPermission(permissionId);
		dao.selectTraining(user.getId(), userId, "2014-11-01",
				DateUtil.nowDate(), DATE_NUM, 0, user.getPassword());
		dialog.show(getFragmentManager(), "dialog");
		initFindViews();
		// setListener();
		setUserData();
		setAdapter();
		setMenuAdapter();
	}

	private void update() {
		adapter.notifyDataSetChanged();
	}

	private void initFindViews() {
		textUserName = (TextView) findViewById(R.id.text_user_name);
		textUserId = (TextView) findViewById(R.id.text_user_id);
		textPermissionName = (TextView) findViewById(R.id.text_permission_name);
		textMenu = (TextView) findViewById(R.id.operationText);
		listMenu = (ListView) findViewById(R.id.list_member_menu);
		listTraining = (ExpandableListView) findViewById(R.id.list_training_data);
		listTraining.setEmptyView(findViewById(R.id.empty));
		imageProfile = (CircleImageView) findViewById(R.id.img_profile);
	}

	private void setMenuAdapter() {
		menus = new ArrayList<String>();
		if (permissionMember.getId() == PermissionConstants.groupAdmin()) {
			textMenu.setVisibility(View.INVISIBLE);
			return;
		}

		if (permission.getPermissionChange()) {
			if (permissionMember.getId() == PermissionConstants.member()) {
				menus.add(SettingConstants.addTrainer());
			} else if (permissionMember.getId() == PermissionConstants
					.trainer()) {
				menus.add(SettingConstants.deleteTrainer());
			}
			menus.add(SettingConstants.deleteMember());
		} else {
			textMenu.setVisibility(View.INVISIBLE);
		}
		MemberMenuListAdapter adapter = new MemberMenuListAdapter(
				getApplicationContext(), R.layout.item_member_menu, menus);
		listMenu.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String menu = menus.get(position);
				if (menu.equals(SettingConstants.addTrainer())) {
					showAddTrainerDialog();
				} else if (menu.equals(SettingConstants.deleteTrainer())) {
					showDeleteTrainerDialog();
				} else if (menu.equals(SettingConstants.deleteMember())) {
					showDeleteMemberDialog();
				}
			}
		});
		listMenu.setAdapter(adapter);
	}

	private void showAddTrainerDialog() {
		SelectionAlertDialog dialog = new SelectionAlertDialog(this,
				SettingConstants.messageTrainer(),
				SettingConstants.messagePositive(),
				SettingConstants.messageNegative(), true);
		dialog.show(getFragmentManager(), "dialog");
	}

	private void showDeleteTrainerDialog() {
		SelectionAlertDialog dialog = new SelectionAlertDialog(this,
				SettingConstants.messageMember(),
				SettingConstants.messagePositive(),
				SettingConstants.messageNegative(), true);
		dialog.show(getFragmentManager(), "dialog");
	}

	private void showDeleteMemberDialog() {
		SelectionAlertDialog dialog = new SelectionAlertDialog(this,
				SettingConstants.messageNotive(),
				SettingConstants.messagePositive(),
				SettingConstants.messageNegative(), true);
		dialog.show(getFragmentManager(), "dialog");
	}

	private void setAdapter() {
		adapter = new TrainingDateListAdapter(getApplicationContext(),
				trainings, this, R.color.theme_white_dark);
		listTraining.setAdapter(adapter);
		listTraining.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// downloadTrainingLog(item);
				intentDetailTrainingData(groupPosition, childPosition);
				return false;
			}
		});
	}

	private void intentDetailTrainingData(int groupPosition, int childPosition) {
		Training item = trainings.get(groupPosition).get(childPosition);
		Intent intent = new Intent(GroupMemberInfoActivity.this,
				TrainingDataDetailActivity.class);
		intent.putExtra(SQLConstants.trainingId(), item.getId());
		intent.putExtra("target_user_id", userId);
		startActivity(intent);
	}

	private void setUserData() {
		textUserName.setText(userName);
		textUserId.setText(userId);
		textPermissionName.setText(permissionMember.getName());
		if (image != null) {
			imageProfile.setImage(image);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void closeGroup(int position) {
		listTraining.collapseGroup(position);
	}

	private Message setMessage(int msg) {
		Message message = new Message();
		message.what = msg;
		return message;
	}

	private void setTraining() {
		String lastDate = response.get(0).getDate();
		List<Training> lastTraining = new ArrayList<Training>();
		for (Training training : response) {
			if (training.getDate().equals(lastDate)) {
				lastTraining.add(training);
			} else {
				trainings.add(lastTraining);
				lastTraining = new ArrayList<Training>();
				lastTraining.add(training);
			}
			lastDate = training.getDate();
		}
		trainings.add(lastTraining);
		adapter.notifyDataSetChanged();
	}

	@Override
	public void complete() {
		dialog.dismiss();
		if (finish) {
			finish();
			return;
		}
		if (!permissionChange) {
			try {
				response = dao.getResponse();
				if (response.size() == 0) {
					return;
				}
				handler.sendMessage(setMessage(MESSAGE_UPDATE));
			} catch (XmlParseException e) {
				e.printStackTrace();
				return;
			} catch (XmlReadException e) {
				e.printStackTrace();
				return;
			}
		} else {
			GroupInfo info;
			try {
				info = dao.getResponse();
				daoSql.deleteGroupMember(info.getId());
				for (MemberInfo member : info.getMembers()) {
					daoSql.insertGroupMember(info.getId(), member.getUserId(),
							member.getUserName(), member.getPermissionId(),
							null);
				}
				finish();
			} catch (XmlParseException e) {
				e.printStackTrace();
			} catch (XmlReadException e) {
				e.printStackTrace();
			} catch (SQLiteInsertException e) {
				e.printStackTrace();
			} catch (SQLiteTableConstraintException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void incomplete() {
		handler.sendMessage(setMessage(MESSAGE_NETWORK_ERROR));
	}

	@Override
	public void complete(Bitmap image) {
	}

	@Override
	public void onNegativeSelected(String message) {
	}

	@Override
	public void onPositiveSelected(String message) {
		permissionChange = true;
		dialog = new LoadingDialog(MessageConstants.setting());
		dialog.show(getFragmentManager(), "dialog");
		if (message.equals(SettingConstants.messageTrainer())) {
			dao.insertGroupTrainer(user.getId(), userId, groupId,
					user.getPassword());
		} else if (message.equals(SettingConstants.messageNotive())) {
			finish = true;
			dao.deleteGroupMember(user.getId(), userId, groupId,
					user.getPassword());
		} else if (message.equals(SettingConstants.messageMember())) {
			dao.insertGroupMember(user.getId(), userId, groupId,
					user.getPassword());
		}

	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub

	}
}
