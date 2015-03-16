package com.avnet.gears.codes.gimbal.store.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.GCMDeviceIdProcessor;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.GenericAsyncTask;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * Created by 914889 on 3/12/15.
 */
public class GCMAccountUtil {
    public static final String PROPERTY_REG_ID = "registration_id";
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private static final String PROPERTY_APP_VERSION = "appVersion";

    /**
     * Check the device to make sure it has the Google Play Services APK. If
     * it doesn't, display a dialog that allows users to download the APK from
     * the Google Play Store or enable it in the device's system settings.
     */
    private static boolean checkPlayServices(Activity callerActivity) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(callerActivity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, callerActivity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                Log.d("DEBUG", "This device is not supported.");
                callerActivity.finish();
            }
            return false;
        }
        return true;
    }

    public static void checkAndRegisterDeviceTOGCM(Activity callerActivity, String senderId,
                                                   Intent intent, int requestCode) {
        if (checkPlayServices(callerActivity)) {
            String gcmDeviceId = "";
            gcmDeviceId = AndroidUtil.getPreferenceString(callerActivity.getApplicationContext(),
                    GimbalStoreConstants.PREF_GCM_DEVICE_ID);
            if (!gcmDeviceId.isEmpty()) {
                Bundle targetBundle = intent.getExtras();
                Log.d("DEBUG", "deviceId = " + gcmDeviceId + "senderId= " + senderId);
                targetBundle.putString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.GIVEN_GCM_DEVICE_ID.toString(), gcmDeviceId);
                intent.putExtras(targetBundle);
                callerActivity.startActivityForResult(intent, requestCode);
            } else {
                // create some processor and pass this intent with request code
                GCMDeviceIdProcessor gcmDeviceIdProcessor = new GCMDeviceIdProcessor(callerActivity, senderId,
                        requestCode, intent);
                // create an async task
                GenericAsyncTask asyncTask = new GenericAsyncTask(gcmDeviceIdProcessor);
                asyncTask.execute();
            }
        } else {
            Log.d("DEBUG", "checking Device Id from GCM");
        }
    }

    /**
     * @return Application's version code from the {@code PackageManager}.
     */
    private static int getAppVersion(Context context) {
        try {
            if (context.getPackageManager() != null && context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0) != null) {
                PackageInfo packageInfo = context.getPackageManager()
                        .getPackageInfo(context.getPackageName(), 0);
                return packageInfo.versionCode;
            }


        } catch (PackageManager.NameNotFoundException e) {
            // should never happen
            // throw new RuntimeException("Could not get package name: " + e);
            Log.e("ERROR", e.getMessage(), e);
        }
        return 1;
    }

    /**
     * Gets the current registration ID for application on GCM service.
     * <p/>
     * If result is empty, the app needs to register.
     *
     * @return registration ID, or empty string if there is no existing
     * registration ID.
     */
    private String getRegistrationId(Activity callerActivity) {
        final SharedPreferences prefs = getGCMPreferences(callerActivity);
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            Log.i("DEBUG", "Registration not found.");
            return "";
        }
        // Check if app was updated; if so, it must clear the registration ID
        // since the existing registration ID is not guaranteed to work with
        // the new app version.
        int registeredVersion = prefs.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = getAppVersion(callerActivity);
        if (registeredVersion != currentVersion) {
            Log.i("DEBUG", "App version changed.");
            return "";
        }
        return registrationId;
    }

    /**
     * @return Application's {@code SharedPreferences}.
     */
    private SharedPreferences getGCMPreferences(Activity callerActivity) {
        // This sample app persists the registration ID in shared preferences, but
        // how you store the registration ID in your app is up to you.
        return callerActivity.getSharedPreferences(callerActivity.getClass().getSimpleName(),
                Context.MODE_PRIVATE);
    }

    /**
     * Sends the registration ID to your server over HTTP, so it can use GCM/HTTP
     * or CCS to send messages to your app. Not needed for this demo since the
     * device sends upstream messages to a server that echoes back the message
     * using the 'from' address in the message.
     */
    private void sendRegistrationIdToBackend() {
        // Your implementation here.
    }

    /**
     * Stores the registration ID and app versionCode in the application's
     * {@code SharedPreferences}.
     *
     * @param callerActivity application's Activity.
     * @param regId          registration ID
     */
    public void storeRegistrationId(Activity callerActivity, String regId) {
        final SharedPreferences prefs = getGCMPreferences(callerActivity);
        int appVersion = getAppVersion(callerActivity);
        Log.i("DEBUG", "Saving regId on app version " + appVersion);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(PROPERTY_REG_ID, regId);
        editor.putInt(PROPERTY_APP_VERSION, appVersion);
        editor.commit();
    }
}
