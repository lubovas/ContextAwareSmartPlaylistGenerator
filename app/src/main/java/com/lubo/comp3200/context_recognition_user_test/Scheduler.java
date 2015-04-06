package com.lubo.comp3200.context_recognition_user_test;

import android.app.Activity;

/**
 * Schedules context paramater recogniton services
 *
 *
 * Created by Lyubomir on 17/03/2015.
 */
public class Scheduler {


    private ActivityRecognitionScan mActivityScanner;
    private WeatherRecognitonScan mWeatherScan;

    private final Activity mCallingActivity;

    private ContextStore mContextStore;
    // If there is no active context, this will be the initial one
    private Context mInitialContext;

    private AppParams.ACTIVITY mCurrentContextActivity;
    private AppParams.WEATHER mCurrentContextWeather;


    public Scheduler(Activity activityContext) {
        mCallingActivity = activityContext;
        mContextStore = new ContextStore(mCallingActivity);
        if (!checkActiveContext()) {
            mInitialContext = new Context();
        }
        mActivityScanner = new ActivityRecognitionScan(mCallingActivity, mCurrentContextActivity);
        mWeatherScan = new WeatherRecognitonScan(mCallingActivity, mCurrentContextWeather);
    }

    // Check whether there is an active context
    public boolean checkActiveContext(){
        Context activeContext = mContextStore.getActiveContext();
        if (activeContext != null) {
            mCurrentContextActivity = activeContext.getActivity();
            mCurrentContextWeather = activeContext.getWeather();
            return true;
        }
        return false;
    }

    public Context getInitialContext() {
        return mInitialContext;
    }
}
