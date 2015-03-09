package com.avnet.gears.codes.gimbal.store.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.CategoryListProcessor;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.SubcategoryListProcessor;
import com.avnet.gears.codes.gimbal.store.bean.CategoryBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_METHODS;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.StoreParameterKeys;
import com.avnet.gears.codes.gimbal.store.fragment.NavigationDrawerFragment;
import com.avnet.gears.codes.gimbal.store.handler.HttpConnectionAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.NotificationUtil;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;
import com.avnet.gears.codes.gimbal.store.utils.TypeConversionUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class HomeActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    // list of categories to be shown on drawer fragment
    protected List<CategoryBean> mCategoryList;
    // section label
    private TextView sectionLabelView ;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        // fetch the category list
        mCategoryList = new ArrayList<CategoryBean>();
        // instantiate spinner
        progressDialog = NotificationUtil.showProgressDialog(this,
                GimbalStoreConstants.DEFAULT_SPINNER_TITLE,
                GimbalStoreConstants.DEFAULT_SPINNER_INFO_TEXT);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        // initialize bean to process value from async net call for category
        CategoryListProcessor cListProcessor = new CategoryListProcessor(mNavigationDrawerFragment,
                mCategoryList, progressDialog);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout)findViewById(R.id.drawer_layout),
                TypeConversionUtil.getCategoryTitleList(mCategoryList));

        mTitle = getTitle();


        Log.d("DEBUG", "Server URL = " + ServerURLUtil.getStoreServletServerURL(getResources()));
        Map<String,String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());

        paramsMap.put(StoreParameterKeys.identifier.toString(),
                GimbalStoreConstants.StoreParameterValues.top.toString());
        paramsMap.put(StoreParameterKeys.type.toString(),
                GimbalStoreConstants.StoreParameterValues.category.toString());
        Log.d("DEBUG", paramsMap.toString());

        HttpConnectionAsyncTask handler = new HttpConnectionAsyncTask(HTTP_METHODS.GET,
                Arrays.asList(new String[]{ServerURLUtil.getStoreServletServerURL(getResources())}),
                paramsMap,
                cListProcessor);
        handler.execute(new String[] {});
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(this.progressDialog != null)
            progressDialog.dismiss();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position, mCategoryList))
                .commit();
    }

    public void onSectionAttached(int number) {
        if(mCategoryList != null && !mCategoryList.isEmpty()) {
            mTitle = mCategoryList.get(number).getName();
        } else {
            mTitle = "Home";
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.menu_home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";
        private TextView sectionLabelTextView ;
        private ImageView imageView;
        private ListView allListItemsView;
        private CategoryBean selectedCategoryBean;
        private static List<CategoryBean> mCategoryBeanList;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber, List<CategoryBean> categoryBeanList) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            mCategoryBeanList = categoryBeanList;
            // Log.d("DEBUG", "" + categoryBeanList);
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Log.d("DEBUG", "in PlaceHolderFragment.onCreateView()");
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            sectionLabelTextView = (TextView)rootView.findViewById(R.id.section_label);
            allListItemsView = (ListView) rootView.findViewById(R.id.subcategories_list_view);
            // use the category Id obtained from HomeActivity to fetch and view list of subcategory items
            imageView = (ImageView) rootView.findViewById(R.id.image_view);
            if(selectedCategoryBean != null) {

                sectionLabelTextView.setText( MessageFormat.format(GimbalStoreConstants.SUB_CATEGORY_VIEW_HEADING,
                        new Object[]{selectedCategoryBean.getName()}));
                SubcategoryListProcessor scProcessor = new SubcategoryListProcessor(getActivity(), allListItemsView);
                Map<String,String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());
                paramsMap.put(StoreParameterKeys.identifier.toString(),
                        GimbalStoreConstants.StoreParameterValues.top.toString());
                paramsMap.put(StoreParameterKeys.type.toString(),
                        GimbalStoreConstants.StoreParameterValues.category.toString());
                paramsMap.put(StoreParameterKeys.parentCategoryId.toString(),
                        selectedCategoryBean.getUniqueId());

                HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(HTTP_METHODS.GET,
                        Arrays.asList(new String[]{ServerURLUtil.getStoreServletServerURL(getResources())}),
                        paramsMap,scProcessor);
                asyncTask.execute(new String[] {});
            }

            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            Log.d("DEBUG", "in PlaceHolderFragment.onAttach()");
            HomeActivity callingHomeActivity = (HomeActivity) activity;
            int selectedPosition = getArguments().getInt(ARG_SECTION_NUMBER);
            callingHomeActivity.onSectionAttached(
                    selectedPosition);
            // fetch the selected category id from HomeActivity

            if( mCategoryBeanList != null && !mCategoryBeanList.isEmpty())
                selectedCategoryBean = mCategoryBeanList.get(selectedPosition);

            Log.d("DEBUG", "Holder selectedCategoryId=" + selectedCategoryBean);
        }
    }

}
