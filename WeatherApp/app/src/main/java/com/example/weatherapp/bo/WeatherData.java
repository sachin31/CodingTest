package com.example.weatherapp.bo;

import com.google.gson.Gson;

/*Business Object for Weather data */
public class WeatherData {

    private static WeatherData mInstance;
    private CurrentWeatherData currently;
    private HourlyWeatherData hourly;
    private DailyWeatherData daily;

    public static WeatherData getInstance() {
        if (mInstance == null) {
            mInstance = new WeatherData();
        }
        return mInstance;
    }

    public void reset() {
        mInstance = new WeatherData();
    }

    public CurrentWeatherData getCurrently() {
        return currently;
    }

    public void setCurrently(CurrentWeatherData currently) {
        this.currently = currently;
    }

    public HourlyWeatherData getHourly() {
        return hourly;
    }

    public void setHourly(HourlyWeatherData hourly) {
        this.hourly = hourly;
    }

    public DailyWeatherData getDaily() {
        return daily;
    }

    public void setDaily(DailyWeatherData daily) {
        this.daily = daily;
    }

    public void setDataFromJSON(String response) {
        Gson gson = new Gson();
        mInstance = gson.fromJson(response, WeatherData.class);
    }
}
