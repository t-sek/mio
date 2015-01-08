package ac.neec.mio.util;

import ac.neec.mio.training.heartrate.HeartRate;

public class TrainingUtil {

	public static void calcCalorie() {

	}

	public static void calcSpeed() {

	}

	public static void calcDistance() {

	}

	public static void heartRateFormat(HeartRate[] heartRates) {

	}

	public static String[] getCalorie() {
		String[] calorie = new String[1000];
		for (int i = 0; i < 1000; i++) {
			calorie[i] = String.valueOf(i + 1);
		}
		return calorie;
	}

	public static String[] getDistance() {
		String[] distance = new String[10];
		for (int i = 0; i < 10; i++) {
			distance[i] = String.valueOf(i + 1);
		}
		return distance;
	}
}
