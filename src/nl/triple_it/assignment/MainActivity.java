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
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

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

		new EmployeeAsyncTask().execute("http://android.json.test/");

	}

	public class EmployeeAsyncTask extends AsyncTask<String, Void, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {
		try {
				HttpClient client = new DefaultHttpClient();
				HttpPost post = new HttpPost(params[0]);
				HttpResponse response = client.execute(post);

				int status = response.getStatusLine().getStatusCode();
				if (status == 200){
					
					HttpEntity entity = response.getEntity();
					String data = EntityUtils.toString(entity);
					
					
					JSONObject jObj = new JSONObject(data);
					//JSONArray jArray = jObj.getJSONArray("Android");
					JSONArray jArray = jObj.getJSONArray("iOS");
					//JSONArray jArray = jObj.getJSONArray("Windows");
					
					
					for(int i=0; i<jArray.length(); i++){
						Employees employee = new Employees();
						
						JSONObject jRealObject = jArray.getJSONObject(i);
						
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
			
			if(result == false){
				//show a msg that data was not parsed
			} else {
				EmployeeAdapter adapter = new EmployeeAdapter(getApplicationContext(), R.layout.row, employeeList);
				androidlist.setAdapter(adapter);
			}

		}
	}

}






















