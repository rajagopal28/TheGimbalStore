package com.avnet.gears.codes.gimbal.store.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.NotificationListDataProcessor;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.HttpConnectionAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;

import java.util.Arrays;
import java.util.Map;

public class NotificationsListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications_list);
        AndroidUtil.instantiateGimbal(this);
        ListView notificationListView = (ListView) findViewById(R.id.user_notification_list_view);
        ListView promotionsListView = (ListView) findViewById(R.id.promotions_list_view);
        ProgressDialog progressDialog = AndroidUtil.showProgressDialog(this,
                GimbalStoreConstants.DEFAULT_SPINNER_TITLE,
                GimbalStoreConstants.DEFAULT_SPINNER_INFO_TEXT);
        NotificationListDataProcessor notificationListDataProcessor = new NotificationListDataProcessor(this,
                notificationListView, promotionsListView,
                progressDialog);

        String urlString = ServerURLUtil.getStoreServletServerURL(getResources());
        String cookieString = AndroidUtil.getPreferenceString(getApplicationContext(),
                GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
        Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());
        paramsMap.put(GimbalStoreConstants.StoreParameterKeys.identifier.toString(),
                GimbalStoreConstants.StoreParameterValues.top.toString());
        paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                GimbalStoreConstants.StoreParameterValues.fetchNotifications.toString());
        HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                Arrays.asList(urlString),
                Arrays.asList(paramsMap),
                cookieString,
                notificationListDataProcessor);
        asyncTask.execute(new String[]{});
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_global, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
