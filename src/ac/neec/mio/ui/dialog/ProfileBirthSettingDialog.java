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

/**
 * 生年月日をピッカーで設定するダイアログクラス
 *
 */
public class ProfileBirthSettingDialog extends DialogFragment implements
		DrumPickerListener {

	/**
	 * 年ピッカーフラグ
	 */
	private static final String PICKER_YEAR = "year";
	/**
	 * 月ピッカーフラグ
	 */
	private static final String PICKER_MONTH = "month";
	/**
	 * 日ピッカーフラグ
	 */
	private static final String PICKER_DAY = "day";

	/**
	 * コールバックリスナー
	 */
	private ProfileBirthCallbackListener listener;
	/**
	 * ダイアログインスタンス
	 */
	private Dialog dialog;
	/**
	 * 決定ボタン
	 */
	private Button buttonDecided;
	/**
	 * タイトルを表示するテキストビュー
	 */
	private TextView textTitle;
	/**
	 * タイトル
	 */
	private String title;
	/**
	 * ユーザ情報
	 */
	private User user = User.getInstance();
	/**
	 * 年ピッカー
	 */
	private DrumPicker pickerYear;
	/**
	 * 月ピッカー
	 */
	private DrumPicker pickerMonth;
	/**
	 * 日ピッカー
	 */
	private DrumPicker pickerDay;
	/**
	 * 年ドラム要素
	 */
	private String[] years;
	/**
	 * 月ドラム要素
	 */
	private String[] months;
	/**
	 * 日ドラム要素
	 */
	private String[] days;
	/**
	 * 選択されている年
	 */
	private String year;
	/**
	 * 選択されている月
	 */
	private String month;
	/**
	 * 選択されている日
	 */
	private String day;

	/**
	 * 選択されている年月日を通知するリスナー
	 */
	public interface ProfileBirthCallbackListener {
		/**
		 * 選択されている年月日を通知する
		 * 
		 * @param year
		 *            年
		 * @param month
		 *            月
		 * @param day
		 *            日
		 */
		void dateChanged(String year, String month, String day);
	}

	public ProfileBirthSettingDialog() {
	}

	/**
	 * 
	 * @param listener
	 *            コールバックリスナー
	 */
	public ProfileBirthSettingDialog(ProfileBirthCallbackListener listener) {
		this.listener = listener;
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

	/**
	 * ピッカー要素の日付を設定する
	 */
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

	/**
	 * ダイアログを設定する
	 */
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

	/**
	 * 画面の初期化処理をする
	 */
	private void initFindViews() {
		buttonDecided = (Button) dialog.findViewById(R.id.btn_dialog_decided);
		buttonDecided.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
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

	/**
	 * ピッカーを設定する
	 */
	private void setPicker() {
		Context context = getActivity().getApplicationContext();
		pickerYear.setElements(context, PICKER_YEAR, years);
		pickerMonth.setElements(context, PICKER_MONTH, months);
		pickerDay.setElements(context, PICKER_DAY, days);
		pickerYear.setOnDrumPickerListener(this);
		pickerMonth.setOnDrumPickerListener(this);
		pickerDay.setOnDrumPickerListener(this);
	}

	/**
	 * 前回のピッカーの位置に移動する
	 */
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

	/**
	 * 年または月が変更されたとき、日の最大値を更新する
	 */
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
