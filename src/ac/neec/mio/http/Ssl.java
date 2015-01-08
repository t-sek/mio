package ac.neec.mio.http;

import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import ac.neec.mio.R;
import android.content.Context;
import android.util.Log;

public class Ssl {
	public static SSLSocketFactory getSocketFactory(Context context) {
		try {
			CertificateFactory certificateFactory = CertificateFactory
					.getInstance("X.509","BC");
			Certificate certificate = null;
			Log.d("", "factory");
			certificate = certificateFactory.generateCertificate(context
					.getResources().openRawResource(R.raw.server));
			Log.d("SSL", "ca=" + ((X509Certificate) certificate).getSubjectDN());

			String keyStoreType = KeyStore.getDefaultType();
			KeyStore keyStore = KeyStore.getInstance(keyStoreType);
			keyStore.load(null, null);
			// 証明書の指定
			keyStore.setCertificateEntry("ca", certificate);

			TrustManagerFactory managerFactory = TrustManagerFactory
					.getInstance(TrustManagerFactory.getDefaultAlgorithm());
			managerFactory.init(keyStore);

			SSLContext sslContext = SSLContext.getInstance("TLS");
			sslContext.init(null, managerFactory.getTrustManagers(), null);
			return sslContext.getSocketFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
