//package ac.neec.mio.ui.dialog;
//
//import java.util.ArrayList;
//
//import ac.neec.mio.R;
//import ac.neec.mio.ui.listener.TrainingSettingCallbackListener;
//import ac.neec.mio.ui.picker.DrumPicker;
//import ac.neec.mio.ui.picker.TrainingSettingPicker;
//import ac.neec.mio.ui.picker.TrainingSettingPickerListener;
//import ac.neec.mio.util.TimeUtil;
//import android.app.Dialog;
//import android.app.DialogFragment;
//import android.graphics.Color;
//import android.graphics.drawable.ColorDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Message;
//import android.util.Log;
//import android.view.MotionEvent;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.View.OnTouchListener;
//import android.view.ViewGroup;
//import android.view.Window;
//import android.view.WindowManager;
//import android.widget.Button;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//public class TrainingMenuDetailTimeSettingDialog extends PickerBaseDialog {
//
//	private TrainingSettingCallbackListener listener;
//	private Dialog dialog;
//	private String settingName;
//	private Button buttonClose;
//	private Button buttonDecided;
//	private TextView textDialogTitle;
//	private TextView textInsertData;
//
//	// ドラム要素
//	private String[] hour;
//	private String[] min;
//
//	private String hourSelected = "0";
//	private String minSelected = "0";
//
//	private String selectedData;
//
//	public TrainingMenuDetailTimeSettingDialog() {
//	}
//
//	public TrainingMenuDetailTimeSettingDialog(
//			TrainingSettingCallbackListener listener, String[] hour,
//			String[] min) {
//		this.listener = listener;
//		this.hour = hour;
//		this.min = min;
//	}
//
//	@Override
//	public Dialog onCreateDialog(Bundle savedInstanceState) {
//		dialog = new Dialog(getActivity());
//		settingName = getArguments().getString("name");
//		setDialog();
//		initFindViews();
//		setListeners();
//		setPicker();
//		return dialog;
//	}
//
//	private void setDialog() {
//		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
//		// フルスクリーン
//		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
//				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
//		dialog.setContentView(R.layout.dialog_training_time_insert);
//		// 背景を透明にする
//		dialog.getWindow().setBackgroundDrawable(
//				new ColorDrawable(Color.TRANSPARENT));
//		setCancelable(false);
//	}
//
//	private void setPicker() {
//		lineHour = (LinearLayout) dialog.findViewById(R.id.main_line_0);
//		lineMin = (LinearLayout) dialog.findViewById(R.id.main_line_1);
//		pickerHour = (TrainingSettingPicker) dialog
//				.findViewById(R.id.picker_hour);
//		pickerMin = (TrainingSettingPicker) dialog
//				.findViewById(R.id.picker_min);
//		setLine();
//		pickerHour.setOnTouchListener(this);
//		pickerHour.setOnScrollViewListener(this);
//		pickerMin.setOnTouchListener(this);
//		pickerMin.setOnScrollViewListener(this);
//
//	}
//
//	private void initFindViews() {
//		buttonClose = (Button) dialog.findViewById(R.id.btn_dialog_close);
//		buttonDecided = (Button) dialog.findViewById(R.id.btn_dialog_decided);
//		textInsertData = (TextView) dialog
//				.findViewById(R.id.txt_setting_insert);
//		textDialogTitle = (TextView) dialog.findViewById(R.id.dialog_title);
//		textDialogTitle.setText(settingName);
//	}
//
//	private void setListeners() {
//		buttonClose.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				dismiss();
//			}
//		});
//		buttonDecided.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				Log.e("", "settingName " + settingName);
//				listener.onDecided(settingName, Integer.valueOf(hourSelected),
//						Integer.valueOf(minSelected));
//				dismiss();
//			}
//		});
//	}
//
//	/**
//	 * ドラム作成
//	 */
//	private void setLine() {
//		ViewGroup viewGroup = null;
//		String[] element;
//		for (int i = 0; i < 2; i++) {// みぎとひだりなので２回まわします
//			if (i == 0) {
//				viewGroup = lineHour;
//				element = hour;
//			} else {
//				viewGroup = lineMin;
//				element = min;
//			}
//			// viewGroup = line;
//			// element = min;
//			for (int j = 0; j < element.length; j++) {
//				View view = dialog.getLayoutInflater().inflate(
//						R.layout.picker_row, null);
//				((TextView) view.findViewById(R.id.textview))
//						.setText(element[j]);
//				viewGroup.addView(view);
//			}
//			// ドラム要素0番目の上と最終要素の下にスキマを作る。今回は常に見える要素数が５なので２個ずつ。７個の場合は３個ずつですよ。
//			unit = dialog.getLayoutInflater()
//					.inflate(R.layout.picker_row, null);
//			View view_0 = dialog.getLayoutInflater().inflate(
//					R.layout.picker_row, null);
//			View view_1 = dialog.getLayoutInflater().inflate(
//					R.layout.picker_row, null);
//			View view_2 = dialog.getLayoutInflater().inflate(
//					R.layout.picker_row, null);
//			viewGroup.addView(unit, 0);
//			viewGroup.addView(view_0, 0);
//			viewGroup.addView(view_1, viewGroup.getChildCount());
//			viewGroup.addView(view_2, viewGroup.getChildCount());
//		}
//
//	}
//
//	protected void moveScrollFirst(final int mode) {
//		Message message = new Message();
//		message.what = MESSAGE_WHAT;
//		if (mode == 0) {// ひだり
//			isRepeat_0 = true;
//			to_0 = getToPosition(hour.length, pickerHour.getScrollY());
//			handler_0.sendMessageDelayed(message, REPEAT_INTERVAL);
//		} else {// みぎ
//			isRepeat_1 = true;
//			to_1 = getToPosition(min.length, pickerMin.getScrollY());
//			handler_1.sendMessageDelayed(message, REPEAT_INTERVAL);
//		}
//	}
//
//	// 繰り返しハンドラー
//	private Handler handler_0 = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			// 2.isRepeatがtrueなら処理を繰り返す
//			if (isRepeat_0) {// 3.繰り返し処理
//				moveScroll(0);
//				// 4.次回処理をセット
//				handler_0.sendMessageDelayed(obtainMessage(), REPEAT_INTERVAL);
//			}
//		}
//	};
//
//	// 繰り返しハンドラー右用
//	private Handler handler_1 = new Handler() {
//		@Override
//		public void handleMessage(Message msg) {
//			// 2.isRepeatがtrueなら処理を繰り返す
//			if (isRepeat_1) {// 3.繰り返し処理
//				moveScroll(1);
//				// 4.次回処理をセット
//				handler_1.sendMessageDelayed(obtainMessage(), REPEAT_INTERVAL);
//			}
//		}
//	};
//
//	private int getToPosition(int max, int currentY) {
//		// 行き先要素番号の決定
//		int index = 0;
//		for (int i = 0; i < max; i++) {
//			if (currentY < unitHeight * i + unitHeight / 2) {
//				index = i;
//				break;
//			}
//		}
//		return unitHeight * index;
//	}
//
//	/**
//	 * 移動量を決めるメソッド
//	 * 
//	 * @param mode
//	 */
//	protected void moveScroll(final int mode) {
//		TrainingSettingPicker sv = (mode == 0 ? pickerHour : pickerMin);
//		int currentY = sv.getScrollY();
//		int toY = (mode == 0 ? to_0 : to_1);
//		if (toY == currentY) {
//			if (mode == 0)
//				isRepeat_0 = false;
//			if (mode == 1)
//				isRepeat_1 = false;
//		} else {
//			int abs = Math.abs(currentY - toY);
//			int deltaY = 1;// ここを1以外にすると振動子になる可能性があります。
//			if (abs > 100)
//				deltaY = 20;
//			else if (abs > 50)
//				deltaY = 10;
//			else if (abs > 10)
//				deltaY = 5;
//			if (currentY < toY)
//				currentY += deltaY;
//			else
//				currentY -= deltaY;
//			sv.scrollTo(0, currentY);
//		}
//	}
//
//	@Override
//	public boolean onTouch(View v, MotionEvent event) {
//		// もし自動で移動しているときに触ったらその瞬間ストップ。
//		if (event.getAction() == MotionEvent.ACTION_DOWN) {
//			if (v.getId() == pickerHour.getId())
//				isRepeat_0 = false;
//			else if (v.getId() == pickerMin.getId())
//				isRepeat_1 = false;
//		}
//		return false;
//	}
//
//	// テキストの更新
//	private void updateText() {
//		hourSelected = hour[pickerHour.getScrollY() / unitHeight];
//		minSelected = min[pickerMin.getScrollY() / unitHeight];
//
//		// StringBuilder sb = new StringBuilder();
//		// if (Integer.valueOf(hour[pickerHour.getScrollY() / unitHeight]) != 0)
//		// {
//		// sb.append(hourSelected);
//		// sb.append("時間");
//		// }
//		// if (Integer.valueOf(min[pickerMin.getScrollY() / unitHeight]) != 0) {
//		// sb.append(minSelected);
//		// sb.append("分");
//		// }
//		// if (sb.toString().length() == 0) {
//		// sb.append("---");
//		// }
//		// selectedData = sb.toString();
//		selectedData = TimeUtil.integerToString(Integer.valueOf(hourSelected),
//				Integer.valueOf(minSelected));
//		textInsertData.setText(selectedData);
//	}
//
//	@Override
//	public void onScrollChanged(TrainingSettingPicker scrollView, int x, int y,
//			int oldx, int oldy) {
//		// ScrollViewの座標が変わったらここが反応します。この場所にupdateText()を入れておけばスマート
//		if (unitHeight == 0)
//			unitHeight = unit.getHeight();
//		updateText();
//		int currentY = 0;
//		int mode = 0;// 0で左1で右を表現。。。でも、昔のコードで何を意図していたのか謎です。
//		if (scrollView.getId() == pickerHour.getId())
//			currentY = pickerHour.getScrollY();
//		else if (scrollView.getId() == pickerMin.getId()) {
//			currentY = pickerMin.getScrollY();
//			mode = 1;
//		}
//		final int final_mode = mode;
//		final int final_currentY = currentY;
//		final TrainingSettingPicker final_scrollView = scrollView;
//		new Handler().postDelayed(new Runnable() {
//			@Override
//			public void run() {
//				if (((final_mode == 0 && !isRepeat_0) || (final_mode == 1 && !isRepeat_1))
//						&& final_scrollView.getScrollY() == final_currentY
//						&& final_currentY % unitHeight != 0)
//					moveScrollFirst(final_mode);
//			}
//		}, 100);
//	}
//
//	@Override
//	public void onScrollChanged(DrumPicker scrollView, int x, int y, int oldx,
//			int oldy) {
//		// TODO Auto-generated method stub
//		
//	}
//
//}
