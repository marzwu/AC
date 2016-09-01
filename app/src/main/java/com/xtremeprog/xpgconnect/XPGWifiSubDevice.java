package com.xtremeprog.xpgconnect;

import org.json.JSONException;
import org.json.JSONObject;

public class XPGWifiSubDevice extends XPGWifiDevice {
    private GWifiDevice originDevice;

    protected XPGWifiSubDevice(GWifiDevice gWifiDevice) {
        super(gWifiDevice);
        this.originDevice = gWifiDevice;
    }

    public String getSubDid() {
        return this.originDevice != null ? new StringBuilder(String.valueOf(this.originDevice.getSubDid())).toString() : "";
    }

    public String getSubProductKey() {
        return this.originDevice != null ? this.originDevice.getSubProductKey() : "";
    }

    public String getSubProductName() {
        return this.originDevice != null ? this.originDevice.getSubProductName() : "";
    }

    public int write(String str) {
        if (this.originDevice != null) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has("cmd") && 1 == jSONObject.getInt("cmd")) {
                    jSONObject.put("cmd", 7);
                }
                if (jSONObject.has("cmd") && 2 == jSONObject.getInt("cmd")) {
                    jSONObject.put("cmd", 8);
                }
                jSONObject.put("subDid", getSubDid());
                return this.originDevice.write(jSONObject.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return GWifiErrorCode.GWifiError_INVALID_PARAM.swigValue();
    }
}
