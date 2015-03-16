package com.avnet.gears.codes.gimbal.store.adapter.pager;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

import com.avnet.gears.codes.gimbal.store.bean.FeedItemBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.fragment.FeedTabPlaceholderFragment;

import java.util.List;
import java.util.Map;

/**
 * Created by 914889 on 3/15/15.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private Map<GimbalStoreConstants.FEED_ITEM_TYPE, List<FeedItemBean>> feedItemsMap;
    private GimbalStoreConstants.FEED_ITEM_TYPE[] headerKeys;

    public SectionsPagerAdapter(FragmentManager fm,
                                GimbalStoreConstants.FEED_ITEM_TYPE[] tabKeys,
                                Map<GimbalStoreConstants.FEED_ITEM_TYPE, List<FeedItemBean>> itemsMap) {
        super(fm);
        this.headerKeys = tabKeys;
        this.feedItemsMap = itemsMap;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        return FeedTabPlaceholderFragment.newInstance(headerKeys[position], feedItemsMap.get(headerKeys[position]));
    }

    @Override
    public int getCount() {
        // Show 3 total pages.
        return headerKeys.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return headerKeys[position].getItemTypeLabel();
    }
}
