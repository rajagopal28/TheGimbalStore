package com.avnet.gears.codes.gimbal.store.async.response.processor.impl;

import android.app.Activity;
import android.widget.TableRow;

import com.avnet.gears.codes.gimbal.store.async.response.processor.AsyncResponseProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ResponseItemBean;

import java.util.List;

/**
 * Created by 914889 on 3/5/15.
 */
public class SubCategoryItemProcessor implements AsyncResponseProcessor {

    private TableRow itemsTableRow;
    private Activity parentActivity;

    public SubCategoryItemProcessor( Activity parentActivity, TableRow itemsTableRow) {
        this.itemsTableRow = itemsTableRow;
        this.parentActivity = parentActivity;
    }

    @Override
    public boolean doProcess(List<ResponseItemBean> responseItemBeanList) {

        return false;
    }
}
