package ch.swisscom.cats_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;

public class GetJsonActivity extends Activity {
	
	SharedPreferences settings;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_json);
		// Some debug
		settings = getSharedPreferences(MainActivity.PREFS_NAME, 0);
		
		this.getHttpRequest();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_get_json, menu);
		return true;
	}

	public void getHttpRequest() {
		HttpHandler.get("time", null, new JsonHttpResponseHandler() {
			@Override
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
				System.out.println("Sebi ist ein ARSCHLOCH");
			}

			@Override
			public void onFinish() {
				// Response failed :(
				System.out.println("Fertig");
			}
		}

		);

	}
	
	public void logoutUser(View view) {
		// clear the data
		if(settings == null)
			return;
		
		SharedPreferences.Editor editor = this.settings.edit();
		editor.putString("user", null);
		editor.putString("password", null);
		
		finish();
		System.exit(0);
		
		// Clear input
//		this.userName.setText(null);
//		this.userPassword.setText(null);
//		
//		// and set the correct focus
//		this.userName.setFocusableInTouchMode(true);
//		this.userName.requestFocus();

		// commit the shit
		editor.commit();
	}
	
}
