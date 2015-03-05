package com.avnet.gears.codes.gimbal.store.handler;

import android.os.AsyncTask;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.HttpResponseBean;
import com.avnet.gears.codes.gimbal.store.client.HttpClient;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.utils.TypeConversionUtil;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 914889 on 3/4/15.
 */
public class ImageResponseAsyncTask extends AsyncTask<String, List<String>, Object> {
    private String url;
    private AsyncResponseProcessor processor;

    public ImageResponseAsyncTask(String url, AsyncResponseProcessor processor) {
        this.url = url;
        this.processor = processor;
    }

    @Override
    protected Object doInBackground(String... params) {
        try {
            HttpURLConnection con = HttpClient.getHttpGetConnection(this.url, new HashMap<String, String>());
            // Log.d("DEBUG", "in image process \n url="+ this.url );
            GimbalStoreConstants.HTTP_HEADER_VALUES imageResponseType = TypeConversionUtil.getImageTypeFromExtension(this.url);
            HttpResponseBean responseBean = new HttpResponseBean();
            responseBean.setResponseType(imageResponseType);
            responseBean.setResponseStream(con.getInputStream());

            responseBean.setResponseCode(GimbalStoreConstants.HTTP_RESPONSE_CODES.getCode(con.getResponseCode()));
            // Log.d("DEBUG", "" + responseBean.toString());
            this.processor.doProcess(responseBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GimbalStoreConstants.SUCCESS_STRING;
    }
}
