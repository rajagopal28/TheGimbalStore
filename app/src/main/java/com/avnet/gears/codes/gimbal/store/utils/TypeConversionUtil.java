package com.avnet.gears.codes.gimbal.store.utils;

import android.util.Log;

import com.avnet.gears.codes.gimbal.store.bean.CategoryBean;

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
        Log.d("DEBUG", categoryTitleList.toArray(new String[]{}).toString());
        return categoryTitleList.toArray(new String[]{});
    }

}
