package com.example.demoweatherapp.bo;


public class WeatherDetails {

	private String locName="";
	private String country="";
	private String currentDate="";
	private String currentTemp="";
	private String minimumTemp="";
	private String maximumTemp="";
	private String weatherType="";
	private String weatherSummary="";
	private String humidity="";
	
	public String getLocName() {
		return locName;
	}

	public void setLocName(String locName) {
		this.locName = locName;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCurrentDate() {
		return currentDate;
	}

	public void setCurrentDate(String currentDate) {
		this.currentDate = currentDate;
	}

	public String getCurrentTemp() {
		return currentTemp;
	}

	public void setCurrentTemp(String currentTemp) {
		this.currentTemp = currentTemp;
	}
	
	public String getMinimumTemp() {
		return minimumTemp;
	}

	public void setMinimumTemp(String minimumTemp) {
		this.minimumTemp = minimumTemp;
	}

	public String getMaximumTemp() {
		return maximumTemp;
	}

	public void setMaximumTemp(String maximumTemp) {
		this.maximumTemp = maximumTemp;
	}

	public String getWeatherType() {
		return weatherType;
	}

	public void setWeatherType(String weatherType) {
		this.weatherType = weatherType;
	}

	public String getWeatherSummary() {
		return weatherSummary;
	}

	public void setWeatherSummary(String weatherSummary) {
		this.weatherSummary = weatherSummary;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}
}
