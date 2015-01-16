package ac.neec.mio.dao;

import java.io.InputStream;

import android.graphics.Bitmap;

public interface Sourceable {
	void complete();
	void complete(InputStream response);
	void complete(Bitmap image);
	void incomplete();
}
