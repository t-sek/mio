package ac.neec.mio.dao.item.api;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import ac.neec.mio.dao.ApiDao;
import ac.neec.mio.exception.NetworkException;
import ac.neec.mio.http.HttpsClient;
import android.content.Context;
import android.util.Log;

public abstract class HttpsDao implements Runnable, ApiDao {

	private String url;
	private Context context;
	private Sourceable listner;
	private DefaultHttpClient client;

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

	protected void execute(String url) {
		Log.d("dao", "url " + url);
		this.url = url;
		Thread thread = new Thread(this);
//		thread.setUncaughtExceptionHandler(new NetworkException());
		thread.start();
	}

	protected abstract void notifyResponse(InputStream response);

}
