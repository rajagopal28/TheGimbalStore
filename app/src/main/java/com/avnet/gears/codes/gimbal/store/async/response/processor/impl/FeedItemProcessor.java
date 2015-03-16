package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.FeedItemResponseBean;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.fragment.FeedItemDialogFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.List;

/**
 * Created by 914889 on 3/15/15.
 */
public class FeedItemProcessor implements AsyncResponseProcessor {
    private Activity callingActivity;
    private FragmentManager fragmentManager;
    private ProgressDialog progressDialog;
    private String productId;

    public FeedItemProcessor(Activity callingActivity, FragmentManager fragmentManager,
                             ProgressDialog pd, String productID) {
        this.callingActivity = callingActivity;
        this.fragmentManager = fragmentManager;
        this.progressDialog = pd;
        this.productId = productID;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeansList) {
        if (!responseItemBeansList.isEmpty()) {
            if (responseItemBeansList.size() == 1) {
                ResponseItemBean responseItemBean1 = responseItemBeansList.get(0);
                GimbalStoreConstants.HTTP_RESPONSE_CODES responseCode = responseItemBean1.getResponseCode();
                if (responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.OK ||
                        responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.CREATED ||
                        responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.ACCEPTED) {
                    String responseString = responseItemBean1.getResponseString();

                    responseString = responseString.trim()
                            .replace(GimbalStoreConstants.START_COMMENT_STRING, "");

                    // get the list of sub categories and populate it to the adapter
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    JsonReader reader = new JsonReader(new StringReader(responseString));
                    reader.setLenient(true);
                    final FeedItemResponseBean feedItemResponseBean = gson.fromJson(responseString, FeedItemResponseBean.class);
                    Log.d("DEBUG", "Displaying Feed Details.." + feedItemResponseBean.toString());
                    FeedItemDialogFragment dialogFragment = FeedItemDialogFragment.newInstance(feedItemResponseBean.getCatalogEntryView()[0].getFeedItemDescription(),
                            productId);
                    dialogFragment.show(fragmentManager, GimbalStoreConstants.TAG_SHOW_FEED_ITEM);
                }
            }
            progressDialog.dismiss();
            return true;
        }
        return false;
    }
}
