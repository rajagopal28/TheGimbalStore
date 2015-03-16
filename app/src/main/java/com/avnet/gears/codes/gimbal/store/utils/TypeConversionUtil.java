package com.avnet.gears.codes.gimbal.store.utils;

import android.util.Log;

import com.avnet.gears.codes.gimbal.store.bean.CategoryBean;
import com.avnet.gears.codes.gimbal.store.bean.FeedItemBean;
import com.avnet.gears.codes.gimbal.store.bean.NotificationDataBean;
import com.avnet.gears.codes.gimbal.store.bean.ProductBean;
import com.avnet.gears.codes.gimbal.store.bean.SubCategoryBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 914889 on 3/4/15.
 */
public class TypeConversionUtil {

    public static String[] getCategoryTitleList(List<CategoryBean> categoryBeanList) {
        List<String> categoryTitleList = new ArrayList<String>();
        if (categoryBeanList != null && !categoryBeanList.isEmpty()) {
            for (CategoryBean categoryBean : categoryBeanList) {
                categoryTitleList.add(categoryBean.getName());
            }
        }
        // Log.d("DEBUG", categoryTitleList.toArray(new String[]{}).toString());
        return categoryTitleList.toArray(new String[]{});
    }

    public static GimbalStoreConstants.HTTP_HEADER_VALUES getImageTypeFromExtension(String imageUrl) {
        GimbalStoreConstants.HTTP_HEADER_VALUES returnValue = null;
        int indexOfDot = imageUrl.lastIndexOf(".");
        if (indexOfDot != -1) {
            String extension = imageUrl.substring(indexOfDot + 1, imageUrl.length());
            GimbalStoreConstants.SupportedImageFormats format = GimbalStoreConstants.SupportedImageFormats.valueOf(extension);
            if (format != null) {
                returnValue = format.getResponseType();
            }
        }
        return returnValue;
    }

    public static List<String> getSubCategoryTitleList(List<SubCategoryBean> scBeanList) {

        List<String> scTitles = new ArrayList<String>();
        if (scBeanList != null && !scBeanList.isEmpty()) {
            for (SubCategoryBean bean : scBeanList) {
                scTitles.add(bean.getName());
            }
        }

        return scTitles;

    }


    public static List<String> getProductsTitleList(List<ProductBean> productBeanList) {

        List<String> prdTitles = new ArrayList<String>();
        if (productBeanList != null && !productBeanList.isEmpty()) {
            for (ProductBean bean : productBeanList) {
                prdTitles.add(bean.getName());
            }
        }

        return prdTitles;

    }

    public static List<String> getFeedDescriptionTitles(List<FeedItemBean> feedItems) {
        List<String> feedDescTitles = new ArrayList<String>();
        for (FeedItemBean feedItemBean : feedItems) {
            feedDescTitles.add(feedItemBean.getFeedItemDescription());
            Log.d("DEBUG", " setting title : " + feedItemBean.getFeedItemDescription());
        }
        return feedDescTitles;
    }

    public static List<String> getNotificationTitles(List<NotificationDataBean> notificationDataBeans) {
        List<String> notificationTitles = new ArrayList<String>();
        for (NotificationDataBean notificationDataBean : notificationDataBeans) {
            notificationTitles.add(notificationDataBean.getNotificationText());
        }
        return notificationTitles;
    }

}
