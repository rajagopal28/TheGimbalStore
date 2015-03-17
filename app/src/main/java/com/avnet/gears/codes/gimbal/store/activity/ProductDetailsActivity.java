package com.avnet.gears.codes.gimbal.store.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.FriendsListDataProcessor;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.PostReviewsProcessor;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.ProductItemProcessor;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.fragment.dialog.FriendsListDialogFragment;
import com.avnet.gears.codes.gimbal.store.handler.HttpConnectionAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ProductDetailsActivity extends Activity implements FriendsListDialogFragment.FriendListSelectListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String prodId = "";
        final Bundle intentBundle = getIntent().getExtras();

        getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_product_details);
        if (intentBundle != null) {
            prodId = intentBundle.getString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString(), "");
        }
        final String selectedProductId = prodId;
        TextView productTitleView = (TextView) findViewById(R.id.product_title);
        TextView productDescriptionView = (TextView) findViewById(R.id.product_description);
        TextView productPriceView = (TextView) findViewById(R.id.product_price);
        TextView productRatingView = (TextView) findViewById(R.id.product_rating);
        TextView productReviewTitleView = (TextView) findViewById(R.id.product_review_details);
        final ProgressDialog progressDialog = AndroidUtil.showProgressDialog(this,
                GimbalStoreConstants.DEFAULT_SPINNER_TITLE,
                GimbalStoreConstants.DEFAULT_SPINNER_INFO_TEXT);
        progressDialog.dismiss();

        RatingBar ratingBar = (RatingBar) findViewById(R.id.product_rating_bar);

        Button buyButton = (Button) findViewById(R.id.buy_now_button);
        Button askButton = (Button) findViewById(R.id.ask_friend_button);
        Button recommendButton = (Button) findViewById(R.id.recommend_button);

        Button postReviewButton = (Button) findViewById(R.id.post_review_button);
        final Intent intent = new Intent(this, ProductDetailsActivity.class);  //your class
        Bundle data = new Bundle();
        data.putString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString(),
                selectedProductId);
        intent.putExtras(data);
        final String ratingValue = "" + ratingBar.getRating();
        final Activity mActivity = this;
        recommendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());

                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                        GimbalStoreConstants.StoreParameterValues.fetchContacts.toString());
                Log.d("DEBUG", "paramsMap=" + paramsMap);
                String cookieString = AndroidUtil.getPreferenceString(getApplicationContext(), GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
                FriendsListDataProcessor friendsListDataProcessor = new FriendsListDataProcessor(mActivity, progressDialog,
                        GimbalStoreConstants.NOTIFICATION_TYPE.FRIEND_RECOMMENDATION,
                        mActivity.getFragmentManager());
                HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                        Arrays.asList(ServerURLUtil.getStoreServletServerURL(getResources())),
                        Arrays.asList(paramsMap), cookieString,
                        friendsListDataProcessor);
                asyncTask.execute(new String[]{});
            }
        });
        askButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());

                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                        GimbalStoreConstants.StoreParameterValues.fetchContacts.toString());
                Log.d("DEBUG", "paramsMap=" + paramsMap);
                String cookieString = AndroidUtil.getPreferenceString(getApplicationContext(), GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
                FriendsListDataProcessor friendsListDataProcessor = new FriendsListDataProcessor(mActivity, progressDialog,
                        GimbalStoreConstants.NOTIFICATION_TYPE.ASK_FRIEND,
                        mActivity.getFragmentManager());
                HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                        Arrays.asList(ServerURLUtil.getStoreServletServerURL(getResources())),
                        Arrays.asList(paramsMap), cookieString,
                        friendsListDataProcessor);
                asyncTask.execute(new String[]{});
            }
        });
        postReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                String reviewText = ((EditText) findViewById(R.id.review_text)).getText().toString();
                Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.productId.toString(),
                        selectedProductId);
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                        GimbalStoreConstants.StoreParameterValues.postReview.toString());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.reviewText.toString(),
                        reviewText);
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.rating.toString(),
                        ratingValue);

                Log.d("DEBUG", "paramsMap=" + paramsMap);
                String cookieString = AndroidUtil.getPreferenceString(getApplicationContext(), GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
                PostReviewsProcessor postReviewsProcessor = new PostReviewsProcessor(mActivity, progressDialog,
                        selectedProductId, intent);
                HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                        Arrays.asList(ServerURLUtil.getStoreServletServerURL(getResources())),
                        Arrays.asList(paramsMap), cookieString,
                        postReviewsProcessor);
                asyncTask.execute(new String[]{});
            }
        });
        // TODO link buttons

        ListView reviewsListView = (ListView) findViewById(R.id.product_reviews_list_view);
        ImageView productImageView = (ImageView) findViewById(R.id.product_display_image);

        Log.d("DEBUG", "Displaying details of product : " + selectedProductId);
        // Make async calls
        if (!selectedProductId.isEmpty()) {
            ProgressDialog dialog = AndroidUtil.showProgressDialog(this,
                    GimbalStoreConstants.DEFAULT_SPINNER_TITLE,
                    GimbalStoreConstants.DEFAULT_SPINNER_INFO_TEXT);
            ProductItemProcessor productItemProcessor = new ProductItemProcessor(this, dialog,
                    productImageView, reviewsListView,
                    productTitleView, productDescriptionView,
                    productPriceView, productRatingView,
                    productReviewTitleView);
            Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());
            paramsMap.put(GimbalStoreConstants.StoreParameterKeys.identifier.toString(),
                    GimbalStoreConstants.StoreParameterValues.top.toString());
            paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                    GimbalStoreConstants.StoreParameterValues.product.toString());
            paramsMap.put(GimbalStoreConstants.StoreParameterKeys.uniqueId.toString(),
                    selectedProductId);
            String cookieString = AndroidUtil.getPreferenceString(getApplicationContext(), GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);


            HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                    Arrays.asList(new String[]{ServerURLUtil.getStoreServletServerURL(getResources())}),
                    Arrays.asList(paramsMap), cookieString,
                    productItemProcessor);
            asyncTask.execute(new String[]{});


        }

    }

    @Override
    public void onFinishedSelectDialog(List<String> friendIdList, GimbalStoreConstants.NOTIFICATION_TYPE postProcessingType) {
        Log.d("DEBUG", "SelectedFriends = " + friendIdList);
        String selectedProductId = getIntent().getStringExtra(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString());
        if (postProcessingType == GimbalStoreConstants.NOTIFICATION_TYPE.ASK_FRIEND) {

            // post this list to ask friend flow
        } else if (postProcessingType == GimbalStoreConstants.NOTIFICATION_TYPE.FRIEND_RECOMMENDATION) {
            // post this list to recommend product flow
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
    protected void onRestart() {
        super.onRestart();
        Intent i = new Intent(this, ProductDetailsActivity.class);  //your class
        startActivity(i);
        finish();

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
