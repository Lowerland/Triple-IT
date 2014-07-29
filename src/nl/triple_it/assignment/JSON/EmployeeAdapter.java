package nl.triple_it.assignment.JSON;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import nl.triple_it.assignment.R;
import nl.triple_it.assignment.imageUtils.ImageLoader;

public class EmployeeAdapter extends ArrayAdapter<Employees> {

	ArrayList<Employees> ArrayListEmployees;
	int Resource;
	LayoutInflater vi;
	private ImageLoader imgLoader;

	public EmployeeAdapter(Context context, int resource, ArrayList<Employees> objects) {
		super(context, resource, objects);
		imgLoader = new ImageLoader(context);
		ArrayListEmployees = objects;
		Resource = resource;
		vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

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
			String photoURL = "http://nmouthaan.triple-it.nl/assignment/photos/" + ArrayListEmployees.get(position).getPhotourl();
			imgLoader.DisplayImage(photoURL, holder.imageview);
		}
		holder.name.setText(ArrayListEmployees.get(position).getFirstname()	+ " " + ArrayListEmployees.get(position).getLastname());

		if (holder.emailaddress != null) {
			holder.emailaddress.setText(ArrayListEmployees.get(position).getEmailaddress());
		}
		return convertView;
	}

	public class ViewHolder {
		public ImageView imageview;
		public TextView name;
		public TextView emailaddress;
	}
}
