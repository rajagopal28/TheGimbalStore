package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.adapter.pager.SectionsPagerAdapter;
import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.FeedItemBean;
import com.avnet.gears.codes.gimbal.store.bean.FeedItemResponseBean;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 914889 on 3/15/15.
 */
public class FeedItemListDataProcessor implements AsyncResponseProcessor {

    private ViewPager viewPager;
    private GimbalStoreConstants.FEED_ITEM_TYPE[] feedItemTypes;
    private Activity parentActivity;
    private ProgressDialog progressDialog;

    public FeedItemListDataProcessor(Activity parentActivity, ViewPager mViewPager,
                                     GimbalStoreConstants.FEED_ITEM_TYPE[] feedItemTypes, ProgressDialog dialog) {
        this.viewPager = mViewPager;
        this.feedItemTypes = feedItemTypes;
        this.parentActivity = parentActivity;
        this.progressDialog = dialog;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeansList) {
        try {
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

                    JsonReader reader = new JsonReader(new StringReader(responseString));
                    reader.setLenient(true);
                    FeedItemResponseBean responseBean = gson.fromJson(reader, FeedItemResponseBean.class);
                    Log.d("HTTP DEBUG", " Response Bean = " + responseBean);
                    FeedItemBean[] feedItemsArray = responseBean.getCatalogEntryView();
                    final List<FeedItemBean> feedItemBeans = Arrays.asList(responseBean.getCatalogEntryView());
                    final Map<GimbalStoreConstants.FEED_ITEM_TYPE, List<FeedItemBean>> feedListMap = new HashMap<GimbalStoreConstants.FEED_ITEM_TYPE, List<FeedItemBean>>();
                    feedListMap.put(GimbalStoreConstants.FEED_ITEM_TYPE.FRIEND_REVIEW, new ArrayList<FeedItemBean>());
                    feedListMap.put(GimbalStoreConstants.FEED_ITEM_TYPE.FRIEND_RECOMMENDATION, new ArrayList<FeedItemBean>());
                    feedListMap.put(GimbalStoreConstants.FEED_ITEM_TYPE.SUGGESTED_PRODUCTS, new ArrayList<FeedItemBean>());
                    if (feedItemsArray != null && feedItemsArray.length > 0) {
                        for (FeedItemBean feedItem : feedItemBeans) {
                            if (feedItem.getFeedItemType() != null) {
                                GimbalStoreConstants.FEED_ITEM_TYPE feedItemType = GimbalStoreConstants.FEED_ITEM_TYPE.valueOf(feedItem.getFeedItemType());
                                feedListMap.get(feedItemType).add(feedItem);
                            }
                        }
                    }
                    parentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // instantiate pager adapter
                            SectionsPagerAdapter adapter = new SectionsPagerAdapter(parentActivity.getFragmentManager(),
                                    feedItemTypes,
                                    feedListMap);
                            viewPager.setAdapter(adapter);
                        }
                    });

                    return true;
                }
                progressDialog.dismiss();
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage(), ex);
        }
        return false;
    }
}
