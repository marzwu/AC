package com.xtremeprog.xpgconnect;

public final class GWifiThirdAccountType {
    public static final GWifiThirdAccountType GWifiThirdAccountTypeBAIDU = new GWifiThirdAccountType("GWifiThirdAccountTypeBAIDU", GConnectJNI.GWifiThirdAccountTypeBAIDU_get());
    public static final GWifiThirdAccountType GWifiThirdAccountTypeQQ = new GWifiThirdAccountType("GWifiThirdAccountTypeQQ");
    public static final GWifiThirdAccountType GWifiThirdAccountTypeSINA = new GWifiThirdAccountType("GWifiThirdAccountTypeSINA");
    private static int swigNext = 0;
    private static GWifiThirdAccountType[] swigValues = new GWifiThirdAccountType[]{GWifiThirdAccountTypeBAIDU, GWifiThirdAccountTypeSINA, GWifiThirdAccountTypeQQ};
    private final String swigName;
    private final int swigValue;

    private GWifiThirdAccountType(String str) {
        this.swigName = str;
        int i = swigNext;
        swigNext = i + 1;
        this.swigValue = i;
    }

    private GWifiThirdAccountType(String str, int i) {
        this.swigName = str;
        this.swigValue = i;
        swigNext = i + 1;
    }

    private GWifiThirdAccountType(String str, GWifiThirdAccountType gWifiThirdAccountType) {
        this.swigName = str;
        this.swigValue = gWifiThirdAccountType.swigValue;
        swigNext = this.swigValue + 1;
    }

    public static GWifiThirdAccountType swigToEnum(int i) {
        if (i < swigValues.length && i >= 0 && swigValues[i].swigValue == i) {
            return swigValues[i];
        }
        for (int i2 = 0; i2 < swigValues.length; i2++) {
            if (swigValues[i2].swigValue == i) {
                return swigValues[i2];
            }
        }
        throw new IllegalArgumentException("No enum " + GWifiThirdAccountType.class + " with value " + i);
    }

    public final int swigValue() {
        return this.swigValue;
    }

    public String toString() {
        return this.swigName;
    }
}
