package com.example.weatherapp.services;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.example.weatherapp.bo.WeatherData;
import com.example.weatherapp.utilities.CommonFunctions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * WeatherForecastService class to fetch weather information based on user's current location;
 * */
public class WeatherForecastService extends AsyncTask<String, Void, String> {

    private WeatherDetailsListner listener;
    private ProgressDialog mDialog;
    private String jsonResponse;

    // Handle callback to activity
    public interface WeatherDetailsListner {
        void onResponseSuccess();
        void onResponseError();
    }

    public WeatherForecastService(Activity context) {
        mDialog = new ProgressDialog(context);
        try {
            listener = (WeatherDetailsListner) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement onWeatherUpdated");
        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mDialog.setMessage("Please Wait...");
        mDialog.show();
    }

    /*Call webService in background */
    @Override
    protected String doInBackground(String... params) {
        try {
            Log.i("URL : ", params[0]);
            jsonResponse = getJsonResponse(params[0]);
            longInfo("Response : ", jsonResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonResponse;
    }

    @Override
    protected void onPostExecute(String response) {
        try {
            mDialog.dismiss();
            if(!CommonFunctions.isNullOrEmpty(response)) {
                WeatherData weatherData = WeatherData.getInstance();
                weatherData.setDataFromJSON(response);
                listener.onResponseSuccess();
            } else {
                listener.onResponseError();
            }
        } catch (Exception e) {
            e.printStackTrace();
            listener.onResponseError();
        }
    }

    public String getJsonResponse(String url) throws Exception {
        String result;
        HttpsURLConnection conn = (HttpsURLConnection) (new URL(url)).openConnection();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        String string;
        StringBuilder stringBuffer = new StringBuilder();
        while ((string = bufferedReader.readLine()) != null) {
            stringBuffer.append(string);
        }
        result = stringBuffer.toString();
        return result;
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
