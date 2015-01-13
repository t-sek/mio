package ac.neec.mio.dao.item.api;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.exception.NetworkException;
import ac.neec.mio.http.HttpsClient;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;

public abstract class HttpsDao implements Runnable, ApiDao {

	private String url;
	private Context context;
	private Sourceable listner;
	private DefaultHttpClient client;
	private boolean image;

	protected HttpsDao(Context context, Sourceable listener) {
		this.context = context;
		this.listner = listener;
	}

	@Override
	public void cancel() {
		client.getConnectionManager().shutdown();
	};

	@Override
	public void run() {
		if (image) {
			image();
		} else {
			connect();
		}

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
			Log.d("dao", "image connect");
			notifyResponse(stream);
			// return BitmapFactory.decodeStream(stream);
		} catch (IOException e) {
			e.printStackTrace();
			listner.incomplete();
		} finally {
			if (stream != null)
				try {
					stream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if (connection != null)
				connection.disconnect();
		}
	}

	private void connect() {
		HttpResponse httpResponse = null;
		InputStream response = null;
		client = new HttpsClient(context);
		try {
			HttpUriRequest httpGet = new HttpGet(url);
			httpResponse = client.execute(httpGet);
			response = httpResponse.getEntity().getContent();
		} catch (IOException e) {
			e.printStackTrace();
			listner.incomplete();
			return;
		}
		notifyResponse(response);
		listner.complete();
	}

	private void connectionStart(String url) {
		Log.d("dao", "url " + url);
		this.url = url;
		Thread thread = new Thread(this);
		thread.setUncaughtExceptionHandler(new NetworkException());
		thread.start();
	}

	protected void executeImage(String url) {
		image = true;
		connectionStart(url);
	}

	protected void execute(String url) {
		image = false;
		connectionStart(url);
	}

	protected abstract void notifyResponse(InputStream response);

}
