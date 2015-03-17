package com.avnet.gears.codes.gimbal.store.bean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/6/15.
 */
public class ProductBean extends BaseServerDataBean {

    private String partNumber;
    private String price;
    private String rating;


    private ReviewBean reviews;
    private String[] attribute;

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public ReviewBean getReviews() {
        return reviews;
    }

    public void setReviews(ReviewBean reviews) {
        this.reviews = reviews;
    }

    public String[] getAttribute() {
        return attribute;
    }

    public void setAttribute(String[] attribute) {
        this.attribute = attribute;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setUniqueId(String[] uniqueId) {
        this.uniqueId = uniqueId[0];
    }

    @Override
    public String toString() {
        return super.toString() +
                "ProductBean{" +
                "partNumber='" + partNumber + '\'' +
                ", reviews='" + reviews + '\'' +
                ", attribute=" + Arrays.toString(attribute) +
                '}';
    }
}
