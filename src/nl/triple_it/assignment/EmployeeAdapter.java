/*
 *  Copyright 2013-2014 Jeroen Gorter <Lowerland@hotmail.com>
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

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
	Context context;
	LayoutInflater vi;

	public EmployeeAdapter(Context context, int resource,
			ArrayList<Employees> objects) {
		super(context, resource, objects);

		ArrayListEmployees = objects;
		Resource = resource;
		this.context = context;

		vi = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		InfoHolder holder;
		if (convertView == null) {
			convertView = vi.inflate(Resource, null);
			holder = new InfoHolder();

			holder.imageview = (ImageView) convertView.findViewById(R.id.photo);
			holder.firstname = (TextView) convertView.findViewById(R.id.firstname);
			holder.lastname = (TextView) convertView.findViewById(R.id.lastname);
			holder.emailaddress = (TextView) convertView.findViewById(R.id.emailaddress);

			convertView.setTag(holder);
			
		} else {
			holder = (InfoHolder) convertView.getTag();
		}
		
		//holder.imageview.setImageResource(resId);

		if (ArrayListEmployees.get(position).getPhotourl() == "null") {
			holder.imageview.setImageResource(R.drawable.no_photo);
			//new DownloadImageTask(holder.imageview).execute("http://android.json.test/photos/" + ArrayListEmployees.get(position).getPhotourl());
		} else {
			new DownloadImageTask(holder.imageview).execute("http://android.json.test/photos/" + ArrayListEmployees.get(position).getPhotourl());
		}
		

		holder.firstname.setText(ArrayListEmployees.get(position).getFirstname());
		holder.lastname.setText(ArrayListEmployees.get(position).getLastname());
		holder.emailaddress.setText(ArrayListEmployees.get(position).getEmailaddress());

		return convertView;
	}

	static class InfoHolder {
		public ImageView imageview;
		public TextView firstname;
		public TextView lastname;
		public TextView emailaddress;
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
		ImageView bmImage;

		public DownloadImageTask(ImageView bmImage) {
			this.bmImage = bmImage;
		}

		protected Bitmap doInBackground(String... urls) {
			String urldisplay = urls[0];
			Bitmap mIcon11 = null;
			try {
				InputStream in = new java.net.URL(urldisplay).openStream();
				mIcon11 = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}
			return mIcon11;
		}

		protected void onPostExecute(Bitmap result) {
			bmImage.setImageBitmap(result);
		}

	}

}
