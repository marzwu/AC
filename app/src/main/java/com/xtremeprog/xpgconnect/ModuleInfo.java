package com.xtremeprog.xpgconnect;

public class ModuleInfo {
    private String ModuleIP;
    private String mac;
    private String mid;

    public String getMac() {
        return this.mac;
    }

    public String getMid() {
        return this.mid;
    }

    public String getModuleIP() {
        return this.ModuleIP;
    }

    public void setMac(String str) {
        this.mac = str;
    }

    public void setMid(String str) {
        this.mid = str;
    }

    public void setModuleIP(String str) {
        this.ModuleIP = str;
    }
}
