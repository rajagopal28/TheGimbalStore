package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.widget.ImageView;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.HttpResponseBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_RESPONSE_CODES;

/**
 * Created by 914889 on 3/4/15.
 */
public class SubcategoryListProcessor implements AsyncResponseProcessor {

    private ImageView imageView;

    public SubcategoryListProcessor(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public boolean doProcess(HttpResponseBean httpResponseBean) {
        String responseString = httpResponseBean.getResponseString();
        HTTP_RESPONSE_CODES responseCode = httpResponseBean.getResponseCode();
        responseString = responseString.trim()
                .replace(GimbalStoreConstants.START_COMMENT_STRING, "")
                .replace(GimbalStoreConstants.END_COMMENT_STRING, "");
        if(responseCode == HTTP_RESPONSE_CODES.OK ||
                responseCode == HTTP_RESPONSE_CODES.CREATED ||
                responseCode == HTTP_RESPONSE_CODES.ACCEPTED) {

            return true;
        }
        return false;
    }
}
