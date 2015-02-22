package ac.neec.mio.util;


public class TrainingUtil {

	public static void calcCalorie() {

	}

	public static void calcSpeed() {

	}

	public static void calcDistance() {

	}

	public static String[] getCalorie() {
		String[] calorie = new String[1000];
		for (int i = 0; i < calorie.length; i++) {
			calorie[i] = String.valueOf(i + 1);
		}
		return calorie;
	}

	public static String[] getDistance() {
		String[] distance = new String[11];
		for (int i = 0; i < distance.length; i++) {
			distance[i] = String.valueOf(i);
		}
		return distance;
	}
}
