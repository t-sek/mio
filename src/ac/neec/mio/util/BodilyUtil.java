package ac.neec.mio.util;

import android.util.Log;

/**
 * 身体情報ユーティリティークラス
 */
public class BodilyUtil {

	/**
	 * 身長設定要素を取得する
	 * 
	 * @return 身長設定要素
	 */
	public static String[] height() {
		int length = 150;
		String[] height = new String[length];
		for (int i = 0; i < length; i++) {
			height[i] = String.valueOf(i + 100);
		}
		return height;
	}

	/**
	 * 体重設定要素を取得する
	 * 
	 * @return 体重設定要素
	 */
	public static String[] weight() {
		int length = 170;
		String[] weight = new String[length];
		for (int i = 0; i < length; i++) {
			weight[i] = String.valueOf(i + 30);
		}
		return weight;
	}

	/**
	 * 安静時心拍数設定要素を取得する
	 * 
	 * @return 安静時心拍数設定要素
	 */
	public static String[] quietHeartRate() {
		int length = 50;
		String[] heartRate = new String[length];
		for (int i = 0; i < length; i++) {
			heartRate[i] = String.valueOf(i + 40);
		}
		return heartRate;
	}

	/**
	 * 体重の少数点を消す
	 * 
	 * @param weight
	 *            体重
	 * @return 小数点を削除した体重
	 */
	public static String weightToRound(float weight) {
		return String.valueOf(Math.round(weight));
	}
}
