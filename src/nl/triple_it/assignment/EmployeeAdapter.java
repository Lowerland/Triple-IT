package nl.triple_it.assignment;

import java.io.InputStream;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EmployeeAdapter extends ArrayAdapter<Employees> {

	ArrayList<Employees> ArrayListEmployees;
	int Resource;
	LayoutInflater vi;

	public EmployeeAdapter(Context context, int resource, ArrayList<Employees> objects) {
		super(context, resource, objects);

		ArrayListEmployees = objects;
		Resource = resource;
		vi = (LayoutInflater) context.
				getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// getting the first list item
		ViewHolder holder;

		if (convertView == null) {
			convertView = vi.inflate(Resource, null);

			holder = new ViewHolder();
			holder.imageview = (ImageView) convertView.findViewById(R.id.photo);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.emailaddress = (TextView) convertView.findViewById(R.id.emailaddress);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		// When no photo exist set a default one
		if (ArrayListEmployees.get(position).getPhotourl() == "null") {
			holder.imageview.setImageResource(R.drawable.no_photo);

		} else {
			// TODO
			// new
			// DownloadImageTask(holder.imageview).execute("http://nmouthaan.triple-it.nl/assignment/photos/"
			// + ArrayListEmployees.get(position).getPhotourl());
			new DownloadImageTask(holder.imageview).execute("http://www.westfrieslandwifi.nl/tripletest/photos/" + ArrayListEmployees.get(position).getPhotourl());
		}
		// Set name
		holder.name.setText(ArrayListEmployees.get(position).getFirstname()	+ " " + ArrayListEmployees.get(position).getLastname());

		// Set emailadress if we are in detail view
		if (holder.emailaddress == null) {
		} else {
			holder.emailaddress.setText(ArrayListEmployees.get(position).getEmailaddress());
		}
		return convertView;
	}

	static class ViewHolder {
		public ImageView imageview;
		public TextView name;
		public TextView emailaddress;
	}

	// Photo downloader
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView fetchImage;

		public DownloadImageTask(ImageView fetchImage) {
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

}
