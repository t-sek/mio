package ac.neec.mio.http;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;

public class JapaneseRequest<T> extends Request<T> {

	public JapaneseRequest(int method, String url, ErrorListener listener) {
		super(method, url, listener);
	}

	@Override
	protected Response<T> parseNetworkResponse(NetworkResponse response) {
		String parsed;
		try {
			parsed = new String(response.data, "utf-8");// ここの第二引数を任意の文字コードに変更
		} catch (UnsupportedEncodingException e) {
			parsed = new String(response.data);
		}
		return (Response<T>) Response.success(parsed,
				HttpHeaderParser.parseCacheHeaders(response));
	}

	@Override
	protected void deliverResponse(T response) {
		// TODO Auto-generated method stub

	}

}
