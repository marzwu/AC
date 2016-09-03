package com.gizwits.framework.webservice;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

public abstract class LocationService {
    private final String requestUrl = "http://api.map.baidu.com/location/ip?ak=F1e62d8e200e8aebda7c57a1633ae453";

    public abstract void onFailed();

    public abstract void onSuccess(JSONObject jSONObject);

    public void startLocation() {
        new AsyncHttpClient().get("http://api.map.baidu.com/location/ip?ak=F1e62d8e200e8aebda7c57a1633ae453", new JsonHttpResponseHandler() {
            public void onFailure(Throwable th, JSONObject jSONObject) {
                super.onFailure(th, jSONObject);
                LocationService.this.onFailed();
            }

            public void onSuccess(JSONObject jSONObject) {
                LocationService.this.onSuccess(jSONObject);
            }
        });
    }
}
