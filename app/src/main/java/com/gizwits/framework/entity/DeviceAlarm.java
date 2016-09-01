package com.gizwits.framework.entity;

import java.io.Serializable;

public class DeviceAlarm implements Serializable {
    private static final long serialVersionUID = 1;
    private String desc;
    private String time;

    public DeviceAlarm(String str, String str2) {
        this.time = str;
        this.desc = str2;
    }

    public String getDesc() {
        return this.desc;
    }

    public String getTime() {
        return this.time;
    }

    public void setDesc(String str) {
        this.desc = str;
    }

    public void setTime(String str) {
        this.time = str;
    }
}
