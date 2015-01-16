package ac.neec.mio.util;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import android.util.Log;

public class DateUtil {

	private static final String SECTION = "-";

	public static String splitCreated(String date) {
		String[] created = date.split(" ");
		return created[0];
	}

	public static String splitDate(String format) {
		String[] date = format.split(SECTION);
		return date[0] + date[1] + date[2];
	}

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
		cal.set(Calendar.YEAR, year);
		cal.set(Calendar.MONTH, month);
		return cal.getActualMaximum(Calendar.DATE);
	}

	public static String nowYear() {
		return nowDate().split(SECTION)[0];
	}

	public static String nowMonth() {
		return nowDate().split(SECTION)[1];
	}

	public static String nowDay() {
		return nowDate().split(SECTION)[2];
	}

	public static String japaneseNowDay() {
		String[] date = nowDate().split(SECTION);
		return date[0] + "年" + date[1] + "月" + date[2] + "日";
	}

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

	public static String timeJapaneseFormat(String format) {
		String[] time = format.split(":");
		return time[0] + "時" + time[1] + "分";
	}

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

	public static String dateFormat(String date) {
		Log.d("util", "date " + date);
		StringBuilder sb = new StringBuilder();
		String[] year = date.split("年");
		sb.append(year[0] + SECTION);
		String[] month = year[1].split("月");
		if (month[0].length() == 1) {
			sb.append("0");
		}
		sb.append(month[0] + SECTION);
		String[] day = month[1].split("日");
		if (day[0].length() == 1) {
			sb.append("0");
		}
		sb.append(day[0]);
		return sb.toString();
	}

	public static String dateFormat(String year, String month, String day) {
		// return japaneseFormat(year + SECTION + month + SECTION + day);
		return year + SECTION + month + SECTION + day;
	}

	public static String japaneseFormat(String date) {
		String[] format = date.split(SECTION);
		return format[0] + "年" + format[1] + "月" + format[2] + "日";
	}

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

	public static String[] getMonths() {
		String[] months = new String[12];
		for (int i = 0; i < 12; i++) {
			months[i] = String.valueOf(i + 1);
		}
		return months;
	}

	public static String[] getDays(int maxDay) {
		String[] days = new String[maxDay];
		for (int i = 0; i < maxDay; i++) {
			days[i] = String.valueOf(i + 1);
		}
		return days;
	}

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
