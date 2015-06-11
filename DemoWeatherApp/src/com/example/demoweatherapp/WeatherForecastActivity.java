package com.example.demoweatherapp;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.demoweatherapp.constants.AppConstant;
import com.example.demoweatherapp.db.RunTimeData;
import com.example.demoweatherapp.services.GPSGetLocation;
import com.example.demoweatherapp.services.WeatherForecastService;
import com.example.demoweatherapp.utils.CommonFunctions;
import com.example.demoweatherapp.utils.NetworkUtils;

public class WeatherForecastActivity extends Activity implements TabListener, WeatherForecastService.WeatherDetailsListner {

	private GPSGetLocation mGPSTracker;
	private boolean mIsDataAvailable;
	private TextView mtxtLocationNotFound;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weather_forecast);
		mtxtLocationNotFound = (TextView) findViewById(R.id.txt_loc_not_found);
		setTabs();
		RunTimeData.clearRuntimeData();
	}

	@Override
	protected void onResume() {
		super.onResume();
		try {
			// If data not available call service
			if(!mIsDataAvailable) {
				getLatLng();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Get lat, lng and call service if connected to network
	private void getLatLng() {
		mGPSTracker = new GPSGetLocation(this);
		if (mGPSTracker.canGetLocation()) {
			if (mGPSTracker.getLatitude() != 0.0 && mGPSTracker.getLatitude() != 0.0) {
				Log.i("LatLng : ", mGPSTracker.getLatitude()+", "+mGPSTracker.getLongitude());
				//					getLocationDetails(mGPSTracker.getLatitude(), mGPSTracker.getLongitude());
				if (NetworkUtils.isConnected(this)) {
					String completeURL = AppConstant.URL + mGPSTracker.getLatitude() + "," + mGPSTracker.getLongitude();
					if (!CommonFunctions.isNullOrEmpty(completeURL)) {
						new WeatherForecastService(WeatherForecastActivity.this).execute(completeURL);
					}
				} else {
					// show alert to enable network
					showNetworkAlert();
				}
			} else {
				// If lat lng could not be found display message to user to
				// retry
				mtxtLocationNotFound.setVisibility(View.VISIBLE);
				mtxtLocationNotFound.setText(getResources().getString(R.string.loc_not_found));
			}
		} else {
			// show alert to enable GPS
			showGPSAlert();
		}
	}

	// show dialog if not connected to network
	public void showNetworkAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle(getResources().getString(R.string.nw_settings));
		alertDialog.setMessage(getResources().getString(R.string.nw_settings_msg));
		alertDialog.setPositiveButton(getResources().getString(R.string.settings), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_SETTINGS);
				startActivity(intent);
			}
		});
		alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				finish();
			}
		});
		alertDialog.show();
	}

	// show alert to enable GPS
	public void showGPSAlert() {
		AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
		alertDialog.setTitle(getResources().getString(R.string.gps_settings));
		alertDialog.setMessage(getResources().getString(R.string.gps_settings_msg));
		alertDialog.setPositiveButton(getResources().getString(R.string.settings), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivity(intent);
			}
		});
		alertDialog.setNegativeButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
				finish();
			}
		});
		alertDialog.show();
	}

	// Update UI once data is received successfully
	@Override
	public void onWeatherDetailsUpdated() {
		try {
			mtxtLocationNotFound.setVisibility(View.GONE);
			FragmentManager fragmentManager = getFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			CurrentWeatherFragment fragment = new CurrentWeatherFragment();
			fragmentTransaction.replace(R.id.fragment_container, fragment);
			fragmentTransaction.commit();
			mIsDataAvailable = true;
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}


	private void setTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS); 
		Tab tabCurrent = actionBar.newTab(); 
		tabCurrent.setText(AppConstant.CURRENT_WEATHER);
		tabCurrent.setTabListener(this);
		actionBar.addTab(tabCurrent);
		Tab tabHourly = actionBar.newTab(); 
		tabHourly.setText(AppConstant.HOURLY_WEATHER);
		tabHourly.setTabListener(this);
		actionBar.addTab(tabHourly);
		Tab tabDaily = actionBar.newTab(); 
		tabDaily.setText(AppConstant.DAILY_WEATHER);
		tabDaily.setTabListener(this);
		actionBar.addTab(tabDaily);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		Fragment fragment = null;
		switch (tab.getPosition()) {
		case 0:
			fragment = new CurrentWeatherFragment();
			break;

		case 1:
			fragment = new HourlyWeatherFragment();
			break;

		case 2:
			fragment = new DailyWeatherFragment();
			break;

		default:
			break;
		}

		if(fragment != null) {
			ft.replace(R.id.fragment_container, fragment);
		}
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onConnectionTimeOut() {
		try {
			mtxtLocationNotFound.setVisibility(View.VISIBLE);
			mtxtLocationNotFound.setText(getResources().getString(R.string.conn_error));
		} catch (NotFoundException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onPause() {
		super.onPause();
		// stop gps service once the app is moved to background
		if(mGPSTracker != null) {
			mGPSTracker.stopUsingGPS();
		}
	}

}



