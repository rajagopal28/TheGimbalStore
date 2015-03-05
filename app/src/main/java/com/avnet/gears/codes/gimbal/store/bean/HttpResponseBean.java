package com.avnet.gears.codes.gimbal.store.bean;

import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_HEADER_VALUES;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_RESPONSE_CODES;

import java.io.InputStream;

/**
 * Created by 914889 on 3/5/15.
 */
public class HttpResponseBean {
    private HTTP_RESPONSE_CODES responseCode;
    private String responseString;
    private HTTP_HEADER_VALUES responseType;
    private InputStream responseStream;

    public HTTP_RESPONSE_CODES getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(HTTP_RESPONSE_CODES responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public HTTP_HEADER_VALUES getResponseType() {
        return responseType;
    }

    public void setResponseType(HTTP_HEADER_VALUES responseType) {
        this.responseType = responseType;
    }

    public InputStream getResponseStream() {
        return responseStream;
    }

    public void setResponseStream(InputStream responseStream) {
        this.responseStream = responseStream;
    }

    @Override
    public String toString() {
        return "HttpResponseBean{" +
                "responseCode=" + responseCode +
                ", responseString='" + responseString + '\'' +
                ", responseType=" + responseType +
                ", responseStream=" + responseStream +
                '}';
    }
}
