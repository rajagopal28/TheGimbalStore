package com.avnet.gears.codes.gimbal.store.delegate;

import android.util.Log;

import com.avnet.gears.codes.gimbal.store.bean.CategoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 914889 on 2/24/15.
 */
public class GimbalStoreRESTServiceDelegate {
    public static List<CategoryBean> getAllCategories() {
        Log.d("DEBUG","Entering GimbalStoreRESTServiceDelegate.getAllCategories()");
        List<CategoryBean> allCategoriesList = new ArrayList<CategoryBean>();
        Log.d("DEBUG","Exiting GimbalStoreRESTServiceDelegate.getAllCategories()");
        return allCategoriesList;
    }
}
