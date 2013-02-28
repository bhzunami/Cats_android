package ch.swisscom.cats_android;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class NewEntryActivity extends FragmentActivity {

	final Calendar c = Calendar.getInstance();
	int year = c.get(Calendar.YEAR);
	int month = c.get(Calendar.MONTH);
	int day = c.get(Calendar.DAY_OF_MONTH);
	
	private Button dateButton;
	private Button timeFromButton;
	private Button timeToButton;
	
	private String timeRightFrom;
	private String timeRightTo;
	
	private Date date1;
	private Date date2;
	
	private String worktime;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		   
		super.onCreate(savedInstanceState);
			
		setContentView(R.layout.activity_new_entry);
		
		Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.planets_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		

		dateButton = (Button) findViewById(R.id.date_button);
		timeFromButton = (Button) findViewById(R.id.time_from_button);
		timeToButton = (Button) findViewById(R.id.time_to_button);


		dateButton.append(new StringBuilder().append(day).append(".")
				.append(month + 1).append(".").append(year));
		
		timeRightFrom = "8:00";
		timeRightTo = "17:00";
		
		timeFromButton.append(new StringBuilder().append(timeRightFrom));
		timeToButton.append(new StringBuilder().append(timeRightTo));
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_new_entry, menu);
		return true;
	}

	public void showDatePickerDialog(View v) {
		DialogFragment newFragment = new DatePickerFragment("NEWENTRY");
		newFragment.show(getSupportFragmentManager(), "datePicker");
	}
	
	public void showTimePickerDialogFrom(View v){
		DialogFragment newFragment = new TimePickerFragment("FROM");
		newFragment.show(getSupportFragmentManager(), "timePicker");
	}
	
	public void showTimePickerDialogTo(View v){
		DialogFragment newFragment = new TimePickerFragment("TO");
		newFragment.show(getSupportFragmentManager(), "timePicker");
	}

	public void setDate(int day, int month, int year) {
		
		dateButton.setText(new StringBuilder().append(day).append(".")
				.append(month).append(".").append(year));
		
	}

	public void setTimeFrom(int hourOfDay, int minute) {
		// TODO Auto-generated method stub
		
		
		if(minute < 10){
			timeRightFrom = timeRightFrom.valueOf(hourOfDay) + ":" +  "0" + timeRightFrom.valueOf(minute);
		}else{
			timeRightFrom = timeRightFrom.valueOf(hourOfDay) + ":" + timeRightFrom.valueOf(minute);
		}
		
		timeFromButton.setText(new StringBuilder().append(timeRightFrom));
		
		
	}

	public void setTimeTo(int hourOfDay, int minute) {
		// TODO Auto-generated method stub
			
		if(minute < 10){
			timeRightTo = hourOfDay + ":" +  "0" + minute;
		}else{
			timeRightTo = hourOfDay + ":" + minute;
		}
		
		
		timeToButton.setText(new StringBuilder().append(timeRightFrom));
	}

	@SuppressLint("SimpleDateFormat")
	public void saveEntry(View v){
		SimpleDateFormat formatter = new SimpleDateFormat("hh:mm");
		try {
			date1 = formatter.parse(timeRightFrom);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try {
			date2 = formatter.parse(timeRightTo);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		long diff = date2.getTime() - date1.getTime();
		
		diff = (diff / (1000 * 60 * 60));
		worktime = "" + diff;

	}


	
	
}

