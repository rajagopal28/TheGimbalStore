package com.avnet.gears.codes.gimbal.store.bean.response;

import com.avnet.gears.codes.gimbal.store.bean.RecommendationBean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/15/15.
 */
public class RecommendationResponseBean extends BaseServerResponseBean {

    private RecommendationBean[] Recommendations;

    public RecommendationBean[] getRecommendations() {
        return Recommendations;
    }

    public void setRecommendations(RecommendationBean[] recommendations) {
        Recommendations = recommendations;
    }

    @Override
    public String toString() {
        return super.toString() +
                "RecommendationResponseBean{" +
                "Recommendations=" + Arrays.toString(Recommendations) +
                '}';
    }
}
