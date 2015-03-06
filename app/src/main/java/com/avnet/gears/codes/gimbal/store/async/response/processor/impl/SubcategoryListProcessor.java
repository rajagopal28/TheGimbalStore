package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.widget.ListView;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_RESPONSE_CODES;

import java.util.List;

/**
 * Created by 914889 on 3/4/15.
 */
public class SubcategoryListProcessor implements AsyncResponseProcessor {

    private Activity parentActivity;
    private ListView subCategoryListView;

    public SubcategoryListProcessor(Activity parentActivity, ListView listView) {
        this.parentActivity = parentActivity;
        this.subCategoryListView = listView;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeanList) {
        if(responseItemBeanList.size() == 1) {
            ResponseItemBean httpResponseBean = responseItemBeanList.get(0);
            String responseString = httpResponseBean.getResponseString();
            HTTP_RESPONSE_CODES responseCode = httpResponseBean.getResponseCode();
            responseString = responseString.trim()
                    .replace(GimbalStoreConstants.START_COMMENT_STRING, "")
                    .replace(GimbalStoreConstants.END_COMMENT_STRING, "");
            if(responseCode == HTTP_RESPONSE_CODES.OK ||
                    responseCode == HTTP_RESPONSE_CODES.CREATED ||
                    responseCode == HTTP_RESPONSE_CODES.ACCEPTED) {
                // TODO process the response list
                return true;
            }
        }
        return false;
    }
}
