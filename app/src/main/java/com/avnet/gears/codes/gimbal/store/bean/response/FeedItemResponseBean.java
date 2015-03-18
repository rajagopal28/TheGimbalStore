package com.avnet.gears.codes.gimbal.store.bean.response;

import com.avnet.gears.codes.gimbal.store.bean.FeedItemBean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/15/15.
 */
public class FeedItemResponseBean extends BaseServerResponseBean {
    private FeedItemBean[] Feeds;

    public FeedItemBean[] getFeeds() {
        return Feeds;
    }

    public void setFeeds(FeedItemBean[] feeds) {
        Feeds = feeds;
    }

    @Override
    public String toString() {
        return super.toString() +
                "FeedItemResponseBean{" +
                "Feeds=" + Arrays.toString(Feeds) +
                '}';
    }
}
