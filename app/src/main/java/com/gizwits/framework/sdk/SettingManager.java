package com.gizwits.framework.sdk;

import android.content.Context;
import android.content.SharedPreferences;

public class SettingManager {
    static String filter = "=====";
    private final String PASSWORD = "password";
    private final String PHONE_NUM = "phonenumber";
    private final String SHARE_PREFERENCES = "set";
    private final String TOKEN = "token";
    private final String UID = "uid";
    private final String UNIT = "unit";
    private final String USER_NAME = "username";
    private Context c;
    SharedPreferences spf;

    public SettingManager(Context context) {
        this.c = context;
        this.spf = context.getSharedPreferences("set", 0);
    }

    public void clean() {
        setUid("");
        setToken("");
        setPhoneNumber("");
        setPassword("");
        setUserName("");
    }

    public String getPassword() {
        return this.spf.getString("password", "");
    }

    public String getPhoneNumber() {
        return this.spf.getString("phonenumber", "");
    }

    public String getToken() {
        return this.spf.getString("token", "");
    }

    public String getUid() {
        return this.spf.getString("uid", "");
    }

    public boolean getUnit() {
        return this.spf.getBoolean("unit", true);
    }

    public String getUserName() {
        return this.spf.getString("username", "");
    }

    public void setPassword(String str) {
        this.spf.edit().putString("password", str).commit();
    }

    public void setPhoneNumber(String str) {
        this.spf.edit().putString("phonenumber", str).commit();
    }

    public void setToken(String str) {
        this.spf.edit().putString("token", str).commit();
    }

    public void setUid(String str) {
        this.spf.edit().putString("uid", str).commit();
    }

    public void setUnit(boolean z) {
        this.spf.edit().putBoolean("unit", z).commit();
    }

    public void setUserName(String str) {
        this.spf.edit().putString("username", str).commit();
    }
}
