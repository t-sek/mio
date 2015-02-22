package ac.neec.mio.ui.dialog;

import static ac.neec.mio.consts.SignUpConstants.*;
import ac.neec.mio.R;
import ac.neec.mio.ui.listener.CallbackListener;
import ac.neec.mio.ui.picker.DrumPicker;
import ac.neec.mio.user.User;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class ProfileBodilySelectDialog extends PickerBaseDialog {

	public static final int HEIGHT = 1;
	public static final int WEIGHT = 2;
	public static final int QUIET_HEART_RATE = 3;
	private static final float DEFAULT_HELGHT = 100;
	private static final float DEFAULT_WEIGHT = 30;
	private static final int DEFAULT_QUIET_HEART_RATE = 40;

	private ProfileBodilyCallbackListener listener;
	private Dialog dialog;
	private int name;
	private Button buttonClose;
	private Button buttonDecided;
	private TextView textDialogTitle;

	private String selectedData;
	private int id;
	private User user = User.getInstance();

	protected String[] row;

	public interface ProfileBodilyCallbackListener {
		void dataChanged();

		void dataChanged(String data);
	}

	public ProfileBodilySelectDialog() {
	}

	public ProfileBodilySelectDialog(ProfileBodilyCallbackListener listener,
			String[] row, int name) {
		this.listener = listener;
		this.row = row;
		this.name = name;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new Dialog(getActivity());
		setDialog();
		initFindViews();
		setListeners();
		setPicker();
		setTitle();
		return dialog;
	}

	private void setTitle() {
		switch (name) {
		case HEIGHT:
			textDialogTitle.setText(height());
			break;
		case WEIGHT:
			textDialogTitle.setText(weight());
			break;
		case QUIET_HEART_RATE:
			textDialogTitle.setText(quietHeartRate());
			break;
		default:
			break;
		}
	}

	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		// フルスクリーン
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		dialog.setContentView(R.layout.dialog_profile_setting_bodily);
		// 背景を透明にする
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
	}

	private void setPicker() {
		line = (LinearLayout) dialog.findViewById(R.id.main_line_0);
		picker = (DrumPicker) dialog.findViewById(R.id.picker);
		setLine();
		picker.setOnTouchListener(this);
		picker.setOnScrollViewListener(this);

		if (name == HEIGHT) {
			String height = String.valueOf(user.getHeight());
			for (int i = 0; i <= height.length(); i++) {
				if (height.equals(row[i])) {
					picker.fullScroll(ScrollView.FOCUS_DOWN);
				}
			}
		}

	}

	private void initFindViews() {
		buttonClose = (Button) dialog.findViewById(R.id.btn_dialog_close);
		buttonDecided = (Button) dialog.findViewById(R.id.btn_dialog_decided);
		textDialogTitle = (TextView) dialog.findViewById(R.id.dialog_title);
	}

	private void setListeners() {
		buttonDecided.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// listener.onSelected(id);
				switch (name) {
				case HEIGHT:
					textDialogTitle.setText(height());
					if (selectedData != null) {
						user.setHeight(Float.valueOf(selectedData));
					} else {
						user.setHeight(DEFAULT_HELGHT);
					}
					break;
				case WEIGHT:
					textDialogTitle.setText(weight());
					if (selectedData != null) {
						user.setWeight(Float.valueOf(selectedData));
					} else {
						user.setWeight(DEFAULT_WEIGHT);
					}
					listener.dataChanged(selectedData);
					break;
				case QUIET_HEART_RATE:
					textDialogTitle.setText(quietHeartRate());
					if (selectedData != null) {
						user.setQuietHeartRate(Integer.valueOf(selectedData));
					} else {
						user.setQuietHeartRate(DEFAULT_QUIET_HEART_RATE);
					}
					break;
				default:
					break;
				}
				listener.dataChanged();
				dismiss();
			}
		});
	}

	// private void setScrollPosition(final int index) {
	// new Handler().post(new Runnable() {
	// public void run() {
	// unitHeight = pickerPanel.getRowHeight(0);
	// scrollTo(0, (index + 1) * unitHeight);
	// }
	// });
	// }

	/**
	 * ドラム作成
	 */
	private void setLine() {
		ViewGroup viewGroup = null;
		String[] element;
		viewGroup = line;
		element = row;
		for (int j = 0; j < element.length; j++) {
			View view = dialog.getLayoutInflater().inflate(R.layout.picker_row,
					null);
			((TextView) view.findViewById(R.id.textview)).setText(element[j]);
			viewGroup.addView(view);
		}
		// ドラム要素0番目の上と最終要素の下にスキマを作る。今回は常に見える要素数が５なので２個ずつ。７個の場合は３個ずつですよ。
		unit = dialog.getLayoutInflater().inflate(R.layout.picker_row, null);
		View view_0 = dialog.getLayoutInflater().inflate(R.layout.picker_row,
				null);
		View view_1 = dialog.getLayoutInflater().inflate(R.layout.picker_row,
				null);
		View view_2 = dialog.getLayoutInflater().inflate(R.layout.picker_row,
				null);
		viewGroup.addView(unit, 0);
		viewGroup.addView(view_0, 0);
		viewGroup.addView(view_1, viewGroup.getChildCount());
		viewGroup.addView(view_2, viewGroup.getChildCount());
	}

	protected void moveScrollFirst(final int mode) {
		Message message = new Message();
		message.what = MESSAGE_WHAT;
		if (mode == 0) {
			isRepeat_0 = true;
			to_0 = getToPosition(row.length, picker.getScrollY());
			handler_0.sendMessageDelayed(message, REPEAT_INTERVAL);
		}
	}

	// 繰り返しハンドラー
	private Handler handler_0 = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 2.isRepeatがtrueなら処理を繰り返す
			if (isRepeat_0) {// 3.繰り返し処理
				moveScroll(0);
				// 4.次回処理をセット
				handler_0.sendMessageDelayed(obtainMessage(), REPEAT_INTERVAL);
			}
		}
	};

	private int getToPosition(int max, int currentY) {
		// 行き先要素番号の決定
		int index = 0;
		for (int i = 0; i < max; i++) {
			if (currentY < unitHeight * i + unitHeight / 2) {
				index = i;
				break;
			}
		}
		return unitHeight * index;
	}

	/**
	 * 移動量を決める
	 * 
	 */
	protected void moveScroll(final int mode) {
		// TrainingSettingPicker sv = (mode == 0 ? picker : mScrollView_1);
		DrumPicker sv = picker;
		int currentY = sv.getScrollY();
		int toY = (mode == 0 ? to_0 : to_1);
		if (toY == currentY) {
			if (mode == 0)
				isRepeat_0 = false;
			if (mode == 1)
				isRepeat_1 = false;
		} else {
			int abs = Math.abs(currentY - toY);
			int deltaY = 1;// ここを1以外にすると振動子になる可能性があります。
			if (abs > 100)
				deltaY = 20;
			else if (abs > 50)
				deltaY = 10;
			else if (abs > 10)
				deltaY = 5;
			if (currentY < toY)
				currentY += deltaY;
			else
				currentY -= deltaY;
			sv.scrollTo(0, currentY);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// もし自動で移動しているときに触ったらその瞬間ストップ。
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (v.getId() == picker.getId())
				isRepeat_0 = false;
			// else if (v.getId() == mScrollView_1.getId())
			// isRepeat_1 = false;
		}
		return false;
	}

	// テキストの更新
	private void updateText() {
		id = picker.getScrollY() / unitHeight;
		selectedData = row[id];
		// ((TextView) dialog.findViewById(R.id.txt_setting_insert))
		// .setText(selectedData);
	}

	@Override
	public void onScrollChanged(DrumPicker scrollView, int x, int y, int oldx,
			int oldy) {
		if (unitHeight == 0)
			unitHeight = unit.getHeight();
		updateText();
		int currentY = 0;
		int mode = 0;
		if (scrollView.getId() == picker.getId())
			currentY = picker.getScrollY();
		final int final_mode = mode;
		final int final_currentY = currentY;
		final DrumPicker final_scrollView = scrollView;
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				if (((final_mode == 0 && !isRepeat_0) || (final_mode == 1 && !isRepeat_1))
						&& final_scrollView.getScrollY() == final_currentY
						&& final_currentY % unitHeight != 0)
					moveScrollFirst(final_mode);
			}
		}, 100);
	}

}
