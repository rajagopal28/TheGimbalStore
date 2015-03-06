package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.widget.TableRow;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.HttpResponseBean;

/**
 * Created by 914889 on 3/5/15.
 */
public class SubCategoryItemProcessor implements AsyncResponseProcessor {

    private TableRow itemsTableRow;
    private Activity parentActivity;

    public SubCategoryItemProcessor(TableRow itemsTableRow, Activity parentActivity) {
        this.itemsTableRow = itemsTableRow;
        this.parentActivity = parentActivity;
    }

    @Override
    public boolean doProcess(HttpResponseBean responseBean) {
        return false;
    }
}
