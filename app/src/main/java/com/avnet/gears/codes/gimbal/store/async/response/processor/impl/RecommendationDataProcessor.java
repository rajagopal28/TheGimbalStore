package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.activity.HomeActivity;
import com.avnet.gears.codes.gimbal.store.activity.ProductDetailsActivity;
import com.avnet.gears.codes.gimbal.store.activity.ProductsListActivity;
import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.NotificationActionBean;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.bean.response.RecommendationResponseBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 914889 on 3/15/15.
 */
public class RecommendationDataProcessor implements AsyncResponseProcessor {
    private Context context;
    private String notificationMsg;

    public RecommendationDataProcessor(Context callingContext, String msg) {
        this.notificationMsg = msg;
        this.context = callingContext;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeansList) {
        for (ResponseItemBean responseItemBean : responseItemBeansList) {
            GimbalStoreConstants.HTTP_RESPONSE_CODES responseCode = responseItemBean.getResponseCode();
            Log.d("PROCESS DEBUG", "Response Code= " + responseCode);

            if (responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.OK ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.CREATED ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.ACCEPTED) {
                // get recommendation data
                String responseString = responseItemBean.getResponseString();

                responseString = responseString.trim()
                        .replace(GimbalStoreConstants.START_COMMENT_STRING, "")
                        .replace(GimbalStoreConstants.END_COMMENT_STRING, "");

                // get the list of sub categories and populate it to the adapter
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                JsonReader reader = new JsonReader(new StringReader(responseString));
                reader.setLenient(true);
                final RecommendationResponseBean recommendationResponseBean = gson.fromJson(responseString, RecommendationResponseBean.class);
                Log.d("DEBUG", "Displaying Recommendation Details.." + recommendationResponseBean.toString());
                String identifierString = recommendationResponseBean.getIdentifierValue();
                GimbalStoreConstants.RECOMMENDATION_TYPE recommendationType = GimbalStoreConstants.RECOMMENDATION_TYPE.valueOf(recommendationResponseBean.getRecommendationType());

                Intent targetIntent;
                Bundle bundle = new Bundle();
                switch (recommendationType) {
                    case TYPE_CATEGORY:
                    case TYPE_REVIEW_CATEGORY:
                        targetIntent = new Intent(context, ProductsListActivity.class);
                        bundle.putString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_SUB_CATEGORY_ID.toString(),
                                identifierString);
                        targetIntent.putExtras(bundle);
                        break;
                    case TYPE_PRODUCT:
                    case TYPE_REVIEW_PRODUCT:
                        targetIntent = new Intent(context, ProductDetailsActivity.class);
                        bundle.putString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString(),
                                identifierString);
                        targetIntent.putExtras(bundle);
                        break;
                    default:
                        // do nothing
                        targetIntent = new Intent(context, HomeActivity.class);
                }

                List<NotificationActionBean> notificationActionBeans = new ArrayList<NotificationActionBean>();
                AndroidUtil.notify(this.context, targetIntent,
                        notificationMsg, GimbalStoreConstants.DEFAULT_STORE_NOTIFICATION_TITLE,
                        R.drawable.ic_store, true,
                        notificationActionBeans);

            }
        }
        return true;
    }
}
