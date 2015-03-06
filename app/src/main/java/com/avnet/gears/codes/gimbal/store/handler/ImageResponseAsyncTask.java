package com.avnet.gears.codes.gimbal.store.handler;

import android.os.AsyncTask;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.client.HttpClient;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.utils.TypeConversionUtil;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 914889 on 3/4/15.
 */
public class ImageResponseAsyncTask extends AsyncTask<String, List<String>, Object> {
    private List<String> urls;
    private AsyncResponseProcessor processor;

    public ImageResponseAsyncTask(List<String> url, AsyncResponseProcessor processor) {
        this.urls = url;
        this.processor = processor;
    }

    @Override
    protected Object doInBackground(String... params) {
        try {
            List<ResponseItemBean> responseItemBeans = new ArrayList<ResponseItemBean>();
            for(String url : this.urls) {
                HttpURLConnection con = HttpClient.getHttpGetConnection(url, new HashMap<String, String>());
                // Log.d("DEBUG", "in image process \n url="+ this.url );
                GimbalStoreConstants.HTTP_HEADER_VALUES imageResponseType = TypeConversionUtil.getImageTypeFromExtension(url);
                ResponseItemBean responseBean = new ResponseItemBean();
                responseBean.setContentType(imageResponseType);
                responseBean.setImageBmp(HttpClient.getBitmapFromStream(con.getInputStream()));
                responseBean.setResponseCode(GimbalStoreConstants.HTTP_RESPONSE_CODES.getCode(con.getResponseCode()));
                con.disconnect();
                // Log.d("DEBUG", "" + responseBean.toString());
            }
            this.processor.doProcess(responseItemBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GimbalStoreConstants.SUCCESS_STRING;
    }
}
