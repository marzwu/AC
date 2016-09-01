package com.xpg.common.device;

import android.content.Context;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class KeyBoardUtils {
    public static void closeKeybord(EditText editText, Context context) {
        ((InputMethodManager) context.getSystemService("input_method")).hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }

    public static void openKeybord(EditText editText, Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService("input_method");
        inputMethodManager.showSoftInput(editText, 2);
        inputMethodManager.toggleSoftInput(2, 1);
    }
}
