package com.gizwits.framework.webservice;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;

public abstract class GetPMService {
    private final String requestUrl = "http://data.gizwits.com/1/pm25?area=";

    public void GetWeather(String str) {
        AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
        asyncHttpClient.addHeader("X-XPG-Application-Id", "c79c8ef6002111e48a9b00163e0e2e0d");
        asyncHttpClient.addHeader("X-XPG-REST-API-Key", "c79cd5c8002111e48a9b00163e0e2e0d");
        asyncHttpClient.addHeader("Content-Type", "application/json");
        asyncHttpClient.get("http://data.gizwits.com/1/pm25?area=" + str, new JsonHttpResponseHandler() {
            public void onFailure(Throwable th, JSONObject jSONObject) {
                super.onFailure(th, jSONObject);
                GetPMService.this.onFailed();
            }

            public void onSuccess(JSONObject jSONObject) {
                GetPMService.this.onSuccess(jSONObject);
            }
        });
    }

    public abstract void onFailed();

    public abstract void onSuccess(JSONObject jSONObject);
}
