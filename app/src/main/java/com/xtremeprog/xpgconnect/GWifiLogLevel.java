package com.xtremeprog.xpgconnect;

public final class GWifiLogLevel {
    public static final GWifiLogLevel GWifiLogLevelAll = new GWifiLogLevel("GWifiLogLevelAll");
    public static final GWifiLogLevel GWifiLogLevelError = new GWifiLogLevel("GWifiLogLevelError", GConnectJNI.GWifiLogLevelError_get());
    public static final GWifiLogLevel GWifiLogLevelWarning = new GWifiLogLevel("GWifiLogLevelWarning");
    private static int swigNext = 0;
    private static GWifiLogLevel[] swigValues = new GWifiLogLevel[]{GWifiLogLevelError, GWifiLogLevelWarning, GWifiLogLevelAll};
    private final String swigName;
    private final int swigValue;

    private GWifiLogLevel(String str) {
        this.swigName = str;
        int i = swigNext;
        swigNext = i + 1;
        this.swigValue = i;
    }

    private GWifiLogLevel(String str, int i) {
        this.swigName = str;
        this.swigValue = i;
        swigNext = i + 1;
    }

    private GWifiLogLevel(String str, GWifiLogLevel gWifiLogLevel) {
        this.swigName = str;
        this.swigValue = gWifiLogLevel.swigValue;
        swigNext = this.swigValue + 1;
    }

    public static GWifiLogLevel swigToEnum(int i) {
        if (i < swigValues.length && i >= 0 && swigValues[i].swigValue == i) {
            return swigValues[i];
        }
        for (int i2 = 0; i2 < swigValues.length; i2++) {
            if (swigValues[i2].swigValue == i) {
                return swigValues[i2];
            }
        }
        throw new IllegalArgumentException("No enum " + GWifiLogLevel.class + " with value " + i);
    }

    public final int swigValue() {
        return this.swigValue;
    }

    public String toString() {
        return this.swigName;
    }
}
