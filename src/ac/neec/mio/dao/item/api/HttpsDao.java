package ac.neec.mio.dao.item.api;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;

import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.NetworkException;
import ac.neec.mio.http.HttpsClient;
import ac.neec.mio.util.BitmapUtil;
import android.content.Context;
import android.util.Log;

public abstract class HttpsDao implements Runnable, ApiDao {

	private static final int IMAGE = 1;
	private static final int POST_IMAGE = 2;
	private static final int HTTP = 3;

	private String url;
	private Context context;
	private Sourceable listener;
	private DefaultHttpClient client;
	private int mode;
	private JSONArray json;
	private List<NameValuePair> postData;

	protected HttpsDao(Context context, Sourceable listener) {
		this.context = context;
		this.listener = listener;
	}

	@Override
	public void cancel() {
		client.getConnectionManager().shutdown();
	};

	@Override
	public void run() {
		switch (mode) {
		case HTTP:
			connect();
			break;
		case POST_IMAGE:
			postImage2();
			break;
		case IMAGE:
			image();
			break;
		default:
			break;
		}
	}

	private void postImage2() {
		HttpImageMultipartRequest request = new HttpImageMultipartRequest(
				"http://www.hogehoge.com", postData, "upload-file",
				"file:///hoge/hoge.jpeg");
		String response = request.send();
	}

	private void postImage() {
		client = new HttpsClient(context);
		HttpPost httpPost = new HttpPost(url);
		// -----[POST送信するデータを格納]
		List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("name", "simo"));
		nameValuePair.add(new BasicNameValuePair("text", "hello!"));
		// -----[POST送信]
		try {
			StringEntity body = new StringEntity(json.toString());
			httpPost.setEntity(body);
			Log.d("dao", "json " + json.toString());
			// httpPost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			// httpPost.setEntity(new UrlEncodedFormEntity(new ImageInfo(null,
			// null, null, null, null, 0, 0).getImage()));
			HttpResponse response = client.execute(httpPost);
			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			response.getEntity().writeTo(byteArrayOutputStream);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			listener.incomplete();
		} catch (IOException e) {
			e.printStackTrace();
			listener.incomplete();
		} finally {
			json = null;
		}

	}

	private void connect() {
		HttpResponse httpResponse = null;
		InputStream response = null;
		client = new HttpsClient(context);
		try {
			// //-----[POST送信するデータを格納]
			// List<NameValuePair> nameValuePair = new
			// ArrayList<NameValuePair>(1);
			// nameValuePair.add(new BasicNameValuePair("name", "simo"));
			// nameValuePair.add(new BasicNameValuePair("text", "hello!"));
			// HttpPost httppost = new HttpPost(url);
			// httppost.setEntity(new UrlEncodedFormEntity(nameValuePair));
			HttpUriRequest httpGet = new HttpGet(url);
			httpResponse = client.execute(httpGet);
			response = httpResponse.getEntity().getContent();
		} catch (IOException e) {
			e.printStackTrace();
			listener.incomplete();
			return;
		}
		notifyResponse(response);
		listener.complete();
	}

	private void image() {
		HttpURLConnection connection = null;
		InputStream stream = null;
		try {
			connection = (HttpURLConnection) new URL(url).openConnection();
			connection.setDoInput(true);
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(30000);
			connection.setUseCaches(true);
			stream = connection.getInputStream();
			// notifyResponse(stream);
			listener.complete(BitmapUtil.streamToBitmap(stream));
		} catch (IOException e) {
			e.printStackTrace();
			listener.incomplete();
			return;
		} finally {
			// if (stream != null)
			// try {
			// stream.close();
			// } catch (IOException e) {
			// e.printStackTrace();
			// }
			if (connection != null)
				connection.disconnect();
		}
		// listner.complete(stream);
	}

	private void connectionStart(String url) {
		Log.d("dao", "url " + url);
		this.url = url;
		Thread thread = new Thread(this);
		thread.setUncaughtExceptionHandler(new NetworkException());
		thread.start();
	}

	protected void executePostImage(String url, JSONArray json) {
		mode = POST_IMAGE;
		this.json = json;
		connectionStart(url);
	}

	protected void executePostImage(String url, List<NameValuePair> postData) {
		mode = POST_IMAGE;
		this.postData = postData;
		connectionStart(url);
	}

	protected void executeImage(String url) {
		mode = IMAGE;
		connectionStart(url);
	}

	protected void execute(String url) {
		mode = HTTP;
		connectionStart(url);
	}

	protected abstract void notifyResponse(InputStream response);

}
