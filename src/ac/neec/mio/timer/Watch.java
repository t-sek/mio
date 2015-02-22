package ac.neec.mio.timer;

import android.util.Log;

/**
 * Timerクラス、StopWatchクラスの共通項目を定義した抽象クラス
 *
 */
public abstract class Watch {

	/**
	 * コールバックリスナー
	 */
	private Observer o;

	/**
	 * 時計をスタートする
	 * 
	 * @param o
	 *            コールバックリスナー
	 */
	public final void measurement(Observer o) {
		this.o = o;
		start();
	}

	/**
	 * 時間を更新する
	 * 
	 * @param time
	 *            時間
	 */
	public final void update(long time) {
		o.onUpdate(time);
	}

	/**
	 * ラップタイムを更新する
	 * 
	 * @param time
	 *            ラップタイム
	 */
	public final void updateLap(long time) {
		o.onUpdateLap(time);
	}

	/**
	 * タイマー終了を通知する
	 */
	public final void alarm() {
		o.onAlarm();
	}

	/**
	 * スタートする
	 */
	protected abstract void start();

	/**
	 * ストップする
	 */
	protected abstract void stop();

	/**
	 * ラップを記録する
	 */
	protected abstract void lap();

	/**
	 * リセットする
	 */
	protected abstract void reset();
}
