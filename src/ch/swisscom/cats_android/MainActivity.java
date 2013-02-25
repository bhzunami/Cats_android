package ch.swisscom.cats_android;

import org.json.JSONException;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

	public static final String PREFS_NAME = "UserAccount";
	
	private String user = null;
	private String password = null;
	// InputsFields
	EditText userName;
	EditText userPassword;
	
	SharedPreferences settings = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get the inputFileds and SharedPreferences
		this.initalize();

		// get the shared preference
		this.user = settings.getString("user", "");
		
		if (this.userName != null)
			this.userName.setText(this.user);
		
		this.password = settings.getString("password", "");
		
		if(this.userPassword != null)
			this.userPassword.setText(this.password);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void getJson(View view) throws JSONException {

		// Set the input
		SharedPreferences.Editor editor = this.settings.edit();
		editor.putString("user", this.userName.getText().toString());
		editor.putString("password", this.userPassword.getText().toString());

		// commit the shit
		editor.commit();
		
		// Start the new acitivity
		Intent intent = new Intent(this, GetJsonActivity.class);
		startActivity(intent);
	}
	
	private void initalize() {
		// Get the shared preferenes
		this.settings = getSharedPreferences(PREFS_NAME, 0);
		// get the editTexts
		this.userName = (EditText) findViewById(R.id.etxt_user_name);
		this.userPassword = (EditText) findViewById(R.id.etxt_password);
	}
}
