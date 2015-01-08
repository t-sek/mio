package ac.neec.mio.ui.activity;

import ac.neec.mio.R;
import ac.neec.mio.ui.dialog.ProfileBodilySelectDialog;
import ac.neec.mio.ui.dialog.ProfileBodilySelectDialog.ProfileBodilyCallbackListener;
import ac.neec.mio.user.User;
import ac.neec.mio.util.BodilyUtil;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class UserSignUpBodilyActivity extends Activity implements
		ProfileBodilyCallbackListener {

	private TextView textHeight;
	private TextView textWeight;
	private TextView textQuietHeartRate;
	private ImageView imageHeight;
	private ImageView imageWeight;
	private ImageView imageQuietHeartRate;
	private Button button;
	private User user = User.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_sign_up_bodily);
		initFindViews();
		setListener();
		setBodilyData();
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		setBodilyData();
	}

	private void initFindViews() {
		textHeight = (TextView) findViewById(R.id.txt_height);
		textWeight = (TextView) findViewById(R.id.txt_weight);
		textQuietHeartRate = (TextView) findViewById(R.id.txt_quiet_heart_rate);
		imageHeight = (ImageView) findViewById(R.id.image_height);
		imageWeight = (ImageView) findViewById(R.id.image_weight);
		imageQuietHeartRate = (ImageView) findViewById(R.id.image_quiet_heart_rate);
		button = (Button) findViewById(R.id.button);
	}

	private void setListener() {
		imageHeight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showHeightDialog();
			}
		});
		imageWeight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showWeightDialog();
			}
		});
		imageQuietHeartRate.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showQuietHeartRateAlertDialog();
			}
		});
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				intentSignUpConf();
			}
		});
	}

	private void setBodilyData() {
		textHeight.setText(String.valueOf(Math.round(user.getHeight())));
		textWeight.setText(BodilyUtil.weightToRound(user.getWeight()));
		textQuietHeartRate.setText(String.valueOf(Math.round(user
				.getQuietHeartRate())));
	}

	private void intentSignUpConf() {
		Intent intent = new Intent(UserSignUpBodilyActivity.this,
				UserSignUpConfActivity.class);
		startActivity(intent);
	}

	private void showHeightDialog() {
		showProfileBodilyDialog(BodilyUtil.height(),
				ProfileBodilySelectDialog.HEIGHT);
	}

	private void showWeightDialog() {
		showProfileBodilyDialog(BodilyUtil.weight(),
				ProfileBodilySelectDialog.WEIGHT);
	}

	private void showQuietHeartRateDialog() {
		showProfileBodilyDialog(BodilyUtil.quietHeartRate(),
				ProfileBodilySelectDialog.QUIET_HEART_RATE);
	}

	private void showProfileBodilyDialog(String[] row, int section) {
		new ProfileBodilySelectDialog(this, row, section).show(
				getFragmentManager(), "dialog");
	}

	private void intentQuietHeartRateMeasurement() {
		Intent intent = new Intent(UserSignUpBodilyActivity.this,
				DeviceScanActivity.class);
		startActivity(intent);
	}
	
	private void showQuietHeartRateAlertDialog() {
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
						showQuietHeartRateDialog();
					}
				});
		alertDialogBuilder.setCancelable(true);
		AlertDialog alertDialog = alertDialogBuilder.create();
		alertDialog.show();
	}

	@Override
	public void dataChanged() {
		setBodilyData();
	}

}
