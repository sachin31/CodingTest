package com.example.demoweatherapp;

import com.example.demoweatherapp.constants.AppConstant;
import com.example.demoweatherapp.db.RunTimeData;
import com.example.demoweatherapp.utils.CommonFunctions;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class CurrentWeatherFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_current_weather, container, false);

		TextView txtSummary = (TextView) rootView.findViewById(R.id.txt_summary);
		ImageView imgWeatherType = (ImageView) rootView.findViewById(R.id.img_weather_type);
		TextView txtDay = (TextView) rootView.findViewById(R.id.txt_day);
		TextView txtTemp = (TextView) rootView.findViewById(R.id.txt_temp);
		TextView txtHumidity = (TextView) rootView.findViewById(R.id.txt_humidity);

		if(RunTimeData.getCurrentlyWeatherDetails() != null) {
			txtSummary.setText((RunTimeData.getCurrentlyWeatherDetails().getWeatherSummary() != null) ? RunTimeData.getCurrentlyWeatherDetails().getWeatherSummary() : "");
			setIconForWeather(imgWeatherType, RunTimeData.getCurrentlyWeatherDetails().getWeatherType());
			txtDay.setText((RunTimeData.getCurrentlyWeatherDetails().getCurrentDate() != null) ? CommonFunctions.getDateFormat(RunTimeData.getCurrentlyWeatherDetails().getCurrentDate()) : "");
			txtTemp.setText((RunTimeData.getCurrentlyWeatherDetails().getCurrentTemp() != null) ? "Temperature : "+RunTimeData.getCurrentlyWeatherDetails().getCurrentTemp()+AppConstant.DEGREE_FARENHEIT : "");
			txtHumidity.setText((RunTimeData.getCurrentlyWeatherDetails().getHumidity() != null) ? "Humidity : "+RunTimeData.getCurrentlyWeatherDetails().getHumidity() : "");
		} 
		return rootView;
	}

	// set icon for weather depending on weather type
	private void setIconForWeather(ImageView view, String weatherType) {
		if (getResources().getString(R.string.rain).equals(weatherType)) {
			view.setImageDrawable(getResources().getDrawable(R.drawable.img_rainy));
		} else if (getResources().getString(R.string.clear_day).equals(weatherType)) {
			view.setImageDrawable(getResources().getDrawable(R.drawable.img_clear));
		} else if ((getResources().getString(R.string.cloudy).equals(weatherType)) || (getResources().getString(R.string.partly_cloudy).equals(weatherType))) {
			view.setImageDrawable(getResources().getDrawable(R.drawable.img_cloudy));
		} else {
			view.setImageDrawable(getResources().getDrawable(R.drawable.img_default));
		}
	}
}
