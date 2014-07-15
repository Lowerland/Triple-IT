package nl.triple_it.assignment;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

//@SuppressLint("CutPasteId")
public class MainActivity extends Activity {

	ExpandableHeightGridView androidlist;
	ExpandableHeightGridView ioslist;
	ExpandableHeightGridView winlist;

	EmployeeAdapter adapter;
	ArrayList<Employees> employeeList; // android
	ArrayList<Employees> employeeList2; // ios
	ArrayList<Employees> employeeList3; // windows

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		employeeList = new ArrayList<Employees>(); // android
		employeeList2 = new ArrayList<Employees>(); // ios
		employeeList3 = new ArrayList<Employees>(); // windows

		// TODO
		// new EmployeeAsyncTask().execute("http://nmouthaan.triple-it.nl/assignment/api.php");
		new EmployeeAsyncTask().execute("http://www.westfrieslandwifi.nl/tripletest/index.html");

		// android
		ExpandableHeightGridView gridView = (ExpandableHeightGridView) findViewById(R.id.ANDROIDLIST);
		gridView.setAdapter(adapter);
		gridView.setExpanded(true);

		adapter = new EmployeeAdapter(getApplicationContext(), R.layout.row, employeeList);

		gridView.setAdapter(adapter);
		gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

			}
		});

		// ios
		ExpandableHeightGridView gridView2 = (ExpandableHeightGridView) findViewById(R.id.IOSLIST);
		gridView2.setAdapter(adapter);
		gridView2.setExpanded(true);

		adapter = new EmployeeAdapter(getApplicationContext(), R.layout.row, employeeList2);

		gridView2.setAdapter(adapter);

		gridView2.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

			}
		});

		// windows
		ExpandableHeightGridView gridView3 = (ExpandableHeightGridView) findViewById(R.id.WINLIST);
		gridView3.setAdapter(adapter);
		gridView3.setExpanded(true);

		adapter = new EmployeeAdapter(getApplicationContext(), R.layout.row, employeeList3);

		gridView3.setAdapter(adapter);

		gridView3.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {

			}
		});

	}

	public class EmployeeAsyncTask extends AsyncTask<String, Void, Boolean> {

		ProgressDialog dialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			dialog = new ProgressDialog(MainActivity.this);
			dialog.setMessage("Loading, please wait");
			dialog.setTitle("Connecting server");
			dialog.show();
			dialog.setCancelable(false);
		}

		// Run a background thread
		@Override
		protected Boolean doInBackground(String... urls) {
			try {
				// JSON Get data
				DefaultHttpClient httpclient = new DefaultHttpClient();
				httpclient.getParams().setParameter(CoreProtocolPNames.USER_AGENT, System.getProperty("http.agent"));
				HttpGet httpget = new HttpGet(urls[0]);
				HttpResponse response = httpclient.execute(httpget);

				//int status = response.getStatusLine().getStatusCode();
				if (response.getStatusLine().getStatusCode() == 200) {
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);

					JSONObject jObj = new JSONObject(data);

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
						employeeList.add(employee);
					}
					// iOS objects
					for (int i = 0; i < jArray2.length(); i++) {
						JSONObject jRealObject = jArray2.getJSONObject(i);
						Employees employee = new Employees();
						employee.setFirstname(jRealObject.getString("firstName"));
						employee.setLastname(jRealObject.getString("lastName"));
						employee.setEmailaddress(jRealObject.getString("emailAddress"));
						employee.setPhotourl(jRealObject.getString("photoUrl"));
						employeeList2.add(employee);
					}
					// Windows objects
					for (int i = 0; i < jArray3.length(); i++) {
						JSONObject jRealObject = jArray3.getJSONObject(i);
						Employees employee = new Employees();
						employee.setFirstname(jRealObject.getString("firstName"));
						employee.setLastname(jRealObject.getString("lastName"));
						employee.setEmailaddress(jRealObject.getString("emailAddress"));
						employee.setPhotourl(jRealObject.getString("photoUrl"));
						employeeList3.add(employee);
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
			return false;
		}

		@Override
		protected void onPostExecute(Boolean result) {
			super.onPostExecute(result);

			adapter.notifyDataSetChanged();
			dialog.cancel();

			if (result == false) {
				Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();

			}
		}
	}

}
