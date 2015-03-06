package com.avnet.gears.codes.gimbal.store.adapter;

import android.app.Activity;
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
import com.avnet.gears.codes.gimbal.store.handler.ImageResponseAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 914889 on 3/6/15.
 */
public class ProductsViewAdapter  extends ArrayAdapter<String> {

    private ListView productsListView;
    private Activity context;
    private List<ProductBean> productBeanList;

    public ProductsViewAdapter(Activity callingActivity, ListView listView,
                               List<ProductBean> productBeans, List<String> productTitles){
        super(callingActivity, R.layout.view_product_list_item, productTitles);
        this.context = callingActivity;
        this.productsListView = listView;
        this.productBeanList = productBeans;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        ProductBean selectedProductBean = productBeanList.get(position);
        View rowView= inflater.inflate(R.layout.view_product_list_item, null, true);
        ImageView thumbnail = (ImageView)rowView.findViewById(R.id.product_thumbnail);

        TextView productTitle = (TextView) rowView.findViewById(R.id.product_title);
        productTitle.setText(selectedProductBean.getName());
        TextView productPrice = (TextView) rowView.findViewById(R.id.product_price);
        productPrice.setText(selectedProductBean.getPartNumber());
        if(selectedProductBean.getThumbnail() != null) {

            String imageToLoad = ServerURLUtil.getAbsoluteUrlFor(context.getResources(),
                    selectedProductBean.getThumbnail());
            ImageDataProcessor imgProcessor = new ImageDataProcessor(context, null,
                    false, Arrays.asList(new ImageView[]{thumbnail}));
            ImageResponseAsyncTask asyncTask = new ImageResponseAsyncTask(Arrays.asList(new String[]{imageToLoad}), imgProcessor);
            asyncTask.execute(new String[]{});
        }
        return rowView;
    }
}
