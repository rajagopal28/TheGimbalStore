package com.avnet.gears.codes.gimbal.store.bean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/5/15.
 */
public class SubCategoryBean extends BaseServerDataBean {
    private ProductBean[] topBrowsed;
    private String partNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public ProductBean[] getTopBrowsed() {
        return topBrowsed;
    }

    public void setTopBrowsed(ProductBean[] topBrowsed) {
        this.topBrowsed = topBrowsed;
    }

    public String getPartNumber() {
        return partNumber;
    }

    public void setPartNumber(String partNumber) {
        this.partNumber = partNumber;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    @Override
    public String toString() {
        return "SubCategoryBean{" +
                "shortdescription='" + shortdescription + '\'' +
                ", name='" + name + '\'' +
                ", partNumber='" + partNumber + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                ", topBrowsed=" + Arrays.toString(topBrowsed) +
                '}';
    }
}
