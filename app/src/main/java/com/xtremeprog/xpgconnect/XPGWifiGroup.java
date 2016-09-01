package com.xtremeprog.xpgconnect;

import android.util.Log;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class XPGWifiGroup {
    public String gid;
    private JSONObject groupJson;
    public String groupName;
    private XPGWifiGroupListener mListener;
    public String productKey;
    private String uid;

    protected XPGWifiGroup(JSONObject jSONObject) {
        this.groupJson = jSONObject;
        try {
            this.gid = this.groupJson.getString("gid");
            this.productKey = this.groupJson.getString("productKey");
            this.groupName = this.groupJson.getString("groupName");
            this.uid = this.groupJson.getString("uid");
            this.groupJson.remove("uid");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    protected static String getGroupConfigFilePath(String str) {
        if (XPGWifiSDK.mContext != null) {
            return XPGWifiSDK.mContext.getFilesDir() + "/XPGWifiSDK/GroupConfigInfo/" + str + "_group.json";
        }
        Log.e("XPGWifiSDK", "Please startWithAppID first!");
        return "";
    }

    protected static JSONArray getGroupConfigJson(String str) throws JSONException {
        JSONArray jSONArray = new JSONArray();
        String readFromFile = readFromFile(getGroupConfigFilePath(str));
        return readFromFile.length() > 0 ? new JSONArray(readFromFile) : jSONArray;
    }

    protected static String readFromFile(String str) {
        String str2;
        IOException iOException;
        String str3 = new String();
        File file = new File(str);
        if (file.exists()) {
            try {
                FileReader fileReader = new FileReader(file);
                char[] cArr = new char[((int) file.length())];
                fileReader.read(cArr);
                str2 = new String(cArr);
                try {
                    fileReader.close();
                } catch (IOException e) {
                    IOException iOException2 = e;
                    str3 = str2;
                    iOException = iOException2;
                    iOException.printStackTrace();
                    return str3;
                }
            } catch (IOException e2) {
                iOException = e2;
                iOException.printStackTrace();
                return str3;
            }
        }
        str2 = str3;
        return str2;
    }

    protected static JSONArray removeJSONArray(JSONArray jSONArray, int i) {
        JSONArray jSONArray2 = new JSONArray();
        int i2 = 0;
        while (i2 < jSONArray.length()) {
            try {
                if (i2 != i) {
                    jSONArray2.put(jSONArray.get(i2));
                }
                i2++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return jSONArray2;
    }

    private void syncGroupJsonToFile() throws JSONException {
        JSONArray groupConfigJson = getGroupConfigJson(this.uid);
        for (int i = 0; i < groupConfigJson.length(); i++) {
            if (groupConfigJson.getJSONObject(i).getString("gid").equals(this.gid)) {
                groupConfigJson.put(i, this.groupJson);
                writeToFile(groupConfigJson.toString(), getGroupConfigFilePath(this.uid));
                tiggerDevicesNotify();
                return;
            }
        }
    }

    private void tiggerDevicesNotify() {
        if (this.groupJson != null) {
            List arrayList = new ArrayList();
            try {
                JSONArray jSONArray = this.groupJson.getJSONArray("devices");
                for (int i = 0; i < jSONArray.length(); i++) {
                    ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
                    concurrentHashMap.put("did", jSONArray.getJSONObject(i).getString("did"));
                    concurrentHashMap.put("sdid", jSONArray.getJSONObject(i).getString("sdid"));
                    arrayList.add(concurrentHashMap);
                }
                if (this.mListener != null) {
                    this.mListener.didGetDevices(0, arrayList);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (this.mListener != null) {
            this.mListener.didGetDevices(-1, null);
        }
    }

    protected static String writeToFile(String str, String str2) {
        File file = new File(str2);
        File file2 = new File(str2.substring(0, str2.lastIndexOf(47)));
        if (!file2.exists()) {
            file2.mkdirs();
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(str);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void addDevice(String str, String str2) {
        if (this.groupJson != null) {
            try {
                Object jSONArray = new JSONArray();
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("did", str);
                jSONObject.put("sdid", str2);
                if (this.groupJson.has("devices")) {
                    jSONArray = this.groupJson.getJSONArray("devices");
                    int i = 0;
                    while (i < jSONArray.length()) {
                        if (jSONArray.getJSONObject(i).getString("sdid").equals(str2) && jSONArray.getJSONObject(i).getString("did").equals(str)) {
                            break;
                        }
                        i++;
                    }
                    if (i == jSONArray.length()) {
                        jSONArray.put(jSONObject);
                    }
                } else {
                    jSONArray.put(jSONObject);
                }
                this.groupJson.put("devices", jSONArray);
                syncGroupJsonToFile();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.mListener.didGetDevices(-1, null);
    }

    public void getDevices() {
        tiggerDevicesNotify();
    }

    public void removeDevice(String str, String str2) {
        if (this.groupJson != null) {
            try {
                if (this.groupJson.has("devices")) {
                    JSONArray jSONArray = this.groupJson.getJSONArray("devices");
                    int i = 0;
                    while (i < jSONArray.length()) {
                        if (jSONArray.getJSONObject(i).getString("sdid").equals(str2) && jSONArray.getJSONObject(i).getString("did").equals(str)) {
                            this.groupJson.put("devices", removeJSONArray(jSONArray, i));
                            break;
                        }
                        i++;
                    }
                }
                syncGroupJsonToFile();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.mListener.didGetDevices(-1, null);
    }

    public void setListener(XPGWifiGroupListener xPGWifiGroupListener) {
        this.mListener = xPGWifiGroupListener;
    }
}
