package com.avnet.gears.codes.gimbal.store.bean;

import android.app.PendingIntent;

/**
 * Created by 914889 on 2/27/15.
 */
public class NotificationActionBean {
    private int drawableIcon;
    private String actionTitle;
    private PendingIntent targetActionPendingIntent;

    public int getDrawableIcon() {
        return drawableIcon;
    }

    public void setDrawableIcon(int drawableIcon) {
        this.drawableIcon = drawableIcon;
    }

    public String getActionTitle() {
        return actionTitle;
    }

    public void setActionTitle(String actionTitle) {
        this.actionTitle = actionTitle;
    }

    public PendingIntent getTargetActionPendingIntent() {
        return targetActionPendingIntent;
    }

    public void setTargetActionPendingIntent(PendingIntent targetActionPendingIntent) {
        this.targetActionPendingIntent = targetActionPendingIntent;
    }

    @Override
    public String toString() {
        return "NotificationActionBean{" +
                "drawableIcon=" + drawableIcon +
                ", actionTitle='" + actionTitle + '\'' +
                ", targetActionPendingIntent=" + targetActionPendingIntent +
                '}';
    }
}
