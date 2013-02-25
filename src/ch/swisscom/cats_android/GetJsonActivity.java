package ch.swisscom.cats_android;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GetJsonActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_get_json);
		this.getHttpRequest();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_get_json, menu);
		return true;
	}

	public void getHttpRequest() {
		HttpHandler.get("time", null, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray array) {
				// Pull out the first event on the public timeline
				System.out.println("Success" +array);
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
				System.out.println(text);
			}

			@Override
			public void onFailure(Throwable e, String response) {
				// Response failed :(
				System.out.println("Sebi ist ein ARSCHLOCH");
			}

			@Override
			public void onFinish() {
				// Response failed :(
				System.out.println("Fertig");
			}
		}

		);

	}
}
