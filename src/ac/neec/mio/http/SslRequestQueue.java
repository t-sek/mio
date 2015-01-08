package ac.neec.mio.http;

import java.io.File;
import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.HurlStack;

public class SslRequestQueue extends RequestQueue {
	private static final String DEFAULT_CACHE_DIR = "volley";

	public SslRequestQueue(final Context context) {
		super(new DiskBasedCache(new File(context.getCacheDir(),
				DEFAULT_CACHE_DIR)), createNetwork(context));
	}

	private static Network createNetwork(Context context) {
		String userAgent = "volley/0";
		try {
			String packageName = context.getPackageName();
			PackageInfo info = context.getPackageManager().getPackageInfo(
					packageName, 0);
			userAgent = packageName + "/" + info.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
		}
		Log.d("queue", "ssl " + Ssl.getSocketFactory(context));
		return new BasicNetwork(new HurlStack(null,
				Ssl.getSocketFactory(context)));
	}

}
