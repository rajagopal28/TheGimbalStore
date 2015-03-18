package com.avnet.gears.codes.gimbal.store.activity;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.AuthTokenReceiverProcessor;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.CategoryListProcessor;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.SubcategoryListProcessor;
import com.avnet.gears.codes.gimbal.store.authenticator.activity.StoreAuthenticatorActivity;
import com.avnet.gears.codes.gimbal.store.bean.CategoryBean;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.HTTP_METHODS;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.StoreParameterKeys;
import com.avnet.gears.codes.gimbal.store.fragment.NavigationDrawerFragment;
import com.avnet.gears.codes.gimbal.store.handler.GenericAsyncTask;
import com.avnet.gears.codes.gimbal.store.handler.HttpConnectionAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.AndroidUtil;
import com.avnet.gears.codes.gimbal.store.utils.GCMAccountUtil;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;
import com.avnet.gears.codes.gimbal.store.utils.TypeConversionUtil;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


public class HomeActivity extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    // list of categories to be shown on drawer fragment
    protected List<CategoryBean> mCategoryList;
    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    // section label
    private TextView sectionLabelView;
    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private ProgressDialog progressDialog;
    private AccountManager accountManager;


    private static final int SETTINGS_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        gimbalInitialize();
        /*IntentFilter filter1 = new IntentFilter(BluetoothDevice.ACTION_ACL_CONNECTED);
        IntentFilter filter2 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECT_REQUESTED);
        IntentFilter filter3 = new IntentFilter(BluetoothDevice.ACTION_ACL_DISCONNECTED); */

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        accountManager = AccountManager.get(getApplicationContext());

        boolean isFirstTimeOpen = AndroidUtil.checkFirsTimeOpen(getApplicationContext());
        String senderId = getResources().getString(R.string.GCM_SENDER_ID);
        // check for already added account through accounts
        Account[] userAccounts = accountManager.getAccountsByType(GimbalStoreConstants.APP_ACCOUNT_TYPE_STRING);
        if (isFirstTimeOpen) {
            if (userAccounts == null || userAccounts.length == 0) {
                Log.d("DEBUG", "got no user accounts");
                Intent intent = new Intent(this, StoreAuthenticatorActivity.class);
                intent.putExtra(GimbalStoreConstants.AUTHENTICATION_INTENT_ARGS.ARG_ACCOUNT_TYPE.toString(),
                        GimbalStoreConstants.APP_ACCOUNT_TYPE_STRING);
                intent.putExtra(GimbalStoreConstants.AUTHENTICATION_INTENT_ARGS.AUTH_TOKEN_TYPE.toString(),
                        GimbalStoreConstants.AUTH_TOKEN_TYPE.STORE_ACCESS_FULL.toString());// full as of now can change in later phase
                intent.putExtra(GimbalStoreConstants.AUTHENTICATION_INTENT_ARGS.ARG_IS_NEW_ACCOUNT.toString(), true);
                // check for GCM id, register if not found
                GCMAccountUtil.checkAndRegisterDeviceTOGCM(this, senderId,
                        intent, GimbalStoreConstants.ACTIVITY_REQUEST_SIGNUP);
                return;
            }
            Log.d("DEBUG", " not inside new account if");
        }
        // now login after signup
        // check for auth token
        if (userAccounts != null && userAccounts.length > 0) {
            String cookieString = AndroidUtil.getPreferenceString(this, GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
            if (cookieString == null) {
                Log.d("DEBUG", "got one user account");
                AuthTokenReceiverProcessor authTokenReceiverProcessor = new AuthTokenReceiverProcessor(this, accountManager, userAccounts[0]);
                GenericAsyncTask asyncTask = new GenericAsyncTask(authTokenReceiverProcessor);
                asyncTask.execute();
                return;
            }
            Log.d("DEBUG", "Home Activity , cookieString = " + cookieString);
        }
        processCategoryListData();
    }

    private void gimbalInitialize() {
        Toast t = Toast.makeText(getApplicationContext(), "Gimbal initialise", Toast.LENGTH_SHORT);
        t.show();

        final TextView placeView = (TextView)findViewById(R.id.placeView);
        final TextView sightingView = (TextView)findViewById(R.id.sightingView);
        final TextView sightingRssiView = (TextView) findViewById(R.id.sightingRssiView);
        final TextView beaconDetailsView = (TextView) findViewById(R.id.beaconDetailsView);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GimbalStoreConstants.ACTIVITY_REQUEST_SIGNUP && resultCode == RESULT_OK) {
            Log.d("DEBUG", "signup success");
            processCategoryListData();
            // sign in success then list the category values
        } else if (resultCode == GimbalStoreConstants.ACTIVITY_RESULT_LOGIN_SUCCESS) {
            processCategoryListData();
            Log.d("DEBUG", "login success");
        }
    }

    private void processCategoryListData() {
        // fetch the category list
        mCategoryList = new ArrayList<CategoryBean>();

        // instantiate spinner
        progressDialog = AndroidUtil.showProgressDialog(this,
                GimbalStoreConstants.DEFAULT_SPINNER_TITLE,
                GimbalStoreConstants.DEFAULT_SPINNER_INFO_TEXT);
        // initialize bean to process value from async net call for category
        CategoryListProcessor cListProcessor = new CategoryListProcessor(mNavigationDrawerFragment,
                mCategoryList, progressDialog);

        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout),
                TypeConversionUtil.getCategoryTitleList(mCategoryList));

        mTitle = getTitle();


        Log.d("DEBUG", "Server URL = " + ServerURLUtil.getStoreServletServerURL(getResources()));
        Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());

        paramsMap.put(StoreParameterKeys.identifier.toString(),
                GimbalStoreConstants.StoreParameterValues.top.toString());
        paramsMap.put(StoreParameterKeys.type.toString(),
                GimbalStoreConstants.StoreParameterValues.category.toString());
        String cookieString = AndroidUtil.getPreferenceString(getApplicationContext(),
                GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
        Log.d("DEBUG", paramsMap.toString());

        HttpConnectionAsyncTask handler = new HttpConnectionAsyncTask(HTTP_METHODS.GET,
                Arrays.asList(new String[]{ServerURLUtil.getStoreServletServerURL(getResources())}),
                Arrays.asList(paramsMap), cookieString,
                cListProcessor);
        handler.execute(new String[]{});
    }

    @Override
    public void onUserInteraction() {
        Log.d("DEBUG", " HomeActivity.onUserInteraction()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("DEBUG", " HomeActivity.onResume()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("DEBUG", " HomeActivity.onStop()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (this.progressDialog != null)
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
        if (mCategoryList != null && !mCategoryList.isEmpty()) {
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
            getMenuInflater().inflate(R.menu.menu_global, menu);
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
            Log.d("Options Selected", "Settings clicked");
            Intent i = new Intent(getApplicationContext(), UserSettingsActivity.class);
            startActivityForResult(i, SETTINGS_RESULT);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        // clear session cookie preference
        // TODO call Async task to logout user
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        pref.edit().remove(GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
        pref.edit().commit();
        super.onDestroy();
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
        private static List<CategoryBean> mCategoryBeanList;
        private TextView sectionLabelTextView;
        private ListView allListItemsView;
        private CategoryBean selectedCategoryBean;

        public PlaceholderFragment() {
        }

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            Log.d("DEBUG", "in PlaceHolderFragment.onCreateView()");
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            sectionLabelTextView = (TextView) rootView.findViewById(R.id.section_label);
            allListItemsView = (ListView) rootView.findViewById(R.id.subcategories_list_view);
            // use the category Id obtained from HomeActivity to fetch and view list of subcategory items
            if (selectedCategoryBean != null) {
                ProgressDialog dialog = AndroidUtil.showProgressDialog(getActivity(),
                        GimbalStoreConstants.DEFAULT_SPINNER_TITLE,
                        GimbalStoreConstants.DEFAULT_SPINNER_INFO_TEXT);
                sectionLabelTextView.setText(MessageFormat.format(GimbalStoreConstants.SUB_CATEGORY_VIEW_HEADING,
                        new Object[]{selectedCategoryBean.getName()}));
                SubcategoryListProcessor scProcessor = new SubcategoryListProcessor(getActivity(), allListItemsView, dialog);
                Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());
                paramsMap.put(StoreParameterKeys.identifier.toString(),
                        GimbalStoreConstants.StoreParameterValues.top.toString());
                paramsMap.put(StoreParameterKeys.type.toString(),
                        GimbalStoreConstants.StoreParameterValues.category.toString());
                paramsMap.put(StoreParameterKeys.parentCategoryId.toString(),
                        selectedCategoryBean.getUniqueId());

                String cookieString = AndroidUtil.getPreferenceString(getActivity().getApplicationContext(),
                        GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY);
                HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(HTTP_METHODS.GET,
                        Arrays.asList(new String[]{ServerURLUtil.getStoreServletServerURL(getResources())}),
                        Arrays.asList(paramsMap), cookieString,
                        scProcessor);
                asyncTask.execute(new String[]{});
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

            if (mCategoryBeanList != null && !mCategoryBeanList.isEmpty())
                selectedCategoryBean = mCategoryBeanList.get(selectedPosition);

            Log.d("DEBUG", "Holder selectedCategoryId=" + selectedCategoryBean);
        }
    }
}
