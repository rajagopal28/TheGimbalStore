package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 3/13/15.
 */
public class ContactsSyncDataBean extends BaseServerDataBean {
    private int numberOfSyncedContacts;
    private String userId;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getNumberOfSyncedContacts() {
        return numberOfSyncedContacts;
    }

    public void setNumberOfSyncedContacts(int numberOfSyncedContacts) {
        this.numberOfSyncedContacts = numberOfSyncedContacts;
    }

    @Override
    public String toString() {
        return super.toString() + "ContactsSyncDataBean{" +
                "numberOfSyncedContacts=" + numberOfSyncedContacts +
                ", userId='" + userId + '\'' +
                '}';
    }
}
