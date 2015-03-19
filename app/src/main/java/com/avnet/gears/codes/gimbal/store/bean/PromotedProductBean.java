package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 3/19/15.
 */
public class PromotedProductBean extends BaseServerDataBean {
    private String RecommendedBy;
    private String price;

    public String getRecommendedBy() {
        return RecommendedBy;
    }

    public void setRecommendedBy(String recommendedBy) {
        RecommendedBy = recommendedBy;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return super.toString() +
                "PromotedProductBean{" +
                "RecommendedBy='" + RecommendedBy + '\'' +
                ", price='" + price + '\'' +
                '}';
    }
}
