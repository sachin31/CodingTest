package com.example.demoweatherapp;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.demoweatherapp.adapter.WeatherAdapter;
import com.example.demoweatherapp.db.RunTimeData;

public class DailyWeatherFragment extends Fragment {

	private WeatherAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_daily_weather, container, false);

		if (adapter == null) {
			if(RunTimeData.getDailyWeatherDetailsList() != null && RunTimeData.getDailyWeatherDetailsList().size() > 0) {
				adapter = new WeatherAdapter(getActivity(), R.layout.row_weather_details, RunTimeData.getDailyWeatherDetailsList(), true);
			}
		}
		ListView lvDailyWeather = (ListView) rootView.findViewById(R.id.lv_daily_weather);
		lvDailyWeather.setAdapter(adapter);

		return rootView;
	}

}
