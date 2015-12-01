package com.example.weatherapp.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.bo.WeatherData;
import com.example.weatherapp.constants.AppConstant;
import com.example.weatherapp.fragments.CurrentWeatherFragment;
import com.example.weatherapp.fragments.DailyWeatherFragment;
import com.example.weatherapp.fragments.HourlyWeatherFragment;
import com.example.weatherapp.services.LocationService;
import com.example.weatherapp.services.WeatherForecastService;
import com.example.weatherapp.ui.utils.DialogCreator;
import com.example.weatherapp.utilities.CommonFunctions;
import com.example.weatherapp.utilities.NetworkUtils;

public class MainActivity extends ActionBarActivity implements WeatherForecastService.WeatherDetailsListner, TabListener {

    private LocationService mLocationService;
    private boolean isDataAvailable;
    private TextView mTxtMsg; /*to show message,if weather information is not available*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTxtMsg = (TextView) findViewById(R.id.txt_msg);
        setTabs();
    }

    // Add tabs to ActionBar
    private void setTabs() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.addTab(actionBar.newTab().setText(AppConstant.CURRENT_WEATHER).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(AppConstant.HOURLY_WEATHER).setTabListener(this));
        actionBar.addTab(actionBar.newTab().setText(AppConstant.DAILY_WEATHER).setTabListener(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Call service only on app launch and if service response fails : service will not be called if app comes back from onPause to onResume
        if (!isDataAvailable) {
            init();
        }
    }

    private void init() {
        mTxtMsg.setVisibility(View.GONE);
        mLocationService = new LocationService(MainActivity.this);
        // Check if network and GPS available
        if (!NetworkUtils.isConnected(MainActivity.this) && !mLocationService.canGetLocation()) {
            // Show dialog to change settings if network not available
            DialogInterface.OnClickListener yes = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    // navigate user to settings page
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    startActivity(intent);
                }
            };
            DialogInterface.OnClickListener no = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    // show error message on page
                    mTxtMsg.setVisibility(View.VISIBLE);
                    mTxtMsg.setText(getResources().getString(R.string.loc_not_found));
                }
            };
            new DialogCreator(MainActivity.this).showDialog(DialogCreator.DIALOG_CONFIRM, yes, no, getResources().getString(R.string.nw_settings), getResources().getString(R.string.nw_settings_msg), true);
        } else {
            // If network available call WebService
            String completeURL = AppConstant.URL + mLocationService.getLatitude() + "," + mLocationService.getLongitude();
            if (!CommonFunctions.isNullOrEmpty(completeURL)) {
                new WeatherForecastService(MainActivity.this).execute(completeURL);
            }
        }
    }

    // Webservice success callback
    @Override
    public void onResponseSuccess() {
        isDataAvailable = true;
        mTxtMsg.setVisibility(View.GONE);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();
    }

    // Webservice error callback
    @Override
    public void onResponseError() {
        mTxtMsg.setVisibility(View.VISIBLE);
        mTxtMsg.setText(getResources().getString(R.string.conn_error));
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationService.stopUsingGPS();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        WeatherData.getInstance().reset();
    }

    // Change fragments on tab selection
    @Override
    public void onTabSelected(Tab tab, FragmentTransaction fragmentTransaction) {
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
            fragmentTransaction.replace(R.id.fragment_container, fragment);
        }
    }

    @Override
    public void onTabUnselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(Tab tab, FragmentTransaction fragmentTransaction) {
    }
}
