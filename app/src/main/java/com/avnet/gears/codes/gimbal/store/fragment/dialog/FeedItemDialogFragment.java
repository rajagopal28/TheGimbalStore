package com.avnet.gears.codes.gimbal.store.fragment.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.activity.ProductDetailsActivity;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.ProductItemProcessor;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.HttpConnectionAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;

import java.util.Arrays;
import java.util.Map;


public class FeedItemDialogFragment extends DialogFragment {

    public static FeedItemDialogFragment newInstance(String feedItemText, String productId) {
        Bundle args = new Bundle();
        args.putString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.FEED_ITEM_TEXT.toString(),
                feedItemText);
        args.putString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString(),
                productId);
        FeedItemDialogFragment fragment = new FeedItemDialogFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        View v = getActivity().getLayoutInflater()
                .inflate(R.layout.fragment_feed_item, null);
        String feedItemText = getArguments().getString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.FEED_ITEM_TEXT.toString());
        final String productId = getArguments().getString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString());
        TextView tv = (TextView) v.findViewById(R.id.feed_item_text);
        tv.setText(feedItemText);
        ImageView imageView = (ImageView) v.findViewById(R.id.product_display_image);

        if (productId != null) {
            String serverURL = ServerURLUtil.getStoreServletServerURL(getResources());
            Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());
            paramsMap.put(GimbalStoreConstants.StoreParameterKeys.identifier.toString(),
                    GimbalStoreConstants.StoreParameterValues.top.toString());
            paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                    GimbalStoreConstants.StoreParameterValues.category.toString());
            paramsMap.put(GimbalStoreConstants.StoreParameterKeys.uniqueId.toString(),
                    productId);
            String cookieString = AndroidUtil.getPreferenceString(getActivity().getApplicationContext(),
                    GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
            ProductItemProcessor productItemProcessor = new ProductItemProcessor(getActivity(), null,
                    imageView, null, null,
                    null, null,
                    null, null, null);
            HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                    Arrays.asList(serverURL),
                    Arrays.asList(paramsMap), cookieString,
                    productItemProcessor);
            asyncTask.execute(new String[]{});
        }
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle(R.string.feed_item_fragment_title)
                .setNegativeButton(
                        android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                .setPositiveButton(
                        android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do open product activity
                                dialog.dismiss();
                                Intent productIntent = new Intent(getActivity().getApplicationContext(), ProductDetailsActivity.class);
                                productIntent.putExtra(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.SELECTED_PRODUCT_ID.toString(),
                                        productId);
                                getActivity().startActivity(productIntent);
                            }
                        })
                .create();
    }

}
