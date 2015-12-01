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

/*Daily weather information screen*/
public class DailyWeatherFragment extends Fragment {

    private CustomWeatherAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daily_weather, container, false);
        if (adapter == null) {
            if(WeatherData.getInstance().getDaily() != null) {
                if (WeatherData.getInstance().getDaily().getData() != null && WeatherData.getInstance().getDaily().getData().size() > 0) {
                    adapter = new CustomWeatherAdapter(getActivity(), R.layout.row_weather_details, WeatherData.getInstance().getDaily().getData(), false);
                }
            }
        }

        // create list of daily weather information
        ListView lvDailyWeather = (ListView) rootView.findViewById(R.id.lv_daily_weather);
        lvDailyWeather.setAdapter(adapter);
        return rootView;
    }
}
