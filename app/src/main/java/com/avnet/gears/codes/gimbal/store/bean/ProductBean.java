package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 3/6/15.
 */
public class ProductBean {
    private String shortdescription;
    private String name;
    private String partNumber;
    private String thumbnail;
    private String uniqueId;

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "ProductBean{" +
                "shortdescription='" + shortdescription + '\'' +
                ", name='" + name + '\'' +
                ", partNumber='" + partNumber + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                '}';
    }
}
