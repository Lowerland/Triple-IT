package nl.triple_it.assignment;

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

	private Button button_ok;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.detail_dialog);

		Intent intent = getIntent();
		String emailaddress = intent.getStringExtra("key_emailaddress");
		String platform = getString(R.string.platform) + " " + intent.getStringExtra("key_platform");

		String name = intent.getStringExtra("key_firstname") + " " + intent.getStringExtra("key_lastname");

		ImageView Vphoto = (ImageView) findViewById(R.id.photo);
		Vphoto.setImageResource(R.drawable.no_photo);

		TextView Vname = (TextView) findViewById(R.id.name);
		Vname.setText(name);

		TextView Vplatform = (TextView) findViewById(R.id.platform);
		Vplatform.setText(platform);

		TextView Vmailaddress = (TextView) findViewById(R.id.emailaddress);
		Vmailaddress.setText(emailaddress);

		button_ok = (Button) findViewById(R.id.btn_dialog_ok);
		button_ok.setOnClickListener(btnDialogListener);
	}

	private OnClickListener btnDialogListener = new OnClickListener() {
		public void onClick(View v) {
			finish();
		}
	};

}
