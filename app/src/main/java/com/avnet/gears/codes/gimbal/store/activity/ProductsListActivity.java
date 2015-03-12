package com.avnet.gears.codes.gimbal.store.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.ProductsListProcessor;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.HttpConnectionAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;

import java.util.Arrays;
import java.util.Map;

public class ProductsListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle intentBundle = getIntent().getExtras();
        setContentView(R.layout.activity_products_list);
        if (intentBundle != null) {
            String selectedSubCategoryId = intentBundle.getString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_SUB_CATEGORY_ID.toString(), "");
            if (!"".equals(selectedSubCategoryId)) {
                // get products list from server
                Log.d("DEBUG", "obtained sub cat id =" + selectedSubCategoryId);
                ListView productsListView = (ListView) findViewById(R.id.products_list_view);
                ProgressDialog dialog = AndroidUtil.showProgressDialog(this,
                        GimbalStoreConstants.DEFAULT_SPINNER_TITLE,
                        GimbalStoreConstants.DEFAULT_SPINNER_INFO_TEXT);
                ProductsListProcessor productsListProcessor = new ProductsListProcessor(this, productsListView, dialog);
                Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.identifier.toString(),
                        GimbalStoreConstants.StoreParameterValues.top.toString());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                        GimbalStoreConstants.StoreParameterValues.category.toString());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.parentCategoryId.toString(),
                        selectedSubCategoryId);

                HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                        Arrays.asList(new String[]{ServerURLUtil.getStoreServletServerURL(getResources())}),
                        paramsMap, productsListProcessor);
                asyncTask.execute(new String[]{});

            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
