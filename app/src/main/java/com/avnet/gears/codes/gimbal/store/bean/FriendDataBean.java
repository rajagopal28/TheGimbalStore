package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 3/16/15.
 */
public class FriendDataBean extends BaseServerDataBean {
    private String UserId;
    private String Name;
    private String PhoneNumber;

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void setName(String name) {
        Name = name;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return super.toString() +
                "FriendDataBean{" +
                "userId='" + UserId + '\'' +
                ", friendContactName='" + Name + '\'' +
                ", friendPhoneNumber='" + PhoneNumber + '\'' +
                '}';
    }
}
