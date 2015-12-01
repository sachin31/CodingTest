package com.example.weatherapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.weatherapp.R;
import com.example.weatherapp.bo.CurrentWeatherData;
import com.example.weatherapp.bo.WeatherData;
import com.example.weatherapp.constants.AppConstant;
import com.example.weatherapp.utilities.CommonFunctions;

/*Current weather information screen*/
public class CurrentWeatherFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.row_weather_details, container, false);
        ImageView imgWeatherType = (ImageView) rootView.findViewById(R.id.img_weather_type);
        TextView txtSummary = (TextView) rootView.findViewById(R.id.txt_summary);
        TextView txtDay = (TextView) rootView.findViewById(R.id.txt_day);
        TextView txtTemp = (TextView) rootView.findViewById(R.id.txt_temp);
        TextView txtHumidity = (TextView) rootView.findViewById(R.id.txt_humidity);
        TextView txtWindSpeed = (TextView) rootView.findViewById(R.id.txt_wind_speed);
        TextView txtVisibility = (TextView) rootView.findViewById(R.id.txt_visibility);
        //if available , show current weather information
        if (WeatherData.getInstance().getCurrently() != null) {
            CurrentWeatherData weatherData = WeatherData.getInstance().getCurrently();
            CommonFunctions.setIconForWeather(getActivity(), imgWeatherType, weatherData.getIcon());
            txtSummary.setText(weatherData.getSummary());
            txtDay.setText(CommonFunctions.getDateFormat(weatherData.getTime()));
            txtTemp.setText("Temperature : " + weatherData.getTemperature() + AppConstant.DEGREE_FARENHEIT);
            txtHumidity.setText("Humidity : " + weatherData.getHumidity());
            txtWindSpeed.setText("Wind Speed : " + weatherData.getWindSpeed() +" "+ AppConstant.WINDSPEED);
            if(weatherData.getVisibility() > 0.0) {
                txtVisibility.setText("Visibility : " + weatherData.getVisibility() +" "+ AppConstant.MILES);
            } else {
                txtVisibility.setVisibility(View.GONE);
            }

        }
        return rootView;
    }
}
