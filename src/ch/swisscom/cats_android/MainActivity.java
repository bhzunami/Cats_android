package ch.swisscom.cats_android;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends Activity {

	public static final String PREFS_NAME = "UserAccount";
	
	private String user = null;
	
	SharedPreferences settings = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get SharedPreferences
		this.initalize();

		// get the userName to check if user is logged in
		this.user = settings.getString("user", "");
		if( this.user == "") {
			this.goToLoginView();
		}
		
		// Get the Json from Server
		this.executeHttpRequest();
		
		//TODO: parse Json array in a ArrayList<String>
		// initalize the list view
		this.createListView( this.getDataFromJson() );
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	
	private void initalize() {
		// Get the sharedPreferenes
		this.settings = getSharedPreferences(PREFS_NAME, 0);
	}
	
	
	public void executeHttpRequest() {
		// perform request to server
		HttpHandler.get("time", null, new JsonHttpResponseHandler() {
			
			public void onSuccess(JSONArray array) {
				// Pull out the first event on the public timeline
				System.out.println("Success" +array);
				
				JSONObject firstEvent = null;
				
				try {
					firstEvent = (JSONObject) array.get(0);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				String text = null;
				try {
					text = firstEvent.getString("res");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
//
				// Do something with the response
				System.out.println(text);
			}

			@Override
			public void onFailure(Throwable e, String response) {
				// Response failed :(
				System.out.println("Fehler");
			}

			@Override
			public void onFinish() {
				// Response failed :(
				System.out.println("Fertig");
			}
		}

		);}
	
	
	// Parse the Json into ArrayList<String>
	private ArrayList<String> getDataFromJson() {
		ArrayList<String> values = new ArrayList<String>();
		values.add("Android");
		values.add("1");
		values.add("2");
		values.add("3");
		values.add("4");
		values.add("5");
		values.add("6");
		values.add("6");
		values.add("7");
		return values;
		
	}
	
	
	private void createListView(ArrayList<String> list) {
		ArrayAdapter<String> adapter;
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list );
		
		ListView listView = (ListView) findViewById(R.id.list_jsonData);
		
		listView.setAdapter( adapter );
	}
	
	public void logoutUser(View view) {
		if(settings == null)
			return;

			SharedPreferences.Editor editor = this.settings.edit();
			editor.putString("user", null);
			editor.putString("password", null);

			editor.commit();
			
			this.goToLoginView();
			
	}
	
	private void goToLoginView() {
		Intent intent = new Intent(this,LoginActivity.class);
		startActivity(intent);
	}
	
}
