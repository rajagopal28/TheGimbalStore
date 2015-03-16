package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.FriendDataBean;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.bean.response.FriendListResponseBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.fragment.dialog.FriendsListDialogFragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import java.io.StringReader;
import java.util.List;

/**
 * Created by 914889 on 3/16/15.
 */
public class FriendsListDataProcessor implements AsyncResponseProcessor {
    private Activity callingActivity;
    private ProgressDialog progressDialog;
    private FragmentManager fragmentManager;

    public FriendsListDataProcessor(Activity callingActivity, ProgressDialog progressDialog, FragmentManager fm) {
        this.callingActivity = callingActivity;
        this.progressDialog = progressDialog;
        this.fragmentManager = fm;
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
                FriendListResponseBean responseBean = gson.fromJson(reader, FriendListResponseBean.class);
                Log.d("HTTP DEBUG", " Response Bean = " + responseBean);
                FriendDataBean[] friendDataBeans = responseBean.getCatalogEntryView();
                if (friendDataBeans != null && friendDataBeans.length > 0) {
                    FriendsListDialogFragment dialogFragment = FriendsListDialogFragment.newInstance(responseBean);
                    dialogFragment.show(fragmentManager, GimbalStoreConstants.TAG_SHOW_FRIENDS_LIST);
                }
            }
            progressDialog.dismiss();
            return true;
        }
        return false;
    }
}
