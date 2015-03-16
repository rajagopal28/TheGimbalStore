package com.avnet.gears.codes.gimbal.store.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.bean.FeedItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.utils.TypeConversionUtil;

import java.util.List;

/**
 * Created by 914889 on 3/15/15.
 */
public class FeedTabPlaceholderFragment extends Fragment {

    private List<FeedItemBean> feedItemBeans;
    private GimbalStoreConstants.FEED_ITEM_TYPE sectionType;

    public FeedTabPlaceholderFragment() {

    }

    public static FeedTabPlaceholderFragment newInstance(GimbalStoreConstants.FEED_ITEM_TYPE feedItemType, List<FeedItemBean> feeds) {
        FeedTabPlaceholderFragment fragment = new FeedTabPlaceholderFragment();
        fragment.setSectionType(feedItemType);
        fragment.setFeedItemBeans(feeds);
        return fragment;
    }

    private void setFeedItemBeans(List<FeedItemBean> feeds) {
        this.feedItemBeans = feeds;
    }

    public void setSectionType(GimbalStoreConstants.FEED_ITEM_TYPE sectionType) {
        this.sectionType = sectionType;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed_list, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.feed_list_view);
        List<String> feedDescList = TypeConversionUtil.getFeedDescriptionTitles(this.feedItemBeans);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                TypeConversionUtil.getFeedDescriptionTitles(this.feedItemBeans));
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("DEBUG", "Clicking the feed item at position = " + position);
            }
        });
        listView.refreshDrawableState();
        return rootView;
    }
}
