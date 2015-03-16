package com.avnet.gears.codes.gimbal.store.bean.response;

import com.avnet.gears.codes.gimbal.store.bean.CategoryBean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/4/15.
 */
public class CategoryResponseBean extends BaseServerResponseBean {

    private CategoryBean[] CatalogGroupView;

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
