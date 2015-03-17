package com.avnet.gears.codes.gimbal.store.bean.response;

import com.avnet.gears.codes.gimbal.store.bean.ReviewResponseBean;

/**
 * Created by 914889 on 3/17/15.
 */
public class PostReviewResponseBean extends BaseServerResponseBean {
    private ReviewResponseBean reviewResponse;

    public ReviewResponseBean getReviewResponse() {
        return reviewResponse;
    }

    public void setReviewResponse(ReviewResponseBean reviewResponse) {
        this.reviewResponse = reviewResponse;
    }

    @Override
    public String toString() {
        return super.toString() +
                "PostReviewResponseBean{" +
                "reviewResponse=" + reviewResponse +
                '}';
    }
}
