package com.avnet.gears.codes.gimbal.store.adapter;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.bean.SubCategoryBean;
import com.avnet.gears.codes.gimbal.store.utils.TypeConversionUtil;

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
        View rowView= inflater.inflate(R.layout.view_subcategory_list_item, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(scBeansList.get(position).getName());

        TableRow itemsRow = (TableRow) rowView.findViewById(R.id.itemsTableRow);

        return rowView;
    }
}
