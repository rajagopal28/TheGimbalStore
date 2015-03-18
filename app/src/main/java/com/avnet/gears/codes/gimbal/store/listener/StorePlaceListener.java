package com.avnet.gears.codes.gimbal.store.listener;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.gimbal.android.PlaceEventListener;
import com.gimbal.android.Visit;

import java.util.Date;

/**
 * Created by 914889 on 3/18/15.
 */
public class StorePlaceListener extends PlaceEventListener {
    private Activity activity;

    public StorePlaceListener(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onVisitStart(Visit visit) {
        Log.i("Info:", "Place : Enter: " + visit.getPlace().getName() + ", at: " + new Date(visit.getArrivalTimeInMillis()));
        // This will be invoked when a place is entered. Example below shows a simple log upon enter
        Log.i("Info:", "Enter: " + visit.getPlace().getName() + ", at: " + new Date(visit.getArrivalTimeInMillis()));

        boolean setting = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("example_checkbox", true);
        Toast t = Toast.makeText(activity, "Onvisitstart : " + String.valueOf(setting), Toast.LENGTH_SHORT);
        t.show();
        super.onVisitStart(visit);
    }

    @Override
    public void onVisitEnd(Visit visit) {
        Log.i("Info:", "Place : Exit: " + visit.getPlace().getName() + ", at: " + new Date(visit.getDepartureTimeInMillis()));
        // This will be invoked when a place is exited. Example below shows a simple log upon exit
        Log.i("Info:", "Exit: " + visit.getPlace().getName() + ", at: " + new Date(visit.getDepartureTimeInMillis()));

        boolean setting = PreferenceManager.getDefaultSharedPreferences(activity).getBoolean("example_checkbox", true);
        Toast t = Toast.makeText(activity, "Onvisitend : " + String.valueOf(setting), Toast.LENGTH_SHORT);
        t.show();
        super.onVisitEnd(visit);
    }
}
