package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 3/15/15.
 */
public class FeedItemBean extends BaseServerDataBean {
    private String feedItemId;
    private String feedItemDescription;
    private String feedItemReadStatus;
    private String feedItemType;

    public String getFeedItemId() {
        return feedItemId;
    }

    public void setFeedItemId(String feedItemId) {
        this.feedItemId = feedItemId;
    }

    public String getFeedItemDescription() {
        return feedItemDescription;
    }

    public void setFeedItemDescription(String feedItemDescription) {
        this.feedItemDescription = feedItemDescription;
    }

    public String getFeedItemReadStatus() {
        return feedItemReadStatus;
    }

    public void setFeedItemReadStatus(String feedItemReadStatus) {
        this.feedItemReadStatus = feedItemReadStatus;
    }

    public String getFeedItemType() {
        return feedItemType;
    }

    public void setFeedItemType(String feedItemType) {
        this.feedItemType = feedItemType;
    }

    @Override
    public String toString() {
        return super.toString() +
                "FeedItemBean{" +
                "feedItemId='" + feedItemId + '\'' +
                ", feedItemDescription='" + feedItemDescription + '\'' +
                ", feedItemReadStatus='" + feedItemReadStatus + '\'' +
                ", feedItemType='" + feedItemType + '\'' +
                '}';
    }
}
