package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ProductBean;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.ImageResponseAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 914889 on 3/9/15.
 */
public class ProductItemProcessor implements AsyncResponseProcessor {

    private Activity parentActivity;
    private ProgressDialog progressDialog;
    private ImageView productImageView;
    private TextView textView;

    public ProductItemProcessor(Activity parentActivity, ProgressDialog progressDialog,
                                ImageView productImageView, TextView textView) {
        this.parentActivity = parentActivity;
        this.progressDialog = progressDialog;
        this.productImageView = productImageView;
        // TODO change constructor for other details
        this.textView = textView;
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

                // get the list of sub categories and populate it to the adapter
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                JsonReader reader = new JsonReader(new StringReader(responseString));
                reader.setLenient(true);
                final ProductBean productBean = gson.fromJson(responseString, ProductBean.class);
                Log.d("DEBUG", "Displaying Product Details.." + productBean.toString());
                if (textView != null) {
                    parentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            textView.setText("Product detail : " + productBean.toString());
                            // TODO add custom logic to display various details like price and reviews
                        }
                    });
                }

                String imageUrl = ServerURLUtil.getAbsoluteUrlFor(parentActivity.getResources(),
                        productBean.getThumbnail());
                String cookieString = AndroidUtil.getPreferenceString(parentActivity.getApplicationContext(),
                        GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
                ImageDataProcessor imageViewProcessor = new ImageDataProcessor(parentActivity, null, Arrays.asList(productImageView));
                ImageResponseAsyncTask imageResponseAsyncTask = new ImageResponseAsyncTask(Arrays.asList(new String[]{imageUrl}), imageViewProcessor, cookieString);
                imageResponseAsyncTask.execute(new String[]{});
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                return true;
            }
        }
        return false;
    }
}
