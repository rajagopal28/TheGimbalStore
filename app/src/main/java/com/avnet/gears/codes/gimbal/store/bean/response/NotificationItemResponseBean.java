package com.avnet.gears.codes.gimbal.store.bean.response;

import com.avnet.gears.codes.gimbal.store.bean.NotificationDataBean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/15/15.
 */
public class NotificationItemResponseBean extends BaseServerResponseBean {
    private NotificationDataBean[] Notifications;

    public NotificationDataBean[] getNotifications() {
        return Notifications;
    }

    public void setNotifications(NotificationDataBean[] notifications) {
        Notifications = notifications;
    }

    @Override
    public String toString() {
        return super.toString() +
                "NotificationItemResponseBean{" +
                "Notifications=" + Arrays.toString(Notifications) +
                '}';
    }
}
