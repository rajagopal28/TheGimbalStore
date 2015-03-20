package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.activity.ProductDetailsActivity;
import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.bean.response.PostReviewResponseBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.List;

/**
 * Created by 914889 on 3/17/15.
 */
public class PostReviewsProcessor implements AsyncResponseProcessor {
    private Activity callingActivity;
    private ProgressDialog progressDialog;
    private Intent intent;

    public PostReviewsProcessor(Activity callingActivity, ProgressDialog progressDialog, Intent returnIntent) {
        this.callingActivity = callingActivity;
        this.progressDialog = progressDialog;
        this.intent = returnIntent;
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

                JsonReader reader = new JsonReader(new StringReader(responseString));
                reader.setLenient(true);
                Log.d("DEBUG", "responseString = " + responseString);
                PostReviewResponseBean responseBean = gson.fromJson(reader, PostReviewResponseBean.class);
                Log.d("HTTP DEBUG", " Response Bean = " + responseBean);
                if (responseBean.getReviewResponse() != null &&
                        responseBean.getReviewResponse().getErrors() != null &&
                        responseBean.getReviewResponse().getErrors().length > 0) {
                    Log.d("DEBUG", "Error submitting Review");
                    return false;
                }
                String prodId = intent.getStringExtra(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString());

                if (intent != null && prodId != null) {
                    Log.d("DEBUG", "In review post processor prodId = " + prodId);
                    Intent targetIntent = new Intent(this.callingActivity, ProductDetailsActivity.class);
                    targetIntent.putExtra(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString(), prodId);
                    callingActivity.startActivity(targetIntent);
                    callingActivity.finish();
                }
                Log.d("DEBUG", "Review submitted successfully!!");
            }
            progressDialog.dismiss();
            return true;
        }
        return false;
    }
}
