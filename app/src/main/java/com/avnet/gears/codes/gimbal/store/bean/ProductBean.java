package com.avnet.gears.codes.gimbal.store.bean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/6/15.
 */
public class ProductBean extends BaseServerDataBean {

    private String partNumber;
    private String price;


    private ReviewDataBean reviews;
    private String[] attribute;

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public ReviewDataBean getReviews() {
        return reviews;
    }

    public void setReviews(ReviewDataBean reviews) {
        this.reviews = reviews;
    }

    public String[] getAttribute() {
        return attribute;
    }

    public void setAttribute(String[] attribute) {
        this.attribute = attribute;
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
