package com.avnet.gears.codes.gimbal.store.authenticator;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.avnet.gears.codes.gimbal.store.authenticator.activity.StoreAuthenticatorActivity;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;
import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants.AUTHENTICATION_INTENT_ARGS;

/**
 * Created by 914889 on 3/11/15.
 */
public class StoreAccountAuthenticator extends AbstractAccountAuthenticator {
    private Context mContext;

    public StoreAccountAuthenticator(Context context) {
        super(context);
        this.mContext = context;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse response, String accountType, String authTokenType, String[] requiredFeatures, Bundle options) throws NetworkErrorException {
        Log.d("AUTH", "adding new account");
        final Intent intent = new Intent(mContext, StoreAuthenticatorActivity.class);
        intent.putExtra(AUTHENTICATION_INTENT_ARGS.ARG_ACCOUNT_TYPE.toString(),
                accountType);
        intent.putExtra(AUTHENTICATION_INTENT_ARGS.AUTH_TOKEN_TYPE.toString(), authTokenType);
        intent.putExtra(AUTHENTICATION_INTENT_ARGS.ARG_IS_NEW_ACCOUNT.toString(), true);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);

        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        Log.d("AUTH", "getting auth token value");
        GimbalStoreConstants.AUTH_TOKEN_TYPE authTokenValueType = GimbalStoreConstants.AUTH_TOKEN_TYPE.valueOf(authTokenType);
        // If the caller requested an authToken type we don't support, then
        // return an error
        if (authTokenValueType != null) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ERROR_MESSAGE, "invalid authTokenType");
            return result;
        }

        // Extract the username and password from the Account Manager, and ask
        // the server for an appropriate AuthToken.
        final AccountManager am = AccountManager.get(mContext);

        String authToken = am.peekAuthToken(account, authTokenType);

        Log.d("AUTH", "> peekAuthToken returned - " + authToken);

        // If we get an authToken - we return it
        if (!TextUtils.isEmpty(authToken)) {
            final Bundle result = new Bundle();
            result.putString(AccountManager.KEY_ACCOUNT_NAME, account.name);
            result.putString(AccountManager.KEY_ACCOUNT_TYPE, account.type);
            result.putString(AccountManager.KEY_AUTHTOKEN, authToken);
            return result;
        }

        // If we get here, then we couldn't access the user's password - so we
        // need to re-prompt them for their credentials. We do that by creating
        // an intent to display our AuthenticatorActivity.
        final Intent intent = new Intent(mContext, StoreAuthenticatorActivity.class);
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
        intent.putExtra(AUTHENTICATION_INTENT_ARGS.ARG_ACCOUNT_TYPE.toString(),
                account.type);
        intent.putExtra(AUTHENTICATION_INTENT_ARGS.AUTH_TOKEN_TYPE.toString(), authTokenType);
        intent.putExtra(AUTHENTICATION_INTENT_ARGS.ARG_USER_ACCOUNT_NAME.toString(), account.name);
        final Bundle bundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public String getAuthTokenLabel(String authTokenType) {
        Log.d("AUTH", "getting auth token label");
        GimbalStoreConstants.AUTH_TOKEN_TYPE authTokenValue = GimbalStoreConstants.AUTH_TOKEN_TYPE.valueOf(authTokenType);
        String authTokenLabel = GimbalStoreConstants.AUTH_TOKEN_TYPE.STORE_ACCESS_LIMITED.getTokenLabel();
        if (authTokenValue != null) {
            authTokenLabel = authTokenValue.getTokenLabel();
        }
        return authTokenLabel;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account, String authTokenType, Bundle options) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account, String[] features) throws NetworkErrorException {
        final Bundle result = new Bundle();
        result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
        return result;
    }
}
