package com.avnet.gears.codes.gimbal.store.bean;

import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

/**
 * Created by 914889 on 3/13/15.
 */
public class PhoneNumberType {
    private GimbalStoreConstants.PHONE_NUMBER_TYPE phoneNumberType;
    private String phoneNumber;

    public GimbalStoreConstants.PHONE_NUMBER_TYPE getPhoneNumberType() {
        return phoneNumberType;
    }

    public void setPhoneNumberType(GimbalStoreConstants.PHONE_NUMBER_TYPE phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "PhoneNumberType{" +
                "phoneNumberType=" + phoneNumberType +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
