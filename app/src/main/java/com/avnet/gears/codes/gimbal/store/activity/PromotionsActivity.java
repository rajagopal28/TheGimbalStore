package com.avnet.gears.codes.gimbal.store.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.GimbalPromotionsDataProcessor;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.HttpConnectionAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;

import java.util.Arrays;
import java.util.Map;

public class PromotionsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        ListView topBrowsedListView = (ListView) findViewById(R.id.top_browsed_list_view);
        ListView bestSellersListView = (ListView) findViewById(R.id.best_sellers_list_view);
        ListView recentlyViewedListView = (ListView) findViewById(R.id.recently_viewed_list_view);
        ListView recommendationsListView = (ListView) findViewById(R.id.recommendations_list_view);

        String selectedBeaconsList = getIntent().getStringExtra(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_BEACONS_ID.toString());
        Log.d("DEBUG", "selectedBeaconsList = " + selectedBeaconsList);
        if (selectedBeaconsList != null) {
            ProgressDialog progressDialog = AndroidUtil.showProgressDialog(this,
                    GimbalStoreConstants.DEFAULT_SPINNER_TITLE,
                    GimbalStoreConstants.DEFAULT_SPINNER_INFO_TEXT);
            GimbalPromotionsDataProcessor gimbalPromotionsDataProcessor = new GimbalPromotionsDataProcessor(this,
                    topBrowsedListView, bestSellersListView,
                    recentlyViewedListView, recommendationsListView, progressDialog);
            Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());


            paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                    GimbalStoreConstants.StoreParameterValues.marketing.toString());
            paramsMap.put(GimbalStoreConstants.StoreParameterKeys.beaconId.toString(),
                    selectedBeaconsList);

            String cookieString = AndroidUtil.getPreferenceString(getApplicationContext(),
                    GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
            // Log.d("DEBUG", paramsMap.toString());
            HttpConnectionAsyncTask handler = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                    Arrays.asList(new String[]{ServerURLUtil.getStoreServletServerURL(getResources())}),
                    Arrays.asList(paramsMap), cookieString,
                    gimbalPromotionsDataProcessor);
            handler.execute(new String[]{});
        }

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
        if (!AndroidUtil.processSettingsAction(this, id)) {
            // do something custom
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
