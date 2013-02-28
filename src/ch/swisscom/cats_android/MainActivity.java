package ch.swisscom.cats_android;

import java.util.ArrayList;
import java.util.Calendar;

import org.json.JSONArray;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import ch.swisscom.cats_android.util.DatePickerFragment;
import ch.swisscom.cats_android.util.HttpHandler;
import ch.swisscom.cats_android.util.HttpHandlerDelegate;

public class MainActivity extends FragmentActivity implements HttpHandlerDelegate {

	public static final String PREFS_NAME = "UserAccount";
	private static final String TAG = "CATS_ANDROID";

	private String userName = null;
	private String userPassword = null;

	private SharedPreferences settings = null;

	private Calendar calendar;
	private int year;
	private int month;
	private int day;

	private View loginStatusView;
	private TextView loginStatusMessageView;
	private ListView listView;
	private Button btn_date;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.i(TAG, "Application started.");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get all variables and settings
		this.initalize();
	}

	protected void onStart() {
		super.onStart();
		Log.i(TAG, "Start Appi");
		// get the userName to check if user is logged in
		if (this.userName == "" || this.userName == null) {
			this.goToLoginView();
			return;
		}
		
		// Show progress status
		loginStatusMessageView.setText(R.string.login_progress_signing_in);
		showProgress(true);
		
		this.getLoginData();
		this.getHttpRequest();
	}

	private void initalize() {
		// Get the sharedPreferenes
		this.settings = getSharedPreferences(PREFS_NAME, 0);

		// Get the userName
		this.getLoginData();
		
		// get the view components on the view
		this.loginStatusView = findViewById(R.id.ll_status);
		this.loginStatusMessageView = (TextView) findViewById(R.id.login_status_message);
		this.listView = (ListView) findViewById(R.id.list_jsonData);
		this.btn_date = (Button) findViewById(R.id.btn_datePicker);

		// set the date for today on the date button
		this.calendar = Calendar.getInstance();
		String date = null;
		this.year = calendar.get(Calendar.YEAR);
		this.month = 1 + calendar.get(Calendar.MONTH);
		this.day = calendar.get(Calendar.DAY_OF_MONTH);
		date = day + "." + month + "." + year;
		this.updateDateButton(date);
	}

	private void updateDateButton(String date) {
		if (btn_date == null)
			return;
		btn_date.setText(date);
	}

	private String getDateFromButton() {
		String date[] = null;
		if (btn_date == null)
			return null;

		date = btn_date.getText().toString().split("[\\s\\.]");
		return date[2] +"-" + date[1] +"-" + date[0];
	}

	private void createListView(ArrayList<String> list) {
		ArrayAdapter<String> adapter;

		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, list);

		listView.setAdapter(adapter);
	}

	private void goToLoginView() {
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	private void goToNewEntryView() {
		Intent intent = new Intent(this, NewEntryActivity.class);
		startActivity(intent);
	}
	
	private void getLoginData() {
		if (settings == null)
			return;
		this.userName = settings.getString("user", "");
		this.userPassword = settings.getString("password", "");

	}

	private void getHttpRequest() {
		// get the data from server
		Log.i(TAG, "Get JsonData");
		HttpHandler jsonHandler = new HttpHandler();
		jsonHandler.setDelegate(this);

		Log.i(TAG, "user: " + this.userName + " Password: " + this.userPassword);
		jsonHandler.setUserName(this.userName);
		jsonHandler.setUserPassword(this.userPassword);
		jsonHandler.execute("time?date=" + getDateFromButton());

	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}
	
	// If there was a error by getting the request
	// let the user know
	public void setToast(String message) {
		Context context = getApplicationContext();
		CharSequence text = message;
		int duration = Toast.LENGTH_SHORT;

		Toast toast = Toast.makeText(context, text, duration);
		toast.show();
	}

	public void jsonHandlerFinishLoading(JSONArray array) {
		Log.i(TAG, array.toString());
		this.showProgress(false);
		// TODO: Handle json array
		this.createListView(new ArrayList<String>());
	}
	
	public void logoutUser() {
		if (settings == null)
			return;

		SharedPreferences.Editor editor = this.settings.edit();
		editor.putString("user", null);
		editor.putString("password", null);
		editor.commit();
		// Go to the Login View
		this.goToLoginView();
	}

	// Catch the choosen menu point
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.menu_logout:
			this.logoutUser();
			return true;
		case R.id.menu_refresh:
			 this.getHttpRequest();
			return true;
		case R.id.menu_newEntry:
			this.goToNewEntryView();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	// Update button from listPicker
	public void setDate(int day, int month, int year) {
		this.updateDateButton(day + "." + month + "." + year);
	}

	@SuppressLint("NewApi")
	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment("MAIN");
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	/**
	 * Shows the progress UI and hides the login form.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
	public void showProgress(final boolean show) {
		// On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
		// for very easy animations. If available, use these APIs to fade-in
		// the progress spinner.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			int shortAnimTime = getResources().getInteger(
					android.R.integer.config_shortAnimTime);

			loginStatusView.setVisibility(View.VISIBLE);
			loginStatusView.animate().setDuration(shortAnimTime)
					.alpha(show ? 1 : 0)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							loginStatusView.setVisibility(show ? View.VISIBLE
									: View.GONE);
						}
					});

			listView.setVisibility(View.VISIBLE);
			listView.animate().setDuration(shortAnimTime).alpha(show ? 0 : 1)
					.setListener(new AnimatorListenerAdapter() {
						@Override
						public void onAnimationEnd(Animator animation) {
							listView.setVisibility(show ? View.GONE
									: View.VISIBLE);
						}
					});
		} else {
			// The ViewPropertyAnimator APIs are not available, so simply show
			// and hide the relevant UI components.
			loginStatusView.setVisibility(show ? View.VISIBLE : View.GONE);
			listView.setVisibility(show ? View.GONE : View.VISIBLE);
		}
	}
}
