package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 3/16/15.
 */
public class ReviewBean extends BaseServerDataBean {
    private String Review;
    private String Rating;
    private String Friend;

    public String getReview() {
        return Review;
    }

    public void setReview(String review) {
        Review = review;
    }

    public String getRating() {
        return Rating;
    }

    public void setRating(String rating) {
        Rating = rating;
    }

    public String getFriend() {
        return Friend;
    }

    public void setFriend(String friend) {
        Friend = friend;
    }

    @Override
    public String toString() {
        return super.toString()+
                "ReviewBean{" +
                "Review='" + Review + '\'' +
                ", Rating='" + Rating + '\'' +
                ", Friend='" + Friend + '\'' +
                '}';
    }
}
