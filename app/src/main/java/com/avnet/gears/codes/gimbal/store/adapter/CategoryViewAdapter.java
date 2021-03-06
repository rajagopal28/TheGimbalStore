package com.avnet.gears.codes.gimbal.store.adapter;

import android.app.Activity;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.activity.ProductDetailsActivity;
import com.avnet.gears.codes.gimbal.store.activity.ProductsListActivity;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.ImageDataProcessor;
import com.avnet.gears.codes.gimbal.store.bean.ProductBean;
import com.avnet.gears.codes.gimbal.store.bean.SubCategoryBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.ImageResponseAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 914889 on 2/23/15.
 */
public class CategoryViewAdapter extends ArrayAdapter<String> {
    private final Activity context;

    private List<SubCategoryBean> scBeansList;

    public CategoryViewAdapter(Activity context,
                               List<SubCategoryBean> list, List<String> scTitles) {
        super(context, R.layout.view_subcategory_list_item, scTitles);
        scBeansList = list;
        this.context = context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final SubCategoryBean selectedSubCategoryBean = scBeansList.get(position);
        View rowView = inflater.inflate(R.layout.view_subcategory_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.sub_category_name);
        String seeAll = GimbalStoreConstants.DELIMITER_SPACE + context.getResources().getString(R.string.link_see_all);
        txtTitle.setText(selectedSubCategoryBean.getName() + seeAll);
        txtTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG", "Clicking see all>>" + selectedSubCategoryBean.getUniqueId() + " >> " + selectedSubCategoryBean.getName());
                Intent intent = new Intent(context, ProductsListActivity.class);
                intent.putExtra(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_SUB_CATEGORY_ID.toString(),
                        selectedSubCategoryBean.getUniqueId());

                context.startActivity(intent);
            }
        });
        LinearLayout itemsRowView = (LinearLayout) rowView.findViewById(R.id.items_table_scroll_view);
        ProductBean[] topBrowsedProducts = selectedSubCategoryBean.getTopBrowsed();
        List<String> imageUrls = new ArrayList<String>();
        List<ImageView> imageViewsList = new ArrayList<ImageView>();
        Log.d("DEBUG", "total items in scat = " + topBrowsedProducts.length);
        for (final ProductBean productBean : topBrowsedProducts) {
            // Log.d("DEBUG", "product item = " + productBean);
            if (productBean.getThumbnail() != null) {
                ImageView imageView = new ImageView(context);
                imageView.setPadding(10, 10, 10, 10);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ProductDetailsActivity.class);
                        intent.putExtra(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString(),
                                productBean.getUniqueId());
                        intent.putExtra(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_SUB_CATEGORY_ID.toString(),
                                selectedSubCategoryBean.getUniqueId());
                        context.startActivity(intent);
                    }
                });
                String absoluteImageUrl = ServerURLUtil.getAbsoluteUrlFor(context.getResources(), productBean.getThumbnail());
                imageUrls.add(absoluteImageUrl);
                imageViewsList.add(imageView);
            }
        }
        // Log.d("DEBUG", "sending image urls" + imageUrls);
        ImageDataProcessor imageDataProcessor = new ImageDataProcessor(context, itemsRowView, imageViewsList);
        String cookieString = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).
                getString(GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY, null);

        ImageResponseAsyncTask imageResponseAsyncTask = new ImageResponseAsyncTask(imageUrls,
                imageDataProcessor, cookieString);
        imageResponseAsyncTask.execute(new String[]{});
        // Log.d("DEBUG", "processed images of sub cat" + selectedSubCategoryBean.getName());
        return rowView;
    }

}
