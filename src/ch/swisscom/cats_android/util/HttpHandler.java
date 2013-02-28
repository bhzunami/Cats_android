package ch.swisscom.cats_android.util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;


public class HttpHandler {
	
	private static final String BASE_URL = "http://riagsrv10.riagdom2.resource.ch:8010/zspa_mobile/";

	  private static AsyncHttpClient client = new AsyncHttpClient();
	  
	  public static void setAuth(String user, String password) {
		  client.setBasicAuth(user, password);
	  }
	  public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.get(getAbsoluteUrl(url), params, responseHandler);
	  }

	  public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
	      client.post(getAbsoluteUrl(url), params, responseHandler);
	  }

	  private static String getAbsoluteUrl(String relativeUrl) {
	      return BASE_URL + relativeUrl;
	  }

}
