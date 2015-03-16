package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ListView;

import com.avnet.gears.codes.gimbal.store.adapter.CategoryViewAdapter;
import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.bean.SubCategoryBean;
import com.avnet.gears.codes.gimbal.store.bean.response.SubCategoryResponseBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_RESPONSE_CODES;
import com.avnet.gears.codes.gimbal.store.utils.TypeConversionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 914889 on 3/4/15.
 */
public class SubcategoryListProcessor implements AsyncResponseProcessor {

    private Activity parentActivity;
    private ListView subCategoryListView;
    private ProgressDialog progressDialog;

    public SubcategoryListProcessor(Activity parentActivity, ListView listView, ProgressDialog progressDialog) {
        this.parentActivity = parentActivity;
        this.subCategoryListView = listView;
        this.progressDialog = progressDialog;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeanList) {
        if (responseItemBeanList.size() == 1) {
            ResponseItemBean httpResponseBean = responseItemBeanList.get(0);
            HTTP_RESPONSE_CODES responseCode = httpResponseBean.getResponseCode();
            if (responseCode == HTTP_RESPONSE_CODES.OK ||
                    responseCode == HTTP_RESPONSE_CODES.CREATED ||
                    responseCode == HTTP_RESPONSE_CODES.ACCEPTED) {
                String responseString = httpResponseBean.getResponseString();

                responseString = responseString.trim()
                        .replace(GimbalStoreConstants.START_COMMENT_STRING, "")
                        .replace(GimbalStoreConstants.END_COMMENT_STRING, "");

                // get the list of sub categories and populate it to the adapter
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                JsonReader reader = new JsonReader(new StringReader(responseString));
                reader.setLenient(true);
                SubCategoryResponseBean subCatResponseBean = gson.fromJson(responseString, SubCategoryResponseBean.class);
                List<SubCategoryBean> subCategoryBeanList = Arrays.asList(subCatResponseBean.getCatalogGroupView());
                Log.d("DEBUG", "sub cat response processed = " + subCategoryBeanList);
                List<String> scTitles = TypeConversionUtil.getSubCategoryTitleList(subCategoryBeanList);
                final CategoryViewAdapter categoryViewAdapter = new CategoryViewAdapter(parentActivity, subCategoryBeanList, scTitles);
                Log.d("DEBUG", "Setting Adapter to sub cat list view");
                parentActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        subCategoryListView.setAdapter(categoryViewAdapter);
                        subCategoryListView.refreshDrawableState();
                    }
                });
                progressDialog.dismiss();
                return true;
            }
        }
        return false;
    }
}
