package ac.neec.mio.util;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

public class ExternalAppGallery {
	public static final int REQUEST_GALLERY = 1;
	public static final int REQUEST_CAMERA = 2;
	public static final int REQUEST_CROP = 3;

	private static final int TRIMMING_X = 100;
	private static final int TRIMMING_Y = 100;
	private static final int ASPECT_X = 1;
	private static final int ASPECT_Y = 1;

	public static void openGallery(Activity activity) {

		// Intent intent = new Intent();
		// intent.setType("image/*");
		// intent.setAction(Intent.ACTION_GET_CONTENT);
		// activity.startActivityForResult(intent, REQUEST_GALLERY);

		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		intent.setType("image/*");
		activity.startActivityForResult(intent, REQUEST_GALLERY);

	}

	public static Uri openCamera(Activity activity) {
		Uri uri = null;
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(System.currentTimeMillis());
		stringBuilder.append(".jpg");
		String fileName = stringBuilder.toString();

		ContentValues values = new ContentValues();
		values.put(MediaStore.Images.Media.TITLE, fileName);
		values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
		ContentResolver contentResolver = activity.getContentResolver();
		uri = contentResolver.insert(
				MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
		Log.d("Gallery", "uri " + uri);
		Intent intent = new Intent();
		intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		activity.startActivityForResult(intent, REQUEST_CAMERA);
		return uri;
	}

	public static void performCrop(Activity activity, Uri uri) {
		try {
			try {
				Intent intent = new Intent("com.android.camera.action.CROP");
				intent.setDataAndType(uri, "image/*");
				intent.putExtra("outputX", TRIMMING_X);
				intent.putExtra("outputY", TRIMMING_Y);
				intent.putExtra("aspectX", ASPECT_X);
				intent.putExtra("aspectY", ASPECT_Y);
				intent.putExtra("return-data", true);
				Log.d("crop", "uri " + uri);
				activity.startActivityForResult(intent, REQUEST_CROP);
			} catch (Exception e) {
			}
		} catch (ActivityNotFoundException anfe) {
			String errorMessage = "Whoops - your device doesn't support the crop action!";
			Toast toast = Toast.makeText(activity.getApplicationContext(),
					errorMessage, Toast.LENGTH_SHORT);
			toast.show();
		}
	}

	public static void openCrop(Activity activity, Uri uri) {
		Log.d("crop", "uri " + uri);
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setData(uri);
		intent.putExtra("outputX", TRIMMING_X);
		intent.putExtra("outputY", TRIMMING_Y);
		intent.putExtra("aspectX", ASPECT_X);
		intent.putExtra("aspectY", ASPECT_Y);
		intent.putExtra("scale", true);
		intent.putExtra("setWallpaper", false);
		intent.putExtra("noFaceDetection", false);
		intent.putExtra("return-data", true);
		activity.startActivityForResult(intent, REQUEST_CROP);
	}
	// }
}
