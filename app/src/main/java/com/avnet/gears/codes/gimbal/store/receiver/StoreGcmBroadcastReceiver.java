package com.avnet.gears.codes.gimbal.store.receiver;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.avnet.gears.codes.gimbal.store.service.StoreGcmServiceIntent;

public class StoreGcmBroadcastReceiver extends WakefulBroadcastReceiver {
    public StoreGcmBroadcastReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // Explicitly specify that GcmIntentService will handle the intent.
        ComponentName comp = new ComponentName(context.getPackageName(),
                StoreGcmServiceIntent.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }
}
