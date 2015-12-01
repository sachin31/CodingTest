package com.example.weatherapp;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;
import com.example.weatherapp.activities.MainActivity;
import com.example.weatherapp.services.LocationService;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    private Activity activity;
    private TextView mTxtMsg;
    private LocationService mLocation;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        activity = getActivity();
        mTxtMsg = (TextView) activity.findViewById(R.id.txt_msg);
        mLocation = new LocationService(activity);
    }

    public void testPreConditions() {
        assertNotNull("mMainActivity not null", activity);
        assertNotNull("mFirstTestText not null", mTxtMsg);
    }

    public void testLatLngValues() {
        if(mLocation.getLatitude() != 0) {
            assertTrue("isLatitude", true);
        } else {
            assertFalse("isLatitude", false);
        }
        if(mLocation.getLongitude() != 0) {
            assertTrue("isLongitude", true);
        } else {
            assertFalse("isLongitude", false);
        }
    }
}
