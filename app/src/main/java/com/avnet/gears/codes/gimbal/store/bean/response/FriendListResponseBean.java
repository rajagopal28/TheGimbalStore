package com.avnet.gears.codes.gimbal.store.bean.response;

import com.avnet.gears.codes.gimbal.store.bean.FriendDataBean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/16/15.
 */
public class FriendListResponseBean extends BaseServerResponseBean {
    private FriendDataBean[] CatalogEntryView;

    public FriendDataBean[] getCatalogEntryView() {
        return CatalogEntryView;
    }

    public void setCatalogEntryView(FriendDataBean[] catalogEntryView) {
        CatalogEntryView = catalogEntryView;
    }

    @Override
    public String toString() {
        return super.toString() +
                "FriendListResponseBean{" +
                "CatalogEntryView=" + Arrays.toString(CatalogEntryView) +
                '}';
    }
}
