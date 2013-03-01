package ch.swisscom.cats_android.util;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import ch.swisscom.cats_android.R;
import ch.swisscom.cats_android.model.Event;

public class EventAdapter extends ArrayAdapter<Event>{
	
	private int resource;

	public EventAdapter(Context context, int resource, List<Event> events) {
		super(context, resource, events);
		this.resource = resource;
	}
	
	 @Override
	    public View getView(int position, View convertView, ViewGroup parent)
	    {
	        LinearLayout eventView;
	        //Get the current event object
	        Event event = getItem(position);
	         
	        //Inflate the view
	        if(convertView==null) {
	            eventView = new LinearLayout( getContext() );
	            String inflater = Context.LAYOUT_INFLATER_SERVICE;
	            LayoutInflater layoutInflater;
	            layoutInflater = (LayoutInflater)getContext().getSystemService(inflater);
	            layoutInflater.inflate(resource, eventView, true);
	            
	        } else {
	            eventView = (LinearLayout) convertView;
	        }
	        //Get the text boxes from the list_item_layout.xml file
	        TextView titleText =(TextView)eventView.findViewById(R.id.txtTitle);
	        TextView subTitleText =(TextView)eventView.findViewById(R.id.txtSubTitle);
	         
	        //Assign the appropriate data from our event object above
	        titleText.setText("Projekt: " +event.getPspnr());

	        subTitleText.setText("Zeit: " +event.getTimeFrom() +" - " +event.getTimeTo());
	         
	        return eventView;
	    }
	     
	     

}
