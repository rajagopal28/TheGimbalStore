package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 914889 on 3/5/15.
 */
public class ImageDataProcessor implements AsyncResponseProcessor {

    private List<ImageView> imageViewList;
    private Activity parentActivity;
    private ViewGroup parentView;
    private boolean setOnParentView;


    public ImageDataProcessor(Activity parentActivity, ViewGroup parentView,
                              boolean setOnParentView, List<ImageView> imageViewList) {

        this.parentActivity = parentActivity;
        this.setOnParentView = setOnParentView;
        this.parentView = parentView;
        this.imageViewList = imageViewList;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeanList) {
        int index = 0;
        for(ResponseItemBean responseItemBean : responseItemBeanList) {
            GimbalStoreConstants.HTTP_RESPONSE_CODES responseCode = responseItemBean.getResponseCode();
            Log.d("PROCESS DEBUG", "Image response = " + responseCode);
            GimbalStoreConstants.HTTP_HEADER_VALUES responseType = responseItemBean.getContentType();

            if (responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.OK ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.CREATED ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.ACCEPTED) {
                if( responseType == GimbalStoreConstants.HTTP_HEADER_VALUES.CONTENT_TYPE_IMAGE_GIF
                        || responseType == GimbalStoreConstants.HTTP_HEADER_VALUES.CONTENT_TYPE_IMAGE_JPEG
                        || responseType == GimbalStoreConstants.HTTP_HEADER_VALUES.CONTENT_TYPE_IMAGE_PNG
                    ) {
                    Log.d("DEBUG", "retrieved image of type " + responseType);
                    final Bitmap bitmap = responseItemBean.getImageBmp();
                    if(bitmap != null) {
                        ImageView imageView = imageViewList.get(index);
                        imageView.setImageBitmap(bitmap);
                    }
                }
            }
            index++;
        }
        if(setOnParentView) {
            parentActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for(ImageView imageView : imageViewList){
                        parentView.addView(imageView);
                    }
                    parentView.refreshDrawableState();

                }
            });
        }

        return true;
    }
}
