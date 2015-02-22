package ac.neec.mio.ui.dialog;

import ac.neec.mio.R;
import ac.neec.mio.consts.MessageConstants;
import ac.neec.mio.timer.TimerManager;
import ac.neec.mio.ui.listener.TimerCallbackListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class LoadingDialog extends DialogFragment implements
		TimerCallbackListener {

	private static final int MESSAGE_UPDATE = 6;
	private static final int MESSAGE_DISMISS = 5;
	private static final String SECTION = ".";

	private Dialog dialog;
	private String message;
	private ProgressBar progressBar;
	private TextView textMessage;
	private TextView textSection;
	private TimerManager manager;
	private int sectionCount = 0;

	Handler handler = new Handler() {
		public void handleMessage(Message message) {
			switch (message.what) {
			case MESSAGE_UPDATE:
				updateTime((String) message.obj);
				break;
			case MESSAGE_DISMISS:
				updateDismiss();
			default:
				break;
			}
		};
	};

	public LoadingDialog() {
		this.message = MessageConstants.getting();
		manager = new TimerManager(this);
	}

	public LoadingDialog(String message) {
		this.message = message;
		manager = new TimerManager(this);
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new Dialog(getActivity());
		setDialog();
		progressBar = (ProgressBar) dialog.findViewById(R.id.progress);
		progressBar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		textMessage = (TextView) dialog.findViewById(R.id.text_message);
		textMessage.setText(message);
		textSection = (TextView) dialog.findViewById(R.id.text_section);
		manager.start();
		return dialog;

	}

	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		// フルスクリーン
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		dialog.setContentView(R.layout.dialog_loading);
		// 背景を透明にする
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		WindowManager.LayoutParams wmlp = dialog.getWindow().getAttributes();
		wmlp.gravity = Gravity.CENTER;
		setCancelable(false);
	}

	@Override
	public void dismiss() {
		super.dismiss();
		manager.stop();
	}

	private void updateDismiss() {
		progressBar.setVisibility(View.INVISIBLE);
		textMessage.setText("完了!");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		dismiss();
	}

	private void updateTime(String time) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i <= sectionCount; i++) {
			sb.append(SECTION);
		}
		if (sectionCount >= 4) {
			sectionCount = 0;
		} else {
			sectionCount++;
		}
		textSection.setText(sb.toString());
	}

	@Override
	public void onUpdate(String time) {
		Message message = new Message();
		message.what = MESSAGE_UPDATE;
		message.obj = time;
		handler.sendMessage(message);
	}

	@Override
	public void onUpdateLap(String time) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAlarm() {
		// TODO Auto-generated method stub

	}

	@Override
	public void notifyMin() {
		// TODO Auto-generated method stub

	}

}
