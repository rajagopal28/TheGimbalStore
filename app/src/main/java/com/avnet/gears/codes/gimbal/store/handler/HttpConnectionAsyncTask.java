package com.avnet.gears.codes.gimbal.store.handler;

import android.os.AsyncTask;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.client.HttpClient;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_METHODS;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_RESPONSE_CODES;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by 914889 on 2/24/15.
 */
public class HttpConnectionAsyncTask extends AsyncTask<String, List<String>, Object> {

    private String url;
    private HTTP_METHODS httpMethod;
    private String responseString;
    private int responseCode;
    private String responseType;
    private Map<String, String> parametersMap;
    private AsyncResponseProcessor processor;

    public HttpConnectionAsyncTask(HTTP_METHODS http_method, String url,
                                   Map<String, String> parametersMap, AsyncResponseProcessor processor){
        this.processor = processor;
        this.httpMethod = http_method;
        this.url = url;
        this.parametersMap = parametersMap;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            HttpURLConnection con = HttpClient.getHttpGetConnection(this.url, parametersMap);
            this.responseString = HttpClient.getResponseString(con);
            this.responseCode = con.getResponseCode();
            // Log.d("DEBUG", "String response code = " + this.responseCode );
            // Log.d("DEBUG", "Response = " + responseString);
            this.processor.doProcess(HTTP_RESPONSE_CODES.getCode(this.responseCode), this.responseString);
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
