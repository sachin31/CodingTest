package com.example.demoweatherapp.adapter;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.demoweatherapp.R;
import com.example.demoweatherapp.bo.WeatherDetails;
import com.example.demoweatherapp.constants.AppConstant;
import com.example.demoweatherapp.utils.CommonFunctions;

public class WeatherAdapter extends ArrayAdapter<WeatherDetails> {

	private Context context;
	private Resources resources;
	private int resId;
	private ArrayList<WeatherDetails> weatherDetailsList;
	private boolean hourlyOrDaily;

	public WeatherAdapter(Context c, int textViewResourceId, ArrayList<WeatherDetails> l, boolean dateTime) {
		super(c, textViewResourceId, l);
		context = c;
		resources = c.getResources();
		resId = textViewResourceId;
		weatherDetailsList = l;
		hourlyOrDaily = dateTime;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		WeatherDetailsListHolder holder = null;
		if (row == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
			row = inflater.inflate(resId, parent, false);
			holder = new WeatherDetailsListHolder();
			holder.imgWeatherType = (ImageView) row.findViewById(R.id.img_weather_type);
			holder.txtSummary = (TextView) row.findViewById(R.id.txt_summary);
			holder.txtDate = (TextView) row.findViewById(R.id.txt_day);
			holder.txtTemp = (TextView) row.findViewById(R.id.txt_temp);
			holder.txtTempMinMax = (TextView) row.findViewById(R.id.txt_min_max_temp);
			holder.txtHumidity = (TextView) row.findViewById(R.id.txt_humidity);
			row.setTag(holder);
		} else {
			holder = (WeatherDetailsListHolder) row.getTag();
		}
		
		if(weatherDetailsList != null && weatherDetailsList.size() > 0) {
				setIconForWeather(holder.imgWeatherType, weatherDetailsList.get(position).getWeatherType());
				holder.txtSummary.setText(weatherDetailsList.get(position).getWeatherSummary());
				if(hourlyOrDaily) {
					holder.txtDate.setText(CommonFunctions.getDateFormat(weatherDetailsList.get(position).getCurrentDate()));
				} else {
					holder.txtDate.setText("Time : "+CommonFunctions.getTimeFormat(weatherDetailsList.get(position).getCurrentDate()));
				}
				if(hourlyOrDaily) {
					holder.txtTemp.setVisibility(View.GONE);
				} else {
					holder.txtTemp.setVisibility(View.VISIBLE);
					holder.txtTemp.setText("Temperature : "+weatherDetailsList.get(position).getCurrentTemp()+AppConstant.DEGREE_FARENHEIT);
				}
				holder.txtTemp.setText("Temperature : "+weatherDetailsList.get(position).getCurrentTemp()+AppConstant.DEGREE_FARENHEIT);
				if(!CommonFunctions.isNullOrEmpty(weatherDetailsList.get(position).getMinimumTemp()) && !CommonFunctions.isNullOrEmpty(weatherDetailsList.get(position).getMaximumTemp())) {
					holder.txtTempMinMax.setText(weatherDetailsList.get(position).getMinimumTemp()+AppConstant.DEGREE_FARENHEIT+" - "+weatherDetailsList.get(position).getMaximumTemp()+AppConstant.DEGREE_FARENHEIT);
					holder.txtTempMinMax.setVisibility(View.VISIBLE);
				} else {
					holder.txtTempMinMax.setVisibility(View.GONE);
				}
				holder.txtHumidity.setText("Humidity : "+weatherDetailsList.get(position).getHumidity());
		}
		return row;
	}

	private class WeatherDetailsListHolder {
		public ImageView imgWeatherType;
		public TextView txtSummary;
		public TextView txtDate;
		public TextView txtTemp;
		public TextView txtTempMinMax;
		public TextView txtHumidity;
	}

	// set icon for weather depending on weather type
	private void setIconForWeather(ImageView view, String weatherType) {
		if (resources.getString(R.string.rain).equals(weatherType)) {
			view.setImageDrawable(resources.getDrawable(R.drawable.img_rainy));
		} else if (resources.getString(R.string.clear_day).equals(weatherType)) {
			view.setImageDrawable(resources.getDrawable(R.drawable.img_clear));
		} else if ((resources.getString(R.string.cloudy).equals(weatherType)) || (resources.getString(R.string.partly_cloudy).equals(weatherType))) {
			view.setImageDrawable(resources.getDrawable(R.drawable.img_cloudy));
		} else {
			view.setImageDrawable(resources.getDrawable(R.drawable.img_default));
		}
	}
}