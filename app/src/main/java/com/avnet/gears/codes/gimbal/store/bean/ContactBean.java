package com.avnet.gears.codes.gimbal.store.bean;

import java.util.List;

/**
 * Created by 914889 on 3/13/15.
 */
public class ContactBean {
    private String contactName;
    private List<PhoneNumberType> phoneNumbersList;

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public List<PhoneNumberType> getPhoneNumbersList() {
        return phoneNumbersList;
    }

    public void setPhoneNumbersList(List<PhoneNumberType> phoneNumbersList) {
        this.phoneNumbersList = phoneNumbersList;
    }

    @Override
    public String toString() {
        return "ContactBean{" +
                "contactName='" + contactName + '\'' +
                ", phoneNumbersList=" + phoneNumbersList +
                '}';
    }
}
