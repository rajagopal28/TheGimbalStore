package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;
import android.widget.ListView;

import com.avnet.gears.codes.gimbal.store.adapter.ProductsViewAdapter;
import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ProductBean;
import com.avnet.gears.codes.gimbal.store.bean.ProductsResponseBean;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.listener.ProductItemClickListener;
import com.avnet.gears.codes.gimbal.store.utils.TypeConversionUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 914889 on 3/6/15.
 */
public class ProductsListProcessor implements AsyncResponseProcessor {

    private Activity parentActivity;
    private ListView productsListView;
    private ProgressDialog progressDialog;

    public ProductsListProcessor(Activity parentActivity, ListView productsListView, ProgressDialog progressDialog) {
        this.parentActivity = parentActivity;
        this.productsListView = productsListView;
        this.progressDialog = progressDialog;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeansList) {
        try {
            if(responseItemBeansList.size() == 1) {
                ResponseItemBean httpResponseBean = responseItemBeansList.get(0);
                String responseString = httpResponseBean.getResponseString();
                GimbalStoreConstants.HTTP_RESPONSE_CODES responseCode = httpResponseBean.getResponseCode();
                Log.d("PROCESS DEBUG", "" + responseCode);

                if(responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.OK ||
                        responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.CREATED ||
                        responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.ACCEPTED) {
                    // creating the gson parser with html escaping disabled
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();

                    JsonReader reader = new JsonReader(new StringReader(responseString));
                    reader.setLenient(true);
                    ProductsResponseBean responseBean = gson.fromJson(reader, ProductsResponseBean.class);
                    Log.d("HTTP DEBUG", " Response Bean = " + responseBean);
                    final List<ProductBean> productsList = Arrays.asList(responseBean.getCatalogEntryView());
                    // Set up the List View.
                    final ProductsViewAdapter productsViewAdapter = new ProductsViewAdapter(parentActivity, productsListView,
                            Arrays.asList(responseBean.getCatalogEntryView()),
                            TypeConversionUtil.getProductsTitleList(productsList));
                    final ProductItemClickListener productItemClickListener = new ProductItemClickListener(parentActivity, productsList);
                    parentActivity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            productsListView.setAdapter(productsViewAdapter);
                            productsListView.setOnItemClickListener(productItemClickListener);
                            productsListView.refreshDrawableState();
                        }
                    });
                    // hide progress bar
                    progressDialog.dismiss();
                    return true;
                }

            }
        } catch (Exception ex){
            Log.e("ERROR", ex.getMessage(), ex);
        }
        return  false;
    }
}
