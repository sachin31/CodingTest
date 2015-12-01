package com.example.weatherapp.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.weatherapp.R;
import com.example.weatherapp.bo.CurrentWeatherData;
import com.example.weatherapp.constants.AppConstant;
import com.example.weatherapp.utilities.CommonFunctions;
import java.util.ArrayList;

// Common adapter for listView hourly and daily data display
public class CustomWeatherAdapter extends ArrayAdapter<CurrentWeatherData> {

    private Context mContext;
    private int resId;
    private ArrayList<CurrentWeatherData> weatherDetailsList;
    private boolean isHourlyData;

    public CustomWeatherAdapter(Context c, int textViewResourceId, ArrayList<CurrentWeatherData> l, boolean flag) {
        super(c, textViewResourceId, l);
        mContext = c;
        resId = textViewResourceId;
        weatherDetailsList = l;
        isHourlyData = flag;
    }

    /*Create view for each data items in the data source*/
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        WeatherDetailsListHolder holder;
        if (row == null) {
            LayoutInflater inflater = ((Activity) mContext).getLayoutInflater();
            row = inflater.inflate(resId, parent, false);
            holder = new WeatherDetailsListHolder();
            holder.imgWeatherType = (ImageView) row.findViewById(R.id.img_weather_type);
            holder.txtSummary = (TextView) row.findViewById(R.id.txt_summary);
            holder.txtDate = (TextView) row.findViewById(R.id.txt_day);
            holder.txtTemp = (TextView) row.findViewById(R.id.txt_temp);
            holder.txtTempMinMax = (TextView) row.findViewById(R.id.txt_min_max_temp);
            holder.txtHumidity = (TextView) row.findViewById(R.id.txt_humidity);
            holder.txtVisibility = (TextView) row.findViewById(R.id.txt_visibility);
            holder.txtWindspeed = (TextView) row.findViewById(R.id.txt_wind_speed);
            row.setTag(holder);
        } else { /*reuse the existing ListView item*/
            holder = (WeatherDetailsListHolder) row.getTag();
        }

        if (weatherDetailsList != null && weatherDetailsList.size() > 0) {
            CommonFunctions.setIconForWeather(mContext, holder.imgWeatherType, weatherDetailsList.get(position).getIcon());
            holder.txtSummary.setText(weatherDetailsList.get(position).getSummary());
            if (isHourlyData) {
                holder.txtDate.setText(CommonFunctions.getDateFormat(weatherDetailsList.get(position).getTime()));
                holder.txtTemp.setVisibility(View.VISIBLE);
                holder.txtTemp.setText("Temperature : " + weatherDetailsList.get(position).getTemperature() + AppConstant.DEGREE_FARENHEIT);
                holder.txtTempMinMax.setVisibility(View.GONE);
            } else {
                holder.txtDate.setText("Time : " + CommonFunctions.getTimeFormat(weatherDetailsList.get(position).getTime()));
                holder.txtTemp.setVisibility(View.GONE);
                holder.txtTempMinMax.setVisibility(View.VISIBLE);
                holder.txtTempMinMax.setText(weatherDetailsList.get(position).getTemperatureMin() + AppConstant.DEGREE_FARENHEIT + " - " + weatherDetailsList.get(position).getTemperatureMax() + AppConstant.DEGREE_FARENHEIT);
            }
            if(weatherDetailsList.get(position).getVisibility() > 0.0) {
                holder.txtVisibility.setVisibility(View.VISIBLE);
                holder.txtVisibility.setText("Visibility : " + weatherDetailsList.get(position).getVisibility() + " " + AppConstant.MILES);
            } else {
                holder.txtVisibility.setVisibility(View.GONE);
            }
            holder.txtHumidity.setText("Humidity : " + weatherDetailsList.get(position).getHumidity());
            holder.txtWindspeed.setText("Wind Speed : " + weatherDetailsList.get(position).getWindSpeed() + " " + AppConstant.WINDSPEED);
        }
        return row;
    }

    /*Holder class for ListView*/
    private class WeatherDetailsListHolder {
        public ImageView imgWeatherType;
        public TextView txtSummary;
        public TextView txtDate;
        public TextView txtTemp;
        public TextView txtTempMinMax;
        public TextView txtHumidity;
        public TextView txtWindspeed;
        public TextView txtVisibility;
    }
}