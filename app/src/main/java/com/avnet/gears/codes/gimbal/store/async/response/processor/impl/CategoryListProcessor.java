package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.ProgressDialog;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.CategoryBean;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.bean.response.CategoryResponseBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_RESPONSE_CODES;
import com.avnet.gears.codes.gimbal.store.fragment.NavigationDrawerFragment;
import com.avnet.gears.codes.gimbal.store.utils.TypeConversionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 914889 on 2/25/15.
 */
public class CategoryListProcessor implements AsyncResponseProcessor {

    private NavigationDrawerFragment navigationDrawerFragment;
    private List<CategoryBean> categoryBeanList;
    private ProgressDialog progressDialog;

    public CategoryListProcessor(NavigationDrawerFragment navigationDrawerFragment,
                                 List<CategoryBean> categoryBeanList, ProgressDialog progressDialog) {
        // add parameters and initialize proper values for
        // sending back the response to the calling activity
        this.categoryBeanList = categoryBeanList;
        this.navigationDrawerFragment = navigationDrawerFragment;
        this.progressDialog = progressDialog;

    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeansList) {
        try {
            if (responseItemBeansList.size() == 1) {
                ResponseItemBean httpResponseBean = responseItemBeansList.get(0);
                String responseString = httpResponseBean.getResponseString();
                HTTP_RESPONSE_CODES responseCode = httpResponseBean.getResponseCode();
                Log.d("PROCESS DEBUG", "" + responseCode);

                if (responseCode == HTTP_RESPONSE_CODES.OK ||
                        responseCode == HTTP_RESPONSE_CODES.CREATED ||
                        responseCode == HTTP_RESPONSE_CODES.ACCEPTED) {
                    // creating the gson parser with html escaping disabled
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();

                    JsonReader reader = new JsonReader(new StringReader(responseString));
                    reader.setLenient(true);
                    CategoryResponseBean responseBean = gson.fromJson(reader, CategoryResponseBean.class);
                    Log.d("HTTP DEBUG", " Response Bean = " + responseBean);
                    // Set up the drawer.
                    categoryBeanList.addAll(Arrays.asList(responseBean.getCatalogGroupView()));
                    navigationDrawerFragment.setmCategoryTitles(TypeConversionUtil.getCategoryTitleList(categoryBeanList));
                    navigationDrawerFragment.refreshDrawerListView();
                    // hide progress bar
                    progressDialog.dismiss();
                    return true;
                }

            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage(), ex);
        }
        return false;
    }
}
