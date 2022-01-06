package com.mobile.auroraai.service.api;

import android.content.Context;

import com.android.volley.Request;
import com.mobile.auroraai.api.APIHelper;
import com.mobile.auroraai.api.ServerCallback;
import com.mobile.auroraai.core.LogAble;

import org.json.JSONObject;

public abstract class BaseAPIService extends LogAble {

    protected abstract String getBaseEndpoint();

    public void create(final Context context, final JSONObject jsonObject, final ServerCallback serverCallback) {
        this.trigger("", Request.Method.POST, context, jsonObject, serverCallback);
    }

    protected void trigger(
            final String endpoint,
            final int method,
            final Context context,
            final JSONObject requestBody,
            final ServerCallback serverCallback) {
        APIHelper.callAPI(String.format("%s/%s", getBaseEndpoint(), endpoint), method, context, requestBody, serverCallback);
    }
}
