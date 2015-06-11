package com.example.demoweatherapp.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.util.Log;

public class CommonFunctions {
	
	public static String getDateFormat(String time) {
		long unixseconds = Long.valueOf(time);
		Date date = new Date(unixseconds*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault()); // the format of your date
		String formattedDate = sdf.format(date);
		Log.i("FormattedDate : ", formattedDate);
		return formattedDate;
	}
	
	public static String getDateWeekFormat(String time) {
		long unixseconds = Long.valueOf(time);
		Date date = new Date(unixseconds*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf1 = new SimpleDateFormat("EEE", Locale.getDefault()); // the format of your date
		SimpleDateFormat sdf2 = new SimpleDateFormat("dd MMM", Locale.getDefault());
		String formattedDate = sdf1.format(date)+"\n"+sdf2.format(date);
		Log.i("FormattedDate : ", formattedDate);
		return formattedDate;
	}
	
	public static String getTimeFormat(String time) {
		long unixseconds = Long.valueOf(time);
		Date date = new Date(unixseconds*1000L); // *1000 is to convert seconds to milliseconds
		SimpleDateFormat sdf1 = new SimpleDateFormat("hh:mm aa", Locale.getDefault()); // the format of your date
		String formattedTime = sdf1.format(date);
		Log.i("FormattedTime : ", formattedTime);
		return formattedTime;
	}
	
	public static boolean isNullOrEmpty(String s) {
		boolean isNullOrEmpty = false;
		if (s == null || s.length() == 0) {
			isNullOrEmpty = true;
		}
		return isNullOrEmpty;
	}
}
