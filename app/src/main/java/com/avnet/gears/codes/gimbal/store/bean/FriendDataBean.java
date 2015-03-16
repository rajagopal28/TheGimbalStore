package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 3/16/15.
 */
public class FriendDataBean extends BaseServerDataBean {
    private String userId;
    private String friendUserId;
    private String friendContactName;
    private String friendPhoneNumber;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendUserId() {
        return friendUserId;
    }

    public void setFriendUserId(String friendUserId) {
        this.friendUserId = friendUserId;
    }

    public String getFriendContactName() {
        return friendContactName;
    }

    public void setFriendContactName(String friendContactName) {
        this.friendContactName = friendContactName;
    }

    public String getFriendPhoneNumber() {
        return friendPhoneNumber;
    }

    public void setFriendPhoneNumber(String friendPhoneNumber) {
        this.friendPhoneNumber = friendPhoneNumber;
    }

    @Override
    public String toString() {
        return super.toString() +
                "FriendDataBean{" +
                "userId='" + userId + '\'' +
                ", friendUserId='" + friendUserId + '\'' +
                ", friendContactName='" + friendContactName + '\'' +
                ", friendPhoneNumber='" + friendPhoneNumber + '\'' +
                '}';
    }
}
