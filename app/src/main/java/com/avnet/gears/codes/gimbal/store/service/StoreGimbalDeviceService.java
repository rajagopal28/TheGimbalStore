package com.avnet.gears.codes.gimbal.store.service;

import android.app.IntentService;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.receiver.StoreGimbalDeviceBroadcastReceiver;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * helper methods.
 */
public class StoreGimbalDeviceService extends Service {

    private StoreGimbalDeviceBroadcastReceiver deviceBroadcastReceiver;

    public StoreGimbalDeviceService() {
        // super(StoreGimbalDeviceIntentService.class.getName());
        Log.d("GIMBAL", "instantiating StoreGimbalDeviceIntentService");
    }

    @Override
    public void onCreate() {
        deviceBroadcastReceiver = new StoreGimbalDeviceBroadcastReceiver(getApplication());
        registerReceiver(deviceBroadcastReceiver, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        if (deviceBroadcastReceiver != null) {
            Log.d("GIMBAL", "StoreGimbalDeviceIntentService.onBind() deviceBroadcastReceiver != null");
            return deviceBroadcastReceiver.peekService(getApplicationContext(), intent);
        }
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

}
