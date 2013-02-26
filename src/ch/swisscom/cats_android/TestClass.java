package ch.swisscom.cats_android;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TestClass extends Activity {
	
//	This Class is a test Class
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	
	public void createListView(ArrayList<String> list) {
		ArrayAdapter<String> adapter;
		
		adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list );
		
		ListView listView = (ListView) findViewById(R.id.list_jsonData);
		
		listView.setAdapter( adapter );
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

}
