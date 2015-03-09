package com.avnet.gears.codes.gimbal.store.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

public class ProductDetailsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String selectedProductId = "";
        Bundle intentBundle = getIntent().getExtras();
        if (intentBundle != null) {
            selectedProductId = intentBundle.getString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString(), "");
        }
        Log.d("DEBUG", "Displaying details of product : " + selectedProductId);
        // Make async calls
        /*if(!"".equals(selectedProductId)) {
            ProgressDialog dialog = NotificationUtil.showProgressDialog(this,
                    GimbalStoreConstants.DEFAULT_SPINNER_TITLE,
                    GimbalStoreConstants.DEFAULT_SPINNER_INFO_TEXT);
            ImageView productImage = (ImageView) findViewById(R.id.product_display_image);
            TextView dummyTextView = (TextView) findViewById(R.id.product_title);
            ProductItemProcessor productItemProcessor = new ProductItemProcessor(this, dialog,
                            productImage, dummyTextView);
            Map<String,String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());
            paramsMap.put(GimbalStoreConstants.StoreParameterKeys.identifier.toString(),
                    GimbalStoreConstants.StoreParameterValues.top.toString());
            paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                    GimbalStoreConstants.StoreParameterValues.category.toString());
            paramsMap.put(GimbalStoreConstants.StoreParameterKeys.productId.toString(),
                    selectedProductId);

            HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                    Arrays.asList(new String[]{ServerURLUtil.getStoreServletServerURL(getResources())}),
                    paramsMap,productItemProcessor);
            asyncTask.execute(new String[] {});
        }*/
        // get product details to display
        // TODO mock and display data Aravindan
        setContentView(R.layout.activity_product_details);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product_details, menu);
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
