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

/**
 * 計測中目標心拍数変更ダイアログクラス
 *
 */
public class TargetHeartRateSettingDislog extends DialogFragment {

	/**
	 * 最低目標心拍数
	 */
	private static final int SEEK_MIN = 150;
	/**
	 * ダイアログインスタンス
	 */
	private Dialog dialog;
	/**
	 * 選択シークバー
	 */
	private SeekBar seekber;
	/**
	 * 決定ボタン
	 */
	private Button button;
	/**
	 * 選択された目標心拍数
	 */
	private int targetValue;
	/**
	 * コールバックリスナー
	 */
	private TargetHeartRateSettingListener listener;

	/**
	 * 
	 * @param listener
	 *            コールバックリスナー
	 * @param targetValue
	 *            現在の目標心拍数
	 */
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

	/**
	 * 画面の初期化処理をする
	 */
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
				listener.onUpdate(progress + SEEK_MIN);
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

	/**
	 * ダイアログを設定する
	 */
	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_setting_target_heart_rate);
	}

}
