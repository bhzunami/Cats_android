package ch.swisscom.cats_android;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;

public class MainActivity extends FragmentActivity {

	public static final String PREFS_NAME = "UserAccount";
	private static final String TAG = "CATS_ANDROID";

	private String user = null;

	SharedPreferences settings = null;
	
	final Calendar c = Calendar.getInstance();
	int year;
	int month;
	int day;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "Application started.");

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get SharedPreferences
		this.initalize();
	}

	public void onStart() {
		super.onStart();
		Log.i(TAG, "Start Appi");
		// get the userName to check if user is logged in
		this.user = settings.getString("user", "");
		if (this.user == "") {
			this.goToLoginView();
			return;
		}

		this.executeHttpRequest();
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

		
		String date = null;
		this.year = c.get(Calendar.YEAR);
		this.month = c.get(Calendar.MONTH);
		this.day = c.get(Calendar.DAY_OF_MONTH);
		
		date = day + "." + month + "." + year;
		this.updateDateButton(date);
		
	}
	
	private void updateDateButton(String date) {
		Button btn_date;
		btn_date = (Button) findViewById(R.id.btn_datePicker);
		btn_date.setText(date);
	}

	public void executeHttpRequest() {
		Log.i(TAG, "Get request.");
		// perform request to server
		HttpHandler.get("time", null, new JsonHttpResponseHandler() {

			public void onSuccess(JSONArray array) {
				// Pull out the first event on the public timeline
				Log.i(TAG, "Request was successful.");

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
				Log.i(TAG, text);

				ajaxHandlerFinishLoading(array);

			}

			@Override
			public void onFailure(Throwable e, String response) {
				// Response failed :(
				Log.e(TAG, "Could not get request!");
				setToast("There was an error by get the request");
			}
			// Finish definition Class
		}

		);
	}

	public void setToast(String message) {
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

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

	public void ajaxHandlerFinishLoading(JSONArray array) {
		// TODO: Handle json array
		this.createListView(this.getDataFromJson());
	}

	private void createListView(ArrayList<String> list) {
		ArrayAdapter<String> adapter;

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);

		ListView listView = (ListView) findViewById(R.id.list_jsonData);

		listView.setAdapter(adapter);
	}

	public void logoutUser() {
		if (settings == null)
			return;

		SharedPreferences.Editor editor = this.settings.edit();
		editor.putString("user", null);
		editor.putString("password", null);

		editor.commit();

		this.goToLoginView();

	}

	private void goToLoginView() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}
	
	private void goToNewEntryView() {
		Intent intent = new Intent(this, NewEntryActivity.class);
		startActivity(intent);
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_logout:
			this.logoutUser();
			return true;
		case R.id.menu_refresh:
			this.executeHttpRequest();
			return true;
		case R.id.menu_newEntry:
			this.goToNewEntryView();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public void setDate(int day, int month, int year) {
		this.updateDateButton(day +"." +month +"." +year);
	}

	@SuppressLint("NewApi")
	public void showDatePickerDialog(View v) {
	    DialogFragment newFragment = new DatePickerFragment("MAIN");
	    newFragment.show(getSupportFragmentManager(), "datePicker");	    
	}

}
