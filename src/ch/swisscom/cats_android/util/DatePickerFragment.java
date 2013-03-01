package ch.swisscom.cats_android.util;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import ch.swisscom.cats_android.MainActivity;
import ch.swisscom.cats_android.NewEntryActivity;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
@SuppressLint({ "NewApi", "ValidFragment" })
public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	
	private String view;
	private Integer date[];
	
	public DatePickerFragment(String view, Integer[] date) {
		this.view = view;
		this.date = date;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {		
		// Create a new instance of DatePickerDialog and return it
		//                                               year  month  day
		return new DatePickerDialog(getActivity(), this, date[2], date[1]-1, date[0]);
	}

	public void onDateSet(DatePicker view, int year, int month, int day) {
//		Button btn_date = (Button) findViewById(R.id.btn_datePicker);
		// Do something with the date chosen by the use
		month = month + 1;
		if(this.view == "MAIN") {
			((MainActivity)getActivity()).setDate(day, month, year);
		} else if (this.view == "NEWENTRY") {
			((NewEntryActivity)getActivity()).setDate(day, month, year);
		}
	}

}
