package com.avnet.gears.codes.gimbal.store.async.response.processor;

import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;

import java.util.List;

/**
 * Created by 914889 on 2/25/15.
 */
public interface AsyncResponseProcessor {

    public boolean doProcess(List<ResponseItemBean> responseItemBeansList);

}
