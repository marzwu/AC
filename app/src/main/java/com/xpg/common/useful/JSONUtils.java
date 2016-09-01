package com.xpg.common.useful;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtils {
    public static String ParseJSON(String str, String str2) {
        String str3 = null;
        if (!(str.isEmpty() || str2.isEmpty())) {
            try {
                JSONObject jSONObject = new JSONObject(str);
                if (jSONObject.has(str2)) {
                    str3 = jSONObject.getString(str2);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return str3;
    }
}
