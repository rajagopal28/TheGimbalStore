package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

import java.util.List;

/**
 * Created by 914889 on 3/5/15.
 */
public class ImageDataProcessor implements AsyncResponseProcessor {

    private GimbalStoreConstants.IMAGE_CONTAINER_TYPE imageContainerType;
    private Activity parentActivity;
    private View parentView;

    public ImageDataProcessor(Activity parentActivity, View parentView, GimbalStoreConstants.IMAGE_CONTAINER_TYPE imageContainerType) {

        this.parentActivity = parentActivity;
        this.imageContainerType = imageContainerType;
        this.parentView = parentView;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeanList) {

        for(ResponseItemBean responseItemBean : responseItemBeanList) {
            GimbalStoreConstants.HTTP_RESPONSE_CODES responseCode = responseItemBean.getResponseCode();
            // Log.d("PROCESS DEBUG", "" + responseCode);
            GimbalStoreConstants.HTTP_HEADER_VALUES responseType = responseItemBean.getContentType();

            if (responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.OK ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.CREATED ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.ACCEPTED) {
                if( responseType == GimbalStoreConstants.HTTP_HEADER_VALUES.CONTENT_TYPE_IMAGE_GIF
                        || responseType == GimbalStoreConstants.HTTP_HEADER_VALUES.CONTENT_TYPE_IMAGE_JPEG
                        || responseType == GimbalStoreConstants.HTTP_HEADER_VALUES.CONTENT_TYPE_IMAGE_PNG
                    ) {

                    final Bitmap bitmap = responseItemBean.getImageBmp();
                    if(bitmap != null) {
                        parentActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if(imageContainerType == GimbalStoreConstants.IMAGE_CONTAINER_TYPE.IMAGE_BUTTON) {

                                }

                            }
                        });


                     }
                }
            }
        }

        return true;
    }
}
