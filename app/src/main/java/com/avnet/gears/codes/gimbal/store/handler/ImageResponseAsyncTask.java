package com.avnet.gears.codes.gimbal.store.handler;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

import java.io.InputStream;
import java.net.URL;
import java.util.List;

/**
 * Created by 914889 on 3/4/15.
 */
public class ImageResponseAsyncTask extends AsyncTask<String, List<String>, Object> {
    private String imageUrl;
    private ImageView imageView;
    private ImageButton imageButton;

    public ImageResponseAsyncTask(String imageUrl, ImageView imageView, ImageButton imageButton) {
        this.imageUrl = imageUrl;
        this.imageView = imageView;
        this.imageButton = imageButton;
    }

    @Override
    protected Object doInBackground(String... params) {
        try {
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());
            if(imageButton != null) {
                imageButton.setImageBitmap(bitmap);
            } else if(imageView != null) {
                imageButton.setImageBitmap(bitmap);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return GimbalStoreConstants.SUCCESS_STRING;
    }
}
