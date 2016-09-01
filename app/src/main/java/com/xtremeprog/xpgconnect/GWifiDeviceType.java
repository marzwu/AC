package com.xtremeprog.xpgconnect;

public final class GWifiDeviceType {
    public static final GWifiDeviceType GWifiDeviceTypeCenterControl = new GWifiDeviceType("GWifiDeviceTypeCenterControl", GConnectJNI.GWifiDeviceTypeCenterControl_get());
    public static final GWifiDeviceType GWifiDeviceTypeNormal = new GWifiDeviceType("GWifiDeviceTypeNormal", GConnectJNI.GWifiDeviceTypeNormal_get());
    public static final GWifiDeviceType GWifiDeviceTypeSub = new GWifiDeviceType("GWifiDeviceTypeSub", GConnectJNI.GWifiDeviceTypeSub_get());
    private static int swigNext = 0;
    private static GWifiDeviceType[] swigValues = new GWifiDeviceType[]{GWifiDeviceTypeNormal, GWifiDeviceTypeCenterControl, GWifiDeviceTypeSub};
    private final String swigName;
    private final int swigValue;

    private GWifiDeviceType(String str) {
        this.swigName = str;
        int i = swigNext;
        swigNext = i + 1;
        this.swigValue = i;
    }

    private GWifiDeviceType(String str, int i) {
        this.swigName = str;
        this.swigValue = i;
        swigNext = i + 1;
    }

    private GWifiDeviceType(String str, GWifiDeviceType gWifiDeviceType) {
        this.swigName = str;
        this.swigValue = gWifiDeviceType.swigValue;
        swigNext = this.swigValue + 1;
    }

    public static GWifiDeviceType swigToEnum(int i) {
        if (i < swigValues.length && i >= 0 && swigValues[i].swigValue == i) {
            return swigValues[i];
        }
        for (int i2 = 0; i2 < swigValues.length; i2++) {
            if (swigValues[i2].swigValue == i) {
                return swigValues[i2];
            }
        }
        throw new IllegalArgumentException("No enum " + GWifiDeviceType.class + " with value " + i);
    }

    public final int swigValue() {
        return this.swigValue;
    }

    public String toString() {
        return this.swigName;
    }
}
