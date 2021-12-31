package com.example.auroraai.api;

import android.content.Context;

import com.example.auroraai.helper.APIHelper;

import org.json.JSONObject;

public abstract class BaseService {

    protected abstract String getBaseEndpoint();

    public void create(final Context context, final JSONObject jsonObject) {
        APIHelper.postHTML(getBaseEndpoint(), context, jsonObject);
    }
}
