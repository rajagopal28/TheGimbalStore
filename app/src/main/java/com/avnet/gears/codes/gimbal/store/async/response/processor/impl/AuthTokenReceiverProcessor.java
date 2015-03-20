package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.accounts.AccountManagerFuture;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.HttpConnectionAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by 914889 on 3/17/15.
 */
public class AuthTokenReceiverProcessor implements AsyncResponseProcessor {

    private AccountManager accountManager;
    private Activity callingActivity;
    private Account userAccount;

    public AuthTokenReceiverProcessor(Activity activity, AccountManager accountManager,
                                      Account userAccount) {
        this.accountManager = accountManager;
        this.userAccount = userAccount;
        this.callingActivity = activity;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeansList) {
        if (accountManager != null) {
            try {
                AccountManagerFuture<Bundle> accountManagerFuture = accountManager.getAuthToken(userAccount,
                        GimbalStoreConstants.AUTH_TOKEN_TYPE.STORE_ACCESS_FULL.toString(),
                        null, callingActivity, null, null);
                Bundle authTokenBundle = accountManagerFuture.getResult();
                String authToken = authTokenBundle.get(AccountManager.KEY_AUTHTOKEN).toString();
                Log.d("DEBUG", "authToken = " + authToken);
                // do something to establish session
                Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(callingActivity.getResources());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.securityToken.toString(),
                        authToken);
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.identifier.toString(),
                        GimbalStoreConstants.StoreParameterValues.signin.toString());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                        GimbalStoreConstants.StoreParameterValues.authentication.toString());
                UserLoginProcessor loginProcessor = new UserLoginProcessor(callingActivity, new Intent());
                HttpConnectionAsyncTask handler = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                        Arrays.asList(new String[]{ServerURLUtil.getStoreServletServerURL(callingActivity.getResources())}),
                        Arrays.asList(paramsMap), null,
                        loginProcessor);
                handler.execute(new String[]{});
                Intent intent = callingActivity.getIntent();
                callingActivity.finish();
                callingActivity.startActivity(intent);
            } catch (Exception e) {
                Log.e("ERROR", e.getMessage(), e);
            }
            return true;
        }
        return false;
    }
}
