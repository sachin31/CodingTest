package com.example.weatherapp.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;

import com.example.weatherapp.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Common functions which can be used throughout the app
public class CommonFunctions {

    /*function to get Date in required format*/
    public static String getDateFormat(long time) {
        Date date = new Date(time * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault()); // the format of your date
        String formattedDate = sdf.format(date);
        Log.i("FormattedDate : ", formattedDate);
        return formattedDate;
    }
    /*function to get Time in required format*/
    public static String getTimeFormat(long time) {
        Date date = new Date(time * 1000L); // *1000 is to convert seconds to milliseconds
        SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm aa", Locale.getDefault()); // the format of your date
        String formattedTime = sdf1.format(date);
        Log.i("FormattedTime : ", formattedTime);
        return formattedTime;
    }

    /*function to verify if the string value is null or empty*/
    public static boolean isNullOrEmpty(String s) {
        boolean isNullOrEmpty = false;
        if (s == null || s.length() == 0) {
            isNullOrEmpty = true;
        }
        return isNullOrEmpty;
    }

    // set icon for weather depending on the weather type
    public static void setIconForWeather(Context context, ImageView view, String weatherType) {
        if (context.getResources().getString(R.string.rain).equals(weatherType)) {
            view.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_rain));
        } else if (context.getResources().getString(R.string.clear_day).equals(weatherType)) {
            view.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clear));
        } else if (context.getResources().getString(R.string.clear_night).equals(weatherType)) {
            view.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clear));
        } else if (context.getResources().getString(R.string.cloudy).equals(weatherType)) {
            view.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_overcast));
        } else if((context.getResources().getString(R.string.partly_cloudy).equals(weatherType))){
            view.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_partly_cloudy));
        } else {
            view.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_clear));
        }
    }
}
