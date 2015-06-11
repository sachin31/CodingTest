package com.example.demoweatherapp.services;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class GPSGetLocation implements LocationListener {

	private final Context mContext;
	// flag for GPS status
	private boolean isGPSEnabled = false;
	// flag for network status
	private boolean isNetworkEnabled = false;
	// flag for GPS status
	private boolean canGetLocation = false;
	private Location location; // location
	private double latitude; // latitude
	private double longitude; // longitude
	// Declaring a Location Manager
	protected LocationManager locationManager;

	public GPSGetLocation(Context context) {
		this.mContext = context;
		getLocation();
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

			// getting GPS status
			isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

			// getting network status
			isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

			if (!isGPSEnabled && !isNetworkEnabled) {
				// no network provider is enabled
			} else {
				this.canGetLocation = true;
				// First get location from Network Provider
				if (isNetworkEnabled) {
						locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
						Log.d("Network", "Network");
						if (locationManager != null) {
							location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
							if (location != null) {
								if(location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
									latitude = location.getLatitude();
									longitude = location.getLongitude();
								}
							}
						}
				}
				// if GPS Enabled get lat/long using GPS Services
				if (isGPSEnabled) {
					locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
					Log.d("GPS Enabled", "GPS Enabled");
					if (locationManager != null) {
						location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
						if (location != null) {
							if(location.getLatitude() != 0.0 && location.getLongitude() != 0.0) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
							}
						}
					}
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return location;
	}

	/**
	 * Stop using GPS listener Calling this function will stop using GPS in your
	 * app
	 * */
	public void stopUsingGPS() {
		if (locationManager != null) {
			locationManager.removeUpdates(GPSGetLocation.this);
		}
	}

	/**
	 * Function to get latitude
	 * */
	public double getLatitude() {
		if (location != null && location.getLatitude() != 0.0) {
			latitude = location.getLatitude();
		}

		// return latitude
		return latitude;
	}

	/**
	 * Function to get longitude
	 * */
	public double getLongitude() {
		if (location != null && location.getLongitude() != 0.0) {
			longitude = location.getLongitude();
		}

		// return longitude
		return longitude;
	}

	/**
	 * Function to check GPS/wifi enabled
	 * 
	 * @return boolean
	 * */
	public boolean canGetLocation() {
		return this.canGetLocation;
	}

	@Override
	public void onLocationChanged(Location location) {
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
