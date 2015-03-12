package com.avnet.gears.codes.gimbal.store.handler;

import android.os.AsyncTask;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 914889 on 3/12/15.
 */
public class GenericAsyncTask extends AsyncTask<Void, Void, Object> {

    private AsyncResponseProcessor processor;

    public GenericAsyncTask(AsyncResponseProcessor processor) {
        this.processor = processor;
    }

    @Override
    protected Object doInBackground(Void... params) {
        List<ResponseItemBean> responseItemBeans = new ArrayList<ResponseItemBean>();
        this.processor.doProcess(responseItemBeans);
        return null;
    }
}
