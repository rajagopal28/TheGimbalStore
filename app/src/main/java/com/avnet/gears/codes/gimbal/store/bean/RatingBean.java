package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 3/18/15.
 */
public class RatingBean {
    private ReviewStatistics ReviewStatistics;

    public ReviewStatistics getReviewStatistics() {
        return ReviewStatistics;
    }

    public void setReviewStatistics(ReviewStatistics reviewStatistics) {
        ReviewStatistics = reviewStatistics;
    }

    @Override
    public String toString() {
        return "RatingBean{" +
                "ReviewStatistics=" + ReviewStatistics +
                '}';
    }
}
