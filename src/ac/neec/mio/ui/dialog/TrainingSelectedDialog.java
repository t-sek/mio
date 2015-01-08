package ac.neec.mio.ui.dialog;

import ac.neec.mio.R;
import ac.neec.mio.ui.listener.TrainingSelectCallbackListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.sek.drumpicker.DrumPicker;
import com.sek.drumpicker.DrumPickerListener;

public class TrainingSelectedDialog extends DialogFragment implements
		DrumPickerListener {

	private Context context;
	private TrainingSelectCallbackListener listener;
	private Dialog dialog;
	private String[] row;
	private Button buttonDecided;
	private TextView textDataInsert;
	private int index;
	private DrumPicker picker;

	public TrainingSelectedDialog(Context context,
			TrainingSelectCallbackListener listener, String[] row, int index) {
		this.context = context;
		this.listener = listener;
		this.row = row;
		Log.d("dialog", "create index " + index);
		this.index = index;
	}

	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		// フルスクリーン
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		dialog.setContentView(R.layout.dialog_training_data_insert);
		// 背景を透明にする
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		// setCancelable(false);
	}

	private void initFindViews() {
		buttonDecided = (Button) dialog.findViewById(R.id.btn_dialog_decided);
		picker = (DrumPicker) dialog.findViewById(R.id.picker);
		textDataInsert = (TextView) dialog
				.findViewById(R.id.txt_setting_insert);
		textDataInsert.setText(row[0]);
		picker.setElements(context, "", row);
		picker.setOnDrumPickerListener(this);
		picker.setUpdateStyle(false);
		picker.setTextSize(20);
		Log.d("dialog", "set index " + index);
		picker.setScrollPosition(index);
	}

	private void setListeners() {
		buttonDecided.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onSelected(textDataInsert.getText().toString());
				dismiss();
			}
		});
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new Dialog(getActivity());
		setDialog();
		initFindViews();
		setListeners();
		return dialog;

	}

	@Override
	public void onScrollChanged(String tag, String element, int index) {
		textDataInsert.setText(element);
		Log.d("dialog", "index " + index);
		listener.onSelected(index);
	}

}
