package com.xtremeprog.xpgconnect;

public final class GCloudService {
    public static final GCloudService XPG_DEVELOPMENT = new GCloudService("XPG_DEVELOPMENT", GConnectJNI.XPG_DEVELOPMENT_get());
    public static final GCloudService XPG_PRODUCTION = new GCloudService("XPG_PRODUCTION", GConnectJNI.XPG_PRODUCTION_get());
    public static final GCloudService XPG_QA = new GCloudService("XPG_QA", GConnectJNI.XPG_QA_get());
    public static final GCloudService XPG_TENCENT = new GCloudService("XPG_TENCENT", GConnectJNI.XPG_TENCENT_get());
    private static int swigNext = 0;
    private static GCloudService[] swigValues = new GCloudService[]{XPG_PRODUCTION, XPG_QA, XPG_DEVELOPMENT, XPG_TENCENT};
    private final String swigName;
    private final int swigValue;

    private GCloudService(String str) {
        this.swigName = str;
        int i = swigNext;
        swigNext = i + 1;
        this.swigValue = i;
    }

    private GCloudService(String str, int i) {
        this.swigName = str;
        this.swigValue = i;
        swigNext = i + 1;
    }

    private GCloudService(String str, GCloudService gCloudService) {
        this.swigName = str;
        this.swigValue = gCloudService.swigValue;
        swigNext = this.swigValue + 1;
    }

    public static GCloudService swigToEnum(int i) {
        if (i < swigValues.length && i >= 0 && swigValues[i].swigValue == i) {
            return swigValues[i];
        }
        for (int i2 = 0; i2 < swigValues.length; i2++) {
            if (swigValues[i2].swigValue == i) {
                return swigValues[i2];
            }
        }
        throw new IllegalArgumentException("No enum " + GCloudService.class + " with value " + i);
    }

    public final int swigValue() {
        return this.swigValue;
    }

    public String toString() {
        return this.swigName;
    }
}
