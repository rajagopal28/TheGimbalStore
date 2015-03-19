package com.avnet.gears.codes.gimbal.store.adapter;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.activity.ProductDetailsActivity;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.ImageDataProcessor;
import com.avnet.gears.codes.gimbal.store.bean.PromotedProductBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.ImageResponseAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 914889 on 3/19/15.
 */
public class PromotedProductsAdapter extends ArrayAdapter<String> implements AdapterView.OnItemClickListener {

    private ListView promotionsListView;
    private Activity context;
    private List<PromotedProductBean> promotedProductBeans;

    public PromotedProductsAdapter(Activity callingActivity, ListView listView,
                                   List<PromotedProductBean> promotedProducts, List<String> productTitles) {
        super(callingActivity, R.layout.view_promotions_list_item, productTitles);
        this.context = callingActivity;
        this.promotionsListView = listView;
        this.promotedProductBeans = promotedProducts;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        PromotedProductBean selectedProductBean = promotedProductBeans.get(position);
        View rowView = inflater.inflate(R.layout.view_promotions_list_item, null, true);
        ImageView thumbnail = (ImageView) rowView.findViewById(R.id.product_thumbnail);

        TextView promotionTitle = (TextView) rowView.findViewById(R.id.promotion_title);
        promotionTitle.setText(selectedProductBean.getName());
        TextView productPrice = (TextView) rowView.findViewById(R.id.product_price);
        productPrice.setText(selectedProductBean.getPrice());
        TextView productRating = (TextView) rowView.findViewById(R.id.product_description);
        productRating.setText(selectedProductBean.getShortdescription());
        if (selectedProductBean.getThumbnail() != null) {

            String imageToLoad = ServerURLUtil.getAbsoluteUrlFor(context.getResources(),
                    selectedProductBean.getThumbnail());
            String cookieString = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).
                    getString(GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY, null);

            Log.d("DEBUG", "Loading image of product : " + selectedProductBean.getName());
            ImageDataProcessor imgProcessor = new ImageDataProcessor(context, null,
                    Arrays.asList(new ImageView[]{thumbnail}));
            ImageResponseAsyncTask asyncTask = new ImageResponseAsyncTask(Arrays.asList(new String[]{imageToLoad}),
                    imgProcessor, cookieString);
            asyncTask.execute(new String[]{});
        }
        return rowView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PromotedProductBean selectedProduct = promotedProductBeans.get(position);
        if (selectedProduct != null) {
            // open selected product
            Intent intent = new Intent(context, ProductDetailsActivity.class);
            intent.putExtra(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString(),
                    selectedProduct.getUniqueId());
            context.startActivity(intent);
        }
    }
}
