package com.avnet.gears.codes.gimbal.store.bean;

/**
 * Created by 914889 on 3/13/15.
 */
public class CookieBean {
    private String value;
    private String domain;

    private String path;

    private String secure;

    private String version;

    private String name;

    private String maxAge;

    private String comment;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getSecure() {
        return secure;
    }

    public void setSecure(String secure) {
        this.secure = secure;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMaxAge() {
        return maxAge;
    }

    public void setMaxAge(String maxAge) {
        this.maxAge = maxAge;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CookieBean{" +
                "value='" + value + '\'' +
                ", domain='" + domain + '\'' +
                ", path='" + path + '\'' +
                ", secure='" + secure + '\'' +
                ", version='" + version + '\'' +
                ", name='" + name + '\'' +
                ", maxAge='" + maxAge + '\'' +
                ", comment='" + comment + '\'' +
                '}';
    }
}
