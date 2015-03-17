package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;
import java.util.List;

/**
 * Created by 914889 on 3/12/15.
 */
public class GCMDeviceIdProcessor implements AsyncResponseProcessor {
    private Activity callingActivity;
    private int requestCode;
    private Intent targetIntent;
    private String senderId;
    private GoogleCloudMessaging googleCloudMessaging;

    public GCMDeviceIdProcessor(Activity callerActivity, String senderId,
                                int requestCode, Intent targetIntent) {
        this.callingActivity = callerActivity;
        this.requestCode = requestCode;
        this.targetIntent = targetIntent;
        this.senderId = senderId;
        this.googleCloudMessaging = GoogleCloudMessaging.getInstance(callerActivity);
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeansList) {
        try {
            String deviceId = googleCloudMessaging.register(this.senderId);
            Bundle targetBundle = this.targetIntent.getExtras();
            Log.d("DEBUG", "deviceId = " + deviceId + "senderId= " + senderId);
            // save the device id in preference
            AndroidUtil.savePreferenceValue(callingActivity.getApplicationContext(),
                    GimbalStoreConstants.PREF_GCM_DEVICE_ID, deviceId);
            targetBundle.putString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.GIVEN_GCM_DEVICE_ID.toString(), deviceId);
            this.targetIntent.putExtras(targetBundle);
            this.callingActivity.startActivityForResult(this.targetIntent, this.requestCode);
            return true;
        } catch (IOException ioe) {
            Log.e("ERROR", ioe.getMessage(), ioe);
        }
        return false;
    }
}
