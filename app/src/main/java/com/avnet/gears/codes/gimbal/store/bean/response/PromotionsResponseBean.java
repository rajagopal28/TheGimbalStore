package com.avnet.gears.codes.gimbal.store.bean.response;

import com.avnet.gears.codes.gimbal.store.bean.PromotedProductBean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/19/15.
 */
public class PromotionsResponseBean extends BaseServerResponseBean {
    private String[] beaconId;
    private PromotedProductBean[] Wishlist;
    private PromotedProductBean[] BestSellers;
    private PromotedProductBean[] RecentlyViewed;
    private PromotedProductBean[] Recommendations;

    public String[] getBeaconId() {
        return beaconId;
    }

    public void setBeaconId(String[] beaconId) {
        this.beaconId = beaconId;
    }

    public PromotedProductBean[] getWishlist() {
        return Wishlist;
    }

    public void setWishlist(PromotedProductBean[] wishlist) {
        Wishlist = wishlist;
    }

    public PromotedProductBean[] getBestSellers() {
        return BestSellers;
    }

    public void setBestSellers(PromotedProductBean[] bestSellers) {
        BestSellers = bestSellers;
    }

    public PromotedProductBean[] getRecentlyViewed() {
        return RecentlyViewed;
    }

    public void setRecentlyViewed(PromotedProductBean[] recentlyViewed) {
        RecentlyViewed = recentlyViewed;
    }

    public PromotedProductBean[] getRecommendations() {
        return Recommendations;
    }

    public void setRecommendations(PromotedProductBean[] recommendations) {
        Recommendations = recommendations;
    }

    @Override
    public String toString() {
        return super.toString() +
                "PromotionsResponseBean{" +
                "beaconId=" + Arrays.toString(beaconId) +
                ", Wishlist=" + Arrays.toString(Wishlist) +
                ", BestSellers=" + Arrays.toString(BestSellers) +
                ", RecentlyViewed=" + Arrays.toString(RecentlyViewed) +
                ", Recommendations=" + Arrays.toString(Recommendations) +
                '}';
    }
}
