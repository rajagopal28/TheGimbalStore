package com.avnet.gears.codes.gimbal.store.authenticator.activity;

import android.accounts.AccountAuthenticatorActivity;
import android.accounts.AccountManager;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.avnet.gears.codes.gimbal.store.R;
import com.avnet.gears.codes.gimbal.store.async.response.processor.impl.AuthDataProcessor;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.handler.HttpConnectionAsyncTask;
import com.avnet.gears.codes.gimbal.store.utils.ServerURLUtil;

import java.util.Arrays;
import java.util.Map;

public class StoreAuthenticatorActivity extends AccountAuthenticatorActivity {

    private AccountManager accountManager;
    private String authTokenType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        accountManager = AccountManager.get(this);
        setContentView(R.layout.activity_store_authenticator);
        Bundle passedBundle = getIntent().getExtras();
        final String registeredGCMDeviceId = passedBundle.getString(GimbalStoreConstants.INTENT_EXTRA_ATTR_KEY.GIVEN_GCM_DEVICE_ID.toString());

        Button signIn = (Button) findViewById(R.id.sign_in_button);
        Intent authenticationIntent = new Intent();
        final AuthDataProcessor authDataProcessor = new AuthDataProcessor(this, authenticationIntent, accountManager);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText textField = (EditText) findViewById(R.id.user_name_text);
                String username = textField.getText().toString();
                textField = (EditText) findViewById(R.id.password_text);
                String password = textField.getText().toString();


                Log.d("DEBUG", "username=" + username + " password =" + password);
                Map<String, String> paramsMap = ServerURLUtil.getBasicConfigParamsMap(getResources());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.type.toString(),
                        GimbalStoreConstants.StoreParameterValues.authentication.toString());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.identifier.toString(),
                        GimbalStoreConstants.StoreParameterValues.signup.toString());
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.logonId.toString(),
                        username);
                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.logonPassword.toString(),
                        password);

                paramsMap.put(GimbalStoreConstants.StoreParameterKeys.gcmDeviceId.toString(), registeredGCMDeviceId);
                Log.d("DEBUG", "paramsMap = " + paramsMap);
                String cookieString = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(GimbalStoreConstants.PREF_SESSION_COOKIE_PARAM_KEY, null);

                HttpConnectionAsyncTask asyncTask = new HttpConnectionAsyncTask(GimbalStoreConstants.HTTP_METHODS.GET,
                        Arrays.asList(new String[]{ServerURLUtil.getStoreServletServerURL(getResources())}),
                        Arrays.asList(paramsMap), cookieString,
                        authDataProcessor);
                asyncTask.execute(new String[]{});

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_store_authenticator, menu);
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
