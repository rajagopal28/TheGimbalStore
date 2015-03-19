package com.avnet.gears.codes.gimbal.store.utils;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.activity.FeedListActivity;
import com.avnet.gears.codes.gimbal.store.activity.NotificationsListActivity;
import com.avnet.gears.codes.gimbal.store.activity.UserSettingsActivity;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.ContactsSyncProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ContactBean;
import com.avnet.gears.codes.gimbal.store.bean.NotificationActionBean;
import com.avnet.gears.codes.gimbal.store.bean.PhoneNumberBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.HttpConnectionAsyncTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*import com.avnet.gears.codes.gimbal.store.listener.StoreBeaconEventListener;
import com.avnet.gears.codes.gimbal.store.listener.StorePlaceListener;
import com.gimbal.android.BeaconManager;
import com.gimbal.android.Gimbal;
import com.gimbal.android.PlaceManager;*/

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
        if (isFirstTimeOpen) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(GimbalStoreConstants.PREF_IS_FIRST_TIME_OPEN, false);
            editor.commit();
            Log.d("DEBUG", "PREF_IS_FIRST_TIME_OPEN = " + sharedPreferences.getBoolean(GimbalStoreConstants.PREF_IS_FIRST_TIME_OPEN, true));
        }
        return isFirstTimeOpen;
    }

    public static String getGCMDeviceId(Context context) {
        return "";

    }

    public static List<ContactBean> getDeviceContacts(Activity callerActivity) {
        List<ContactBean> contactBeanList = new ArrayList<ContactBean>();
        Cursor c = callerActivity.getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        String name;
        String id;
        c.moveToFirst();
        for (int i = 0; i < c.getCount(); i++) {
            name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
            id = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
            ContactBean contactBean = new ContactBean();
            contactBean.setContactName(name);

            if (Integer.parseInt(c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                Cursor pCur = callerActivity.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id},
                        null);
                List<PhoneNumberBean> phoneNumbers = new ArrayList<PhoneNumberBean>();
                while (pCur.moveToNext()) {
                    PhoneNumberBean phoneNumber = new PhoneNumberBean();

                    String phoneNumberString = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    phoneNumberString = phoneNumberString.replace(GimbalStoreConstants.DELIMITER_OPEN_PARENTHESIS, "").
                            replace(GimbalStoreConstants.DELIMITER_CLOSE_PARENTHESIS, "").
                            replace(GimbalStoreConstants.DELIMITER_HYPHEN, "").
                            replace(GimbalStoreConstants.DELIMITER_SPACE, "");
                    phoneNumber.setPhoneNumber(phoneNumberString);
                    phoneNumber.setPhoneNumberType(GimbalStoreConstants.PHONE_NUMBER_TYPE.getNumberType(pCur.getInt(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.TYPE))));
                    phoneNumbers.add(phoneNumber);
                }
                contactBean.setPhoneNumbersList(phoneNumbers);
                contactBeanList.add(contactBean);
            }
            c.moveToNext();
        }
        c.close();
        return contactBeanList;
    }

    public static boolean processSettingsAction(Activity parentActivity, int selectedAction) {
        boolean returnFlag;
        switch (selectedAction) {
            case R.id.action_sync_contacts:
                Log.d("DEBUG", "In action setting Sync Contacts");
                ProgressDialog progressDialog = showProgressDialog(parentActivity,
                        GimbalStoreConstants.SYNC_CONTACTS_SPINNER_TITLE,
                        GimbalStoreConstants.SYNC_CONTACTS_SPINNER_INFO_TEXT);
                List<ContactBean> contacts = AndroidUtil.getDeviceContacts(parentActivity);
                Log.d("DEBUG", "Retrieved Contacts : " + contacts);
                List<String> allContactsFormatString = new ArrayList<String>();
                for (ContactBean contact : contacts) {
                    String contactName = contact.getContactName();
                    List<PhoneNumberBean> phoneNumberList = contact.getPhoneNumbersList();
                    if (phoneNumberList != null) {
                        for (PhoneNumberBean phoneNumber : phoneNumberList) {
                            String contactParam = contactName
                                    + GimbalStoreConstants.DELIMITER_COLAN
                                    + phoneNumber.getPhoneNumber();
                            allContactsFormatString.add(contactParam);
                        }
                    }

                }
                // construct params Map and async task
                List<String> urlsList = new ArrayList<String>();
                List<Map<String, String>> paramsMapList = new ArrayList<Map<String, String>>();
                String cookieString = AndroidUtil.getPreferenceString(parentActivity.getApplicationContext(),
                        GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
                String urlString = ServerURLUtil.getStoreServletServerURL(parentActivity.getResources());

                int subIndex = 0;
                String contactValues = "";

                for (String paramStringValue : allContactsFormatString) {
                    if (!contactValues.isEmpty()) {
                        contactValues += GimbalStoreConstants.DELIMITER_COMMA;
                    }
                    contactValues += paramStringValue;
                    if (subIndex == GimbalStoreConstants.CONTACTS_UPLOAD_CHUNK_LIMIT - 1 ||
                            subIndex == allContactsFormatString.size() - 1) {
                        Map<String, String> contactParam = ServerURLUtil.getBasicConfigParamsMap(parentActivity.getResources());
                        contactParam.put(GimbalStoreConstants.StoreParameterKeys.friends.toString(),
                                contactValues);
                        contactParam.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                                GimbalStoreConstants.StoreParameterValues.createNetwork.toString());
                        urlsList.add(new String(urlString));
                        Log.d("DEBUG", "contactParam=" + contactParam);
                        paramsMapList.add(contactParam);
                        subIndex = 0;
                        contactValues = "";
                    }
                    subIndex++;
                }
                Log.d("DEBUG", "List of contact params Maps = " + paramsMapList);
                ContactsSyncProcessor contactsProcessor = new ContactsSyncProcessor(parentActivity, progressDialog);

                HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                        urlsList, paramsMapList,
                        cookieString, contactsProcessor);
                asyncTask.execute(new String[]{});
                progressDialog.dismiss();
                returnFlag = true;
                break;
            case R.id.action_settings:
                Log.d("DEBUG", "In action setting --");
                Intent intent = new Intent(parentActivity, UserSettingsActivity.class);
                parentActivity.startActivity(intent);
                returnFlag = true;
                break;
            case R.id.action_about:
                Log.d("DEBUG", "In action setting About");
                returnFlag = true;
                break;
            case R.id.action_feed_list:
                Log.d("DEBUG", "In action setting About");
                intent = new Intent(parentActivity, FeedListActivity.class);
                parentActivity.startActivity(intent);
                returnFlag = true;
                break;
            case R.id.action_notifications:
                Log.d("DEBUG", "In action setting About");
                intent = new Intent(parentActivity, NotificationsListActivity.class);
                parentActivity.startActivity(intent);
                returnFlag = true;
                break;
            case android.R.id.home:
                Log.d("DEBUG", "Clicked back button");
                parentActivity.onBackPressed();
                return true;
            default:
                Log.d("DEBUG", "In action setting default");
                returnFlag = false;

        }
        return returnFlag;
    }

    public static void savePreferenceValue(Context context, String preferenceKey, String preferenceValue) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        if (preferenceValue == null) {
            // remove the preference
            editor.remove(preferenceKey);
        } else {
            // else update it
            editor.putString(preferenceKey, preferenceValue);
        }
        editor.commit();
    }

    public static String getPreferenceString(Context context, String preferenceKey) {
        String preferenceValue;
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferenceValue = preferences.getString(preferenceKey, null);
        return preferenceValue;
    }

    public static void setDynamicHeight(ListView mListView) {
        ListAdapter mListAdapter = mListView.getAdapter();
        if (mListAdapter == null) {
            // when adapter is null
            return;
        }
        int height = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
        for (int i = 0; i < mListAdapter.getCount(); i++) {
            View listItem = mListAdapter.getView(i, null, mListView);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            height += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = mListView.getLayoutParams();
        params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
        mListView.setLayoutParams(params);
        mListView.requestLayout();
    }

    /*public static void instantiateGimbal(Activity activity) {
        Gimbal.setApiKey(activity.getApplication(), activity.getResources().getString(R.string.GIMBAL_APPLICATION_ID));
        Log.d("GIMBAL", "Registering application.." + activity.getResources().getString(R.string.GIMBAL_APPLICATION_ID));
        StorePlaceListener placeEventListener = new StorePlaceListener(activity);
        PlaceManager.getInstance().addListener(placeEventListener);
        StoreBeaconEventListener beaconSightingListener = new StoreBeaconEventListener();
        BeaconManager beaconManager = new BeaconManager();
        beaconManager.addListener(beaconSightingListener);

        PlaceManager.getInstance().startMonitoring();
        beaconManager.startListening();

        Log.d("GIMBAL", "registering receiver");
    }*/
}
