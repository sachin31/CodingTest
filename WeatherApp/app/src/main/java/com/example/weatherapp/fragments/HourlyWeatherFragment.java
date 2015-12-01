package com.example.weatherapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.weatherapp.R;
import com.example.weatherapp.adapters.CustomWeatherAdapter;
import com.example.weatherapp.bo.WeatherData;

/*Hourly weather information screen*/
public class HourlyWeatherFragment extends Fragment {

    private CustomWeatherAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_hourly_weather, container, false);
        if (adapter == null) {
            if(WeatherData.getInstance().getHourly() != null) {
                if (WeatherData.getInstance().getHourly().getData() != null && WeatherData.getInstance().getHourly().getData().size() > 0) {
                    adapter = new CustomWeatherAdapter(getActivity(), R.layout.row_weather_details, WeatherData.getInstance().getHourly().getData(), true);
                }
            }
        }
        // create a list of hourly weather information
        ListView lvHourlyWeather = (ListView) rootView.findViewById(R.id.lv_hourly_weather);
        lvHourlyWeather.setAdapter(adapter);
        return rootView;
    }
}
