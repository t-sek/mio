package ac.neec.mio.dao.item.api;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.SingleClientConnManager;

import ac.neec.mio.R;
import android.content.Context;

/**
 * WebAPIとHTTPS通信するための証明書の設定をするクラス
 *
 */
public class HttpsClient extends DefaultHttpClient {

	/**
	 * コンテキスト
	 */
	private final Context context;

	/**
	 * 
	 * @param context
	 *            コンテキスト
	 */
	public HttpsClient(Context context) {
		this.context = context;
	}

	@Override
	protected ClientConnectionManager createClientConnectionManager() {
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		registry.register(new Scheme("https", getSocketFactory(context), 443));
		return new SingleClientConnManager(getParams(), registry);
	}

	/**
	 * ソケットファクトリーを証明書を設定して返す
	 * 
	 * @param context
	 *            コンテキスト
	 * @return ソケットファクトリー
	 */
	private SSLSocketFactory getSocketFactory(Context context) {
		SSLSocketFactory socketFactory = null;
		try {
			CertificateFactory certificateFactory = CertificateFactory
					.getInstance("X.509", "BC");
			Certificate certificate = null;
			certificate = certificateFactory.generateCertificate(context
					.getResources().openRawResource(R.raw.server));
			String keyStoreType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			keyStore.load(null, null);
			// 証明書の指定
			keyStore.setCertificateEntry("ca", certificate);
			socketFactory = new SSLSocketFactory(keyStore);
			socketFactory
					.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return socketFactory;
	}
}
