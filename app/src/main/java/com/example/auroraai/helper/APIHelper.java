package com.example.auroraai.helper;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class APIHelper {

    public static void putHTML(final String endpoint, final Context context, final JSONObject requestBody) {
        final String url = String.format("%s/%s", AppConfig.getURL(), endpoint);
        final RequestQueue queue = Volley.newRequestQueue(context);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> Log.d("TAG", response.toString()),
                error -> Log.e("TAG", error.getMessage(), error)) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", AppConfig.getToken());
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public static void postHTML(final String endpoint, final Context context, final JSONObject requestBody) {
        final String url = String.format("%s/%s", AppConfig.getURL(), endpoint);
        final RequestQueue queue = Volley.newRequestQueue(context);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, requestBody,
                response -> Log.d("TAG", response.toString()),
                error -> Log.e("TAG", error.getMessage(), error)) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", AppConfig.getToken());
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }

    public static void getHTML(final String endpoint, final Context context) {
        final String url = String.format("%s/%s", AppConfig.getURL(), endpoint);
        final RequestQueue queue = Volley.newRequestQueue(context);
        final JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> Log.d("TAG", response.toString()),
                error -> Log.e("TAG", error.getMessage(), error)) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Content-Type", "application/json");
                params.put("Authorization", AppConfig.getToken());
                return params;
            }
        };
        queue.add(jsonObjectRequest);
    }
}
