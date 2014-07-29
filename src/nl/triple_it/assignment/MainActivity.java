package nl.triple_it.assignment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Toast;

import nl.triple_it.assignment.JSON.EmployeeAdapter;
import nl.triple_it.assignment.JSON.Employees;
import nl.triple_it.assignment.imageUtils.ExpandableHeightGridView;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

	EmployeeAdapter adapter;
	EmployeeAdapter adapter2;
	EmployeeAdapter adapter3;

	ExpandableHeightGridView gridView;
	ExpandableHeightGridView gridView2;
	ExpandableHeightGridView gridView3;

	private ArrayList<Employees> androidList = new ArrayList<Employees>();
	private ArrayList<Employees> iosList = new ArrayList<Employees>();
	private ArrayList<Employees> windowsList = new ArrayList<Employees>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		gridView = (ExpandableHeightGridView) findViewById(R.id.ANDROIDLIST);
		gridView2 = (ExpandableHeightGridView) findViewById(R.id.IOSLIST);
		gridView3 = (ExpandableHeightGridView) findViewById(R.id.WINLIST);

		new JSONAsyncTask().execute("http://nmouthaan.triple-it.nl/assignment/api.php");

		InflateGridViews();
	}

	/**
	 * AsyncTask JSON parsing
	 */
	public class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
		ProgressDialog dialog;

		@Override
		protected Boolean doInBackground(String... urls) {
			ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = cm.getActiveNetworkInfo();

			/**
			 * String JSON_content will hold our Online or Offline JSON data And
			 * will be used for JSON Parsing
			 */
			String JSON_content = null;

			/**
			 * Fetch JSON (Online) else from cache (Offline)
			 */
			if (netInfo != null && netInfo.isConnected()) {
				try {
					HttpClient client = new DefaultHttpClient();
					client.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));
					HttpGet httpget = new HttpGet(urls[0]);

					String string = client.execute(httpget, new BasicResponseHandler());
					if (string != null) {
						JSON_content = string;

						// Cache JSON
						FileOutputStream outputStream;
						try {
							outputStream = openFileOutput("JSON.Cache", Context.MODE_PRIVATE);
							outputStream.write(JSON_content.getBytes());
							outputStream.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				} catch (ClientProtocolException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				/**
				 * JSON from cache
				 */
				try {
					FileInputStream mInput = openFileInput("JSON.Cache");
					byte[] cached_json = new byte[mInput.available()];
					mInput.read(cached_json);
					mInput.close();
					JSON_content = new String(cached_json);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			/**
			 * JSON PARSER
			 */
			if (JSON_content != null) {
				try {
					JSONObject jObj;
					jObj = new JSONObject(JSON_content);

					// Get Android Array
					JSONArray jArray = jObj.getJSONArray("Android");

					// Get iOS Array
					JSONArray jArray2 = jObj.getJSONArray("iOS");

					// Get Windows Array
					JSONArray jArray3 = jObj.getJSONArray("Windows");

					// Android objects
					for (int i = 0; i < jArray.length(); i++) {
						JSONObject jRealObject = jArray.getJSONObject(i);
						Employees employee = new Employees();
						employee.setFirstname(jRealObject.getString("firstName"));
						employee.setLastname(jRealObject.getString("lastName"));
						employee.setEmailaddress(jRealObject.getString("emailAddress"));
						employee.setPhotourl(jRealObject.getString("photoUrl"));
						androidList.add(employee);
					}

					// iOS objects
					for (int i = 0; i < jArray2.length(); i++) {
						JSONObject jRealObject = jArray2.getJSONObject(i);
						Employees employee = new Employees();
						employee.setFirstname(jRealObject.getString("firstName"));
						employee.setLastname(jRealObject.getString("lastName"));
						employee.setEmailaddress(jRealObject.getString("emailAddress"));
						employee.setPhotourl(jRealObject.getString("photoUrl"));
						iosList.add(employee);
					}

					// Windows objects
					for (int i = 0; i < jArray3.length(); i++) {
						JSONObject jRealObject = jArray3.getJSONObject(i);
						Employees employee = new Employees();
						employee.setFirstname(jRealObject.getString("firstName"));
						employee.setLastname(jRealObject.getString("lastName"));
						employee.setEmailaddress(jRealObject.getString("emailAddress"));
						employee.setPhotourl(jRealObject.getString("photoUrl"));
						windowsList.add(employee);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				return true;
			}
			return false;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setMessage(getString(R.string.loading));
			dialog.setTitle(getString(R.string.connecting));
			dialog.show();
			dialog.setCancelable(false);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);
			adapter.notifyDataSetChanged();
			adapter2.notifyDataSetChanged();
			adapter3.notifyDataSetChanged();

			dialog.cancel();

			/**
			 * The toast should only appear if the app runs without internet and
			 * without cache
			 */
			if (!result) {
				Toast.makeText(getApplicationContext(), getString(R.string.connection_error_msg), Toast.LENGTH_LONG).show();
			}
		}

	}

	/**
	 * Inflates all our GridViews with the data from our adapters
	 */
	public void InflateGridViews() {

		// android
		adapter = new EmployeeAdapter(this, R.layout.row, androidList);
		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), DetailsDialog.class);
				intent.putExtra("key_firstname", androidList.get(position).getFirstname());
				intent.putExtra("key_lastname", androidList.get(position).getLastname());
				intent.putExtra("key_emailaddress", androidList.get(position).getEmailaddress());
				intent.putExtra("key_photourl", androidList.get(position).getPhotourl());
				intent.putExtra("key_platform", "Android");
				startActivity(intent);
			}
		});

		// ios
		adapter2 = new EmployeeAdapter(this, R.layout.row, iosList);
		gridView2.setAdapter(adapter2);
		gridView2.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), DetailsDialog.class);
				intent.putExtra("key_firstname", iosList.get(position).getFirstname());
				intent.putExtra("key_lastname", iosList.get(position).getLastname());
				intent.putExtra("key_emailaddress", iosList.get(position).getEmailaddress());
				intent.putExtra("key_photourl", iosList.get(position).getPhotourl());
				intent.putExtra("key_platform", "iOS");
				startActivity(intent);
			}
		});

		// windows
		adapter3 = new EmployeeAdapter(this, R.layout.row, windowsList);
		gridView3.setAdapter(adapter3);
		gridView3.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {
				Intent intent = new Intent(getApplicationContext(), DetailsDialog.class);
				intent.putExtra("key_firstname", windowsList.get(position).getFirstname());
				intent.putExtra("key_lastname", windowsList.get(position).getLastname());
				intent.putExtra("key_emailaddress", windowsList.get(position).getEmailaddress());
				intent.putExtra("key_photourl", windowsList.get(position).getPhotourl());
				intent.putExtra("key_platform", "Windows");
				startActivity(intent);
			}
		});
	}
}
