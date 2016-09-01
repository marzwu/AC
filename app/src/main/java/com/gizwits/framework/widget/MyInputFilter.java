package com.gizwits.framework.widget;

import android.text.LoginFilter.UsernameFilterGMail;

public class MyInputFilter extends UsernameFilterGMail {
    public boolean isAllowed(char c) {
        return ('0' <= c && c <= '9') || (('a' <= c && c <= 'z') || ('A' <= c && c <= 'Z'));
    }
}
