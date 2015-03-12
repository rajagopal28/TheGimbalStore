package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.accounts.Account;
import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.AuthDataBean;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.List;

/**
 * Created by 914889 on 3/11/15.
 */
public class AuthDataProcessor implements AsyncResponseProcessor {
    private AccountAuthenticatorActivity parentActivity;
    private Intent dataIntent;
    private AccountManager accountManager;

    public AuthDataProcessor(AccountAuthenticatorActivity parentActivity, Intent dataIntent, AccountManager accountManager) {
        this.parentActivity = parentActivity;
        this.dataIntent = dataIntent;
        this.accountManager = accountManager;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeansList) {
        if (responseItemBeansList.size() == 1) {
            ResponseItemBean httpResponseBean = responseItemBeansList.get(0);
            String responseString = httpResponseBean.getResponseString();
            GimbalStoreConstants.HTTP_RESPONSE_CODES responseCode = httpResponseBean.getResponseCode();
            Log.d("PROCESS DEBUG", "" + responseCode);

            if (responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.OK ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.CREATED ||
                    responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.ACCEPTED) {
                // creating the gson parser with html escaping disabled
                Gson gson = new GsonBuilder().disableHtmlEscaping().create();

                JsonReader reader = new JsonReader(new StringReader(responseString));
                reader.setLenient(true);
                AuthDataBean responseBean = gson.fromJson(responseString, AuthDataBean.class);
                Log.d("HTTP DEBUG", " Response Bean = " + responseBean);
                Bundle data = dataIntent.getExtras();
                data.putString(AccountManager.KEY_ACCOUNT_NAME, responseBean.getUsername());
                data.putString(AccountManager.KEY_AUTHTOKEN, responseBean.getAuthToken());
                data.putString(GimbalStoreConstants.AUTHENTICATION_INTENT_ARGS.ARG_USER_PASS.toString(),
                        responseBean.getPassword());
                dataIntent.putExtras(data);
                Account userAccount = new Account(responseBean.getUsername(), responseBean.getAccountType());
                // Creating the account on the device and setting the auth token we got
                // (Not setting the auth token will cause another call to the server to authenticate the user)
                boolean isNewAccount = dataIntent.getBooleanExtra(GimbalStoreConstants.AUTHENTICATION_INTENT_ARGS.ARG_IS_NEW_ACCOUNT.toString(), false);
                if (isNewAccount) {
                    // if new account then create account here
                    accountManager.addAccountExplicitly(userAccount, responseBean.getPassword(), null);
                    accountManager.setAuthToken(userAccount, responseBean.getAuthTokenType().toString(),
                            responseBean.getAuthToken());
                } else {
                    // else update the password for existing account
                    accountManager.setPassword(userAccount, responseBean.getPassword());
                }
                parentActivity.setAccountAuthenticatorResult(dataIntent.getExtras());
                parentActivity.setResult(Activity.RESULT_OK, dataIntent);

            }
            return true;
        }
        return false;
    }
}
