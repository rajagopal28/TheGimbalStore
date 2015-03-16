package com.avnet.gears.codes.gimbal.store.bean;

import com.avnet.gears.codes.gimbal.store.bean.response.BaseServerResponseBean;

import java.util.Arrays;

/**
 * Created by 914889 on 3/11/15.
 */
public class AuthDataBean extends BaseServerResponseBean {
    private String[] logonId;
    private String[] logonPassword;
    private String reLogonURL;
    private String securityToken;
    private String URL;
    private String[] gcmDeviceId;
    private CookieBean[] Cookies;

    public String[] getLogonId() {
        return logonId;
    }

    public void setLogonId(String[] logonId) {
        this.logonId = logonId;
    }

    public String[] getLogonPassword() {
        return logonPassword;
    }

    public void setLogonPassword(String[] logonPassword) {
        this.logonPassword = logonPassword;
    }

    public String getReLogonURL() {
        return reLogonURL;
    }

    public void setReLogonURL(String reLogonURL) {
        this.reLogonURL = reLogonURL;
    }

    public String getSecurityToken() {
        return securityToken;
    }

    public void setSecurityToken(String securityToken) {
        this.securityToken = securityToken;
    }

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String[] getGcmDeviceId() {
        return gcmDeviceId;
    }

    public void setGcmDeviceId(String[] gcmDeviceId) {
        this.gcmDeviceId = gcmDeviceId;
    }

    public CookieBean[] getCookies() {
        return Cookies;
    }

    public void setCookies(CookieBean[] cookies) {
        Cookies = cookies;
    }

    @Override
    public String toString() {
        return super.toString() + "AuthDataBean{" +
                "logonId=" + Arrays.toString(logonId) +
                ", logonPassword=" + Arrays.toString(logonPassword) +
                ", reLogonURL='" + reLogonURL + '\'' +
                ", securityToken='" + securityToken + '\'' +
                ", URL='" + URL + '\'' +
                ", gcmDeviceId=" + Arrays.toString(gcmDeviceId) +
                ", Cookies=" + Arrays.toString(Cookies) +
                '}';
    }
}
