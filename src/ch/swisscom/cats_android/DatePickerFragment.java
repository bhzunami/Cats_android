package ch.swisscom.cats_android;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint({ "NewApi", "ValidFragment" })
public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	
	private String view;
	
	public DatePickerFragment(String view) {
		this.view = view;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// Use the current date as the default date in the picker
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);
		
		

		Log.i("HULULUL", "Before return");
		// Create a new instance of DatePickerDialog and return it
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
//		Button btn_date = (Button) findViewById(R.id.btn_datePicker);
		// Do something with the date chosen by the use
		Log.i("ASF", "set year " +year);

		if(this.view == "MAIN") {
			month++;
			((MainActivity)getActivity()).setDate(day, month, year);
		} else if (this.view == "NEWENTRY") {
			month++;
			((NewEntryActivity)getActivity()).setDate(day, month, year);
		}
	}

}
