package com.avnet.gears.codes.gimbal.store.handler;

import android.os.AsyncTask;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.client.HttpClient;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_METHODS;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_RESPONSE_CODES;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 914889 on 2/24/15.
 */
public class HttpConnectionAsyncTask extends AsyncTask<String, List<String>, Object> {

    private List<String> urls;
    private HTTP_METHODS httpMethod;
    private String responseType;
    private Map<String, String> parametersMap;
    private AsyncResponseProcessor processor;

    public HttpConnectionAsyncTask(HTTP_METHODS http_method, List<String> urls,
                                   Map<String, String> parametersMap, AsyncResponseProcessor processor){
        this.processor = processor;
        this.httpMethod = http_method;
        this.urls = urls;
        this.parametersMap = parametersMap;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            List<ResponseItemBean> responseItemBeans = new ArrayList<ResponseItemBean>();
            for(String url : this.urls){
                HttpURLConnection con = null;
                if(this.httpMethod == HTTP_METHODS.POST) {
                    con = HttpClient.getHttPOSTConnection(url);
                } else {
                    con = HttpClient.getHttpGetConnection(url, parametersMap);
                }
                String responseString = HttpClient.getResponseAsString(con.getInputStream());
                responseString = responseString.trim()
                        .replace(GimbalStoreConstants.START_COMMENT_STRING, "")
                        .replace(GimbalStoreConstants.END_COMMENT_STRING, "");
                // Log.d("DEBUG", "String response code = " + this.responseCode );
                // Log.d("DEBUG", "Response = " + responseString);
                ResponseItemBean responseBean = new ResponseItemBean();
                responseBean.setResponseString(responseString);
                responseBean.setContentType(GimbalStoreConstants.HTTP_HEADER_VALUES.CONTENT_TYPE_TEXT);
                responseBean.setResponseCode(HTTP_RESPONSE_CODES.getCode(con.getResponseCode()));

                responseItemBeans.add(responseBean);
                con.disconnect();
            }
            this.processor.doProcess(responseItemBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GimbalStoreConstants.SUCCESS_STRING;
    }
    protected void onPostExecute(String page)
    {
        //onPostExecute call the response handler's process
    }
 }
