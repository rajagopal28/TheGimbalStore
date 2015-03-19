package com.avnet.gears.codes.gimbal.store.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.service.StoreGimbalDeviceService;

public class StoreBootBroadcastReceiver extends BroadcastReceiver {
    public StoreBootBroadcastReceiver() {
        Log.d("GIMBAL", "instantiating StoreBootBroadcastReceiver");
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d("SERVICE", "in StoreBootBroadcastReceiver.onRecieve()");
        // start the bluetooth broadcast receiver service
        Intent startServiceIntent = new Intent(context, StoreGimbalDeviceService.class);
        context.startService(startServiceIntent);
        Log.d("GIMBAL", "Started Device state service");

    }
}
