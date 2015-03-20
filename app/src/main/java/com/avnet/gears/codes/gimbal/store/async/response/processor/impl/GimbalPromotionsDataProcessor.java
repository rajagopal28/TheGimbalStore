package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.widget.ListView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.activity.PromotionsActivity;
import com.avnet.gears.codes.gimbal.store.adapter.PromotedProductsAdapter;
import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.NotificationActionBean;
import com.avnet.gears.codes.gimbal.store.bean.PromotedProductBean;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.bean.response.PromotionsResponseBean;
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
 * Created by 914889 on 3/19/15.
 */
public class GimbalPromotionsDataProcessor implements AsyncResponseProcessor {
    private Activity callingActivity;
    private ListView topBrowsedListView;
    private ListView bestSellersListView;
    private ListView promotionsListView;
    private ListView recentlyViewedListView;
    private ProgressDialog progressDialog;

    public GimbalPromotionsDataProcessor(Activity callingContext, ListView topBrowsed,
                                         ListView bestSellers, ListView recentlyViewed,
                                         ListView promotionsView, ProgressDialog progressDialog) {
        this.callingActivity = callingContext;
        this.recentlyViewedListView = recentlyViewed;
        this.topBrowsedListView = topBrowsed;
        this.bestSellersListView = bestSellers;
        this.promotionsListView = promotionsView;
        this.progressDialog = progressDialog;
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
                Log.d("DEBUG", "responseString = " + responseString);

                JsonReader reader = new JsonReader(new StringReader(responseString));
                reader.setLenient(true);
                PromotionsResponseBean responseBean = gson.fromJson(reader, PromotionsResponseBean.class);

                Log.d("HTTP DEBUG", " Response Bean = " + responseBean);

                final PromotedProductBean[] topBrowsed = responseBean.getWishlist();
                final PromotedProductBean[] bestSellers = responseBean.getBestSellers();
                final PromotedProductBean[] recentlyViewed = responseBean.getRecentlyViewed();
                final PromotedProductBean[] recommendations = responseBean.getRecommendations();


                String selectedBeaconsList = responseBean.getBeaconId()[0];
                if (this.recentlyViewedListView != null &&
                        this.bestSellersListView != null &&
                        this.topBrowsedListView != null) {
                    if (callingActivity != null) {
                        callingActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // then Generate Promotions list to Activity
                                if (topBrowsed != null && topBrowsed.length != 0) {
                                    List<PromotedProductBean> listPromotion = Arrays.asList(topBrowsed);
                                    PromotedProductsAdapter topBrowsedAdapter = new PromotedProductsAdapter(callingActivity,
                                            topBrowsedListView, listPromotion,
                                            TypeConversionUtil.getPromotedProductsTitles(listPromotion));
                                    topBrowsedListView.setAdapter(topBrowsedAdapter);
                                    topBrowsedListView.setOnItemClickListener(topBrowsedAdapter);
                                    topBrowsedListView.refreshDrawableState();
                                    AndroidUtil.setDynamicHeight(topBrowsedListView);
                                }
                                if (bestSellers != null && bestSellers.length != 0) {
                                    List<PromotedProductBean> listPromotion = Arrays.asList(bestSellers);
                                    PromotedProductsAdapter bestSellersAdapters = new PromotedProductsAdapter(callingActivity,
                                            bestSellersListView, listPromotion,
                                            TypeConversionUtil.getPromotedProductsTitles(listPromotion));
                                    bestSellersListView.setAdapter(bestSellersAdapters);
                                    bestSellersListView.refreshDrawableState();
                                    bestSellersListView.setOnItemClickListener(bestSellersAdapters);
                                    AndroidUtil.setDynamicHeight(bestSellersListView);
                                }
                                if (recentlyViewed != null && recentlyViewed.length != 0) {
                                    List<PromotedProductBean> listPromotion = Arrays.asList(recentlyViewed);
                                    PromotedProductsAdapter recentlyViewedAdapters = new PromotedProductsAdapter(callingActivity,
                                            recentlyViewedListView, listPromotion,
                                            TypeConversionUtil.getPromotedProductsTitles(listPromotion));
                                    recentlyViewedListView.setAdapter(recentlyViewedAdapters);
                                    recentlyViewedListView.setOnItemClickListener(recentlyViewedAdapters);
                                    recentlyViewedListView.refreshDrawableState();
                                    AndroidUtil.setDynamicHeight(recentlyViewedListView);
                                }
                                if (recommendations != null && recommendations.length != 0) {
                                    List<PromotedProductBean> listPromotion = Arrays.asList(recommendations);
                                    PromotedProductsAdapter promotionsAdapters = new PromotedProductsAdapter(callingActivity,
                                            promotionsListView, listPromotion,
                                            TypeConversionUtil.getPromotedProductsTitles(listPromotion));
                                    promotionsListView.setAdapter(promotionsAdapters);
                                    promotionsListView.setOnItemClickListener(promotionsAdapters);
                                    promotionsListView.refreshDrawableState();
                                    AndroidUtil.setDynamicHeight(promotionsListView);
                                }
                            }
                        });
                    }


                } else {
                    // generate notification
                    List<PromotedProductBean> allPromotions = new ArrayList<PromotedProductBean>();
                    if (topBrowsed != null && topBrowsed.length != 0) {
                        allPromotions.addAll(Arrays.asList(topBrowsed));
                    }
                    if (bestSellers != null && bestSellers.length != 0) {
                        allPromotions.addAll(Arrays.asList(bestSellers));
                    }
                    if (recentlyViewed != null && recentlyViewed.length != 0) {
                        allPromotions.addAll(Arrays.asList(recentlyViewed));
                    }
                    if (recommendations != null && recommendations.length != 0) {
                        allPromotions.addAll(Arrays.asList(recommendations));
                    }
                    if (!allPromotions.isEmpty()) {
                        // notify user about promotion
                        Intent intent = new Intent(this.callingActivity, PromotionsActivity.class);
                        intent.putExtra(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_BEACONS_ID.toString(),
                                selectedBeaconsList);
                        AndroidUtil.notify(this.callingActivity,
                                intent, GimbalStoreConstants.PROMOTIONS_STORE_NOTIFICATION_DESC,
                                GimbalStoreConstants.DEFAULT_STORE_NOTIFICATION_TITLE, R.drawable.ic_store,
                                false, new ArrayList<NotificationActionBean>());
                    }

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
