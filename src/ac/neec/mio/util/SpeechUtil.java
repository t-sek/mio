package ac.neec.mio.util;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.speech.tts.SynthesisCallback;
import android.speech.tts.SynthesisRequest;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.speech.tts.TextToSpeechService;

public class SpeechUtil implements OnInitListener {

	public static final int DATA_CHECK_CODE = 1;
	
	private static TextToSpeech speech;
	private static OnInitListener instance = new SpeechUtil();
	private static String text;

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

	public static void speechOnDestroy() {
		speech.shutdown();
	}

}
