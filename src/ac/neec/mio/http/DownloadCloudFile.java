package ac.neec.mio.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

public class DownloadCloudFile extends AsyncTask<Void, Void, File> {
	private Context context;
	private Uri uri;

	public DownloadCloudFile(Context context, Uri uri) {
		this.context = context;
		this.uri = uri;
	}

	@Override
	protected File doInBackground(Void... params) {
		File cacheFile = new File(context.getExternalCacheDir(), "image_cache");
		try {
			InputStream is = null;
			is = context.getContentResolver().openInputStream(uri);
			OutputStream os = new FileOutputStream(cacheFile);
//			FileUtility.copyStream(is, os);
			return cacheFile;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onPreExecute() {
	}

	@Override
	protected void onPostExecute(File file) {
		if (file != null) {
			// file処理する
			// パラメータのファイルが実態なのであとはこのfileを扱えばOK
		} else {
			Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
		}
	}
}
