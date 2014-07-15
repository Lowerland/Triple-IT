package nl.triple_it.assignment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

public class DetailActivity extends Activity {
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detailed_view);
		
	showdetails();

	}
	
	private void showdetails() {

		Bundle bundle = getIntent().getExtras();
		int message = bundle.getInt("send_data");
		Log.d("MESSAGE"," our msg: "+message);

	}

}
