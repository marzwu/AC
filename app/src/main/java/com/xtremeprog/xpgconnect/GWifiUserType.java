package com.xtremeprog.xpgconnect;

public final class GWifiUserType {
    public static final GWifiUserType GWifiUserTypeANONYMOUS = new GWifiUserType("GWifiUserTypeANONYMOUS", GConnectJNI.GWifiUserTypeANONYMOUS_get());
    public static final GWifiUserType GWifiUserTypeEMAIL = new GWifiUserType("GWifiUserTypeEMAIL");
    public static final GWifiUserType GWifiUserTypePHONE = new GWifiUserType("GWifiUserTypePHONE");
    public static final GWifiUserType GWifiUserTypeTHIRDACCOUNT = new GWifiUserType("GWifiUserTypeTHIRDACCOUNT");
    public static final GWifiUserType GWifiUserTypeUSERNAME = new GWifiUserType("GWifiUserTypeUSERNAME");
    private static int swigNext = 0;
    private static GWifiUserType[] swigValues = new GWifiUserType[]{GWifiUserTypeANONYMOUS, GWifiUserTypeUSERNAME, GWifiUserTypePHONE, GWifiUserTypeTHIRDACCOUNT, GWifiUserTypeEMAIL};
    private final String swigName;
    private final int swigValue;

    private GWifiUserType(String str) {
        this.swigName = str;
        int i = swigNext;
        swigNext = i + 1;
        this.swigValue = i;
    }

    private GWifiUserType(String str, int i) {
        this.swigName = str;
        this.swigValue = i;
        swigNext = i + 1;
    }

    private GWifiUserType(String str, GWifiUserType gWifiUserType) {
        this.swigName = str;
        this.swigValue = gWifiUserType.swigValue;
        swigNext = this.swigValue + 1;
    }

    public static GWifiUserType swigToEnum(int i) {
        if (i < swigValues.length && i >= 0 && swigValues[i].swigValue == i) {
            return swigValues[i];
        }
        for (int i2 = 0; i2 < swigValues.length; i2++) {
            if (swigValues[i2].swigValue == i) {
                return swigValues[i2];
            }
        }
        throw new IllegalArgumentException("No enum " + GWifiUserType.class + " with value " + i);
    }

    public final int swigValue() {
        return this.swigValue;
    }

    public String toString() {
        return this.swigName;
    }
}
