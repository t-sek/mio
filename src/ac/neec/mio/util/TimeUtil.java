package ac.neec.mio.util;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 時間ユーティリティークラス
 */
public class TimeUtil {

	/**
	 * 時間区切り
	 */
	private static final String SECTION = ":";

	/**
	 * mm:ss形式の時間の秒を取得する
	 * 
	 * @param strTime
	 *            mm:ss形式の時間
	 * @return 秒
	 */
	public static String stringToSec(String strTime) {
		String[] splitTime = strTime.split(SECTION, 0);
		return splitTime[2];
	}

	/**
	 * 秒をmm:ss形式に変換する
	 * 
	 * @param sec
	 *            秒
	 * @return mm:ss形式の時間
	 */
	public static String stringToFormat(String sec) {
		return integerToString(Integer.valueOf(sec));
	}

	/**
	 * 秒をmm:ss形式に変換する
	 * 
	 * @param sec
	 *            秒
	 * @return mm:ss形式の時間
	 */
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

	/**
	 * long型に変換する
	 * 
	 * @param hour
	 *            時間
	 * @param min
	 *            分
	 * @param sec
	 *            秒
	 * @return long型
	 */
	public static long integerToLong(int hour, int min, int sec) {
		return ((long) hour * 3600 + (long) min * 60 + (long) sec) * 1000;
	}

	/**
	 * Time型をlong型に変換する
	 * 
	 * @param time
	 *            Time型
	 * @return long型
	 */
	public static long timeToLong(Time time) {
		return integerToLong(time.getHours(), time.getMinutes(),
				time.getSeconds());
	}

	/**
	 * Time型をint型に変換する
	 * 
	 * @param time
	 *            Time型
	 * @return int型
	 */
	public static int timeToInteger(Time time) {
		return (time.getHours() * 360) + (time.getMinutes() * 60)
				+ (time.getSeconds());
	}

	/**
	 * hh:mm:ss形式をmm:ss形式に変換する
	 * 
	 * @param time
	 *            変換する時間
	 * @return 変換後時間
	 */
	public static String longTimeToSmartTime(String time) {
		StringBuilder sb = new StringBuilder();
		sb.append(time.split(SECTION)[1]);
		sb.append(SECTION);
		sb.append(time.split(SECTION)[2]);
		return sb.toString();
	}

	/**
	 * hh-mm-ss形式から秒を取得する
	 * 
	 * @param strTime
	 *            変換する時間
	 * @return 秒
	 */
	public static int stringToInteger(String strTime) {
		int time;
		if (strTime == null) {
			return 0;
		}
		String[] splitTime = strTime.split(SECTION, 0);
		time = Integer.valueOf(splitTime[0]) * 60;
		time += Integer.valueOf(splitTime[1]);
		return time;
	}

	/**
	 * hh-mm-ss形式から秒を取得する
	 * 
	 * @param strTime
	 *            変換する時間
	 * @return 秒
	 */
	public static long stringToLong(String time) {
		int hour = Integer.valueOf(time.split(SECTION)[0]);
		int min = Integer.valueOf(time.split(SECTION)[1]);
		return hour * 60 + min;
	}

	/**
	 * 分と秒から秒を算出する
	 * 
	 * @param min
	 *            分
	 * @param sec
	 *            秒
	 * @return 秒
	 */
	public static String timetoSec(String min, String sec) {
		int time = Integer.valueOf(minToSec(min));
		time += Integer.valueOf(sec);
		return String.valueOf(time);
	}

	/**
	 * 分と秒から秒を算出する
	 * 
	 * @param min
	 *            分
	 * @param sec
	 *            秒
	 * @return 秒
	 */
	public static int timeToSec(int min, int sec) {
		return min * 60 + sec;
	}

	/**
	 * 分から秒を算出する
	 * 
	 * @param min
	 *            分
	 * @return 秒
	 */
	public static String minToSec(String min) {
		int time = Integer.valueOf(min);
		time *= 60;
		return String.valueOf(time);
	}

	/**
	 * 時間から秒を算出する
	 * 
	 * @param hour
	 *            時間
	 * @return 秒
	 */
	public static String hourToSec(String hour) {
		int time = Integer.valueOf(hour);
		time *= 360;
		return String.valueOf(time);
	}

	/**
	 * 現在の時間を表示する
	 * 
	 * @return 時
	 */
	public static String nowDateHour() {
		Date date = new Date();
		return String.valueOf(date.getHours());
	}

	/**
	 * 現在の時間を表示する
	 * 
	 * @return 分
	 */
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

	/**
	 * 現在の時間を取得する
	 * 
	 * @return 現在の時間
	 */
	public static String nowDateTime() {
		Date date = new Date();
		int hour = date.getHours();
		int min = date.getMinutes();
		StringBuilder sb = new StringBuilder();
		sb.append(hour);
		if (min < 10) {
			sb.append(0);
		}
		sb.append(min);
		return sb.toString();
	}

	/**
	 * 計測時間の0からの時間要素を取得する
	 * 
	 * @return 要素
	 */
	public static String[] getPlayZeroHour() {
		String[] hour = new String[181];
		for (int i = 0; i < 181; i++) {
			hour[i] = String.valueOf(i);
		}
		return hour;
	}

	/**
	 * 計測時間の1からの時間要素を取得する
	 * 
	 * @return 要素
	 */
	public static String[] getPlayHour() {
		String[] hour = new String[180];
		for (int i = 0; i < 180; i++) {
			hour[i] = String.valueOf(i + 1);
		}
		return hour;
	}

	/**
	 * 計測時間の1からの分要素を取得する
	 * 
	 * @return 要素
	 */
	public static String[] getPlayNotZeroMin() {
		String[] min = new String[59];
		for (int i = 0; i < 59; i++) {
			min[i] = String.valueOf(i + 1);
		}
		return min;
	}

	/**
	 * 時計の時間要素を取得する
	 * 
	 * @return 要素
	 */
	public static String[] getHour() {
		String[] hour = new String[23];
		for (int i = 0; i < 23; i++) {
			hour[i] = String.valueOf(i + 1);
		}
		return hour;
	}

	/**
	 * 時計の分要素を取得する
	 * 
	 * @return 要素
	 */
	public static String[] getMin() {
		String[] min = new String[60];
		for (int i = 0; i < 60; i++) {
			min[i] = String.valueOf(i);
		}
		return min;
	}

}
