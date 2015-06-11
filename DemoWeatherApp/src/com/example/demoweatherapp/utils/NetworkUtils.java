package com.example.demoweatherapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {

	// Network type wifi
	public static int TYPE_WIFI = 1;

	// Network type mobile
	public static int TYPE_MOBILE = 2;

	// Network not connected
	public static int TYPE_NOT_CONNECTED = 0;

	// Get connectivity status
	public static int getConnectivityStatus(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
		if (null != activeNetwork) {
			if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
				return TYPE_WIFI;
			if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
				return TYPE_MOBILE;
		}
		return TYPE_NOT_CONNECTED;
	}

	// Check if connected to network
	public static boolean isConnected(Context context) {
		int conn = NetworkUtils.getConnectivityStatus(context);
		boolean status = false;
		if (conn == NetworkUtils.TYPE_WIFI || conn == NetworkUtils.TYPE_MOBILE) {
			status = true;
		} else if (conn == NetworkUtils.TYPE_NOT_CONNECTED) {
			status = false;
		}
		return status;
	}
}
