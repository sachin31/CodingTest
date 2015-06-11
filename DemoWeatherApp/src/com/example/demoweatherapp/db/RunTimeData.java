package com.example.demoweatherapp.db;

import java.util.ArrayList;
import android.util.Log;
import com.example.demoweatherapp.bo.WeatherDetails;

public class RunTimeData {

	private static RunTimeData instance;
	private static WeatherDetails currentlyWeatherDetails;
	private static ArrayList<WeatherDetails> dailyWeatherDetailsList;
	private static ArrayList<WeatherDetails> hourlyWeatherDetailsList;
	private static String dailySummary;
	private static String hourlySummary;

	private RunTimeData() {
	}

	public static RunTimeData getInstance() {

		if (instance == null) {
			Log.i("RunTimeData", "new RunTimeData()");
			instance = new RunTimeData();
		}

		return instance;
	}

	public static WeatherDetails getCurrentlyWeatherDetails() {
		return currentlyWeatherDetails;
	}

	public static void setCurrentlyWeatherDetais(WeatherDetails currentlyWeatherDetails) {
		RunTimeData.currentlyWeatherDetails = currentlyWeatherDetails;
	}

	public static ArrayList<WeatherDetails> getDailyWeatherDetailsList() {
		return dailyWeatherDetailsList;
	}

	public static void setDailyWeatherDetailsList(ArrayList<WeatherDetails> dailyWeatherDetailsList) {
		RunTimeData.dailyWeatherDetailsList = dailyWeatherDetailsList;
	}

	public static ArrayList<WeatherDetails> getHourlyWeatherDetailsList() {
		return hourlyWeatherDetailsList;
	}

	public static void setHourlyWeatherDetailsList(ArrayList<WeatherDetails> hourlyWeatherDetailsList) {
		RunTimeData.hourlyWeatherDetailsList = hourlyWeatherDetailsList;
	}

	public static String getDailySummary() {
		return dailySummary;
	}

	public static void setDailySummary(String dailySummary) {
		RunTimeData.dailySummary = dailySummary;
	}

	public static String getHourlySummary() {
		return hourlySummary;
	}

	public static void setHourlySummary(String hourlySummary) {
		RunTimeData.hourlySummary = hourlySummary;
	}

	public static void clearRuntimeData() {
		instance = new RunTimeData();
	}
}
