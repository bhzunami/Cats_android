package ch.swisscom.cats_android.util;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import ch.swisscom.cats_android.MainActivity;
import ch.swisscom.cats_android.model.Event;

public class JsonParser {
	
	ArrayList<Event> events = new ArrayList<Event>();
	JSONArray jArray;
	
	public JsonParser(JSONArray jArray) {
		this.jArray = jArray;
	}
	
	
	public ArrayList<Event> getEventsFromJson() {
		Log.i(MainActivity.TAG, "Get Events from Json");
		for(int i = 0; i < this.jArray.length(); i++ ) {
			Event event;
			try {
				event = this.createEvent( (JSONObject) this.jArray.get(i) );
				this.events.add(event);
				
			} catch(JSONException ex) {
				Log.e(MainActivity.TAG, "Could not create JsonObject from Array");
			}
		}
		return events;
	}
	
	private Event createEvent(JSONObject object ) {
		Event event = new Event();
		
		try {
			event.setId( object.getInt("ID") );
			event.setPspnr( object.getString("PSPNR") );
			event.setbName( object.getString("BNAME") );
			event.setAtDate( object.getString("ATDATE") );
			event.setTimeFrom( object.getString("TIMEFROM") );
			event.setTimeTo( object.getString("TIMETO") );
			event.setMemo( object.getString("MEMO") );
			
		} catch(JSONException ex) {
			Log.e(MainActivity.TAG, "Could not create Event from JsonObject");
		}
		return event;
	}

}
