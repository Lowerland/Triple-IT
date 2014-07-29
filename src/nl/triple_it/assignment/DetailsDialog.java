package nl.triple_it.assignment;

import nl.triple_it.assignment.imageUtils.ImageLoader;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsDialog extends Activity {

	private ImageLoader imgLoader;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_dialog);

		imgLoader = new ImageLoader(this);

		Intent intent = getIntent();
		
		String emailaddress = intent.getStringExtra("key_emailaddress");
		String platform = getString(R.string.platform) + " " + intent.getStringExtra("key_platform");
		String name = intent.getStringExtra("key_firstname") + " " + intent.getStringExtra("key_lastname");
		String photourl = intent.getStringExtra("key_photourl");

		ImageView Vphoto = (ImageView) findViewById(R.id.photo);
		
		TextView Vname = (TextView) findViewById(R.id.name);
		TextView Vplatform = (TextView) findViewById(R.id.platform);
		TextView Vmailaddress = (TextView) findViewById(R.id.emailaddress);
		
		Button button_ok = (Button) findViewById(R.id.btn_dialog_ok);

		if (photourl.equals("null")) {
			Vphoto.setImageResource(R.drawable.no_photo);
		} else {
			String photoURL = "http://nmouthaan.triple-it.nl/assignment/photos/" + photourl;
			imgLoader.DisplayImage(photoURL, Vphoto);
		}

		Vname.setText(name);
		Vplatform.setText(platform);
		Vmailaddress.setText(emailaddress);
		
		button_ok.setOnClickListener(btnDialogListener);

	}

	private OnClickListener btnDialogListener = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};
}
