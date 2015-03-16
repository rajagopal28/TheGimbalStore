package com.avnet.gears.codes.gimbal.store.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.activity.FeedListActivity;
import com.avnet.gears.codes.gimbal.store.activity.NotificationsListActivity;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.RecommendationDataProcessor;
import com.avnet.gears.codes.gimbal.store.bean.NotificationActionBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.HttpConnectionAsyncTask;
import com.avnet.gears.codes.gimbal.store.receiver.StoreGcmBroadcastReceiver;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * <p/>
 * helper methods.
 */
public class StoreGcmServiceIntent extends IntentService {

    public StoreGcmServiceIntent() {
        super(StoreGcmServiceIntent.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        // The getMessageType() intent parameter must be the intent you received
        // in your BroadcastReceiver.
        String messageType = gcm.getMessageType(intent);

        if (!extras.isEmpty()) {  // has effect of unparcelling Bundle
        /*
         * Filter messages based on message type. Since it is likely that GCM
         * will be extended in the future with new message types, just ignore
         * any message types you're not interested in, or that you don't
         * recognize.
         */
            if (GoogleCloudMessaging.
                    MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                sendNotification(GoogleCloudMessaging.
                        MESSAGE_TYPE_SEND_ERROR, extras);
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_DELETED.equals(messageType)) {
                sendNotification(GoogleCloudMessaging.
                                MESSAGE_TYPE_DELETED,
                        extras);
                // If it's a regular GCM message, do some work.
            } else if (GoogleCloudMessaging.
                    MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                // This loop represents the service doing some work.
                for (int i = 0; i < 5; i++) {
                    Log.i("DEBUG", "Working... " + (i + 1)
                            + "/5 @ " + SystemClock.elapsedRealtime());
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                    }
                }
                Log.d("DEBUG", "Completed work @ " + SystemClock.elapsedRealtime());
                // Post notification of received message.
                sendNotification(GoogleCloudMessaging.
                        MESSAGE_TYPE_MESSAGE, extras);
                Log.d("DEBUG", "Received: " + extras.toString());
            }
        }
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        StoreGcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // Put the message into a notification and post it.
    // This is just one simple example of what you might choose to do with
    // a GCM message.
    private void sendNotification(String messageType, Bundle extra) {
        String msg = extra.getString(GimbalStoreConstants.notificationDataKey.message.toString());

        String notificationTypeString = extra.getString(GimbalStoreConstants.notificationDataKey.notificationType.toString());
        GimbalStoreConstants.NOTIFICATION_TYPE notificationType = GimbalStoreConstants.NOTIFICATION_TYPE.valueOf(notificationTypeString);


        Intent targetIntent;
        Bundle bundle = new Bundle();
        switch (notificationType) {
            case ASK_FRIEND:
            case ASK_FRIEND_REVIEW:
                String recommendationId = extra.getString(GimbalStoreConstants.notificationDataKey.recommendationId.toString());
                RecommendationDataProcessor recommendationDataProcessor = new RecommendationDataProcessor(getApplicationContext(), msg);
                Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.recommendationId.toString(),
                        recommendationId);
                String targetURL = ServerURLUtil.getStoreServletServerURL(getResources());
                HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                        Arrays.asList(targetURL),
                        Arrays.asList(paramsMap), null,
                        recommendationDataProcessor);
                return;// Notification is sent in the processor class
            case FRIEND_RECOMMENDATION:
            case FRIEND_REVIEW:
                targetIntent = new Intent(this, FeedListActivity.class);
                String feedItemId = extra.getString(GimbalStoreConstants.notificationDataKey.feedItemId.toString());
                String productId = extra.getString(GimbalStoreConstants.notificationDataKey.productId.toString());
                bundle.putString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_FEED_ID.toString(), feedItemId);
                bundle.putString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString(), productId);
                targetIntent.putExtras(bundle);
                break;
            default:
                targetIntent = new Intent(this, NotificationsListActivity.class);
        }
        List<NotificationActionBean> notificationActionBeans = new ArrayList<NotificationActionBean>();
        AndroidUtil.notify(this, targetIntent,
                msg, GimbalStoreConstants.DEFAULT_STORE_NOTIFICATION_TITLE,
                R.drawable.ic_store, true,
                notificationActionBeans);
    }
}
