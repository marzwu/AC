package com.xtremeprog.xpgconnect;

public final class GWifiErrorCode {
    public static final GWifiErrorCode GWifiError_CONFIGURE_SENDFAILED = new GWifiErrorCode("GWifiError_CONFIGURE_SENDFAILED", GConnectJNI.GWifiError_CONFIGURE_SENDFAILED_get());
    public static final GWifiErrorCode GWifiError_CONFIGURE_TIMEOUT = new GWifiErrorCode("GWifiError_CONFIGURE_TIMEOUT", GConnectJNI.GWifiError_CONFIGURE_TIMEOUT_get());
    public static final GWifiErrorCode GWifiError_CONNECTION_CLOSED = new GWifiErrorCode("GWifiError_CONNECTION_CLOSED", GConnectJNI.GWifiError_CONNECTION_CLOSED_get());
    public static final GWifiErrorCode GWifiError_CONNECTION_ERROR = new GWifiErrorCode("GWifiError_CONNECTION_ERROR", GConnectJNI.GWifiError_CONNECTION_ERROR_get());
    public static final GWifiErrorCode GWifiError_CONNECTION_ID = new GWifiErrorCode("GWifiError_CONNECTION_ID", GConnectJNI.GWifiError_CONNECTION_ID_get());
    public static final GWifiErrorCode GWifiError_CONNECTION_NO_GATEWAY = new GWifiErrorCode("GWifiError_CONNECTION_NO_GATEWAY", GConnectJNI.GWifiError_CONNECTION_NO_GATEWAY_get());
    public static final GWifiErrorCode GWifiError_CONNECTION_POOL_FULLED = new GWifiErrorCode("GWifiError_CONNECTION_POOL_FULLED", GConnectJNI.GWifiError_CONNECTION_POOL_FULLED_get());
    public static final GWifiErrorCode GWifiError_CONNECTION_REFUSED = new GWifiErrorCode("GWifiError_CONNECTION_REFUSED", GConnectJNI.GWifiError_CONNECTION_REFUSED_get());
    public static final GWifiErrorCode GWifiError_CONNECT_TIMEOUT = new GWifiErrorCode("GWifiError_CONNECT_TIMEOUT", GConnectJNI.GWifiError_CONNECT_TIMEOUT_get());
    public static final GWifiErrorCode GWifiError_DISCOVERY_MISMATCH = new GWifiErrorCode("GWifiError_DISCOVERY_MISMATCH", GConnectJNI.GWifiError_DISCOVERY_MISMATCH_get());
    public static final GWifiErrorCode GWifiError_GENERAL = new GWifiErrorCode("GWifiError_GENERAL", GConnectJNI.GWifiError_GENERAL_get());
    public static final GWifiErrorCode GWifiError_GET_PASSCODE_FAIL = new GWifiErrorCode("GWifiError_GET_PASSCODE_FAIL", GConnectJNI.GWifiError_GET_PASSCODE_FAIL_get());
    public static final GWifiErrorCode GWifiError_HTTP_FAIL = new GWifiErrorCode("GWifiError_HTTP_FAIL", GConnectJNI.GWifiError_HTTP_FAIL_get());
    public static final GWifiErrorCode GWifiError_INSUFFIENT_MEM = new GWifiErrorCode("GWifiError_INSUFFIENT_MEM", GConnectJNI.GWifiError_INSUFFIENT_MEM_get());
    public static final GWifiErrorCode GWifiError_INVALID_PARAM = new GWifiErrorCode("GWifiError_INVALID_PARAM", GConnectJNI.GWifiError_INVALID_PARAM_get());
    public static final GWifiErrorCode GWifiError_INVALID_VERSION = new GWifiErrorCode("GWifiError_INVALID_VERSION", GConnectJNI.GWifiError_INVALID_VERSION_get());
    public static final GWifiErrorCode GWifiError_LOGIN_FAIL = new GWifiErrorCode("GWifiError_LOGIN_FAIL", GConnectJNI.GWifiError_LOGIN_FAIL_get());
    public static final GWifiErrorCode GWifiError_MQTT_FAIL = new GWifiErrorCode("GWifiError_MQTT_FAIL", GConnectJNI.GWifiError_MQTT_FAIL_get());
    public static final GWifiErrorCode GWifiError_NONE = new GWifiErrorCode("GWifiError_NONE", GConnectJNI.GWifiError_NONE_get());
    public static final GWifiErrorCode GWifiError_NOT_IMPLEMENTED = new GWifiErrorCode("GWifiError_NOT_IMPLEMENTED", GConnectJNI.GWifiError_NOT_IMPLEMENTED_get());
    public static final GWifiErrorCode GWifiError_NOT_IN_SOFTAPMODE = new GWifiErrorCode("GWifiError_NOT_IN_SOFTAPMODE", GConnectJNI.GWifiError_NOT_IN_SOFTAPMODE_get());
    public static final GWifiErrorCode GWifiError_NULL_CLIENT_ID = new GWifiErrorCode("GWifiError_NULL_CLIENT_ID", GConnectJNI.GWifiError_NULL_CLIENT_ID_get());
    public static final GWifiErrorCode GWifiError_PACKET_CHECKSUM = new GWifiErrorCode("GWifiError_PACKET_CHECKSUM", GConnectJNI.GWifiError_PACKET_CHECKSUM_get());
    public static final GWifiErrorCode GWifiError_PACKET_DATALEN = new GWifiErrorCode("GWifiError_PACKET_DATALEN", GConnectJNI.GWifiError_PACKET_DATALEN_get());
    public static final GWifiErrorCode GWifiError_SET_SOCK_OPT = new GWifiErrorCode("GWifiError_SET_SOCK_OPT", GConnectJNI.GWifiError_SET_SOCK_OPT_get());
    public static final GWifiErrorCode GWifiError_THREAD_BUSY = new GWifiErrorCode("GWifiError_THREAD_BUSY", GConnectJNI.GWifiError_THREAD_BUSY_get());
    public static final GWifiErrorCode GWifiError_THREAD_CREATE = new GWifiErrorCode("GWifiError_THREAD_CREATE", GConnectJNI.GWifiError_THREAD_CREATE_get());
    public static final GWifiErrorCode GWifiError_UNRECOGNIZED_DATA = new GWifiErrorCode("GWifiError_UNRECOGNIZED_DATA", GConnectJNI.GWifiError_UNRECOGNIZED_DATA_get());
    private static int swigNext = 0;
    private static GWifiErrorCode[] swigValues = new GWifiErrorCode[]{GWifiError_NONE, GWifiError_GENERAL, GWifiError_NOT_IMPLEMENTED, GWifiError_PACKET_DATALEN, GWifiError_CONNECTION_ID, GWifiError_CONNECTION_CLOSED, GWifiError_PACKET_CHECKSUM, GWifiError_LOGIN_FAIL, GWifiError_MQTT_FAIL, GWifiError_DISCOVERY_MISMATCH, GWifiError_SET_SOCK_OPT, GWifiError_THREAD_CREATE, GWifiError_CONNECTION_POOL_FULLED, GWifiError_NULL_CLIENT_ID, GWifiError_CONNECTION_ERROR, GWifiError_INVALID_PARAM, GWifiError_CONNECT_TIMEOUT, GWifiError_INVALID_VERSION, GWifiError_INSUFFIENT_MEM, GWifiError_THREAD_BUSY, GWifiError_HTTP_FAIL, GWifiError_GET_PASSCODE_FAIL, GWifiError_CONFIGURE_TIMEOUT, GWifiError_CONFIGURE_SENDFAILED, GWifiError_NOT_IN_SOFTAPMODE, GWifiError_UNRECOGNIZED_DATA, GWifiError_CONNECTION_NO_GATEWAY, GWifiError_CONNECTION_REFUSED};
    private final String swigName;
    private final int swigValue;

    private GWifiErrorCode(String str) {
        this.swigName = str;
        int i = swigNext;
        swigNext = i + 1;
        this.swigValue = i;
    }

    private GWifiErrorCode(String str, int i) {
        this.swigName = str;
        this.swigValue = i;
        swigNext = i + 1;
    }

    private GWifiErrorCode(String str, GWifiErrorCode gWifiErrorCode) {
        this.swigName = str;
        this.swigValue = gWifiErrorCode.swigValue;
        swigNext = this.swigValue + 1;
    }

    public static GWifiErrorCode swigToEnum(int i) {
        if (i < swigValues.length && i >= 0 && swigValues[i].swigValue == i) {
            return swigValues[i];
        }
        for (int i2 = 0; i2 < swigValues.length; i2++) {
            if (swigValues[i2].swigValue == i) {
                return swigValues[i2];
            }
        }
        throw new IllegalArgumentException("No enum " + GWifiErrorCode.class + " with value " + i);
    }

    public final int swigValue() {
        return this.swigValue;
    }

    public String toString() {
        return this.swigName;
    }
}
