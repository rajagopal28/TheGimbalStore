package com.avnet.gears.codes.gimbal.store.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.Identifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;

import java.util.Collection;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class StoreGimbalDeviceService extends Service implements BeaconConsumer {
    private BeaconManager beaconManager;
    private int beaconEncounterSeconds = 10;
    private int BEACON_SIGHTING_INTERVAL_SECONDS;

    public StoreGimbalDeviceService() {
        // super(StoreGimbalDeviceIntentService.class.getName());
        Log.d("GIMBAL", "StoreGimbalDeviceService instantiating StoreGimbalDeviceIntentService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        beaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
        BEACON_SIGHTING_INTERVAL_SECONDS = getResources().getInteger(R.integer.GIMBAL_BEACON_SIGHTING_INTERVAL);
        if (AndroidUtil.verifyBluetooth(this.getApplicationContext(), beaconManager)) {
            Log.d("GIMBAL", "StoreGimbalDeviceService Bluetooth no issues..");
            beaconManager.bind(this);
        }
        Log.d("DEBUG", "StoreGimbalDeviceService Instantiating BeaconManager in Service");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("DEBUG", "StoreGimbalDeviceService Inside StoreGimbalDeviceService.onDestroy()");
        beaconManager.unbind(this);
    }

    @Override
    public void onBeaconServiceConnect() {
        if (beaconManager != null) {
            beaconManager.setRangeNotifier(new RangeNotifier() {
                @Override
                public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                    if (beaconEncounterSeconds % BEACON_SIGHTING_INTERVAL_SECONDS == 0 &&
                            beacons.size() > 0) {
                        String deviceIdList = "";
                        for (Beacon beacon : beacons) {
                            Log.d("GIMBAL", "StoreGimbalDeviceService beacon " + beacon.toString() + " is about " + beacon.getDistance() + " meters away.");
                            if (!deviceIdList.isEmpty()) {
                                deviceIdList += ",";
                            }
                            String deviceId = beacon.getId1()
                                    + GimbalStoreConstants.DELIMITER_HYPHEN
                                    + beacon.getId2()
                                    + GimbalStoreConstants.DELIMITER_HYPHEN
                                    + beacon.getId3();
                            deviceIdList += deviceId;
                        }
                    }
                }
            });

            try {
                String uuid = getResources().getString(R.string.GIMBAL_DEVICE_UUID);
                beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", Identifier.parse(uuid), null, null));
            } catch (RemoteException e) {
                Log.e("GIMBAL", e.getMessage(), e);
            }
        } else {
            Log.d("GIMBAL", "StoreGimbalDeviceService Beacon manager null");
        }
        // onResume
        //if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);

        // onPause
        // if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(true);

    }
}
