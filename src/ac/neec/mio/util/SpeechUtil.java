package ac.neec.mio.util;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.SynthesisCallback;
import android.speech.tts.SynthesisRequest;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeechService;

/**
 * 音声読み上げユーティリティークラス
 */
public class SpeechUtil implements OnInitListener {

	/**
	 * スピーチインスタンス
	 */
	private static TextToSpeech speech;
	/**
	 * コールバックインスタンス
	 */
	private static OnInitListener instance = new SpeechUtil();
	/**
	 * 読み上げるメッセージ
	 */
	private static String text;

	/**
	 * 音声を読み上げる
	 * 
	 * @param context
	 *            コンテキスト
	 * @param text
	 *            読み上げるメッセージ
	 */
	public static void speech(Context context, String text) {
		SpeechUtil.text = text;
		speech = new TextToSpeech(context, instance);
		speech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS) {
			// 音声合成の設定を行う
			float pitch = 1.0f; // 音の高低
			float rate = 1.0f; // 話すスピード
			Locale locale = Locale.JAPAN; // 対象言語のロケール
			speech.setPitch(pitch);
			speech.setSpeechRate(rate);
			speech.setLanguage(locale);
		}
		speech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	/**
	 * 音声読み上げを終了する
	 */
	public static void speechOnDestroy() {
		speech.shutdown();
	}

}
