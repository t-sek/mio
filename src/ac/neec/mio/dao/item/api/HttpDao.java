package ac.neec.mio.dao.item.api;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import ac.neec.mio.dao.ApiDao;

public abstract class HttpDao implements ApiDao, Runnable {

	protected String url;
	private Thread thread;

	protected HttpDao(String url) {
		this.url = url;
		this.thread = new Thread(this);
	}

	@Override
	public void run() {
		HttpResponse httpResponse = null;
		InputStream response;
		try {
			DefaultHttpClient client = new DefaultHttpClient();
			HttpUriRequest httpGet = new HttpGet(url);
			httpResponse = client.execute(httpGet);
			response = httpResponse.getEntity().getContent();
			notifyResponse(response);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void execute() {
		thread.start();
	}

	protected abstract void notifyResponse(InputStream response);

}
