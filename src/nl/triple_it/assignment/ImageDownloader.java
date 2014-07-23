package nl.triple_it.assignment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
	ImageView fetchImage;

	public ImageDownloader(ImageView fetchImage) {
		this.fetchImage = fetchImage;
	}

	protected Bitmap doInBackground(String... urls) {
		String urldisplay = urls[0];
		Bitmap fetchobject = null;
		try {
			InputStream in = new java.net.URL(urldisplay).openStream();
			fetchobject = BitmapFactory.decodeStream(in);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		return fetchobject;
	}

	protected void onPostExecute(Bitmap result) {
		fetchImage.setImageBitmap(result);
	}

}
