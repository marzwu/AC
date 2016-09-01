package com.xpg.common.useful;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

public class NetworkUtils {
    public static String getCurentWifiSSID(Context context) {
        String str = "";
        if (context == null) {
            return str;
        }
        str = ((WifiManager) context.getSystemService("wifi")).getConnectionInfo().getSSID();
        return (str.substring(0, 1).equals("\"") && str.substring(str.length() - 1).equals("\"")) ? str.substring(1, str.length() - 1) : str;
    }

    public static boolean isMobileConnected(Context context) {
        if (context != null) {
            NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(0);
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getActiveNetworkInfo();
            if (activeNetworkInfo != null) {
                return activeNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static boolean isWifiConnected(Context context) {
        if (context != null) {
            NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService("connectivity")).getNetworkInfo(1);
            if (networkInfo != null) {
                return networkInfo.isAvailable();
            }
        }
        return false;
    }
}
