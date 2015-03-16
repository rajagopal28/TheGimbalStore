package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 2/23/15.
 */
public class CategoryBean extends BaseServerDataBean {

    @Override
    public String toString() {
        return "CategoryBean{" +
                "shortdescription='" + shortdescription + '\'' +
                ", identifier='" + identifier + '\'' +
                ", name='" + name + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", uniqueId='" + uniqueId + '\'' +
                '}';
    }
}
