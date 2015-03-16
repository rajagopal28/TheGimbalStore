package com.avnet.gears.codes.gimbal.store.bean.response;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by 914889 on 3/13/15.
 */
public class BaseServerResponseBean implements Serializable {
    protected String[] storeId;
    protected String[] type;
    protected String[] identifier;
    protected String[] langId;
    protected String[] catalogId;

    public String[] getStoreId() {
        return storeId;
    }

    public void setStoreId(String[] storeId) {
        this.storeId = storeId;
    }

    public String[] getType() {
        return type;
    }

    public void setType(String[] type) {
        this.type = type;
    }

    public String[] getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String[] identifier) {
        this.identifier = identifier;
    }

    public String[] getLangId() {
        return langId;
    }

    public void setLangId(String[] langId) {
        this.langId = langId;
    }


    public String[] getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(String[] catalogId) {
        this.catalogId = catalogId;
    }

    @Override
    public String toString() {
        return "BaseServerResponseBean{" +
                "storeId=" + Arrays.toString(storeId) +
                ", type=" + Arrays.toString(type) +
                ", identifier=" + Arrays.toString(identifier) +
                ", langId=" + Arrays.toString(langId) +
                ", catalogId=" + Arrays.toString(catalogId) +
                '}';
    }

}
