package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 3/18/15.
 */
public class ReviewStatistics {
    private String AverageOverallRating;

    public String getAverageOverallRating() {
        return AverageOverallRating;
    }

    public void setAverageOverallRating(String averageOverallRating) {
        AverageOverallRating = averageOverallRating;
    }

    @Override
    public String toString() {
        return "ReviewStatistics{" +
                "AverageOverallRating='" + AverageOverallRating + '\'' +
                '}';
    }
}
