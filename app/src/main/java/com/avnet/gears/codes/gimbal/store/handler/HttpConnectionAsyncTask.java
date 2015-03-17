package com.avnet.gears.codes.gimbal.store.handler;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;
import com.avnet.gears.codes.gimbal.store.client.HttpClient;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_METHODS;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_RESPONSE_CODES;

import java.io.OutputStream;
import java.net.CookieManager;
import java.net.HttpCookie;
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
    private String cookieString;
    private List<Map<String, String>> parametersMapList;
    private AsyncResponseProcessor processor;

    public HttpConnectionAsyncTask(HTTP_METHODS http_method, List<String> urls,
                                   List<Map<String, String>> parametersMap, String cookieString,
                                   AsyncResponseProcessor processor) {
        this.processor = processor;
        this.httpMethod = http_method;
        this.urls = urls;
        this.cookieString = cookieString;
        this.parametersMapList = parametersMap;
        Log.d("DeBuG", "cookieString = " + cookieString);
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            int index = 0;
            List<ResponseItemBean> responseItemBeans = new ArrayList<ResponseItemBean>();
            for (String url : this.urls) {
                Log.d("DEBUG", "HTTP processing URL" + url + " For HTTP METHOD=" + this.httpMethod);
                HttpURLConnection con = null;
                if (this.httpMethod == HTTP_METHODS.POST) {
                    con = HttpClient.getMultiPartHttpPOSTConnection(url, cookieString);
                    if (parametersMapList != null && parametersMapList.get(index) != null) {
                        OutputStream os = con.getOutputStream();
                        HttpClient.writeMultiPartParamData(os, parametersMapList.get(index));
                        HttpClient.finishMultipart(os);
                        os.flush();
                    }
                } else {
                    con = HttpClient.getHttpGetConnection(url, cookieString, parametersMapList.get(index));
                }
                String responseString = HttpClient.getResponseAsString(con.getInputStream());
                responseString = responseString.trim()
                        .replace(GimbalStoreConstants.START_COMMENT_STRING, "")
                        .replace(GimbalStoreConstants.END_COMMENT_STRING, "");
                CookieManager msCookieManager = new java.net.CookieManager();

                Map<String, List<String>> headerFields = con.getHeaderFields();
                List<String> cookiesHeader = headerFields.get(GimbalStoreConstants.COOKIES_RESPONSE_HEADER);

                if (cookiesHeader != null) {
                    for (String cookie : cookiesHeader) {
                        msCookieManager.getCookieStore().add(null, HttpCookie.parse(cookie).get(0));
                    }
                }
                String cookieString = TextUtils.join(GimbalStoreConstants.DELIMITER_COMMA,
                        msCookieManager.getCookieStore().getCookies());

                ResponseItemBean responseBean = new ResponseItemBean();
                responseBean.setCookieValue(cookieString);
                responseBean.setResponseString(responseString);
                responseBean.setContentType(GimbalStoreConstants.HTTP_HEADER_VALUES.CONTENT_TYPE_TEXT);
                responseBean.setResponseCode(HTTP_RESPONSE_CODES.getCode(con.getResponseCode()));
                // Log.d("DEBUG", "Response = " + responseBean);
                responseItemBeans.add(responseBean);
                con.disconnect();
                index++;
            }
            this.processor.doProcess(responseItemBeans);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GimbalStoreConstants.SUCCESS_STRING;
    }

    protected void onPostExecute(String page) {
        //onPostExecute call the response handler's process
    }
}
