package com.mobile.auroraai.api;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonElement;
import com.mobile.auroraai.core.AppConfig;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIHelper {

    public static void callAPI(
            final String endpoint,
            final int method,
            final Context context,
            final JSONObject requestBody,
            final ServerCallback serverCallback) {
        processAPIRequest(endpoint, method, context, requestBody, serverCallback);
    }

    private static void processAPIRequest(
            final String endpoint,
            final int method,
            final Context context,
            final JSONObject requestBody,
            final ServerCallback serverCallback) {
        final String url = String.format("%s/%s", AppConfig.getURL(), endpoint);
        final RequestQueue queue = Volley.newRequestQueue(context);

        final JsonRequest<JsonElement> jsonRequest = new CustomJsonRequest(method, url, requestBody,
                serverCallback::onSuccess,
                serverCallback::onError) {
            @Override
            public Map<String, String> getHeaders() {
                return APIHelper.getParams();
            }
        };
        queue.add(jsonRequest);
    }

    private static Map<String, String> getParams() {
        final Map<String, String> params = new HashMap<>();
        params.put("Content-Type", "application/json");
        params.put("Authorization", AppConfig.getToken());
        return params;
    }
}
