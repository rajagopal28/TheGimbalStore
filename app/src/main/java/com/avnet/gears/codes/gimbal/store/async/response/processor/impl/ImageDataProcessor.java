package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.HttpResponseBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.utils.TypeConversionUtil;

/**
 * Created by 914889 on 3/5/15.
 */
public class ImageDataProcessor implements AsyncResponseProcessor {

    private ImageView imageView;
    private ImageButton imageButton;
    private Activity parentActivity;

    public ImageDataProcessor(Activity parentActivity, ImageView imageView, ImageButton imageButton) {
        this.imageView = imageView;
        this.parentActivity = parentActivity;
        this.imageButton = imageButton;
    }

    @Override
    public boolean doProcess(HttpResponseBean httpResponseBean) {

        GimbalStoreConstants.HTTP_RESPONSE_CODES responseCode = httpResponseBean.getResponseCode();
        // Log.d("PROCESS DEBUG", "" + responseCode);
        GimbalStoreConstants.HTTP_HEADER_VALUES responseType = httpResponseBean.getResponseType();
        if( responseType == GimbalStoreConstants.HTTP_HEADER_VALUES.CONTENT_TYPE_IMAGE_GIF
                || responseType == GimbalStoreConstants.HTTP_HEADER_VALUES.CONTENT_TYPE_IMAGE_JPEG
                || responseType == GimbalStoreConstants.HTTP_HEADER_VALUES.CONTENT_TYPE_IMAGE_PNG
                ) {
            if (responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.OK ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.CREATED ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.ACCEPTED) {
                if (httpResponseBean.getResponseStream() != null) {
                    final Bitmap bitmap = TypeConversionUtil.getBitmapFromStream(httpResponseBean.getResponseStream());
                    if(bitmap != null) {
                        parentActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                if(imageView != null) {
                                    // Log.d("DEBUG","placing image in view");
                                    imageView.setImageBitmap(bitmap);
                                } else if(imageButton != null) {
                                    // Log.d("DEBUG","placing image in button");
                                    imageButton.setImageBitmap(bitmap);
                                }
                            }
                        });
                    }

                }

                return true;
            }
        }
        return false;
    }
}
