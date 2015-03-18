package com.avnet.gears.codes.gimbal.store.receiver;

import android.app.Application;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.activity.NotificationsListActivity;
import com.gimbal.android.BeaconEventListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.PlaceEventListener;

public class StoreGimbalDeviceBroadcastReceiver extends BroadcastReceiver {
    private Application application;

    //Gimbal
    private PlaceEventListener placeEventListener;
    private BeaconEventListener beaconSightingListener;
    private BeaconManager beaconManager;

    public StoreGimbalDeviceBroadcastReceiver() {
    }

    public StoreGimbalDeviceBroadcastReceiver(Application base) {
        this.application = base;
        Log.d("GIMBAL", "Instantiated StoreGimbalDeviceBroadcastReceiver");
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        final int bluetoothState = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);

        if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(action)) {
            if (bluetoothState == BluetoothAdapter.STATE_TURNING_ON ||
                    bluetoothState == BluetoothAdapter.STATE_ON) {
                // start the service
                Log.d("GIMBAL", "Bluetooth state changed ON =" + bluetoothState);
                Intent mIntent = new Intent(context, NotificationsListActivity.class);
                mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(mIntent);
            }
        } else if (bluetoothState == BluetoothAdapter.STATE_TURNING_OFF ||
                bluetoothState == BluetoothAdapter.STATE_OFF) {
            // Bluetooth is disconnected, do handling here
            Log.d("GIMBAL", "Bluetooth state changed OFF =" + bluetoothState);
            // stop the service
        }
    }
}
