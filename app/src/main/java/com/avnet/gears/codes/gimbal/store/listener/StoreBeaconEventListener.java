package com.avnet.gears.codes.gimbal.store.listener;

import android.util.Log;

import com.gimbal.android.Beacon;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconSighting;

import java.util.Date;

/**
 * Created by 914889 on 3/18/15.
 */
public class StoreBeaconEventListener extends BeaconEventListener {
    @Override
    public void onBeaconSighting(BeaconSighting sighting) {

        Log.i("Info:", "BeaconSighting");

        Log.i("Info:", "RSSI : " + sighting.getRSSI() + "\nTime in Millis : " + new Date(sighting.getTimeInMillis()));

        Beacon beacon = sighting.getBeacon();
        Log.i("Info:", "Beacon Name : " + beacon.getName() +
                "\nIdentifier : " + beacon.getIdentifier() +
                "\nBattery Level : " + beacon.getBatteryLevel() +
                "\nBeacon Temperature : " + beacon.getTemperature());
        Log.i("INFO", sighting.toString());
    }
}
