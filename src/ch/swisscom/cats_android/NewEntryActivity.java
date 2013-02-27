package ch.swisscom.cats_android;

import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

public class NewEntryActivity extends FragmentActivity {

	final Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH);
	int day = c.get(Calendar.DAY_OF_MONTH);
	
	private TextView tvDisplayDate;
	private TextView titleText;
	
	private Button dateButton;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_new_entry);
		


		

//		Will ich den Button verwenden um das Datum zu bestimmen?
//		Momentan nicht!
		
//		dateButton = (Button) findViewById(R.id.date_button);
//
//
//		dateButton.append(new StringBuilder().append(day).append("-")
//				.append(month + 1).append("-").append(year));
		
		
//		aufrufen des DatePickers beim Start der Activity
		showDatePickerDialog(null);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_entry, menu);
		return true;
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragmentEntry();
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}

	public void setDate(int day, int month, int year) {
		
		
		//Titel setzen 
	    setTitle(new StringBuilder().append(day).append(" - ").append(month).append(" - ").append(year));
		

	}

	// ____________________________________________________________________________________________________

}

