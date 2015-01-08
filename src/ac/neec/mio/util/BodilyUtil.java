package ac.neec.mio.util;

import android.util.Log;

public class BodilyUtil {

	public static String[] height() {
		int length = 150;
		String[] height = new String[length];
		for (int i = 0; i < length; i++) {
			height[i] = String.valueOf(i + 100);
		}
		return height;
	}

	public static String[] weight() {
		int length = 170;
		String[] weight = new String[length];
		for (int i = 0; i < length; i++) {
			weight[i] = String.valueOf(i + 30);
		}
		return weight;
	}

	public static String[] quietHeartRate() {
		int length = 50;
		String[] heartRate = new String[length];
		for (int i = 0; i < length; i++) {
			heartRate[i] = String.valueOf(i + 40);
		}
		return heartRate;
	}

	public static String weightToRound(float weight) {
		return String.valueOf(Math.round(weight));
	}
}
