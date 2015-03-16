package com.avnet.gears.codes.gimbal.store.bean;

import java.io.Serializable;

/**
 * Created by 914889 on 3/13/15.
 */
public class BaseServerDataBean implements Serializable {

    protected String shortdescription;
    protected String identifier;
    protected String name;
    protected String thumbnail;
    protected String uniqueId;

    public String getShortdescription() {
        return shortdescription;
    }

    public void setShortdescription(String shortdescription) {
        this.shortdescription = shortdescription;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "BaseServerDataBean{" +
                "shortdescription='" + shortdescription + '\'' +
                ", identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                '}';
    }
}
