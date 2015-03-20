package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.NotificationDataBean;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.bean.response.NotificationItemResponseBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.avnet.gears.codes.gimbal.store.utils.TypeConversionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 914889 on 3/15/15.
 */
public class NotificationListDataProcessor implements AsyncResponseProcessor {

    private Activity parentActivity;
    private ListView friendNotificationsListView;
    private ProgressDialog progressDialog;

    public NotificationListDataProcessor(Activity callingActivity, ListView friendNotificationsList,
                                         ProgressDialog dialog) {
        this.parentActivity = callingActivity;
        this.friendNotificationsListView = friendNotificationsList;
        this.progressDialog = dialog;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeansList) {
        if (responseItemBeansList.size() == 1) {
            ResponseItemBean httpResponseBean = responseItemBeansList.get(0);
            String responseString = httpResponseBean.getResponseString();
            GimbalStoreConstants.HTTP_RESPONSE_CODES responseCode = httpResponseBean.getResponseCode();
            Log.d("PROCESS DEBUG", "" + responseCode);

            if (responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.OK ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.CREATED ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.ACCEPTED) {
                // creating the gson parser with html escaping disabled
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                Log.d("DEBUG", "responseString =" + responseString);

                JsonReader reader = new JsonReader(new StringReader(responseString));
                reader.setLenient(true);
                NotificationItemResponseBean responseBean = gson.fromJson(reader, NotificationItemResponseBean.class);
                Log.d("HTTP DEBUG", " Response Bean = " + responseBean);
                NotificationDataBean[] notificationItemsArray = responseBean.getNotifications();
                final List<NotificationDataBean> notificationItemBeans = Arrays.asList(notificationItemsArray);
                final List<NotificationDataBean> friendNotificationList = new ArrayList<NotificationDataBean>();
                if (notificationItemsArray != null && notificationItemsArray.length > 0) {
                    for (NotificationDataBean notificationItem : notificationItemBeans) {
                        if (notificationItem.getType() != null) {
                            GimbalStoreConstants.NOTIFICATION_TYPE notificationType = GimbalStoreConstants.NOTIFICATION_TYPE.valueOf(notificationItem.getType());
                            switch (notificationType) {
                                case RECOMMENDED:
                                case REVIEWED:
                                    friendNotificationList.add(notificationItem);
                                    break;
                                default:
                                    // ignore the item

                            }
                        }
                    }
                }
                Log.d("DEBUG", "friendNotificationList = " + friendNotificationList);
                parentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        List<String> friendNotificationsTitles = new ArrayList<String>(TypeConversionUtil.getNotificationTitles(friendNotificationList));
                        if (!friendNotificationsTitles.isEmpty()) {

                            ArrayAdapter<String> friendNotificationAdapter = new ArrayAdapter<String>(parentActivity, android.R.layout.simple_list_item_1, friendNotificationsTitles);
                            friendNotificationsListView.setAdapter(friendNotificationAdapter);
                            friendNotificationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    Log.d("DEBUG", "Notification item clicked = " + position + " value = " + friendNotificationList.get(position));
                                }
                            });
                            friendNotificationsListView.refreshDrawableState();
                        }
                        if (!friendNotificationsTitles.isEmpty())
                            AndroidUtil.setDynamicHeight(friendNotificationsListView);

                        if (progressDialog != null)
                            progressDialog.dismiss();
                    }
                });
            }
            progressDialog.dismiss();
            return true;
        }
        return false;
    }
}
