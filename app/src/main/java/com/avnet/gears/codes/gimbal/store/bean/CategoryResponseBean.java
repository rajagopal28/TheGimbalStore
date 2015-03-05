package com.avnet.gears.codes.gimbal.store.bean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/4/15.
 */
public class CategoryResponseBean {
    private String[] storeId;
    private String[] type;
    private String[] identifier;
    private String[] langId;
    private CategoryBean[] CatalogGroupView;

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

    public CategoryBean[] getCatalogGroupView() {
        return CatalogGroupView;
    }

    public void setCatalogGroupView(CategoryBean[] catalogGroupView) {
        CatalogGroupView = catalogGroupView;
    }

    @Override
    public String toString() {
        return "CategoryResponseBean{" +
                "storeId=" + Arrays.toString(storeId) +
                ", type=" + Arrays.toString(type) +
                ", identifier=" + Arrays.toString(identifier) +
                ", langId=" + Arrays.toString(langId) +
                ", CatalogGroupView=" + Arrays.toString(CatalogGroupView) +
                '}';
    }
}
