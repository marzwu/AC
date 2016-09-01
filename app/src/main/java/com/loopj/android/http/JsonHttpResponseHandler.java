package com.loopj.android.http;

import android.os.Message;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JsonHttpResponseHandler extends AsyncHttpResponseHandler {
    protected static final int SUCCESS_JSON_MESSAGE = 100;

    protected void handleFailureMessage(Throwable th, String str) {
        if (str != null) {
            try {
                Object parseResponse = parseResponse(str);
                if (parseResponse instanceof JSONObject) {
                    onFailure(th, (JSONObject) parseResponse);
                    return;
                } else if (parseResponse instanceof JSONArray) {
                    onFailure(th, (JSONArray) parseResponse);
                    return;
                } else {
                    onFailure(th, str);
                    return;
                }
            } catch (JSONException e) {
                onFailure(th, str);
                return;
            }
        }
        onFailure(th, "");
    }

    protected void handleMessage(Message message) {
        switch (message.what) {
            case SUCCESS_JSON_MESSAGE /*100*/:
                Object[] objArr = (Object[]) message.obj;
                handleSuccessJsonMessage(((Integer) objArr[0]).intValue(), objArr[1]);
                return;
            default:
                super.handleMessage(message);
                return;
        }
    }

    protected void handleSuccessJsonMessage(int i, Object obj) {
        if (obj instanceof JSONObject) {
            onSuccess(i, (JSONObject) obj);
        } else if (obj instanceof JSONArray) {
            onSuccess(i, (JSONArray) obj);
        } else {
            onFailure(new JSONException("Unexpected type " + obj.getClass().getName()), (JSONObject) null);
        }
    }

    public void onFailure(Throwable th, JSONArray jSONArray) {
    }

    public void onFailure(Throwable th, JSONObject jSONObject) {
    }

    public void onSuccess(int i, JSONArray jSONArray) {
        onSuccess(jSONArray);
    }

    public void onSuccess(int i, JSONObject jSONObject) {
        onSuccess(jSONObject);
    }

    public void onSuccess(JSONArray jSONArray) {
    }

    public void onSuccess(JSONObject jSONObject) {
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected java.lang.Object parseResponse(java.lang.String r4) throws org.json.JSONException {
        /*
        r3 = this;
        r0 = r4.trim();
        r1 = "{";
        r1 = r0.startsWith(r1);
        if (r1 != 0) goto L_0x0015;
    L_0x000c:
        r1 = "[";
        r2 = r0.startsWith(r1);
        r1 = 0;
        if (r2 == 0) goto L_0x001e;
    L_0x0015:
        r1 = new org.json.JSONTokener;
        r1.<init>(r0);
        r1 = r1.nextValue();
    L_0x001e:
        if (r1 != 0) goto L_0x0021;
    L_0x0020:
        return r0;
    L_0x0021:
        r0 = r1;
        goto L_0x0020;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.loopj.android.http.JsonHttpResponseHandler.parseResponse(java.lang.String):java.lang.Object");
    }

    protected void sendSuccessMessage(int i, String str) {
        if (i != 204) {
            try {
                Object parseResponse = parseResponse(str);
                sendMessage(obtainMessage(SUCCESS_JSON_MESSAGE, new Object[]{Integer.valueOf(i), parseResponse}));
                return;
            } catch (Throwable e) {
                sendFailureMessage(e, str);
                return;
            }
        }
        sendMessage(obtainMessage(SUCCESS_JSON_MESSAGE, new Object[]{Integer.valueOf(i), new JSONObject()}));
    }
}
