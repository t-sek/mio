package ac.neec.mio.dao.item.api;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import ac.neec.mio.consts.AppConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.ImageFileNotFoundException;
import ac.neec.mio.util.BitmapUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;

/**
 * WebAPI通信処理の実装クラス
 *
 */
public abstract class HttpsDao implements Runnable, ApiDao {

	private static final int IMAGE = 1;
	private static final int POST_IMAGE = 2;
	private static final int HTTP = 3;

	private String url;
	private Context context;
	private Sourceable listener;
	private DefaultHttpClient client;
	private int mode;
	private List<NameValuePair> postData;
	private String filePath;

	protected HttpsDao(Sourceable listener) {
		this.context = AppConstants.getContext();
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
			postImage();
			break;
		case IMAGE:
			image();
			break;
		default:
			break;
		}
	}

	/**
	 * 画像をPOST送信する
	 */
	private void postImage() {
		HttpImageMultipartRequest request = new HttpImageMultipartRequest(url,
				postData, filePath);
		for (NameValuePair nameValuePair : postData) {
			Log.d("dao", "pair " + nameValuePair.getName());
			Log.d("dao", "pair " + nameValuePair.getValue());
		}
		request.exetute(context, listener);
	}

	/**
	 * 接続する
	 */
	private void connect() {
		HttpResponse httpResponse = null;
		InputStream response = null;
		client = new HttpsClient(context);
		try {
			HttpUriRequest httpGet = new HttpGet(url);
			httpResponse = client.execute(httpGet);
			response = httpResponse.getEntity().getContent();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			listener.validate();
			return;
		} catch (IOException e) {
			e.printStackTrace();
			listener.incomplete();
			return;
		}
		notifyResponse(response);
		listener.complete();
	}

	/**
	 * 画像を取得する
	 */
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
			listener.complete(BitmapUtil.streamToBitmap(stream));
		} catch (IOException e) {
			e.printStackTrace();
			listener.incomplete();
			return;
		} finally {
			if (connection != null)
				connection.disconnect();
		}
	}

	/**
	 * 接続開始
	 * 
	 * @param url
	 *            接続先URL
	 */
	private void connectionStart(String url) {
		Log.d("dao", "url " + url);
		this.url = url;
		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * 画像をPOST送信する
	 * 
	 * @param url
	 *            接続先URL
	 * @param filePath
	 *            画像ファイルパス
	 */
	protected void executePostImage(String url, String filePath) {
		this.filePath = filePath;
		connectionStart(url);
	}

	/**
	 * 画像を取得する
	 * 
	 * @param url
	 *            取得したい画像URL
	 */
	protected void executeImage(String url) {
		mode = IMAGE;
		connectionStart(url);
	}

	/**
	 * 接続をする
	 * 
	 * @param url
	 *            接続先URL
	 */
	protected void execute(String url) {
		mode = HTTP;
		connectionStart(url);
	}

	protected abstract void notifyResponse(InputStream response);

}
