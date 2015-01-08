package ac.neec.mio.ui.dialog;

import ac.neec.mio.R;
import ac.neec.mio.ui.listener.TargetHeartRateSettingListener;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class TargetHeartRateSettingDislog extends DialogFragment {

	private static final int SEEK_MIN = 150;

	private Dialog dialog;
	private SeekBar seekber;
	private Button button;

	private int targetValue;

	private TargetHeartRateSettingListener listener;

	public TargetHeartRateSettingDislog(
			TargetHeartRateSettingListener listener, int targetValue) {
		this.listener = listener;
		this.targetValue = targetValue - SEEK_MIN;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new Dialog(getActivity());
		setDialog();
		init();
		return dialog;
	}

	@Override
	public void onCancel(DialogInterface dialog) {
		super.onCancel(dialog);
		listener.onCancel();
	}

	private void init() {
		seekber = (SeekBar) dialog.findViewById(R.id.seek_target_heart_rate);
		seekber.setProgress(targetValue);
		seekber.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				listener.onUpdate(progress+SEEK_MIN);
			}
		});
		button = (Button) dialog
				.findViewById(R.id.btn_target_heart_rate_setting);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onDecided(seekber.getProgress() + SEEK_MIN);
				dismiss();
			}
		});
	}

	private void setDialog() {
		// dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		// フルスクリーン
		// dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		dialog.setContentView(R.layout.dialog_setting_target_heart_rate);
	}

}
