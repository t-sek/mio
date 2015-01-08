package ac.neec.mio.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

public class InputStreamRequest extends Request<InputStream> {

	private final Listener<InputStream> listener;

	public InputStreamRequest(int method, String url,
			Listener<InputStream> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		Log.d("request", "new ");
		this.listener = listener;
	}

	public InputStreamRequest(String url, Listener<InputStream> listener,
			ErrorListener errorListener) {
		this(Method.GET, url, listener, errorListener);
	}

	@Override
	protected void deliverResponse(InputStream response) {
		listener.onResponse(response);
	}

	@Override
	protected Response<InputStream> parseNetworkResponse(
			NetworkResponse response) {
		Log.d("request", "response " + response);
		InputStream is = new ByteArrayInputStream(response.data);
		return Response.success(is,
				HttpHeaderParser.parseCacheHeaders(response));
	}

	public void doRequest(String url) {
		InputStreamRequest request = new InputStreamRequest(url,
				new Listener<InputStream>() {
					@Override
					public void onResponse(InputStream in) {
						try {
							in.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						if (listener != null) {
						}
					}
				}, new ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// error
					}
				});
	}
}
