package com.lubo.comp3200.context_recognition_user_test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class Main extends ActionBarActivity {

    private SharedPreferences mPrefs;
    private final static String SHARED_PREFERENCES = "MainActivity";

    protected static Scheduler mScheduler;

    protected ContextParser mParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mPrefs = this.getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE);
       /* if (checkFirstStart()){
            SharedPreferences.Editor editor = mPrefs.edit();
            editor.putString(AppParams.KEY_FIRST_START, "True");
            editor.apply();
        }*/
        mParser = new ContextParser(this);
        mScheduler = new Scheduler(this);

        Intent intent = new Intent(this, TestService.class);
        startService(intent);

    }

    // Check if this is the first time the app was ever started
    public boolean checkFirstStart() {
        if (mPrefs.getString(AppParams.KEY_FIRST_START, AppParams.INVALID_STRING_VALUE) != AppParams.INVALID_STRING_VALUE) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class FirstTimeSetup extends AsyncTask<Void, Void, Void> {

       @Override
       protected Void doInBackground(Void... params) {
           mParser.loadJSONAssets();
           mParser.extractContexts();
           mParser.saveContexts();
           return null;
       }
   }
}
