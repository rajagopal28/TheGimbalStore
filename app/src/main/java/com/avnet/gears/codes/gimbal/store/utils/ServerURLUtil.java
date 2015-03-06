package com.avnet.gears.codes.gimbal.store.utils;

import android.content.res.Resources;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 914889 on 3/4/15.
 */
public class ServerURLUtil {
    private static String SEPARATOR_SLASH = "/";

    public static String getStoreServletServerURL(Resources resource) {
        StringBuilder activeServerURL = new StringBuilder();
        activeServerURL.append(resource.getString(R.string.SERVER_PROTOCOL));
        activeServerURL.append(resource.getString(R.string.STORE_SERVICE_HOST));
        activeServerURL.append(resource.getString(R.string.STORE_SERVLET_CONTEXT_ROOT));
        activeServerURL.append(resource.getString(R.string.MOBILE_ACCESS_CONTROLLER_SERVLET));
        return activeServerURL.toString();
    }
    public static String getAbsoluteUrlFor(Resources resource, String relativeURL){
        StringBuilder activeServerURL = new StringBuilder();
        activeServerURL.append(resource.getString(R.string.SERVER_PROTOCOL));
        activeServerURL.append(resource.getString(R.string.STORE_SERVICE_HOST));
        if(!relativeURL.startsWith(SEPARATOR_SLASH)) {
            activeServerURL.append(SEPARATOR_SLASH);
        }
        activeServerURL.append(relativeURL);
        return activeServerURL.toString();
    }
    public static Map<String,String> getBasicConfigParamsMap(Resources resource) {
        Map<String,String> paramsMap = new HashMap<String, String>();
        paramsMap.put(GimbalStoreConstants.StoreParameterKeys.langId.toString(),
                String.valueOf(resource.getString(R.string.ACTIVE_STORE_LANG_ID)));
        paramsMap.put(GimbalStoreConstants.StoreParameterKeys.storeId.toString(),
                String.valueOf(resource.getString(R.string.ACTIVE_STORE_ID)));
        paramsMap.put(GimbalStoreConstants.StoreParameterKeys.catalogId.toString(),
                String.valueOf(resource.getString(R.string.DEFAULT_CATALOG_ID)));
        return paramsMap;
    }
}
