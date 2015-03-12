package com.avnet.gears.codes.gimbal.store.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.avnet.gears.codes.gimbal.store.bean.NotificationActionBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

import java.util.List;

/**
 * Created by 914889 on 2/27/15.
 */
public class AndroidUtil {

    public static void notify(Context context, Intent targetIntent,
                              String contentText, String notificationTitle, int resourceIcon,
                              boolean autoCancel, List<NotificationActionBean> actionsBeanList) {
        // prepare intent which is triggered if the
        // notification is selected

        PendingIntent pIntent = PendingIntent.getActivity(context, 0, targetIntent, 0);

        // build notification
        // the addAction re-use the same intent to keep the example short
        Notification.Builder builder = new Notification.Builder(context)
                .setContentTitle(notificationTitle)
                .setContentText(contentText)
                .setSmallIcon(resourceIcon)
                .setContentIntent(pIntent)
                .setAutoCancel(autoCancel);
        // dynamically add sub actions, if any
        if (actionsBeanList != null) {
            for (NotificationActionBean actionBean : actionsBeanList) {
                builder.addAction(actionBean.getDrawableIcon(),
                        actionBean.getActionTitle(),
                        actionBean.getTargetActionPendingIntent());
            }
        }

        // build to notifications once we set all teh parameters
        Notification n = builder.build();

        // create a notification manager from the calling context
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // send notification
        notificationManager.notify(0, n);
    }


        /*
        Log.d("DEBUG NOTIFY", "Sending notification from Home Activity");
        Intent targetIntent = new Intent(getApplicationContext(), FeedListActivity.class);
        targetIntent.putExtra(INTENT_EXTRA_ATTR_KEY.SELECTED_NOTIFICATION_ID.toString(), "notify_id_111");
        NotificationUtil.notify(this, targetIntent, "Home Notification", " Notification test from home activity",
                R.drawable.ic_drawer,true, null);
        Log.d("DEBUG NOTIFY", "End of notification sending from Home Activity");
        */


    public static ProgressDialog showProgressDialog(Context context, String titleText, String processingText) {
        ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle(titleText);
        progress.setMessage(processingText);
        progress.show();
        return progress;
    }

    public static boolean checkFirsTimeOpen(Context context) {
        boolean isFirstTimeOpen = true;
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        // if no value then first time
        isFirstTimeOpen = sharedPreferences.getBoolean(GimbalStoreConstants.PREF_IS_FIRST_TIME_OPEN, true);
        // update the preference if first time
        if (isFirstTimeOpen)
            sharedPreferences.edit().putBoolean(GimbalStoreConstants.PREF_IS_FIRST_TIME_OPEN, false);
        return isFirstTimeOpen;
    }

    public static String getGCMDeviceId(Context context) {
        return "";

    }
}
