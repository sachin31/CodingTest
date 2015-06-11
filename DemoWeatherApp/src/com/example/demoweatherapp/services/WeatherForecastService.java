package com.example.demoweatherapp.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.demoweatherapp.parser.ParseJsonResponse;
import com.example.demoweatherapp.utils.CommonFunctions;

/**
 * 
 * @author Sachin_590802
 * 
 */
public class WeatherForecastService extends AsyncTask<String, Void, String> {

	private Activity mContext;
	private WeatherDetailsListner listener;
	private ProgressDialog mDailog;

	public interface WeatherDetailsListner {
		public void onWeatherDetailsUpdated();
		public void onConnectionTimeOut();
	}

	public WeatherForecastService(Activity context) {
		mContext = context;
		mDailog = new ProgressDialog(mContext);
		try {
			listener = (WeatherDetailsListner) mContext;
		} catch (ClassCastException e) {
			throw new ClassCastException(mContext.toString() + " must implement onWeatherUpdated");
		}

	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		mDailog.setMessage("Please Wait...");
		mDailog.show();
	}

	@Override
	protected String doInBackground(String... params) {

		try {
			Log.i("URL : ", params[0]);
			String responseString = executeHttpGet(params[0]);
			longInfo("Response : ", responseString.toString());
			ParseJsonResponse parseJson = new ParseJsonResponse();
			if(!CommonFunctions.isNullOrEmpty(responseString)) {
				parseJson.parseJSONResponse(responseString);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		mDailog.dismiss();
		listener.onWeatherDetailsUpdated();
	}

	public String executeHttpGet(String url) throws Exception {
		StringBuffer stringBuffer = null;
		try {
			HttpGet req = new HttpGet(url);
			HttpClient client = new DefaultHttpClient();
			final HttpParams httpParams = new BasicHttpParams();
			HttpConnectionParams.setConnectionTimeout(httpParams, 15000);
			client = new DefaultHttpClient(httpParams);
			HttpResponse resLogin = client.execute(req);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(resLogin.getEntity().getContent(), "UTF-8"));
			String string = new String();
			stringBuffer = new StringBuffer();
			while ((string = bufferedReader.readLine()) != null) {
				stringBuffer.append(string);
			}
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
			listener.onConnectionTimeOut();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

	// to print log if length greater than 4000
	public void longInfo(String tag, String str) {
		if (str.length() > 4000) {
			Log.i(tag, str.substring(0, 4000));
			longInfo(tag, str.substring(4000));
		} else
			Log.i(tag, str);
	}
}
