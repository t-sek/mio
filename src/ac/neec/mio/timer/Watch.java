package ac.neec.mio.timer;

import android.util.Log;

public abstract class Watch {

	private Observer o;

	public final void measurement(Observer o) {
		this.o = o;
		start();
	}

	public final void update(long time) {
		o.onUpdate(time);
	}

	public final void updateLap(long time) {
		o.onUpdateLap(time);
	}

	public final void alarm() {
		o.onAlarm();
	}

	protected abstract void start();

	protected abstract void stop();

	protected abstract void lap();

	protected abstract void reset();
}
