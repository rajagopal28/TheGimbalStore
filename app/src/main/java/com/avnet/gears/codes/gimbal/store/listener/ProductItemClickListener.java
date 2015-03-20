package com.avnet.gears.codes.gimbal.store.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.avnet.gears.codes.gimbal.store.activity.ProductDetailsActivity;
import com.avnet.gears.codes.gimbal.store.bean.ProductBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

import java.util.List;

/**
 * Created by 914889 on 3/9/15.
 */
public class ProductItemClickListener implements AdapterView.OnItemClickListener {

    private Activity parentActivity;
    private List<ProductBean> productBeanList;

    public ProductItemClickListener(Activity parentActivity, List<ProductBean> productBeanList) {
        this.parentActivity = parentActivity;
        this.productBeanList = productBeanList;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // get the corresponding product id

        ProductBean selectedProduct = productBeanList.get(position);
        // redirect to product details activity
        if (selectedProduct.getUniqueId() != null) {
            Intent intent = new Intent(parentActivity, ProductDetailsActivity.class);
            intent.putExtra(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString(),
                    selectedProduct.getUniqueId());
            intent.putExtra(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_SUB_CATEGORY_ID.toString(),
                    selectedProduct.getSubCategoryId());
            parentActivity.startActivity(intent);
        }

    }
}
