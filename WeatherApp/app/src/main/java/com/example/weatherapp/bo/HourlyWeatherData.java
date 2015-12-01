package com.example.weatherapp.bo;

import java.util.ArrayList;

/*Business Object for Hourly Weather data */
public class HourlyWeatherData {

    private String summary = "";
    private String icon = "";
    private ArrayList<CurrentWeatherData> data;

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public ArrayList<CurrentWeatherData> getData() {
        return data;
    }

    public void setData(ArrayList<CurrentWeatherData> data) {
        this.data = data;
    }
}
