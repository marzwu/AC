package com.xtremeprog.xpgconnect;

public final class GWifiLoginErrorCode {
    public static final GWifiLoginErrorCode GWifiLoginError_CONTROL_ENABLED = new GWifiLoginErrorCode("GWifiLoginError_CONTROL_ENABLED", GConnectJNI.GWifiLoginError_CONTROL_ENABLED_get());
    public static final GWifiLoginErrorCode GWifiLoginError_FAILED = new GWifiLoginErrorCode("GWifiLoginError_FAILED");
    public static final GWifiLoginErrorCode GWifiLoginError_LOGINED = new GWifiLoginErrorCode("GWifiLoginError_LOGINED");
    private static int swigNext = 0;
    private static GWifiLoginErrorCode[] swigValues = new GWifiLoginErrorCode[]{GWifiLoginError_CONTROL_ENABLED, GWifiLoginError_LOGINED, GWifiLoginError_FAILED};
    private final String swigName;
    private final int swigValue;

    private GWifiLoginErrorCode(String str) {
        this.swigName = str;
        int i = swigNext;
        swigNext = i + 1;
        this.swigValue = i;
    }

    private GWifiLoginErrorCode(String str, int i) {
        this.swigName = str;
        this.swigValue = i;
        swigNext = i + 1;
    }

    private GWifiLoginErrorCode(String str, GWifiLoginErrorCode gWifiLoginErrorCode) {
        this.swigName = str;
        this.swigValue = gWifiLoginErrorCode.swigValue;
        swigNext = this.swigValue + 1;
    }

    public static GWifiLoginErrorCode swigToEnum(int i) {
        if (i < swigValues.length && i >= 0 && swigValues[i].swigValue == i) {
            return swigValues[i];
        }
        for (int i2 = 0; i2 < swigValues.length; i2++) {
            if (swigValues[i2].swigValue == i) {
                return swigValues[i2];
            }
        }
        throw new IllegalArgumentException("No enum " + GWifiLoginErrorCode.class + " with value " + i);
    }

    public final int swigValue() {
        return this.swigValue;
    }

    public String toString() {
        return this.swigName;
    }
}
