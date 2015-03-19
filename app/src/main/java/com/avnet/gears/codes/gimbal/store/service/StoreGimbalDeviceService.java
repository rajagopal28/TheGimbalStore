package com.avnet.gears.codes.gimbal.store.service;

import android.app.IntentService;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.R;
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
    private boolean enteredRegion = false;

    public StoreGimbalDeviceService() {
        // super(StoreGimbalDeviceIntentService.class.getName());
        Log.d("GIMBAL", "StoreGimbalDeviceService instantiating StoreGimbalDeviceIntentService");
    }

    @Override
    public IBinder onBind(Intent intent) {
        beaconManager = BeaconManager.getInstanceForApplication(this.getApplicationContext());
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
            final double DEVICE_CIRCLE_DISTANCE_LIMIT = getResources().getInteger(R.integer.GIMBAL_BEACON_SIGHTING_DISTANCE);
            try {
                beaconManager.setRangeNotifier(new RangeNotifier() {
                    @Override
                    public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {
                        try {
                            if (beacons.size() > 0) {
                                // Log.d("DEBUG", "beaconEncounterSecond = " + beaconEncounterSeconds);
                                String deviceIdList = "";
                                for (Beacon beacon : beacons) {
                                    Log.d("GIMBAL", "Entered region flag : " + enteredRegion + " beacon " + beacon.toString() + " is about " + beacon.getDistance() + " meters away.");

                                    if ((beacon.getDistance() <= DEVICE_CIRCLE_DISTANCE_LIMIT) && !enteredRegion) {
                                        Log.d("GIMBAL", "*********Entered region since the beacon is within 1 meter range : " + beacon.getDistance());
                                        enteredRegion = true;
                                        /*GimbalPromotionsDataProcessor gpDataProcessor = new GimbalPromotionsDataProcessor(this,
                                                null, null,
                                                null, null,
                                                null);
                                        if (!deviceIdList.isEmpty()) {
                                            deviceIdList += ",";
                                        }
                                        String deviceId = beacon.getId1()
                                                + GimbalStoreConstants.DELIMITER_HYPHEN
                                                + beacon.getId2()
                                                + GimbalStoreConstants.DELIMITER_HYPHEN
                                                + beacon.getId3();
                                        deviceIdList += deviceId;
                                        Log.d("DEBUG", "Server URL = " + ServerURLUtil.getStoreServletServerURL(getResources()));
                                        Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());


                                        paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                                                GimbalStoreConstants.StoreParameterValues.marketing.toString());
                                        paramsMap.put(GimbalStoreConstants.StoreParameterKeys.beaconId.toString(),
                                                deviceIdList);

                                        String cookieString = AndroidUtil.getPreferenceString(getApplicationContext(),
                                                GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
                                        Log.d("DEBUG", paramsMap.toString());
                                        HttpConnectionAsyncTask handler = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                                                Arrays.asList(new String[]{ServerURLUtil.getStoreServletServerURL(getResources())}),
                                                Arrays.asList(paramsMap), cookieString,
                                                gpDataProcessor);
                                        handler.execute(new String[]{});*/
                                    }

                                    if (enteredRegion && (beacon.getDistance() > DEVICE_CIRCLE_DISTANCE_LIMIT)) {
                                        Log.d("GIMBAL", "*********Exited region because the distance is greater than 1 : " + + beacon.getDistance());
                                        enteredRegion = false;
                                    }
                                }

                            }
                            //beaconEncounterSeconds++;
                        } catch (Exception e) {
                            Log.e("ERROR", e.getMessage(), e);
                        }

                    }
                });
                String uuid = getResources().getString(R.string.GIMBAL_DEVICE_UUID);
                beaconManager.startRangingBeaconsInRegion(new Region("myRangingUniqueId", Identifier.parse(uuid), null, null));
            } catch (RemoteException e) {
                Log.e("ERROR", e.getMessage(), e);
            }
        } else {
            Log.d("GIMBAL", "Beacon manager null");
        }
        // onResume
        //if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(false);

        // onPause
        // if (beaconManager.isBound(this)) beaconManager.setBackgroundMode(true);

    }
}
