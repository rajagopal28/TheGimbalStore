package com.avnet.gears.codes.gimbal.store.bean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/6/15.
 */
public class ProductsResponseBean extends BaseServerResponseBean {


    private ProductBean[] CatalogEntryView;

    public ProductBean[] getCatalogEntryView() {
        return CatalogEntryView;
    }

    public void setCatalogEntryView(ProductBean[] catalogEntryView) {
        CatalogEntryView = catalogEntryView;
    }

    @Override
    public String toString() {
        return "SubCategoryResponseBean{" +
                "catalogId=" + Arrays.toString(catalogId) +
                ", langId=" + Arrays.toString(langId) +
                ", type=" + Arrays.toString(type) +
                ", storeId=" + Arrays.toString(storeId) +
                ", CatalogGroupView=" + Arrays.toString(CatalogEntryView) +
                '}';
    }
}
