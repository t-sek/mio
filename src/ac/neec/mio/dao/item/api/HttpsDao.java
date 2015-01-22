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
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

import ac.neec.mio.consts.AppConstants;
import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.dao.Sourceable;
import ac.neec.mio.exception.NetworkException;
import ac.neec.mio.http.HttpsClient;
import ac.neec.mio.user.User;
import ac.neec.mio.util.BitmapUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
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
			postImage4();
			break;
		case IMAGE:
			image();
			break;
		default:
			break;
		}
	}

	private void postImage3() {
		HttpPost request = new HttpPost(url);
		try {
			// 送信パラメータのエンコードを指定
			request.setEntity(new UrlEncodedFormEntity(postData, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		DefaultHttpClient httpClient = new DefaultHttpClient();
		String ret = "";
		try {
			Log.d("posttest", "POST開始");
			ret = httpClient.execute(request, new ResponseHandler<String>() {
				@Override
				public String handleResponse(HttpResponse response)
						throws IOException {
					Log.d("posttest", "レスポンスコード："
							+ response.getStatusLine().getStatusCode());
					// 正常に受信できた場合は200
					switch (response.getStatusLine().getStatusCode()) {
					case HttpStatus.SC_OK:
						Log.d("posttest", "レスポンス取得に成功");
						// レスポンスデータをエンコード済みの文字列として取得する
						return EntityUtils.toString(response.getEntity(),
								"UTF-8");
					case HttpStatus.SC_NOT_FOUND:
						Log.d("posttest", "データが存在しない");
						return null;
					default:
						Log.d("posttest", "通信エラー");
						return null;
					}
				}
			});
		} catch (IOException e) {
			Log.d("posttest", "通信に失敗：" + e.toString());
		} finally {
			// shutdownすると通信できなくなる
			httpClient.getConnectionManager().shutdown();
		}
	}

	private void postImage4() {
		HttpImageMultipartRequest request = new HttpImageMultipartRequest(url,
				postData, "nori",
				"/storage/emulated/0/DCIM/Camera/148101398012~2~2.jpg");
		for (NameValuePair nameValuePair : postData) {
			Log.d("dao", "pair " + nameValuePair.getName());
			Log.d("dao", "pair " + nameValuePair.getValue());
		}
		request.exetute(context, listener);
		// String response = request.execute();
	}

	private void postImage2() {
		HttpImageMultipartRequest request = new HttpImageMultipartRequest(
				"http://www.hogehoge.com", postData, "upload-file",
				"file:///hoge/hoge.jpeg");
		// HttpImageMultipartRequest request = new
		// HttpImageMultipartRequest(url,
		// postData, bos.toByteArray());
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		Bitmap image = User.getInstance().getImage();
		image.compress(CompressFormat.JPEG, 100, bos);
		String response = request.execute();
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
