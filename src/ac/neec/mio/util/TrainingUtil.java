package ac.neec.mio.util;

/**
 * カロリー、走行距離を設定する要素ユーティリティークラス
 */
public class TrainingUtil {

	/**
	 * カロリーの要素を取得する
	 * 
	 * @return 要素
	 */
	public static String[] getCalorie() {
		String[] calorie = new String[1000];
		for (int i = 0; i < calorie.length; i++) {
			calorie[i] = String.valueOf(i + 1);
		}
		return calorie;
	}

	/**
	 * 走行距離の要素を取得する
	 * 
	 * @return 要素
	 */
	public static String[] getDistance() {
		String[] distance = new String[11];
		for (int i = 0; i < distance.length; i++) {
			distance[i] = String.valueOf(i);
		}
		return distance;
	}
}
