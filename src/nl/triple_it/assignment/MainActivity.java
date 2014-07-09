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

import java.io.IOException;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
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
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MainActivity extends Activity {

	ListView androidlist;
	EmployeeAdapter adapter;
	ArrayList<Employees> employeeList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		androidlist = (ListView) findViewById(R.id.androidlist);
		employeeList = new ArrayList<Employees>();

		// new EmployeeAsyncTask().execute("http://android.json.test/");
		new EmployeeAsyncTask()
				.execute("http://www.westfrieslandwifi.nl/tripletest/");
		ListView listview = (ListView) findViewById(R.id.androidlist);
		adapter = new EmployeeAdapter(getApplicationContext(), R.layout.row,employeeList);

		listview.setAdapter(adapter);

		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "blaa",
						Toast.LENGTH_LONG).show();
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

		@Override
		protected Boolean doInBackground(String... urls) {
			try {
				
				//------------------>>
				HttpGet httppost = new HttpGet(urls[0]);
				HttpClient httpclient = new DefaultHttpClient();
				HttpResponse response = httpclient.execute(httppost);
				
//				HttpClient client = new DefaultHttpClient();
//				HttpPost post = new HttpPost(params[0]);
//				HttpResponse response = client.execute(post);

				int status = response.getStatusLine().getStatusCode();
				
				if (status == 200) {

					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);

					JSONObject jObj = new JSONObject(data);
					JSONArray jArray = jObj.getJSONArray("Android");
					JSONArray jArray2 = jObj.getJSONArray("iOS");
					JSONArray jArray3 = jObj.getJSONArray("Windows");

					for (int i = 0; i < jArray.length(); i++) {						
						JSONObject jRealObject = jArray.getJSONObject(i);
						
						Employees employee = new Employees();


						employee.setFirstname(jRealObject.getString("firstName"));
						employee.setLastname(jRealObject.getString("lastName"));
						employee.setEmailaddress(jRealObject.getString("emailAddress"));
						employee.setPhotourl(jRealObject.getString("photoUrl"));

						employeeList.add(employee);

					}
					for (int i = 0; i < jArray2.length(); i++) {						
						JSONObject jRealObject = jArray2.getJSONObject(i);
						
						Employees employee = new Employees();


						employee.setFirstname(jRealObject.getString("firstName"));
						employee.setLastname(jRealObject.getString("lastName"));
						employee.setEmailaddress(jRealObject.getString("emailAddress"));
						employee.setPhotourl(jRealObject.getString("photoUrl"));

						employeeList.add(employee);

					}
					for (int i = 0; i < jArray3.length(); i++) {						
						JSONObject jRealObject = jArray3.getJSONObject(i);
						
						Employees employee = new Employees();


						employee.setFirstname(jRealObject.getString("firstName"));
						employee.setLastname(jRealObject.getString("lastName"));
						employee.setEmailaddress(jRealObject.getString("emailAddress"));
						employee.setPhotourl(jRealObject.getString("photoUrl"));

						employeeList.add(employee);

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
			if(result == false){
				Toast.makeText(getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
				
			}

//			if (result == false) {
//				// show a msg that data was not parsed
//			} else {
//
//				EmployeeAdapter adapter = new EmployeeAdapter(
//						getApplicationContext(), R.layout.row, employeeList);
//				adapter.notifyDataSetChanged();
//				androidlist.setAdapter(adapter);
//
//			}

		}
	}

}
