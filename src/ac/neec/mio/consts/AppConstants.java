package ac.neec.mio.consts;

import android.content.Context;
import android.content.res.Resources;

public abstract class AppConstants {

	protected static Resources resources;
	protected static Context context;

	public static void setResorces(Resources resources) {
		AppConstants.resources = resources;
	}

	public static void setContext(Context context) {
		AppConstants.context = context;
	}

	public static Context getContext() {
		return context;
	}

}
