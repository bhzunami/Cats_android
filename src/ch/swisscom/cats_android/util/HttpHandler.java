package ch.swisscom.cats_android.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;


import android.os.AsyncTask;
import android.util.Log;

/*
 * AsyncTask class to fetch tweets in background thread - receives URL
 * search string and updates UI on calling execute - specify parameter and
 * return types as strings - parameter is twitter URL search string - result
 * is retrieved tweets
 */
public class HttpHandler extends AsyncTask<String, Void, String> {
	
	private static final String BASE_URL = "http://riagsrv10.riagdom2.resource.ch:8010/zspa_mobile/";
	
	private HttpHandlerDelegate delegate;
	
	private String userName;
	private String userPassword;
	
	protected String doInBackground(String... url) {

		// start building result which will be json string
		StringBuilder sapFeedBuilder = new StringBuilder();
		// should only be one URL, receives array
		for (String searchURL : url) {
			HttpClient sapClient = new DefaultHttpClient();

			try {

				HttpGet httpGet = new HttpGet(BASE_URL +searchURL);
				httpGet.setHeader("X-Sapu", this.userName);
				httpGet.setHeader("X-Sapp", this.userPassword);
				
				httpGet.wait(10000);

				HttpResponse sapResponse = sapClient.execute(httpGet);

				StatusLine searchStatus = sapResponse.getStatusLine();
				// Check if we get 200
				if (searchStatus.getStatusCode() == 200) {
					// get the response
					HttpEntity sapEntity = sapResponse.getEntity();
					InputStream sapContent = sapEntity.getContent();

					// process the results
					InputStreamReader sapInput = new InputStreamReader(
							sapContent);

					BufferedReader tweetReader = new BufferedReader(sapInput);

					String lineIn;
					while ((lineIn = tweetReader.readLine()) != null) {
						sapFeedBuilder.append(lineIn);
					}
				} 

			} catch (Exception e) {
				
				if( this.isCancelled() ) {
					Log.e("Cats", "Fehler beim Daten holen");
				}
				
			}

		}
		return sapFeedBuilder.toString();
	}

	/*
	 * Process result of search query - this receives JSON string representing
	 * tweets with search term included
	 */
	protected void onPostExecute(String result) {
		try {

			// get JSONArray contained within the JSONObject retrieved -
			// "results"
			JSONArray jsonArray = new JSONArray(result);
			if( this.delegate == null)
				return;
			this.delegate.jsonHandlerFinishLoading(jsonArray);

		} catch (Exception e) {
			Log.e("CATS_ANDROID", "Json Failure");
			e.printStackTrace();
		}
	}
	

	protected void onCancelled() {
		Log.i("CATS_ANDROID", "onCancel");
		this.delegate.setToast("Fehler");
		this.delegate.showProgress(false);
	}
	
	public void setDelegate(HttpHandlerDelegate delegate) {
		this.delegate = delegate;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

}
