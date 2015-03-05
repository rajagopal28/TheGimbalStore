package com.avnet.gears.codes.gimbal.store.adapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.avnet.gears.codes.gimbal.store.R;

/**
 * Created by 914889 on 2/23/15.
 */
public class CategoryViewAdapter extends ArrayAdapter<String> {
    private final Activity context;

    private String[] labelList;

    public CategoryViewAdapter(Activity context,
                               String[] list) {
        super(context, R.layout.view_subcategory_list_item, list);
        labelList = list;
        this.context = context;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.view_subcategory_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);
        ImageView[] imageViews = new ImageView[]{
                (ImageView) rowView.findViewById(R.id.imageButton1),
                (ImageView) rowView.findViewById(R.id.imageButton2),
                (ImageView) rowView.findViewById(R.id.imageButton3),
                (ImageView) rowView.findViewById(R.id.imageButton4)
        };
        txtTitle.setText(labelList[position]);
        for(ImageView imageView : imageViews) {
            imageView.setImageResource(R.drawable.ic_launcher);
        }
        return rowView;
    }
}
