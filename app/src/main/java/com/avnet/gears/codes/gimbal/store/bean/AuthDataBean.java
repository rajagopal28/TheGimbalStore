package com.avnet.gears.codes.gimbal.store.bean;

import com.avnet.gears.codes.gimbal.store.constant.GimbalStoreConstants;

/**
 * Created by 914889 on 3/11/15.
 */
public class AuthDataBean {
    private String username;
    private String password;
    private String authToken;
    private String accountType;
    private GimbalStoreConstants.AUTH_TOKEN_TYPE authTokenType;
    private boolean isValidUser;
    private boolean isAuthenticated;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public boolean isValidUser() {
        return isValidUser;
    }

    public void setValidUser(boolean isValidUser) {
        this.isValidUser = isValidUser;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public GimbalStoreConstants.AUTH_TOKEN_TYPE getAuthTokenType() {
        return authTokenType;
    }

    public void setAuthTokenType(GimbalStoreConstants.AUTH_TOKEN_TYPE authTokenType) {
        this.authTokenType = authTokenType;
    }

    @Override
    public String toString() {
        return "AuthDataBean{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", authToken='" + authToken + '\'' +
                ", accountType='" + accountType + '\'' +
                ", authTokenType='" + authTokenType + '\'' +
                ", isValidUser=" + isValidUser +
                ", isAuthenticated=" + isAuthenticated +
                '}';
    }
}
