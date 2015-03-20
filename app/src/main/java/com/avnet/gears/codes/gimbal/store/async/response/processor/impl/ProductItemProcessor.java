package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ProductBean;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.bean.ReviewDataBean;
import com.avnet.gears.codes.gimbal.store.bean.response.ProductsResponseBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.ImageResponseAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;
import com.avnet.gears.codes.gimbal.store.utils.TypeConversionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 914889 on 3/9/15.
 */
public class ProductItemProcessor implements AsyncResponseProcessor {

    private Activity parentActivity;
    private ProgressDialog progressDialog;
    private ImageView productImageView;
    private ListView reviewsListView;
    private TextView titleView;
    private TextView descriptionView;
    private TextView priceView;
    private TextView ratingView;
    private TextView reviewTitleView;
    private TextView recommendedFriendsView;

    public ProductItemProcessor(Activity parentActivity, ProgressDialog progressDialog,
                                ImageView productImageView, ListView productReviewsListView,
                                TextView productTitleView, TextView productDescriptionView,
                                TextView productPriceView, TextView productRatingView,
                                TextView productReviewTitleView, TextView recommendedFriendsView) {
        this.parentActivity = parentActivity;
        this.progressDialog = progressDialog;

        this.productImageView = productImageView;
        this.reviewsListView = productReviewsListView;

        this.titleView = productTitleView;
        this.descriptionView = productDescriptionView;
        this.priceView = productPriceView;
        this.ratingView = productRatingView;
        this.reviewTitleView = productReviewTitleView;
        this.recommendedFriendsView = recommendedFriendsView;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeanList) {
        if (responseItemBeanList.size() == 1) {
            ResponseItemBean httpResponseBean = responseItemBeanList.get(0);
            GimbalStoreConstants.HTTP_RESPONSE_CODES responseCode = httpResponseBean.getResponseCode();
            if (responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.OK ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.CREATED ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.ACCEPTED) {
                String responseString = httpResponseBean.getResponseString();

                responseString = responseString.trim()
                        .replace(GimbalStoreConstants.START_COMMENT_STRING, "")
                        .replace(GimbalStoreConstants.END_COMMENT_STRING, "");
                Log.d("DEBUG", "responseString = " + responseString);

                // get the list of sub categories and populate it to the adapter
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                JsonReader reader = new JsonReader(new StringReader(responseString));
                reader.setLenient(true);
                ProductsResponseBean productRespBean = gson.fromJson(responseString, ProductsResponseBean.class);
                final ProductBean productBean = productRespBean.getCatalogEntryView()[0];
                Log.d("DEBUG", "Displaying Product Details.." + productBean.toString());

                parentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (titleView != null) {
                            titleView.setText(productBean.getName());
                        }
                        if (descriptionView != null) {
                            descriptionView.setText(productBean.getShortdescription());
                        }
                        if (priceView != null) {
                            String currencyUnit = parentActivity.getResources().getString(R.string.CURRENCY_UNIT);
                            priceView.setText(currencyUnit + productBean.getPrice());
                        }

                        ReviewDataBean reviews = productBean.getReviews();
                        if (reviews != null) {
                            Log.d("DEBUG", "reviews =" + reviews);
                            List<String> reviewString = TypeConversionUtil.getReviewTextAsStrings(Arrays.asList(reviews.getReviews()));
                            ArrayAdapter<String> reviewsAdapter = new ArrayAdapter<String>(parentActivity,
                                    android.R.layout.simple_list_item_1,
                                    reviewString);
                            reviewsListView.setAdapter(reviewsAdapter);
                            reviewsListView.refreshDrawableState();
                            AndroidUtil.setDynamicHeight(reviewsListView);
                            if (reviewsListView != null) {

                                if (reviews.getReviews() != null && reviews.getReviews().length > 0) {
                                    reviewTitleView.setText(MessageFormat.format(GimbalStoreConstants.MESSAGE_PRODUCT_GOT_REVIEWS,
                                            new Object[]{reviews.getReviews().length}));
                                } else {
                                    reviewTitleView.setText(GimbalStoreConstants.MESSAGE_NO_REVIEWS);
                                }

                            }
                            String[] recommendedBy = productBean.getRecommendedBy();
                            if (recommendedBy != null && recommendedBy.length != 0) {
                                Log.d("DEBUG", "recommendedBy = " + Arrays.toString(recommendedBy));
                                recommendedFriendsView.setText(MessageFormat.format(
                                        GimbalStoreConstants.LABEL_FRIEND_RECOMMENDED,
                                        new Object[]{TextUtils.join(GimbalStoreConstants.DELIMITER_COMMA, recommendedBy)}
                                ));

                            } else {
                                recommendedFriendsView.setText(GimbalStoreConstants.LABEL_NO_FRIEND_RECOMMENDED);
                            }
                            if (ratingView != null) {
                                if (reviews.getAverageRating() != null && "0".equals(reviews.getReviews())) {
                                    // Log.d("DEBUG", "Rating overall = " + reviews.getIncludes());
                                    ratingView.setText(GimbalStoreConstants.LABEL_OVERALL_RATING + reviews.getAverageRating());
                                } else {
                                    // LABEL_NO_RATING
                                    ratingView.setText(GimbalStoreConstants.LABEL_NO_RATING);
                                }
                            }
                        }
                    }
                });
                if (productBean.getThumbnail() != null) {
                    String imageUrl = ServerURLUtil.getAbsoluteUrlFor(parentActivity.getResources(),
                            productBean.getThumbnail());
                    String cookieString = AndroidUtil.getPreferenceString(parentActivity.getApplicationContext(),
                            GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
                    ImageDataProcessor imageViewProcessor = new ImageDataProcessor(parentActivity, null, Arrays.asList(productImageView));
                    ImageResponseAsyncTask imageResponseAsyncTask = new ImageResponseAsyncTask(Arrays.asList(new String[]{imageUrl}), imageViewProcessor, cookieString);
                    imageResponseAsyncTask.execute(new String[]{});
                }
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                return true;
            }
        }
        return false;
    }
}
