package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 3/15/15.
 */
public class NotificationDataBean extends BaseServerDataBean {
    private String notificationId;
    private String notificationText;
    private String notificationType;
    private String notificationStatus;

    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationStatus() {
        return notificationStatus;
    }

    public void setNotificationStatus(String notificationStatus) {
        this.notificationStatus = notificationStatus;
    }

    @Override
    public String toString() {
        return super.toString() +
                "NotificationDataBean{" +
                "notificationId='" + notificationId + '\'' +
                ", notificationText='" + notificationText + '\'' +
                ", notificationType='" + notificationType + '\'' +
                ", notificationStatus='" + notificationStatus + '\'' +
                '}';
    }
}
