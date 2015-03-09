package com.avnet.gears.codes.gimbal.store.bean;

import android.graphics.Bitmap;

import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

/**
 * Created by 914889 on 3/5/15.
 */
public class ResponseItemBean {
    private GimbalStoreConstants.HTTP_HEADER_VALUES contentType;
    private String responseString;
    private Bitmap imageBmp;
    private GimbalStoreConstants.HTTP_RESPONSE_CODES responseCode;

    public GimbalStoreConstants.HTTP_HEADER_VALUES getContentType() {
        return contentType;
    }

    public void setContentType(GimbalStoreConstants.HTTP_HEADER_VALUES contentType) {
        this.contentType = contentType;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public Bitmap getImageBmp() {
        return imageBmp;
    }

    public void setImageBmp(Bitmap imageBmp) {
        this.imageBmp = imageBmp;
    }

    public GimbalStoreConstants.HTTP_RESPONSE_CODES getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(GimbalStoreConstants.HTTP_RESPONSE_CODES responseCode) {
        this.responseCode = responseCode;
    }

    @Override
    public String toString() {
        return "ResponseItemBean{" +
                "contentType=" + contentType +
                ", responseString='" + responseString + '\'' +
                ", imageBmp=" + imageBmp +
                ", responseCode=" + responseCode +
                '}';
    }
}
