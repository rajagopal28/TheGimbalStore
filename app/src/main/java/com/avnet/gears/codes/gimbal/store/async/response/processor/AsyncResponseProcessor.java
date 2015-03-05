package com.avnet.gears.codes.gimbal.store.async.response.processor;

import android.graphics.Bitmap;

import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

/**
 * Created by 914889 on 2/25/15.
 */
public interface AsyncResponseProcessor {

    public boolean doProcess(GimbalStoreConstants.HTTP_RESPONSE_CODES responseCode, String responseString);

}
