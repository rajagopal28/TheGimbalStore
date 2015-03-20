package com.avnet.gears.codes.gimbal.store.adapter;

import android.app.Activity;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.ImageDataProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ProductBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.ImageResponseAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 914889 on 3/6/15.
 */
public class ProductsViewAdapter extends ArrayAdapter<String> {

    private ListView productsListView;
    private Activity context;
    private String categoryId;
    private List<ProductBean> productBeanList;

    public ProductsViewAdapter(Activity callingActivity, ListView listView,
                               List<ProductBean> productBeans, List<String> productTitles,
                               String categoryId) {
        super(callingActivity, R.layout.view_product_list_item, productTitles);
        this.context = callingActivity;
        this.productsListView = listView;
        this.productBeanList = productBeans;
        this.categoryId = categoryId;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        ProductBean selectedProductBean = productBeanList.get(position);
        selectedProductBean.setSubCategoryId(categoryId);
        View rowView = inflater.inflate(R.layout.view_product_list_item, null, true);
        ImageView thumbnail = (ImageView) rowView.findViewById(R.id.product_thumbnail);

        TextView productTitle = (TextView) rowView.findViewById(R.id.product_title);
        productTitle.setText(selectedProductBean.getName());
        TextView productPrice = (TextView) rowView.findViewById(R.id.product_description);
        productPrice.setText(selectedProductBean.getPartNumber());
        TextView productRating = (TextView) rowView.findViewById(R.id.product_rating);
        productRating.setText(selectedProductBean.getShortdescription());
        if (selectedProductBean.getThumbnail() != null) {

            String imageToLoad = ServerURLUtil.getAbsoluteUrlFor(context.getResources(),
                    selectedProductBean.getThumbnail());
            String cookieString = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).
                    getString(GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY, null);

            // Log.d("DEBUG", "Loading image of product : " + selectedProductBean.getName());
            ImageDataProcessor imgProcessor = new ImageDataProcessor(context, null,
                    Arrays.asList(new ImageView[]{thumbnail}));
            ImageResponseAsyncTask asyncTask = new ImageResponseAsyncTask(Arrays.asList(new String[]{imageToLoad}),
                    imgProcessor, cookieString);
            asyncTask.execute(new String[]{});
        }
        return rowView;
    }

}
