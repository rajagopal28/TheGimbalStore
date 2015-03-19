package com.avnet.gears.codes.gimbal.store.bean;

import java.util.Arrays;

/**
 * Created by 914883 on 3/19/15.
 */
public class ReviewDataBean {
    private String AverageRating;
    private ReviewBean[] Reviews;

    public String getAverageRating() {
        return AverageRating;
    }

    public void setAverageRating(String averageRating) {
        AverageRating = averageRating;
    }

    public ReviewBean[] getReviews() {
        return Reviews;
    }

    public void setReviews(ReviewBean[] reviews) {
        Reviews = reviews;
    }

    @Override
    public String toString() {
        return "ReviewDataBean{" +
                "AverageRating='" + AverageRating + '\'' +
                ", Reviews=" + Arrays.toString(Reviews) +
                '}';
    }
}
