package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.app.ProgressDialog;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ContactsSyncDataBean;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.List;

/**
 * Created by 914889 on 3/13/15.
 */
public class ContactsSyncProcessor implements AsyncResponseProcessor {
    private ProgressDialog dialog;
    private Activity callerActivity;

    public ContactsSyncProcessor(Activity activity, ProgressDialog progressDialog) {
        this.callerActivity = activity;
        this.dialog = progressDialog;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeansList) {
        int totalSyncedContacts = 0;
        if (!responseItemBeansList.isEmpty()) {
            for (ResponseItemBean responseItemBean : responseItemBeansList) {

                GimbalStoreConstants.HTTP_RESPONSE_CODES responseCode = responseItemBean.getResponseCode();
                if (responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.OK ||
                        responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.CREATED ||
                        responseCode == GimbalStoreConstants.HTTP_RESPONSE_CODES.ACCEPTED) {
                    String responseString = responseItemBean.getResponseString();

                    responseString = responseString.trim()
                            .replace(GimbalStoreConstants.START_COMMENT_STRING, "")
                            .replace(GimbalStoreConstants.END_COMMENT_STRING, "");

                    // get the list of sub categories and populate it to the adapter
                    Gson gson = new GsonBuilder().disableHtmlEscaping().create();
                    JsonReader reader = new JsonReader(new StringReader(responseString));
                    reader.setLenient(true);
                    final ContactsSyncDataBean contactsSyncDataBean = gson.fromJson(responseString, ContactsSyncDataBean.class);
                    Log.d("DEBUG", "Displaying Contact sync Details.." + contactsSyncDataBean);
                    //totalSyncedContacts += contactsSyncDataBean.getNumberOfSyncedContacts();

                }
            }
            if (dialog != null) {
                dialog.dismiss();
            }
            Log.d("DEBUG", "totalSyncedContacts=" + totalSyncedContacts);
            return true;
        }
        return false;
    }
}
