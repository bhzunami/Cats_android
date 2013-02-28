package ch.swisscom.cats_android.util;

import org.json.JSONArray;

public interface HttpHandlerDelegate {
	
	public void jsonHandlerFinishLoading(JSONArray jArray);

	public void setToast(String message);
	
	public void showProgress(final boolean show);
	
}
