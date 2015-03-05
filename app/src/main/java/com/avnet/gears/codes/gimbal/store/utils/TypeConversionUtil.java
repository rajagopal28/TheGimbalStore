package com.avnet.gears.codes.gimbal.store.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.bean.CategoryBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 914889 on 3/4/15.
 */
public class TypeConversionUtil {

    public static String[] getCategoryTitleList(List<CategoryBean> categoryBeanList) {
        List<String> categoryTitleList = new ArrayList<>();
        if(categoryBeanList != null && !categoryBeanList.isEmpty()) {
            for(CategoryBean categoryBean : categoryBeanList){
                categoryTitleList.add(categoryBean.getName());
            }
        }
        // Log.d("DEBUG", categoryTitleList.toArray(new String[]{}).toString());
        return categoryTitleList.toArray(new String[]{});
    }
    public static Bitmap getBitmapFromStream(InputStream inputStream) {
        Bitmap bmp = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inMutable = true;
        if(inputStream != null) {
            bmp = BitmapFactory.decodeStream(inputStream);
        }
        return bmp;
    }
    public static GimbalStoreConstants.HTTP_HEADER_VALUES getImageTypeFromExtension(String imageUrl) {
        GimbalStoreConstants.HTTP_HEADER_VALUES returnValue = null;
        int indexOfDot = imageUrl.lastIndexOf(".");
        if(indexOfDot != -1) {
            String extension = imageUrl.substring(indexOfDot + 1, imageUrl.length());
            GimbalStoreConstants.SupportedImageFormats format = GimbalStoreConstants.SupportedImageFormats.valueOf(extension);
            if( format != null) {
                returnValue = format.getResponseType();
            }
        }
        return  returnValue;
    }
}
