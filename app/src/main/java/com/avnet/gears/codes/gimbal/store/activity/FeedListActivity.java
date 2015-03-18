package com.avnet.gears.codes.gimbal.store.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.FeedItemListDataProcessor;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.FeedItemProcessor;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY;
import com.avnet.gears.codes.gimbal.store.handler.HttpConnectionAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;

import java.util.Arrays;
import java.util.Map;

public class FeedListActivity extends Activity implements ActionBar.TabListener {


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    private GimbalStoreConstants.FEED_ITEM_TYPE[] feedItemTypes = new GimbalStoreConstants.FEED_ITEM_TYPE[]{
            GimbalStoreConstants.FEED_ITEM_TYPE.FRIEND_RECOMMENDED,
            GimbalStoreConstants.FEED_ITEM_TYPE.FRIEND_REVIEWED,
            GimbalStoreConstants.FEED_ITEM_TYPE.SUGGESTED_PRODUCTS
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_list);

        Bundle bundle = getIntent().getExtras();

        String feedItemId = null, productId = null;
        if (bundle != null) {
            feedItemId = bundle.getString(INTENT_EXTRA_ATTR_KEY.SELECTED_FEED_ID.toString());
            productId = bundle.getString(INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString());
        }


        String cookieString = AndroidUtil.getPreferenceString(getApplicationContext(),
                GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
        Log.d("DEBUG NOTIFY", "getting data from the notification feedItemId ="
                + feedItemId + " productId= " + productId);
        if (feedItemId != null) {
            ProgressDialog progressDialog = AndroidUtil.showProgressDialog(this,
                    GimbalStoreConstants.DEFAULT_SPINNER_TITLE,
                    GimbalStoreConstants.DEFAULT_SPINNER_INFO_TEXT);
            Map<String, String> feedItemParams = ServerURLUtil.getBasicConfigParamsMap(getResources());
            feedItemParams.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                    GimbalStoreConstants.StoreParameterValues.fetchFeeds.toString());
            feedItemParams.put(GimbalStoreConstants.StoreParameterKeys.identifier.toString(),
                    GimbalStoreConstants.StoreParameterValues.top.toString());
            feedItemParams.put(GimbalStoreConstants.StoreParameterKeys.uniqueId.toString(),
                    feedItemId);

            String serverURL = ServerURLUtil.getStoreServletServerURL(getResources());
            Log.d("DEBUG", feedItemParams.toString());
            FragmentManager fm = getFragmentManager();
            FeedItemProcessor feedItemProcessor = new FeedItemProcessor(this, fm, progressDialog, productId);
            HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(
                    GimbalStoreConstants.HTTP_METHODS.GET,
                    Arrays.asList(serverURL), Arrays.asList(feedItemParams),
                    cookieString, feedItemProcessor);
            asyncTask.execute(new String[]{});
        }

        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < feedItemTypes.length; i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(feedItemTypes[i].getItemTypeLabel())
                            .setTabListener(this));
        }
        ProgressDialog progressDialog = AndroidUtil.showProgressDialog(this,
                GimbalStoreConstants.DEFAULT_SPINNER_TITLE,
                GimbalStoreConstants.DEFAULT_SPINNER_INFO_TEXT);
        String urlString = ServerURLUtil.getStoreServletServerURL(getResources());
        Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());
        paramsMap.put(GimbalStoreConstants.StoreParameterKeys.identifier.toString(),
                GimbalStoreConstants.StoreParameterValues.top.toString());
        paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                GimbalStoreConstants.StoreParameterValues.fetchFeeds.toString());

        FeedItemListDataProcessor feedItemListDataProcessor = new FeedItemListDataProcessor(this, mViewPager,
                feedItemTypes, progressDialog);
        HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                Arrays.asList(urlString),
                Arrays.asList(paramsMap),
                cookieString,
                feedItemListDataProcessor);
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
        boolean result = AndroidUtil.processSettingsAction(this, id);

        //noinspection SimplifiableIfStatement
        if (!result) {
            switch (id) {
                case R.id.action_settings:
                    return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

}
