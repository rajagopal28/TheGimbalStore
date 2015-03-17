package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;

import java.util.List;

/**
 * Created by 914889 on 3/13/15.
 */
public class UserLoginProcessor implements AsyncResponseProcessor {
    private Intent passedIntent;
    private Activity parentActivity;

    public UserLoginProcessor(Activity activity, Intent passedIntent) {
        this.passedIntent = passedIntent;
        this.parentActivity = activity;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeansList) {
        if (responseItemBeansList.size() == 1) {
            ResponseItemBean httpResponseBean = responseItemBeansList.get(0);
            String cookie = httpResponseBean.getCookieValue();
            // get shared preference and set session cookie
            AndroidUtil.savePreferenceValue(parentActivity.getApplicationContext(),
                    GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY, cookie);
            Log.d("DEBUG", "Loged in with response = " + httpResponseBean);

            parentActivity.setResult(GimbalStoreConstants.ACTIVITY_RESULT_LOGIN_SUCCESS, passedIntent);
            return true;
        }
        return false;
    }
}
