package com.avnet.gears.codes.gimbal.store.bean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/15/15.
 */
public class FeedItemResponseBean extends BaseServerResponseBean {
    private FeedItemBean[] CatalogEntryView;

    public FeedItemResponseBean(FeedItemBean[] catalogEntryView) {
        CatalogEntryView = catalogEntryView;
    }

    public FeedItemBean[] getCatalogEntryView() {
        return CatalogEntryView;
    }

    public void setCatalogEntryView(FeedItemBean[] catalogEntryView) {
        CatalogEntryView = catalogEntryView;
    }

    @Override
    public String toString() {
        return super.toString() +
                "FeedItemResponseBean{" +
                "CatalogEntryView=" + Arrays.toString(CatalogEntryView) +
                '}';
    }
}
