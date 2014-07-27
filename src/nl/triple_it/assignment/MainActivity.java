package nl.triple_it.assignment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends Activity {

	EmployeeAdapter adapter;
	EmployeeAdapter adapter2;
	EmployeeAdapter adapter3;

	private ArrayList<Employees> androidList = new ArrayList<Employees>();
	private ArrayList<Employees> iosList = new ArrayList<Employees>();
	private ArrayList<Employees> windowsList = new ArrayList<Employees>();

	ExpandableHeightGridView gridView;
	ExpandableHeightGridView gridView2;
	ExpandableHeightGridView gridView3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// start tracing to "/sdcard/calc.trace"
		// Debug.startMethodTracing("onCreate2");

		setContentView(R.layout.main);
		gridView = (ExpandableHeightGridView) findViewById(R.id.ANDROIDLIST);

		gridView2 = (ExpandableHeightGridView) findViewById(R.id.IOSLIST);
		gridView3 = (ExpandableHeightGridView) findViewById(R.id.WINLIST);

		// new EmployeeAsyncTask().execute("http://android.json.test/");
		new EmployeeAsyncTask().execute("http://westfrieslandwifi.nl/tripletest/");

		Inflatorrrr();
		// new
		// EmployeeAsyncTask().execute("http://nmouthaan.triple-it.nl/assignment/api.php");

	}

	public class EmployeeAsyncTask extends AsyncTask<String, Void, Boolean> {

		ProgressDialog dialog;

		// Run a background thread
		@Override
		protected Boolean doInBackground(String... urls) {

			// ConnectivityManager cm = (ConnectivityManager)
			// getSystemService(Context.CONNECTIVITY_SERVICE);
			// NetworkInfo netInfo = cm.getActiveNetworkInfo();
			// Log.d(TAG, "cm.getActiveNetworkInfo() " +
			// cm.getActiveNetworkInfo());
			// if (netInfo != null && netInfo.isConnected()) {
			// Log.d(TAG, "netInfo.isConnected() " + netInfo.isConnected());
			try {
				// JSON Get data
				// DefaultHttpClient httpclient = new DefaultHttpClient();
				// httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
				// System.getProperty("http.agent"));
				// HttpGet httpget = new HttpGet(urls[0]);
				// HttpResponse response = httpclient.execute(httpget);

				HttpClient client = new DefaultHttpClient();
				// httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT,
				// System.getProperty("http.agent"));
				HttpGet get = new HttpGet(urls[0]);
				// HttpResponse response = httpclient.execute(httpget);
				String content = client.execute(get, new BasicResponseHandler());

				if (client != null) {
					// HttpEntity entity = response.getEntity();
					// String data = EntityUtils.toString(entity);

					// JSONObject jObj = new JSONObject(data);
					JSONObject jObj = new JSONObject(content);
					// Get our Android Array
					JSONArray jArray = jObj.getJSONArray("Android");
					// Get our iOS Array
					JSONArray jArray2 = jObj.getJSONArray("iOS");
					// Get our Windows Array
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

					return true;
				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			// }
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

			if (!result) {
				Toast.makeText(getApplicationContext(), getString(R.string.connection_error_msg), Toast.LENGTH_LONG).show();
			}
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// Inflatorrrr();
	}

	public void Inflatorrrr() {
		// android

		// gridView.setAdapter(adapter);
		// gridView.setExpanded(true);
		adapter = new EmployeeAdapter(this, R.layout.row, androidList);
		gridView.setAdapter(adapter);
		// gridView.setExpanded(true);
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

		/*
		 * gridView2.setAdapter(adapter2); gridView2.setExpanded(true);
		 */
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

		// gridView3.setAdapter(adapter3);
		// gridView3.setExpanded(true);
		//
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// stop tracing
		// Debug.stopMethodTracing();
	}

}
