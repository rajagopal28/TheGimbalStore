package com.avnet.gears.codes.gimbal.store.async.response.processor;

import com.avnet.gears.codes.gimbal.store.bean.HttpResponseBean;

/**
 * Created by 914889 on 2/25/15.
 */
public interface AsyncResponseProcessor {

    public boolean doProcess(HttpResponseBean responseBean);

}
