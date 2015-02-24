package ac.neec.mio.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.util.Log;

/**
 * 日付ユーティリティークラス
 */
public class DateUtil {

	/**
	 * 日付区切り
	 */
	private static final String SECTION = "-";

	/**
	 * 
	 * @param date
	 * @return
	 */
	public static String splitCreated(String date) {
		String[] created = date.split(" ");
		return created[0];
	}

	/**
	 * 今日の日付をyyyy-mm-dd形式で取得する
	 * 
	 * @return 今日の日付
	 */
	public static String nowDate() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String date = sdf.format(cal.getTime());
		return date;
	}

	/**
	 * 配列に格納されている日付のインデックスを返す
	 * 
	 * @param strings
	 *            配列
	 * @param date
	 *            日付
	 * @return
	 */
	public static int dateIndex(String[] strings, String date) {
		List<String> elements = Arrays.asList(strings);
		return elements.indexOf(date);
	}

	/**
	 * 月末日を返す
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return
	 */
	public static int getActualMaximum(int year, int month) {
		Calendar cal = Calendar.getInstance();
		cal.clear();
		cal.set(year, month - 1, 1);
		return cal.getActualMaximum(Calendar.DATE);
	}

	/**
	 * 今日の年を返す
	 * 
	 * @return 年
	 */
	public static String nowYear() {
		return nowDate().split(SECTION)[0];
	}

	/**
	 * 今日の月を返す
	 * 
	 * @return 月
	 */
	public static String nowMonth() {
		return nowDate().split(SECTION)[1];
	}

	/**
	 * 今日の日を返す
	 * 
	 * @return 日
	 */
	public static String nowDay() {
		return nowDate().split(SECTION)[2];
	}

	/**
	 * 今日の日付をyyyy年mm月dd日形式で取得する
	 * 
	 * @return 今日の日付
	 */
	public static String japaneseNowDay() {
		String[] date = nowDate().split(SECTION);
		return date[0] + "年" + date[1] + "月" + date[2] + "日";
	}

	/**
	 * 生年月日から年齢を算出する
	 * 
	 * @param y
	 *            生年月日 年
	 * @param m
	 *            生年月日 月
	 * @param d
	 *            生年月日 日
	 * @return 年齢
	 */
	public static int getAge(String y, String m, String d) {
		if (y.length() == 0 || m.length() == 0 || d.length() == 0) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);
		int mon = cal.get(Calendar.MONTH) + 1;
		int day = cal.get(Calendar.DATE);
		return (year - Integer.parseInt(y) - ((Integer.parseInt(m) > mon || (Integer
				.parseInt(m) == mon && Integer.parseInt(d) > day)) ? 1 : 0));
	}

	/**
	 * hh:mm形式をhh時:mm分に変換する
	 * 
	 * @param format
	 *            変換する時間
	 * @return 変換後時間
	 */
	public static String trainingTimeJapaneseFormat(String format) {
		String[] time = format.split(":");
		return time[1] + "時" + time[2] + "分";
	}

	/**
	 * yyyy年mm月dd日形式をString配列に変換する
	 * 
	 * @param date
	 *            変換する日付
	 * @return 変換後日付
	 */
	public static String[] getSplitDate(String date) {
		String[] splitDate = new String[3];
		String[] year = date.split("年");
		splitDate[0] = year[0];
		String[] month = year[1].split("月");
		splitDate[1] = month[0];
		String[] day = month[1].split("日");
		splitDate[2] = day[0];
		return splitDate;
	}

	/**
	 * 年月日をyyyy-mm-dd形式に変換する
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @param day
	 *            日
	 * @return yyyy-mm-dd形式年月日
	 * 
	 */
	public static String dateFormat(String year, String month, String day) {
		return year + SECTION + month + SECTION + day;
	}

	/**
	 * yyyy-mm-dd形式をyyyy年mm月dd日に変換する
	 * 
	 * @param date
	 *            変換する日付
	 * @return 変換後日付
	 */
	public static String japaneseFormat(String date) {
		String[] format = date.split(SECTION);
		return format[0] + "年" + format[1] + "月" + format[2] + "日";
	}

	/**
	 * 日付設定で使う年要素を取得する
	 * 
	 * @return 要素
	 */
	public static String[] getYears() {
		String[] years = new String[100];
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.YEAR, 0);
		int nowYear = calendar.get(Calendar.YEAR);
		for (int i = 0; i < 100; i++) {
			years[i] = String.valueOf(nowYear - i);
		}
		return years;
	}

	/**
	 * 日付設定で使う月要素を取得する
	 * 
	 * @return 要素
	 */
	public static String[] getMonths() {
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = String.valueOf(i + 1);
		}
		return months;
	}

	/**
	 * 日付設定で使う日要素を取得する
	 * 
	 * @return 要素
	 */
	public static String[] getDays(int maxDay) {
		String[] days = new String[maxDay];
		for (int i = 0; i < maxDay; i++) {
			days[i] = String.valueOf(i + 1);
		}
		return days;
	}

	/**
	 * 年月から日の要素を取得する
	 * 
	 * @param year
	 *            年
	 * @param month
	 *            月
	 * @return 日
	 */
	public static String[] getDays(int year, int month) {
		if (year == 0 || month == 0) {
			year = Integer.valueOf(nowYear());
			month = Integer.valueOf(nowMonth());
		}
		Calendar c = new GregorianCalendar(year, month, 1);
		int monthDays = c.getActualMaximum(Calendar.DAY_OF_MONTH);
		String[] days = new String[monthDays];
		for (int i = 0; i < monthDays; i++) {
			days[i] = String.valueOf(monthDays);
		}
		return days;
	}

	/**
	 * 日付をyyyy-mm-dd形式で取得する
	 * 
	 * @param last
	 *            前
	 * @return yyyy-mm-dd形式日付
	 */
	public static String getDate(int last) {
		if (last == 0) {
			nowDate();
		}
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -(last));
		String year = Integer.toString(calendar.get(Calendar.YEAR));
		String month = Integer.toString(calendar.get(Calendar.MONTH) + 1);
		String day = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
		if (month.length() == 1) {
			month = "0".concat(month);
		}
		if (day.length() == 1) {
			day = "0".concat(day);
		}
		return year + SECTION + month + SECTION + day;
	}

}
