package ac.neec.mio.ui.dialog;

import ac.neec.mio.R;
import ac.neec.mio.user.User;
import ac.neec.mio.util.DateUtil;
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

public class ProfileBirthSettingDialog extends DialogFragment implements
		DrumPickerListener {

	private static final String PICKER_YEAR = "year";
	private static final String PICKER_MONTH = "month";
	private static final String PICKER_DAY = "day";

	private ProfileBirthCallbackListener listener;
	private Dialog dialog;
	private Button buttonDecided;
	private TextView textTitle;
	private String title;
	private User user = User.getInstance();
	private DrumPicker pickerYear;
	private DrumPicker pickerMonth;
	private DrumPicker pickerDay;
	// ドラム要素
	private String[] years;
	private String[] months;
	private String[] days;

	private String year;
	private String month;
	private String day;

	public interface ProfileBirthCallbackListener {
		void dateChanged(String year, String month, String day);
	}

	public ProfileBirthSettingDialog() {
	}

	public ProfileBirthSettingDialog(ProfileBirthCallbackListener listener,
			String yaer, String month, String day) {
		this.listener = listener;
		this.year = year;
		this.month = month;
		this.day = day;
	}

	public ProfileBirthSettingDialog(ProfileBirthCallbackListener listener) {
		this.listener = listener;
	}

	public ProfileBirthSettingDialog(ProfileBirthCallbackListener listener,
			String title) {
		this.listener = listener;
		this.title = title;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		dialog = new Dialog(getActivity());
		setDialog();
		initFindViews();
		// setListeners();
		setDate();
		setPicker();
		setPickerIndex();
		// setPicker();
		return dialog;
	}

	private void setDate() {
		years = DateUtil.getYears();
		year = years[0];
		months = DateUtil.getMonths();
		month = months[0];
		days = DateUtil.getDays(31);
		int maxDay = DateUtil.getActualMaximum(Integer.valueOf(years[0]),
				Integer.valueOf(months[0]));
		days = DateUtil.getDays(maxDay);
		day = days[0];
	}

	private void setDialog() {
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		// フルスクリーン
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		dialog.setContentView(R.layout.dialog_profile_setting_birth);
		// 背景を透明にする
		dialog.getWindow().setBackgroundDrawable(
				new ColorDrawable(Color.TRANSPARENT));
		// setCancelable(false);
	}

	private void initFindViews() {
		buttonDecided = (Button) dialog.findViewById(R.id.btn_dialog_decided);
		buttonDecided.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("dialog", "yaer " + year + " month " + month + " day "
						+ day);
				listener.dateChanged(year, month, day);
				dismiss();
			}
		});
		textTitle = (TextView) dialog.findViewById(R.id.title);
		if (title != null) {
			textTitle.setText(title);
		}
		pickerYear = (DrumPicker) dialog.findViewById(R.id.picker_year);
		pickerMonth = (DrumPicker) dialog.findViewById(R.id.picker_month);
		pickerDay = (DrumPicker) dialog.findViewById(R.id.picker_day);
	}

	private void setPicker() {
		Context context = getActivity().getApplicationContext();
		pickerYear.setElements(context, PICKER_YEAR, years);
		pickerMonth.setElements(context, PICKER_MONTH, months);
		pickerDay.setElements(context, PICKER_DAY, days);
		pickerYear.setOnDrumPickerListener(this);
		pickerMonth.setOnDrumPickerListener(this);
		pickerDay.setOnDrumPickerListener(this);
	}

	private void setPickerIndex() {
		String[] date = DateUtil.getSplitDate(DateUtil.japaneseFormat(user
				.getBirth()));
		int indexYear = DateUtil.dateIndex(years, date[0]) - 1;
		int indexMonth = DateUtil.dateIndex(months, date[1]) - 1;
		int indexDay = DateUtil.dateIndex(days, date[2]) - 1;
		pickerYear.setScrollPosition(indexYear);
		pickerMonth.setScrollPosition(indexMonth);
		pickerDay.setScrollPosition(indexDay);
	}

	private void updateDay() {
		int day = DateUtil.getActualMaximum(Integer.valueOf(year),
				Integer.valueOf(month));
		String[] days = new String[DateUtil.getDays(day).length];
		days = DateUtil.getDays(day);
		pickerDay.setElements(getActivity().getApplicationContext(),
				PICKER_DAY, days);
	}

	@Override
	public void onScrollChanged(String tag, String element, int index) {
		if (tag.equals(PICKER_YEAR)) {
			year = element;
			updateDay();
		} else if (tag.equals(PICKER_MONTH)) {
			month = element;
			updateDay();
		} else if (tag.equals(PICKER_DAY)) {
			day = element;
		}
	}

}
