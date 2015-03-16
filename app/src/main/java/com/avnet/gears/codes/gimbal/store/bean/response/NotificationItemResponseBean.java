package com.avnet.gears.codes.gimbal.store.bean.response;

import com.avnet.gears.codes.gimbal.store.bean.NotificationDataBean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/15/15.
 */
public class NotificationItemResponseBean extends BaseServerResponseBean {
    private NotificationDataBean[] CatalogEntryView;

    public NotificationDataBean[] getCatalogEntryView() {
        return CatalogEntryView;
    }

    public void setCatalogEntryView(NotificationDataBean[] catalogEntryView) {
        CatalogEntryView = catalogEntryView;
    }

    @Override
    public String toString() {
        return super.toString() +
                "NotificationItemResponseBean{" +
                "CatalogEntryView=" + Arrays.toString(CatalogEntryView) +
                '}';
    }
}
