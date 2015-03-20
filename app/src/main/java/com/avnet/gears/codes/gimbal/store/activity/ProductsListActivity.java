package com.avnet.gears.codes.gimbal.store.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.FriendsListDataProcessor;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.PostReviewsProcessor;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.ProductsListProcessor;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.fragment.dialog.FriendsListDialogFragment;
import com.avnet.gears.codes.gimbal.store.handler.HttpConnectionAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProductsListActivity extends Activity implements FriendsListDialogFragment.FriendListSelectListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle intentBundle = getIntent().getExtras();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        final Activity mActivity = this;

        setContentView(R.layout.activity_products_list);
        if (intentBundle != null) {
            String selectedSubCategoryId = intentBundle.getString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_SUB_CATEGORY_ID.toString(), "");
            if (!"".equals(selectedSubCategoryId)) {
                // get products list from server
                final ProgressDialog progressDialog = AndroidUtil.showProgressDialog(mActivity,
                        GimbalStoreConstants.DEFAULT_SPINNER_TITLE,
                        GimbalStoreConstants.DEFAULT_SPINNER_INFO_TEXT);

                Button askFriendButton = (Button) findViewById(R.id.ask_friend_button);
                askFriendButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());
                        paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                                GimbalStoreConstants.StoreParameterValues.fetchContacts.toString());
                        String cookieString = AndroidUtil.getPreferenceString(getApplicationContext(), GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
                        FriendsListDataProcessor friendsListDataProcessor = new FriendsListDataProcessor(mActivity, progressDialog,
                                GimbalStoreConstants.NOTIFICATION_TYPE.ASKED_TO_REC_CAT,
                                mActivity.getFragmentManager());
                        HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                                Arrays.asList(ServerURLUtil.getStoreServletServerURL(getResources())),
                                Arrays.asList(paramsMap), cookieString,
                                friendsListDataProcessor);
                        asyncTask.execute(new String[]{});
                    }
                });
                Log.d("DEBUG", "obtained sub cat id =" + selectedSubCategoryId);
                ListView productsListView = (ListView) findViewById(R.id.products_list_view);
                ProductsListProcessor productsListProcessor = new ProductsListProcessor(this, productsListView, progressDialog, selectedSubCategoryId);
                Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.identifier.toString(),
                        GimbalStoreConstants.StoreParameterValues.top.toString());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                        GimbalStoreConstants.StoreParameterValues.product.toString());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.parentCategoryId.toString(),
                        selectedSubCategoryId);
                String cookieString = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY, null);


                HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                        Arrays.asList(new String[]{ServerURLUtil.getStoreServletServerURL(getResources())}),
                        Arrays.asList(paramsMap), cookieString,
                        productsListProcessor);
                asyncTask.execute(new String[]{});

            }
        }
    }

    @Override
    public void onFinishedSelectDialog(List<String> friendIdList, GimbalStoreConstants.NOTIFICATION_TYPE postProcessingType) {
        if (friendIdList != null && !friendIdList.isEmpty()) {
            Log.d("DEBUG", "friendIdList=" + friendIdList + "postProcessingType =" + postProcessingType);
            if (postProcessingType == GimbalStoreConstants.NOTIFICATION_TYPE.ASKED_TO_REC_CAT) {
                String friends = "";
                for (String friendId : friendIdList) {
                    if (!friends.isEmpty()) {
                        friends += GimbalStoreConstants.DELIMITER_COMMA;
                    }
                    friends += friendId;
                }
                Bundle intentBundle = getIntent().getExtras();
                String subCategoryId = null;
                if (intentBundle != null) {
                    subCategoryId = intentBundle.getString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_SUB_CATEGORY_ID.toString(), "");
                }
                Log.d("DEBUG", "Asking friend for categoryId = " + subCategoryId);
                ProgressDialog progressDialog = AndroidUtil.showProgressDialog(this,
                        GimbalStoreConstants.DEFAULT_SPINNER_TITLE,
                        GimbalStoreConstants.DEFAULT_SPINNER_INFO_TEXT);
                Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());


                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.friends.toString(),
                        friends);
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.categoryId.toString(),
                        subCategoryId);
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                        GimbalStoreConstants.StoreParameterValues.askRecommendation.toString());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.rectype.toString(),
                        GimbalStoreConstants.StoreParameterValues.category.toString());
                String cookieString = AndroidUtil.getPreferenceString(getApplicationContext(), GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
                PostReviewsProcessor postReviewsProcessor = new PostReviewsProcessor(this, progressDialog, this.getIntent());
                HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                        Arrays.asList(ServerURLUtil.getStoreServletServerURL(getResources())),
                        Arrays.asList(paramsMap), cookieString,
                        postReviewsProcessor);
                asyncTask.execute(new String[]{});
            }

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
    public void onBackPressed() {
        super.onBackPressed();
    }
}
