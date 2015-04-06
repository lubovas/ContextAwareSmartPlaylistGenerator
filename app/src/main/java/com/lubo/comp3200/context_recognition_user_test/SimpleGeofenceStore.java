package com.lubo.comp3200.context_recognition_user_test;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Storage for geofence values, implemented in SharedPreferences.
 * Code heavily based on Google's examples
 */
public class SimpleGeofenceStore {
    // The SharedPreferences object in which geofences are stored
    private final SharedPreferences mPrefs;
    // The name of the SharedPreferences
    private static final String SHARED_PREFERENCES = "GeoStorePrefs";

    public SimpleGeofenceStore(Context context) {
        // Create the SharedPreferences storage with private access only
        mPrefs = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    // Returns a stored geofence by its id, or returns null if it's not found.
    public SimpleGeofence getGeofence(String id) {
        // Get the geofence's paramters; return invalid values if unsuccessful
        double lat = mPrefs.getFloat(getGeofenceFieldKey(id, AppParams.KEY_LATITUDE), AppParams.INVALID_FLOAT_VALUE);
        double lng = mPrefs.getFloat(getGeofenceFieldKey(id, AppParams.KEY_LONGITUDE), AppParams.INVALID_FLOAT_VALUE);
        float radius = mPrefs.getFloat(getGeofenceFieldKey(id, AppParams.KEY_RADIUS), AppParams.INVALID_FLOAT_VALUE);
        long expirationDuration = mPrefs.getLong(getGeofenceFieldKey(id, AppParams.KEY_EXPIRATION_DURATION), AppParams.INVALID_LONG_VALUE);
        int transitionType = mPrefs.getInt(getGeofenceFieldKey(id, AppParams.KEY_TRANSITION_TYPE), AppParams.INVALID_INT_VALUE);
        // If none of the values is incorrect, return the geofence object
        if (
                lat != AppParams.INVALID_FLOAT_VALUE &&
                        lng != AppParams.INVALID_FLOAT_VALUE &&
                        radius != AppParams.INVALID_FLOAT_VALUE &&
                        expirationDuration != AppParams.INVALID_LONG_VALUE &&
                        transitionType != AppParams.INVALID_INT_VALUE) {

            // Return a true Geofence object
            return new SimpleGeofence(id, lat, lng, radius, expirationDuration, transitionType);
            // Otherwise, return null.
        } else {
            return null;
        }
    }
    // Save a geofence
    public void setGeofence(String id, SimpleGeofence geofence) {
        // Get a SharedPreferences editor instance
        SharedPreferences.Editor editor = mPrefs.edit();
        // Write the Geofence values to SharedPreferences
        editor.putFloat(getGeofenceFieldKey(id, AppParams.KEY_LATITUDE),(float) geofence.getLatitude());
        editor.putFloat(getGeofenceFieldKey(id, AppParams.KEY_LONGITUDE),(float) geofence.getLongitude());
        editor.putFloat(getGeofenceFieldKey(id, AppParams.KEY_RADIUS), geofence.getRadius());
        editor.putLong(getGeofenceFieldKey(id, AppParams.KEY_EXPIRATION_DURATION), geofence.getExpirationDuration());
        editor.putInt(getGeofenceFieldKey(id, AppParams.KEY_TRANSITION_TYPE), geofence.getTransitionType());
        // Commit the changes
        editor.apply();
    }
    public void clearGeofence(String id) {
        // Remove a flattened geofence object from storage by removing all of its keys
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.remove(getGeofenceFieldKey(id, AppParams.KEY_LATITUDE));
        editor.remove(getGeofenceFieldKey(id, AppParams.KEY_LONGITUDE));
        editor.remove(getGeofenceFieldKey(id, AppParams.KEY_RADIUS));
        editor.remove(getGeofenceFieldKey(id, AppParams.KEY_EXPIRATION_DURATION));
        editor.remove(getGeofenceFieldKey(id, AppParams.KEY_TRANSITION_TYPE));
        editor.apply();
    }
    // Given a Geofence object's ID and the name of a field, return the key name of the object's values in SharedPreferences.
    private String getGeofenceFieldKey(String id, String fieldName) {
        return AppParams.KEY_PREFIX + "_" + id + "_" + fieldName;
    }

    // Check if a geofence with the given id exists in the store
    public boolean checkGeoStore(String id){
        if(mPrefs.getString(id,null) != null){
            return true;
        }
        return false;
    }
}