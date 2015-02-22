package ac.neec.mio.util;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	private static final String SECTION = ":";

	public static String integerToString(int hour, int min) {
		StringBuilder sb = new StringBuilder();
		if (hour != 0) {
			sb.append(hour);
			sb.append("時間");
		}
		if (min != 0) {
			sb.append(min);
			sb.append("分");
		}
		if (sb.toString().length() == 0) {
			sb.append("---");
		}
		return sb.toString();
	}

	public static String stringToSec(String strTime) {
		String[] splitTime = strTime.split(SECTION, 0);
		return splitTime[2];
	}

	public static String stringToFormat(String sec) {
		return integerToString(Integer.valueOf(sec));
	}

	public static String integerToString(int sec) {
		StringBuilder sb = new StringBuilder();
		int min = sec / 60;
		sec -= min * 60;
		if (min < 10) {
			sb.append(0);
		}
		sb.append(min);
		sb.append(SECTION);
		if (sec < 10) {
			sb.append(0);
		}
		sb.append(sec);
		return sb.toString();
	}

	public static long integerToLong(int hour, int min, int sec) {
		return ((long) hour * 3600 + (long) min * 60 + (long) sec) * 1000;
	}

	public static long timeToLong(Time time) {
		return integerToLong(time.getHours(), time.getMinutes(),
				time.getSeconds());
	}

	public static int timeToInteger(Time time) {
		return (time.getHours() * 360) + (time.getMinutes() * 60)
				+ (time.getSeconds());
	}

	public static String longTimeToSmartTime(String time) {
		StringBuilder sb = new StringBuilder();
		sb.append(time.split(SECTION)[1]);
		sb.append(SECTION);
		sb.append(time.split(SECTION)[2]);
		return sb.toString();
	}

	public static int stringToInteger(String strTime) {
		int time;
		if (strTime == null) {
			return 0;
		}
		String[] splitTime = strTime.split(SECTION, 0);
		// time = Integer.valueOf(strTime.split(SECTION)[0]) * 60;
		// time += Integer.valueOf(strTime.split(SECTION)[1]);
		time = Integer.valueOf(splitTime[0]) * 60;
		time += Integer.valueOf(splitTime[1]);
		return time;
	}

	public static long stringToLong(String time) {
		int hour = Integer.valueOf(time.split(SECTION)[0]);
		int min = Integer.valueOf(time.split(SECTION)[1]);
		return hour * 60 + min;
	}

	public static String timetoSec(String min, String sec) {
		int time = Integer.valueOf(minToSec(min));
		time += Integer.valueOf(sec);
		return String.valueOf(time);
	}

	public static int timeToSec(int min, int sec) {
		return min * 60 + sec;
	}

	public static String minToSec(String min) {
		int time = Integer.valueOf(min);
		time *= 60;
		return String.valueOf(time);
	}

	public static String hourToSec(String hour) {
		int time = Integer.valueOf(hour);
		time *= 360;
		return String.valueOf(time);
	}

	public static String nowDateHour() {
		Date date = new Date();
		return String.valueOf(date.getHours());
	}

	public static String nowDateMin() {
		Date date = new Date();
		int min = date.getMinutes();
		StringBuilder sb = new StringBuilder();
		if (min < 10) {
			sb.append("0");
		}
		sb.append(min);
		return sb.toString();
	}

	public static String nowDateTime() {
		Date date = new Date();
		int hour = date.getHours();
		int min = date.getMinutes();
		int sec = date.getSeconds();
		StringBuilder sb = new StringBuilder();
		sb.append(hour);
		if (min < 10) {
			sb.append(0);
		}
		sb.append(min);
		// if (sec < 10) {
		// sb.append(0);
		// }
		// sb.append(sec);
		return sb.toString();
	}

	public static String[] getPlayZeroHour() {
		String[] hour = new String[181];
		for (int i = 0; i < 181; i++) {
			hour[i] = String.valueOf(i);
		}
		return hour;
	}

	public static String[] getPlayHour() {
		String[] hour = new String[180];
		for (int i = 0; i < 180; i++) {
			hour[i] = String.valueOf(i + 1);
		}
		return hour;
	}

	public static String[] getPlayNotZeroMin() {
		String[] min = new String[59];
		for (int i = 0; i < 59; i++) {
			min[i] = String.valueOf(i + 1);
		}
		return min;
	}

	public static String[] getHour() {
		String[] hour = new String[23];
		for (int i = 0; i < 23; i++) {
			hour[i] = String.valueOf(i + 1);
		}
		return hour;
	}

	public static String[] getMin() {
		String[] min = new String[60];
		for (int i = 0; i < 60; i++) {
			min[i] = String.valueOf(i);
		}
		return min;
	}

}
