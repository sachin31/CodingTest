package com.example.demoweatherapp.parser;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.demoweatherapp.bo.WeatherDetails;
import com.example.demoweatherapp.db.RunTimeData;
import com.example.demoweatherapp.utils.CommonFunctions;

public class ParseJsonResponse {
	
	
	
	// parse the received json and add data to weather details list
	public void parseJSONResponse(String responseString) {
		try {
			RunTimeData.getInstance();
			if(!CommonFunctions.isNullOrEmpty(responseString)) {
				JSONObject jsonResponse = new JSONObject(responseString);
				if (jsonResponse.getJSONObject("currently") != null) {
					//parse currently					
					RunTimeData.setCurrentlyWeatherDetais(parseCurrentlyJSON(jsonResponse.getJSONObject("currently")));
				}
				if (jsonResponse.getJSONObject("daily") != null) {
					//parse daily
					RunTimeData.setDailyWeatherDetailsList(parseDailyJSON(jsonResponse.getJSONObject("daily")));
				}
				if (jsonResponse.getJSONObject("hourly") != null) {
					//parse daily
					RunTimeData.setHourlyWeatherDetailsList(parseHourlyJSON(jsonResponse.getJSONObject("hourly")));
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public WeatherDetails parseCurrentlyJSON(JSONObject currentWeatherJsonObj)
	{
		WeatherDetails currWeatherDetails = new WeatherDetails();
		try {
			if (currentWeatherJsonObj.has("time")) {
				currWeatherDetails.setCurrentDate(currentWeatherJsonObj.getString("time"));
			}
			if (currentWeatherJsonObj.has("icon")) {
				currWeatherDetails.setWeatherType(currentWeatherJsonObj.getString("icon"));
			}
			if (currentWeatherJsonObj.has("temperature")) {
				currWeatherDetails.setCurrentTemp(currentWeatherJsonObj.getString("temperature"));
			}
			if (currentWeatherJsonObj.has("summary")) {
				currWeatherDetails.setWeatherSummary(currentWeatherJsonObj.getString("summary"));
			}
			if (currentWeatherJsonObj.has("humidity")) {
				currWeatherDetails.setHumidity(currentWeatherJsonObj.getString("humidity"));
			}
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return currWeatherDetails;
	}

	public ArrayList<WeatherDetails> parseDailyJSON(JSONObject dailyWeatherJsonObj){
		ArrayList<WeatherDetails> dailyWeatherDetailsList = new ArrayList<WeatherDetails>();

		try {
			if (dailyWeatherJsonObj.has("summary")) {
				RunTimeData.setDailySummary(dailyWeatherJsonObj.getString("summary"));
			}

			if (dailyWeatherJsonObj.getJSONArray("data") != null) {
				JSONArray dailyWeatherData = new JSONArray();
				dailyWeatherData = dailyWeatherJsonObj.getJSONArray("data");
				if (dailyWeatherData != null && dailyWeatherData.length() > 0) {
					WeatherDetails tempWeekWeather;
					for (int i = 0; i < dailyWeatherData.length(); i++) {
						tempWeekWeather = new WeatherDetails();
						JSONObject tempWeather = new JSONObject();
						tempWeather = dailyWeatherData.getJSONObject(i);
						if (tempWeather.has("time")) {
							tempWeekWeather.setCurrentDate(tempWeather.getString("time"));
						}
						if (tempWeather.has("icon")) {
							tempWeekWeather.setWeatherType(tempWeather.getString("icon"));
						}
						if (tempWeather.has("temperatureMin")) {
							tempWeekWeather.setMinimumTemp(tempWeather.getString("temperatureMin"));
						}
						if (tempWeather.has("temperatureMax")) {
							tempWeekWeather.setMaximumTemp(tempWeather.getString("temperatureMax"));
						}
						if (tempWeather.has("summary")) {
							tempWeekWeather.setWeatherSummary(tempWeather.getString("summary"));
						}
						if (tempWeather.has("humidity")) {
							tempWeekWeather.setHumidity(tempWeather.getString("humidity"));
						}
						dailyWeatherDetailsList.add(tempWeekWeather);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return dailyWeatherDetailsList;
	}

	public ArrayList<WeatherDetails> parseHourlyJSON(JSONObject hourlyWeatherJsonObject) {
		ArrayList<WeatherDetails> hourlyWeatherDetailsList = new ArrayList<WeatherDetails>();

		try {
			if (hourlyWeatherJsonObject.has("summary")) {
				RunTimeData.setHourlySummary(hourlyWeatherJsonObject.getString("summary"));
			}
			if (hourlyWeatherJsonObject.getJSONArray("data") != null) {
				JSONArray hourlyWeatherData = new JSONArray();
				hourlyWeatherData = hourlyWeatherJsonObject.getJSONArray("data");
				if (hourlyWeatherData != null && hourlyWeatherData.length() > 0) {
					WeatherDetails tempHourlyWeather;
					for (int i = 0; i < hourlyWeatherData.length(); i++) {
						tempHourlyWeather = new WeatherDetails();
						JSONObject tempWeather = new JSONObject();
						tempWeather = hourlyWeatherData.getJSONObject(i);
						if (tempWeather.has("time")) {
							tempHourlyWeather.setCurrentDate(tempWeather.getString("time"));
						}
						if (tempWeather.has("summary")) {
							tempHourlyWeather.setWeatherSummary(tempWeather.getString("summary"));
						}
						if (tempWeather.has("icon")) {
							tempHourlyWeather.setWeatherType(tempWeather.getString("icon"));
						}
						if (tempWeather.has("temperature")) {
							tempHourlyWeather.setCurrentTemp(tempWeather.getString("temperature"));
						}
						if (tempWeather.has("humidity")) {
							tempHourlyWeather.setHumidity(tempWeather.getString("humidity"));
						}
						hourlyWeatherDetailsList.add(tempHourlyWeather);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return hourlyWeatherDetailsList;
	}
	
	
}
