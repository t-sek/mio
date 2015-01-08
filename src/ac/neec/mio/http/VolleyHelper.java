package ac.neec.mio.http;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyHelper {
	public static final Object lock = new Object();

	public static RequestQueue requestQueue;

	public static RequestQueue getRequestQueue(final Context context) {
		synchronized (lock) {
			if (requestQueue == null) {
				requestQueue = Volley.newRequestQueue(context);
			}
			return requestQueue;
		}
	}

}
