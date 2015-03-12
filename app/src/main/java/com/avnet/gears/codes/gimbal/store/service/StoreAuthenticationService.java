package com.avnet.gears.codes.gimbal.store.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.avnet.gears.codes.gimbal.store.authenticator.StoreAccountAuthenticator;

public class StoreAuthenticationService extends Service {
    public StoreAuthenticationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // bind authenticator on service bind
        StoreAccountAuthenticator authenticator = new StoreAccountAuthenticator(this);
        return authenticator.getIBinder();
    }
}
